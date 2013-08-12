package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.user.FateUser;

public class UserCommand extends Command {
	public UserCommand() {
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

		bot.sendMessage(channel, String.format("[%s] Messages: %d, Effective Messages: %d, Last Seen: %d, Last Heard: %d, Idle Ticks: %d", user.getNick(), user.getMessages(), user.getEffectiveMessages(), user.getLastIdleTime(), user.getLastMessageTime(), user.getIdleTicks()));
	}
}
