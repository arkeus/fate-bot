package io.arkeus.fatebot.user;

import java.util.Date;

public class FateUser {
	private static final long EFFECT_MESSAGE_DELAY = 2_000;

	private final String nick;
	private int messages;
	private int effectiveMessages;
	private long lastMessageTime;
	private long lastIdleTime;
	private int idleTicks;

	public FateUser(final String nick) {
		this.nick = nick;
		this.messages = 0;
		this.effectiveMessages = 0;
		this.lastMessageTime = 0;
		this.idleTicks = 0;
	}

	public String getNick() {
		return nick;
	}

	public void addMessage() {
		messages++;

		final long now = new Date().getTime();
		if (lastMessageTime < now - EFFECT_MESSAGE_DELAY) {
			lastMessageTime = now;
			effectiveMessages++;
		}
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(final int messages) {
		this.messages = messages;
	}

	public int getEffectiveMessages() {
		return effectiveMessages;
	}

	public void setEffectiveMessages(final int effectiveMessages) {
		this.effectiveMessages = effectiveMessages;
	}

	public long getLastMessageTime() {
		return lastMessageTime;
	}

	public void setLastMessageTime(final long lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}

	public long getLastIdleTime() {
		return lastIdleTime;
	}

	public void setLastIdleTime(final long lastIdleTime) {
		this.lastIdleTime = lastIdleTime;
	}

	public void grantIdleTick() {
		setIdleTicks(getIdleTicks() + 1);
		setLastIdleTime(System.currentTimeMillis());
	}

	public int getIdleTicks() {
		return idleTicks;
	}

	public void setIdleTicks(final int idleTicks) {
		this.idleTicks = idleTicks;
	}
}
