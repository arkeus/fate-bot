package io.arkeus.fatebot.commands.impl.lyrics;

public class Song {
	private final String name;
	private final String artist;
	private final String url;

	public Song(final String name, final String artist, final String url) {
		this.name = name;
		this.artist = artist;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public String getUrl() {
		return url;
	}
}
