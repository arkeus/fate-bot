package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.config.Config;

public abstract class MessageHandler {
	protected final Fate bot;
	protected final Config config;

	public MessageHandler(final Fate bot) {
		this.bot = bot;
		this.config = bot.getConfig();
	}

	public abstract void handle(final String channel, final String sender, final String login, final String hostname, final String message);
}
