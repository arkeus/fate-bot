package io.arkeus.fatebot.util;

import org.jibble.pircbot.Colors;

/**
 * A builder to create messages with formatting applied.
 */
public class MessageBuilder {
	private final StringBuilder builder;

	public MessageBuilder() {
		this.builder = new StringBuilder();
	}

	/**
	 * Appends a bracketed value using the given color.
	 *
	 * @param value The value to show.
	 * @param color The color of the brackets.
	 * @return The message builder.
	 */
	public MessageBuilder appendBrackets(final String value, final String color) {
		builder.append(Colors.NORMAL)	.append(Colors.BOLD).append(color).append("[");
		builder.append(Colors.NORMAL).append(value).append(Colors.NORMAL);
		builder.append(Colors.BOLD).append(color).append("]");
		return this;
	}

	/**
	 * Appends a bracketed key and value using the given color.
	 *
	 * @param key The key to show.
	 * @param value The value to show.
	 * @param color The color of the brackets.
	 * @return The message builder.
	 */
	public MessageBuilder appendBrackets(final String key, final String value, final String color) {
		builder.append(Colors.NORMAL).append(color).append(Colors.BOLD).append("[");
		builder.append(Colors.NORMAL).append(Colors.BOLD).append(key).append(Colors.NORMAL).append(" ").append(value).append(Colors.NORMAL);
		builder.append(Colors.BOLD).append(color).append("]");
		return this;
	}

	/**
	 * Appends another message builder to this message builder.
	 *
	 * @param mb The message builder to append.
	 * @return The message builder.
	 */
	public MessageBuilder append(final MessageBuilder mb) {
		builder.append(mb.toString());
		return this;
	}

	/**
	 * Appends a basic string to this message builder.
	 *
	 * @param str The string to append.
	 * @return The message builder.
	 */
	public MessageBuilder append(final String str) {
		builder.append(str);
		return this;
	}

	/**
	 * Clears the content of this message builder.
	 *
	 * @return The message builder.
	 */
	public MessageBuilder clear() {
		builder.setLength(0);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
