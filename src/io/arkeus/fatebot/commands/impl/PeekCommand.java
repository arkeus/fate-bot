package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;

public class PeekCommand extends Command {
	public PeekCommand() {
		super(0);
	}

	@Override
	protected void run() throws CommandException {
		if (!bot.isAdministrator(sender)) {
			return;
		}
		Fate.logger.info("User " + sender + " is peeking at the trap card");
		return;
	}
}