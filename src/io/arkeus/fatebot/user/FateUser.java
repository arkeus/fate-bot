package io.arkeus.fatebot.user;


public class FateUser {
	private static final long EFFECT_MESSAGE_DELAY = 5_000;

	private final String nick;
	private int messages;
	private int effectiveMessages;
	private String lastMessage;
	private long lastMessageTime;
	private long lastEffectiveMessageTime;
	private long lastIdleTime;
	private int idleTicks;

	public FateUser(final String nick) {
		this.nick = nick;
		this.messages = 0;
		this.effectiveMessages = 0;
		this.lastMessageTime = System.currentTimeMillis();
		this.lastEffectiveMessageTime = System.currentTimeMillis();
		this.idleTicks = 0;
	}

	public String getNick() {
		return nick;
	}

	public void addMessage(final String message) {
		final long now = System.currentTimeMillis();
		messages++;
		lastMessageTime = now;
		lastMessage = message;

		if (lastEffectiveMessageTime < now - EFFECT_MESSAGE_DELAY) {
			lastEffectiveMessageTime = now;
			effectiveMessages++;
		}
	}

	public int getLevel() {
		// math is hard
		// y = 100x + 5x²
		// x = sqrt(y+500)/sqrt(5)-10 ~~ 0.44721 sqrt(y+500.00)-10.000
		return (int) (Math.sqrt(getExperience() + 500) / Math.sqrt(5) - 10) + 1;
	}

	public int getExperience() {
		return idleTicks * 2 + effectiveMessages * 2;
	}

	public int getExperienceRequiredForLevel(final int level) {
		if (level < 2) {
			return 0;
		}
		final int experienceLevel = level - 1;
		return 100 * experienceLevel + 5 * experienceLevel * experienceLevel;
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

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(final String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public long getLastMessageTime() {
		return lastMessageTime;
	}

	public void setLastMessageTime(final long lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}

	public long getLastEffectiveMessageTime() {
		return lastEffectiveMessageTime;
	}

	public void setLastEffectiveMessageTime(final long lastEffectiveMessageTime) {
		this.lastEffectiveMessageTime = lastEffectiveMessageTime;
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
