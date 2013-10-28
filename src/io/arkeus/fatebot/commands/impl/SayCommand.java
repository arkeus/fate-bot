package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;

/**
 * A command that causes the bot to relay a message to the channel.
 */
public class SayCommand extends Command {
	public SayCommand() {
		super(1, true);
	}

	@Override
	protected void run() throws CommandException {
		bot.sendMessage(channel, arguments.getMessage());
	}
}
