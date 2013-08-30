package io.arkeus.fatebot.commands.impl;

import org.jibble.pircbot.Colors;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.user.FateUser;
import io.arkeus.fatebot.util.MessageBuilder;
import io.arkeus.fatebot.util.TimeUtils;

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

		mb.clear();
		mb.appendBrackets("Seen", TimeUtils.getDurationFromMillis(System.currentTimeMillis() - user.getLastMessageTime()), Colors.GREEN).append(" ");
		mb.appendBrackets("Saying", user.getLastMessage(), Colors.OLIVE);

		bot.sendMessage(channel, mb.toString());
	}
}
