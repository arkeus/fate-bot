package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandArguments;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.commands.CommandMap;
import io.arkeus.fatebot.commands.InvalidCommandException;
import io.arkeus.fatebot.commands.impl.LyricsCommand;
import io.arkeus.fatebot.commands.impl.PeekCommand;
import io.arkeus.fatebot.commands.impl.PingsCommand;
import io.arkeus.fatebot.commands.impl.SeenCommand;
import io.arkeus.fatebot.commands.impl.TrapCommand;
import io.arkeus.fatebot.commands.impl.UserCommand;

public class CommandHandler extends MessageHandler {
	private final CommandMap commandMap;

	public CommandHandler(final Fate bot) {
		super(bot);

		commandMap = new CommandMap();
		//commandMap.registerCommand(SayCommand.class, "say", "s");
		commandMap.registerCommand(LyricsCommand.class, "lyrics", "l");
		commandMap.registerCommand(TrapCommand.class, "trap", "t");
		commandMap.registerCommand(UserCommand.class, "user", "u");
		commandMap.registerCommand(PingsCommand.class, "pings", "p");
		commandMap.registerCommand(PeekCommand.class, "peek");
		commandMap.registerCommand(SeenCommand.class, "seen", "s");
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		if (!message.startsWith(config.getPrefix())) {
			return;
		}

		final String alias = CommandArguments.getAlias(message);

		final Command command;
		try {
			command = commandMap.get(alias);
		} catch (final InvalidCommandException e) {
			Fate.logger.error("Invalid command: " + message, e);
			return;
		}

		try {
			command.initialize(bot, channel, sender, login, hostname, message);
			command.execute();
		} catch (final CommandException e) {
			Fate.logger.error("Failed to execute command: " + message, e);
		}
	}
}
