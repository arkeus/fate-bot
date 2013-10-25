package io.arkeus.fatebot.commands;

/**
 * Indicates a user does not have permissions to run a command.
 */
public class InvalidPermissionsException extends Exception {
	private static final long serialVersionUID = 8910987880085168247L;

	public InvalidPermissionsException() {
		super();
	}

	public InvalidPermissionsException(final String message) {
		super(message);
	}

	public InvalidPermissionsException(final String message, final Throwable source) {
		super(message, source);
	}

	public InvalidPermissionsException(final Throwable source) {
		super(source);
	}
}
