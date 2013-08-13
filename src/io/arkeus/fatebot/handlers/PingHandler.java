package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.message.Message;
import io.arkeus.fatebot.user.UserManager;
import io.arkeus.fatebot.util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jibble.pircbot.Colors;

public class PingHandler extends MessageHandler {
	public static final long MISSED_PING_TIME = TimeUnit.MINUTES.toMillis(5);

	private final Map<String, List<Message>> pingMap;
	private final Set<String> pingableUsers;

	public PingHandler(final Fate bot) {
		super(bot);
		pingMap = new HashMap<>();
		pingableUsers = new HashSet<>();
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		final String normalizedMessage = message.toLowerCase();
		final String normalizedNick = UserManager.normalizeNick(sender);

		for (final String nick : pingableUsers) {
			// Do a quick indexOf before the more expensive regex. Investigate performance of this. 4770k can't handle this shit.
			if (normalizedMessage.indexOf(nick) != -1 /*&& !sender.equalsIgnoreCase(nick)*/ && normalizedMessage.matches(".*\\b" + nick + "\\b.*")) {
				addPing(nick, message);
			}
		}

		final long now = System.currentTimeMillis();
		if (pingMap.containsKey(normalizedNick) && !pingMap.get(normalizedNick).isEmpty()) {
			final List<Message> pings = getPings(normalizedNick);
			final StringBuilder sb = new StringBuilder();
			for (final Message ping : pings) {
				if (ping.getTime() > now - MISSED_PING_TIME) {
					continue;
				}
				sb.append(Colors.BOLD).append("<").append(ping.getNick()).append("> ").append(Colors.NORMAL).append(ping.getMessage()).append(" (").append(TimeUtils.getDurationFromMillis(now - ping.getTime())).append(") ");
			}
			if (sb.length() > 0) {
				final List<String> missedMessages = splitString(sb.toString(), bot.getMaxLineLength());
				bot.sendNotice(normalizedNick, "You have missed pings since your last message:");
				for (final String missedMessage : missedMessages) {
					bot.sendNotice(normalizedNick, missedMessage);
				}
			}
		}
	}

	public boolean setPingableStatus(final String nick, final boolean pingable) {
		if (pingable) {
			return pingableUsers.add(nick);
		} else {
			return pingableUsers.remove(nick);
		}
	}

	private void addPing(final String nick, final String message) {
		if (!pingMap.containsKey(nick)) {
			pingMap.put(nick, new ArrayList<Message>());
		}
		final List<Message> list = pingMap.get(nick);
		list.add(new Message(nick, message, System.currentTimeMillis(), bot.getConfig().getChannel()));
	}

	public List<Message> getPings(final String nick) {
		if (!pingMap.containsKey(nick)) {
			return null;
		}
		final List<Message> pings = pingMap.get(nick);
		pingMap.remove(nick);
		return pings;
	}

	private List<String> splitString(final String string, final int maxLength) {
		final String[] tokens = string.split(" ");
		final List<String> results = new ArrayList<>();
		final StringBuilder sb = new StringBuilder();
		for (final String token : tokens) {
			if (sb.length() + token.length() + 1 < maxLength) {
				sb.append(token).append(" ");
			} else {
				results.add(sb.toString());
				sb.setLength(0);
			}
		}
		if (sb.length() > 0) {
			results.add(sb.toString());
		}
		return results;
	}
}