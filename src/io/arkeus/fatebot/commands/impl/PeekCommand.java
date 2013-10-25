package io.arkeus.fatebot.commands.impl;

import org.jibble.pircbot.Colors;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.handlers.TrapHandler;
import io.arkeus.fatebot.util.MessageBuilder;
import io.arkeus.fatebot.util.TimeUtils;

/**
 * A command to peek at the current trap card, if any.
 */
public class PeekCommand extends Command {
	private static final MessageBuilder mb = new MessageBuilder();

	public PeekCommand() {
		super(0);
	}

	@Override
	protected void run() throws CommandException {
		final TrapHandler handler = (TrapHandler) bot.getHandler("trap");
		final String trap = handler.getTrap();
		if (trap == null) {
			bot.sendNotice(sender, "No trap card is set.");
			return;
		}

		final String trapper = handler.getTrapper();
		mb.clear();
		mb.appendBrackets("Trap", trap, Colors.BLUE).append(" ");
		mb.appendBrackets("Trapper", trapper, Colors.BROWN).append(" ");
		mb.appendBrackets("Time", TimeUtils.getDurationFromMillis(handler.getTrapTime()) + " ago", Colors.DARK_BLUE);
		bot.sendNotice(sender, mb.toString());
	}
}