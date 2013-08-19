package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.handlers.TrapHandler;

public class PeekCommand extends Command {
	public PeekCommand() {
		super(0);
	}

	@Override
	protected void run() throws CommandException {
		if (!bot.isAdministrator(sender)) {
			return;
		}

		final TrapHandler handler = (TrapHandler) bot.getHandler("trap");
		final String trap = handler.getTrap();
		if (trap == null) {
			bot.sendNotice(sender, "No trap card is set.");
			return;
		}

		final String trapper = handler.getTrapper();
		bot.sendNotice(sender, "You peek at the trap card and find a [Shadow Word: " + trap + "], set by " + trapper);
	}
}