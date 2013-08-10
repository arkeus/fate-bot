package io.arkeus.fatebot;

import java.io.IOException;

import io.arkeus.fatebot.config.Config;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class FateBot extends PircBot {
	private final Config config;

	public FateBot(final Config config) {
		this.config = config;
	}

	public void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		setLogin(config.getLogin());
		setVerbose(config.getVerbose());
		setName(config.getNick());
		setVersion(config.getLogin());

		try {
			connect(config.getServer());
		} catch (final NickAlreadyInUseException e) {
			setName(config.getAltNick());
			connect(config.getServer());
		}

		joinChannel(config.getChannel());
	}
}
