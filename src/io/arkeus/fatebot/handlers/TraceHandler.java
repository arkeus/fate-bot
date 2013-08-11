package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;

public class TraceHandler extends MessageHandler {
	public TraceHandler(final Fate bot) {
		super(bot);
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		Fate.logger.info(String.format("Message received channel=%s, sender=%s, login=%s, hostname=%s, message=%s", channel, sender, login, hostname, message));
	}
}