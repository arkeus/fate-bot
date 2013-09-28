package io.arkeus.fatebot;

import io.arkeus.fatebot.config.Config;
import io.arkeus.fatebot.executors.ChronoThread;
import io.arkeus.fatebot.handlers.CommandHandler;
import io.arkeus.fatebot.handlers.DebugHandler;
import io.arkeus.fatebot.handlers.MessageHandler;
import io.arkeus.fatebot.handlers.PingHandler;
import io.arkeus.fatebot.handlers.TraceHandler;
import io.arkeus.fatebot.handlers.TrapHandler;
import io.arkeus.fatebot.handlers.UserHandler;
import io.arkeus.fatebot.user.FateUser;
import io.arkeus.fatebot.user.UserManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

/**
 * A generic irc bot developed for #lightninghoof. Contains commands to waste everyone's time, as a
 * good bot should do.
 */
public class Fate extends PircBot {
	public static final Logger logger = LogManager.getLogger(Fate.class.getName());

	private final Config config;
	private final Map<String, MessageHandler> handlers;
	private final UserManager users;

	public Fate(final Config config) {
		this.config = config;
		this.handlers = new LinkedHashMap<>();
		this.users = new UserManager();
	}

	/**
	 * Create a new handler of each type and add it to the handler map.
	 * To obtain a specific handler, use {@link #getHandler(String)}.
	 */
	private void initializeHandlers() {
		Fate.logger.info("Initializing handlers");
		handlers.put("trace", new TraceHandler(this));
		handlers.put("trap", new TrapHandler(this));
		handlers.put("user", new UserHandler(this));
		handlers.put("command", new CommandHandler(this));
		handlers.put("ping", new PingHandler(this));
		handlers.put("debug", new DebugHandler(this));
		Fate.logger.info("Handlers initialized");
	}

	/**
	 * Initializes any separate threads to run in the background.
	 */
	private void initializeThreads() {
		(new ChronoThread(this)).start();
	}

	// TODO: Move to user class?
	private void initializeAdmins() {
		final Set<String> administrators = config.getAdministrators();
		for (final String administrator : administrators) {
			// 0. Setup useful variables
			final String normalizedNick = UserManager.normalizeNick(administrator);
			@SuppressWarnings("unused")
			final FateUser fateUser = users.getOrCreateFateUser(normalizedNick);
			// 1. Enable pings for admins by default
			final PingHandler handler = (PingHandler) getHandler("ping");
			handler.setPingableStatus(normalizedNick, true);
		}
	}

	@Override
	protected void onUserList(final String channel, final User[] users) {
		this.users.updateActiveUsers(users);
	}

	@Override
	protected void onMessage(final String channel, final String sender, final String login, final String hostname, final String message) {
		// Don't handle self messages for now
		if (sender.equals(getNick())) {
			return;
		}

		for (final Map.Entry<String, MessageHandler> handler : handlers.entrySet()) {
			handler.getValue().handle(channel, sender, login, hostname, message);
		}
	}

	@Override
	protected void onKick(final String channel, final String kickerNick, final String kickerLogin, final String kickerHostname, final String recipientNick, final String reason) {
		if (recipientNick.equals(getNick())) {
			joinChannel(channel);
		}
	}

	@Override
	protected void onNotice(final String sourceNick, final String sourceLogin, final String sourceHostname, final String target, final String notice) {
		if (config.getPassword() == null || config.getPassword().isEmpty() || notice.indexOf("This nickname is registered") == -1) {
			return;
		}

		identify(config.getPassword());
	}

	@Override
	protected void onDisconnect() {
		Fate.logger.warn("Fate has been disconnected.");

		while (!isConnected()) {
			try {
				Fate.logger.info("Attempting to reconnect...");
				reconnect();
			} catch (final Exception e) {
				// ignore
			} finally {
				try {
					Thread.sleep(25000);
				} catch (final InterruptedException ignore) {}
			}
		}
	}

	/**
	 * A single time based tick. Handle all periodic updates here.
	 */
	public void chronoTick() {
		if (!isConnected() || !inChannel()) {
			Fate.logger.info("Not in channel, skipping chrono tick");
			return;
		}

		Fate.logger.info("Chrono Tick");
		users.updateActiveUsers(getUsers(config.getChannel()));

		if (getNick() != config.getNick()) {
			setName(config.getNick());
		}
	}

	/**
	 * Handles all one time initialization of the bot. This binds the
	 * handlers, sets up the threads and admins, sets various parameters
	 * based on the configuration, and connects to the server.
	 */
	public void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		initializeHandlers();
		initializeThreads();
		initializeAdmins();

		setLogin(config.getLogin());
		setVerbose(config.getVerbose());
		setName(config.getNick());
		setVersion(config.getLogin());

		try {
			connect(config.getServer());
		} catch (final NickAlreadyInUseException e) {
			if (config.getAltNick() != null && !config.getAltNick().isEmpty()) {
				setName(config.getAltNick());
				connect(config.getServer());
			} else {
				throw e;
			}
		}

		joinChannel(config.getChannel());
	}

	/**
	 * Returns whether or not the bot is currently in the configured channel.
	 *
	 * @return True if the bot is in the configured channel, false otherwise.
	 */
	public boolean inChannel() {
		for (final String channel : getChannels()) {
			Fate.logger.info("k " + channel + " == " + config.getChannel());
			if (channel.equalsIgnoreCase(config.getChannel())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the configuration the bot was loaded with.
	 *
	 * @return The configuration.
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * Given a handler name, returns the handler that was registered with that name.
	 *
	 * @param name The name of the handler.
	 * @return The handler.
	 */
	public MessageHandler getHandler(final String name) {
		return handlers.get(name);
	}

	/**
	 * Given a nickname, returns the user in the channel with that nickname, or null if that
	 * user is not in the channel.
	 *
	 * @param nick The nickname of the user to grab.
	 * @return The user, in the form of a PircBot user.
	 */
	public User getUser(final String nick) {
		final User[] users = getUsers(config.getChannel());
		for (final User user : users) {
			if (user.getNick().endsWith(nick)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Given a nickname, returns the fate user with that nick (even if they are no longer in
	 * the channel.
	 *
	 * @param nick The nickname of the user to grab.
	 * @return The user, in the form of a FateUser.
	 */
	public FateUser getFateUser(final String nick) {
		return users.getFateUser(nick);
	}

	/**
	 * Returns whether the passed nick is a recognized fate user yet.
	 *
	 * @param nick The nickname of the user to check.
	 * @return True if the user is a recognized fate user, false otherwise.
	 */
	public boolean hasFateUser(final String nick) {
		return users.hasFateUser(nick);
	}

	/**
	 * Returns the user manager for the bot.
	 *
	 * @return The user manager.
	 */
	public UserManager getUsers() {
		return users;
	}

	public boolean isAdministrator(final String nick) {
		return config.getAdministrators().contains(UserManager.normalizeNick(nick));
	}
}
