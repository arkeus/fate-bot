package io.arkeus.fatebot.handlers;

import io.arkeus.fatebot.Fate;

import java.util.regex.Pattern;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.User;

public class TrapHandler extends MessageHandler {
	private String trap;
	private String trapper;
	private Pattern pattern;

	public TrapHandler(final Fate bot) {
		super(bot);
		trap = null;
		pattern = null;
	}

	@Override
	public void handle(final String channel, final String sender, final String login, final String hostname, final String message) {
		if (!hasTrapSet()) {
			return;
		}

		if (pattern.matcher(Colors.removeFormattingAndColors(message).toLowerCase()).find()) {
			final User trapperUser = bot.getUser(trapper);
			final String target = Math.random() < 0.2 && trapperUser != null && !trapperUser.isOp() ? trapper : sender;
			if (!bot.getUser(target).isOp()) {
				bot.kick(channel, target, "You have activated my trap card: [Shadow Word: " + trap + "]! I banish you to the Shadow Realm!" + (sender != target ? " But what's this? My spell has been reflected at the one who set the trap!" : ""));
			} else {
				bot.sendMessage(channel, "It appears you are too strong for me to banish you, " + target + "... " + trapper + "'s card has fizzled.");
			}
			disarmTrap();
		}
	}

	public boolean hasTrapSet() {
		return this.trap != null;
	}

	public void setTrap(final String trap, final String trapper) {
		this.trap = trap;
		this.trapper = trapper;
		this.pattern = Pattern.compile("\\b" + trap.toLowerCase() + "\\b");
	}

	public String getTrap() {
		return trap;
	}

	public String getTrapper() {
		return trapper;
	}

	public void disarmTrap() {
		this.trap = null;
		this.pattern = null;
	}
}