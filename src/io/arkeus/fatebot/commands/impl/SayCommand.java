package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;

public class SayCommand extends Command {
	public SayCommand() {
		super(1);
	}

	@Override
	protected void run() throws CommandException {
		bot.sendMessage(channel, arguments.getMessage());
	}
}
