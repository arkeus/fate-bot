package io.arkeus.fatebot.executors;

import java.util.concurrent.TimeUnit;

import io.arkeus.fatebot.Fate;

public class ChronoThread extends Thread {
	private static final long TIME_DELAY = TimeUnit.MINUTES.toMillis(5);

	private final Fate bot;

	public ChronoThread(final Fate bot) {
		this.bot = bot;
	}

	@Override
	public void run() {
		while (true) {
			bot.chronoTick();
			try {
				Thread.sleep(TIME_DELAY);
			} catch (final InterruptedException ignore) {}
		}
	}
}
