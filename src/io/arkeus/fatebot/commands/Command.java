package io.arkeus.fatebot.commands;

import io.arkeus.fatebot.Fate;

/**
 * A class containing information about a single bot command.
 */
public abstract class Command {
	protected final int expectedParameters;

	protected Fate bot;
	protected String channel;
	protected String sender;
	protected String login;
	protected String hostname;
	protected CommandArguments arguments;

	public Command(final int expectedParameters) {
		this.expectedParameters = expectedParameters;
	}

	public void initialize(final Fate bot, final String channel, final String sender, final String login, final String hostname, final String message) throws CommandException {
		this.bot = bot;
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.arguments = new CommandArguments(message, expectedParameters);
	}

	public void execute() {
		try {
			validate();
			run();
		} catch(final CommandException e) {
			bot.sendMessage(channel, "Error: " + e.getMessage());
			Fate.logger.error("Recieved invalid command or invalid arguments, ignoring", e);
		}
	}

	protected void validate() throws CommandException {
		// todo?
	}

	protected abstract void run() throws CommandException;
}
