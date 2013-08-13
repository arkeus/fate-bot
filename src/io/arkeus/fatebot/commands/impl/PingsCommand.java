package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.handlers.PingHandler;
import io.arkeus.fatebot.user.UserManager;

public class PingsCommand extends Command {
	public PingsCommand() {
		super(1);
	}

	@Override
	protected void run() throws CommandException {
		final String message = arguments.getMessage();
		final String normalizedNick = UserManager.normalizeNick(sender);
		if (!message.equalsIgnoreCase("on") && !message.equalsIgnoreCase("off")) {
			bot.sendMessage(channel, "Invalid option. Valid options are 'on' and 'off'.");
			return;
		}

		final boolean enable = message.equalsIgnoreCase("on");
		final PingHandler handler = (PingHandler) bot.getHandler("ping");
		final boolean changed = handler.setPingableStatus(normalizedNick, enable);

		if (enable) {
			if (changed) {
				bot.sendNotice(sender, "You will now receive missed pings.");
			} else {
				bot.sendNotice(sender, "You are already receiving missed pings.");
			}
		} else {
			if (changed) {
				bot.sendNotice(sender, "You will no longer receive missed pings.");
			} else {
				bot.sendNotice(sender, "You are already not receiving missed pings.");
			}
		}
	}
}
