package io.arkeus.fatebot.util;

public class TimeUtils {
	public static String getDurationFromMillis(final long millis) {
		final long seconds = millis / 1000;
		final long minutes = seconds / 60;
		if (minutes == 0) {
			return seconds + "s ago";
		}
		final long hours = minutes / 60;
		if (hours == 0) {
			return minutes + "m ago";
		}
		final long days = hours / 24;
		if (days == 0) {
			return hours + "h ago";
		}
		return days + "d ago";
	}
}
