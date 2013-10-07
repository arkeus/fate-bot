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

	/**
	 * Initializes a command with all the information regarding who triggered the command.
	 *
	 * @param bot The bot instance.
	 * @param channel The channel the command was used in.
	 * @param sender The user who used the command.
	 * @param login The login of the user who used the command.
	 * @param hostname The hostname of the user who used the command.
	 * @param message The entire command message.
	 * @throws CommandException
	 */
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
