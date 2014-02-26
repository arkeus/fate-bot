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

	public MessageBuilder appendBrackets(final String value, final String color) {
		builder.append(Colors.NORMAL)	.append(Colors.BOLD).append(color).append("[");
		builder.append(Colors.NORMAL).append(value).append(Colors.NORMAL);
		builder.append(Colors.BOLD).append(color).append("]");
		return this;
	}

	public MessageBuilder appendBrackets(final String key, final String value, final String color) {
		builder.append(Colors.NORMAL).append(color).append(Colors.BOLD).append("[");
		builder.append(Colors.NORMAL).append(Colors.BOLD).append(key).append(Colors.NORMAL).append(" ").append(value).append(Colors.NORMAL);
		builder.append(Colors.BOLD).append(color).append("]");
		return this;
	}

	public MessageBuilder append(final MessageBuilder mb) {
		builder.append(mb.toString());
		return this;
	}

	public MessageBuilder append(final String str) {
		builder.append(str);
		return this;
	}

	public MessageBuilder clear() {
		builder.setLength(0);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
