package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.handlers.TrapHandler;

public class TrapCommand extends Command {
	public TrapCommand() {
		super(1);
	}

	@Override
	protected void run() throws CommandException {
		final TrapHandler handler = (TrapHandler) bot.getHandler("trap");

		if (handler.hasTrapSet()) {
			bot.sendMessage(channel, "A trap card is already face down.");
			return;
		}

		final String trap = arguments.getMessage();
		if (trap.matches(".*\\s.*")) {
			bot.sendMessage(channel, "You can only trap a single word");
			return;
		} else if (!trap.matches("\\w{1,20}")) {
			bot.sendMessage(channel, "Invalid trap");
			return;
		}

		handler.setTrap(trap, sender);
		bot.sendMessage(channel, "You have placed a trap card face down: [Shadow Word: " + trap + "]");
	}
}
