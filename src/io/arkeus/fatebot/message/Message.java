package io.arkeus.fatebot.message;

/**
 * A structure for containing information about a single message in a channel.
 */
public class Message {
	private String nick;
	private String message;
	private long time;
	private String channel;

	/**
	 * Construct a message structure for a single message.
	 *
	 * @param nick The user who sent the message.
	 * @param message The full message being sent.
	 * @param time The time the message was received.
	 * @param channel The channel the message was sent in.
	 */
	public Message(final String nick, final String message, final long time, final String channel) {
		this.nick = nick;
		this.message = message;
		this.time = time;
		this.channel = channel;
	}

	/**
	 * Returns the nick of the user who sent the message.
	 *
	 * @return The user's nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick of the user who sent the message.
	 *
	 * @param nick The user's nick.
	 */
	public void setNick(final String nick) {
		this.nick = nick;
	}

	/**
	 * Returns the full message that was sent.
	 *
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(final long time) {
		this.time = time;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}
}
