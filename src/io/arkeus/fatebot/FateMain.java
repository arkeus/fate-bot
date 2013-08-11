package io.arkeus.fatebot;

import io.arkeus.fatebot.config.Config;
import io.arkeus.fatebot.config.ConfigReader;

import java.io.File;

public class FateMain {
	public static void main(final String[] args) throws Exception {
		final Config config = new ConfigReader(new File(args[0])).generateConfiguration();
		final Fate bot = new Fate(config);
		bot.initialize();
	}
}
