package io.arkeus.fatebot.commands;

/**
 * An exception relating to a single bot command.
 */
public class CommandException extends Exception {
	private static final long serialVersionUID = -4706634159337945399L;

	public CommandException() {
		super();
	}

	public CommandException(final String message) {
		super(message);
	}

	public CommandException(final String message, final Throwable source) {
		super(message, source);
	}

	public CommandException(final Throwable source) {
		super(source);
	}
}
