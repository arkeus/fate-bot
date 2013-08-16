package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;
import io.arkeus.fatebot.user.FateUser;

/**
 * Debug stuff for now.
 */
public class DebugHandler extends MessageHandler {
	private static final String ALMIGHTY_GOD = "Arkeus";

	public DebugHandler(final Fate bot) {
		super(bot);
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		if (!isGodSpeaking(sender, hostname)) {
			return;
		}

		try {
			final DebugMessage dm = new DebugMessage(message);
			@SuppressWarnings("unused")
			final FateUser fu = bot.getFateUser(sender);

			if (dm.isCommand() && dm.getName().equals("xp") && dm.getNumParams() >= 2) {
				final String targetNick = dm.getParameter(1);
				final int targetXP = Integer.parseInt(dm.getParameter(2));
				if (bot.hasFateUser(targetNick)) {
					final FateUser target = bot.getFateUser(targetNick);
					target.setIdleTicks(target.getIdleTicks() + targetXP);
				} else {
					bot.sendMessage(channel, "User " + targetNick + " does not exist.");
				}
			}
		} catch (final Exception e) {
			// ignore all exceptions in debug logic
			Fate.logger.info("Debug error", e);
		}
	}

	private static boolean isGodSpeaking(final String nick, final String hostname) {
		if (!nick.equalsIgnoreCase(ALMIGHTY_GOD) || !hostname.equals("198.244.103.85")) {
			return false;
		}

		return true;
	}

	private static class DebugMessage {
		private static enum DebugMessageType {
			COMMAND, OTHER
		};

		private static final StringBuilder sb = new StringBuilder();

		private final DebugMessageType type;
		private final String[] split;

		private DebugMessage(final String message) {
			this.type = message.startsWith(";") ? DebugMessageType.COMMAND : DebugMessageType.OTHER;
			this.split = message.split(" ");
		}

		private String getName() {
			return isCommand() ? split[0].substring(1) : split[0];
		}

		private String getParameter(final int index) {
			if (split.length > index) {
				return split[index];
			}
			return null;
		}

		@SuppressWarnings("unused")
		private String getMessage(final int numParameters) {
			sb.setLength(0);
			for (int i = numParameters + 1; i < split.length; i++) {
				sb.append(split[i]).append(" ");
			}
			return sb.toString();
		}

		private int getNumParams() {
			return split.length - 1;
		}

		private boolean isCommand() {
			return type == DebugMessageType.COMMAND;
		}
	}
}