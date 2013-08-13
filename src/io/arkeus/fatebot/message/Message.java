package io.arkeus.fatebot.message;

public class Message {
	private String nick;
	private String message;
	private long time;
	private String channel;

	public Message(final String nick, final String message, final long time, final String channel) {
		this.nick = nick;
		this.message = message;
		this.time = time;
		this.channel = channel;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

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
