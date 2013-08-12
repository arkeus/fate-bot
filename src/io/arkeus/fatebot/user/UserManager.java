package io.arkeus.fatebot.user;

import io.arkeus.fatebot.Fate;

import java.util.HashMap;
import java.util.Map;

import org.jibble.pircbot.User;

public class UserManager {
	private final Map<String, FateUser> users;

	public UserManager() {
		users = new HashMap<>();
	}

	public void updateActiveUsers(final User[] users) {
		for (final User user : users) {
			updateActiveUser(user);
		}
	}

	public void updateActiveUser(final User user) {
		final FateUser fateUser = getOrCreateFateUser(user.getNick());
		fateUser.grantIdleTick();
	}

	public FateUser getOrCreateFateUser(final String nick) {
		final String normalizedNick = normalizeNick(nick);
		if (users.containsKey(normalizedNick)) {
			return users.get(normalizedNick);
		} else {
			Fate.logger.info("Creating FateUser for " + normalizedNick);
			final FateUser fateUser = new FateUser(normalizedNick);
			users.put(normalizedNick, fateUser);
			return fateUser;
		}
	}

	public FateUser getFateUser(final String nick) {
		final String normalizedNick = normalizeNick(nick);
		return users.get(normalizedNick);
	}

	private static String normalizeNick(final String nick) {
		return nick.toLowerCase();
	}
}
