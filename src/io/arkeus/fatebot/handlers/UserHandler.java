package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;

public class UserHandler extends MessageHandler {
	public UserHandler(final Fate bot) {
		super(bot);
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		bot.getUsers().getOrCreateFateUser(sender).addMessage(message);
	}
}