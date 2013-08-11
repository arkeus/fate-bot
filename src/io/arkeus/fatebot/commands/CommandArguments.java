package io.arkeus.fatebot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandArguments {
	private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");
	private final String alias;
	private final List<String> parameters;

	public CommandArguments(final String message, final int expectedParameters) throws CommandException {
		final String[] aliasSplit = SPLIT_PATTERN.split(message, 2);
		this.alias = aliasSplit[0];

		if (aliasSplit.length < 2 && expectedParameters > 0) {
			throw new CommandException("Expected parameters, found none: " + message);
		}

		parameters = new ArrayList<>();
		if (expectedParameters > 0) {
			final String[] parameterSplit = SPLIT_PATTERN.split(aliasSplit[1], expectedParameters);

			if (parameterSplit.length < expectedParameters) {
				throw new CommandException("Invalid parameter count, expected " + expectedParameters + ", found " + parameterSplit.length + ": " + message);
			}

			for (final String parameter : parameterSplit) {
				parameters.add(parameter);
			}
		}
	}

	public String getAlias() {
		return alias;
	}

	public String getParameter(final int index) {
		return parameters.get(index);
	}

	public int numParameters() {
		return parameters.size();
	}

	public String getMessage() {
		return parameters.get(parameters.size() - 1);
	}

	public static String getAlias(final String message) {
		return SPLIT_PATTERN.split(message, 2)[0].substring(1);
	}
}
