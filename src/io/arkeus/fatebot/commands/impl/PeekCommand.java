package io.arkeus.fatebot.commands.impl;

import org.jibble.pircbot.Colors;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.handlers.TrapHandler;
import io.arkeus.fatebot.util.MessageBuilder;

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
		final MessageBuilder mb = new MessageBuilder();
		mb.appendBrackets("Trap", trap, Colors.BLUE);
		mb.appendBrackets("Trapper", trapper, Colors.BROWN);
		bot.sendNotice(sender, mb.toString());
	}
}