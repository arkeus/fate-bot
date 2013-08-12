package io.arkeus.fatebot;

import io.arkeus.fatebot.config.Config;
import io.arkeus.fatebot.executors.ChronoThread;
import io.arkeus.fatebot.handlers.CommandHandler;
import io.arkeus.fatebot.handlers.MessageHandler;
import io.arkeus.fatebot.handlers.TraceHandler;
import io.arkeus.fatebot.handlers.TrapHandler;
import io.arkeus.fatebot.handlers.UserHandler;
import io.arkeus.fatebot.user.FateUser;
import io.arkeus.fatebot.user.UserManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class Fate extends PircBot {
	public static final Logger logger = LogManager.getLogger(Fate.class.getName());

	private final Config config;
	private final Map<String, MessageHandler> handlers;
	private final UserManager users;
	private boolean inChannel;

	public Fate(final Config config) {
		this.config = config;
		this.handlers = new LinkedHashMap<>();
		this.users = new UserManager();
		this.inChannel = false;
	}

	private void initializeHandlers() {
		Fate.logger.info("Initializing handlers");
		handlers.put("trace", new TraceHandler(this));
		handlers.put("trap", new TrapHandler(this));
		handlers.put("command", new CommandHandler(this));
		handlers.put("user", new UserHandler(this));
		Fate.logger.info("Handlers initialized");
	}

	private void initializeThreads() {
		(new ChronoThread(this)).start();
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
	protected void onJoin(final String channel, final String sender, final String login, final String hostname) {
		if (channel.equals(config.getChannel()) && sender.equals(getNick())) {
			inChannel = true;
		}
	}

	@Override
	protected void onPart(final String channel, final String sender, final String login, final String hostname) {
		if (channel.equals(config.getChannel()) && sender.equals(getNick())) {
			inChannel = false;
		}
	}

	@Override
	protected void onQuit(final String sourceNick, final String sourceLogin, final String sourceHostname, final String reason) {
		if (sourceNick.equals(getNick())) {
			inChannel = false;
		}
	}

	@Override
	protected void onDisconnect() {
		Fate.logger.warn("Fate has been disconnected.");

		while (!isConnected()) {
			try {
				Fate.logger.info("Attempting to reconnect...");
				reconnect();
			} catch (final Exception e) {
				try {
					Thread.sleep(5000);
				} catch (final InterruptedException ignore) {}
			}
		}
	}

	public void chronoTick() {
		if (!isConnected() || !inChannel) {
			Fate.logger.info("Not in channel, skipping chrono tick");
			return;
		}
		Fate.logger.info("Chrono Tick");
		users.updateActiveUsers(getUsers(config.getChannel()));
	}

	public void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		initializeHandlers();
		initializeThreads();

		setLogin(config.getLogin());
		setVerbose(config.getVerbose());
		setName(config.getNick());
		setVersion(config.getLogin());

		try {
			connect(config.getServer());
		} catch (final NickAlreadyInUseException e) {
			setName(config.getAltNick());
			connect(config.getServer());
		}

		if (config.getPassword() != null && !config.getPassword().isEmpty()) {
			identify(config.getPassword());
		}

		joinChannel(config.getChannel());
	}

	public Config getConfig() {
		return config;
	}

	public MessageHandler getHandler(final String name) {
		return handlers.get(name);
	}

	public User getUser(final String nick) {
		final User[] users = getUsers(config.getChannel());
		for (final User user : users) {
			if (user.getNick().endsWith(nick)) {
				return user;
			}
		}
		return null;
	}

	public FateUser getFateUser(final String nick) {
		return users.getFateUser(nick);
	}

	public UserManager getUsers() {
		return users;
	}
}
