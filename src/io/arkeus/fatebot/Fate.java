package io.arkeus.fatebot;

import io.arkeus.fatebot.config.Config;
import io.arkeus.fatebot.handlers.CommandHandler;
import io.arkeus.fatebot.handlers.MessageHandler;
import io.arkeus.fatebot.handlers.TraceHandler;
import io.arkeus.fatebot.handlers.TrapHandler;

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

	public Fate(final Config config) {
		this.config = config;
		this.handlers = new LinkedHashMap<>();
	}

	private void initializeHandlers() {
		Fate.logger.info("Initializing handlers");
		handlers.put("trace", new TraceHandler(this));
		handlers.put("trap", new TrapHandler(this));
		handlers.put("command", new CommandHandler(this));
		Fate.logger.info("Handlers initialized");
	}

	@Override
	protected void onMessage(final String channel, final String sender, final String login, final String hostname, final String message) {
		// Don't handle self messages for now
		if (sender == getNick()) {
			return;
		}

		for (final Map.Entry<String, MessageHandler> handler : handlers.entrySet()) {
			handler.getValue().handle(channel, sender, login, hostname, message);
		}
	}

	@Override
	protected void onKick(final String channel, final String kickerNick, final String kickerLogin, final String kickerHostname, final String recipientNick, final String reason) {
		System.out.println(recipientNick + " -- " + getNick());
		if (recipientNick == getNick()) {
			joinChannel(channel);
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

	public void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		initializeHandlers();

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
}
