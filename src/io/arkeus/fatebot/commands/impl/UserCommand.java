package io.arkeus.fatebot.commands.impl;

import org.jibble.pircbot.Colors;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.user.FateUser;
import io.arkeus.fatebot.util.MessageBuilder;
import io.arkeus.fatebot.util.TimeUtils;

public class UserCommand extends Command {
	private static final MessageBuilder mb = new MessageBuilder();

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

		final int level = user.getLevel();
		final int experience = user.getExperience();
		final int currentMaxExperience = user.getExperienceRequiredForLevel(level + 1);
		final int previousMaxExperience = user.getExperienceRequiredForLevel(level);

		final int progress = experience - previousMaxExperience;
		final int progressMax = currentMaxExperience - previousMaxExperience;
		final int percent = (int) Math.floor(((float) progress / progressMax) * 100);

		mb.clear();
		mb.appendBrackets(user.getNick(), Colors.RED).append(" ");
		mb.appendBrackets("Level", Integer.toString(user.getLevel()), Colors.GREEN).append(" ");
		mb.appendBrackets("XP", progress + "/" + progressMax + " (" + percent + "%)", Colors.TEAL).append(" ");
		mb.appendBrackets("Seen", TimeUtils.getDurationFromMillis(System.currentTimeMillis() - user.getLastMessageTime()), Colors.BLUE);

		bot.sendMessage(channel, mb.toString());
	}
}
