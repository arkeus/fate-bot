package io.arkeus.fatebot.commands.impl;

import io.arkeus.fatebot.commands.Command;
import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.commands.impl.lyrics.LyricsSearcher;
import io.arkeus.fatebot.commands.impl.lyrics.Song;
import io.arkeus.fatebot.util.http.HttpException;

import java.io.IOException;

public class LyricsCommand extends Command {
	public LyricsCommand() {
		super(1);
	}

	@Override
	protected void run() throws CommandException {
		try {
			final Song song = LyricsSearcher.searchLyrics(arguments.getMessage());
			bot.sendMessage(channel, String.format("Title: %s, Artist: %s, Lyrics: %s", song.getName(), song.getArtist(), song.getUrl()));
		} catch (final HttpException | IOException e) {
			throw new CommandException("Could not find song for lyrics", e);
		}
	}
}