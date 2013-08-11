package io.arkeus.fatebot.commands;

public class InvalidCommandException extends Exception {
	private static final long serialVersionUID = -4656333502349209091L;

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(final String message) {
		super(message);
	}
}
