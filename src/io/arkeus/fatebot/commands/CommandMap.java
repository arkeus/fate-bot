package io.arkeus.fatebot.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * A container that maps command aliases to their corresopnding commands.
 */
public class CommandMap {
	private final Map<String, Class<?>> commands;

	public CommandMap() {
		commands = new HashMap<>();
	}

	/**
	 * Registers a command to any number of aliases. An entry is inserted for each alias.
	 *
	 * @param klass The command to register.
	 * @param aliases Any number of aliases to map to the given command.
	 */
	public void registerCommand(final Class<?> klass, final String... aliases) {
		for (final String alias : aliases) {
			commands.put(alias, klass);
		}
	}

	public Command get(final String alias) throws InvalidCommandException {
		final Class<?> klass = commands.get(alias);
		if (klass == null) {
			throw new InvalidCommandException("Could not find command for alias '" + alias + "'");
		}

		try {
			return (Command) klass.newInstance();
		} catch (final InstantiationException | IllegalAccessException e) {
			throw new InvalidCommandException("Could not instantiate commmand for alias '" + alias + "'");
		}
	}
}
