package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.user.FateUser;
import io.arkeus.fatebot.util.MessageBuilder;

public class SeenCommand extends Command {
	private static final MessageBuilder mb = new MessageBuilder();

	public SeenCommand() {
		super(1);
	}

	@Override
	protected void run() throws CommandException {
		final String nick = arguments.getMessage();
		final FateUser user = bot.getFateUser(nick);

		if (user == null) {
			bot.sendMessage(channel, "Unknown user");
			return;
		}

		bot.sendMessage(channel, "test");
	}
}
