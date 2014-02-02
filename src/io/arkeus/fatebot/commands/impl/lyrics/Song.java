package io.arkeus.fatebot.commands.impl.lyrics;

/**
 * A container containing information about a song.
 */
public class Song {
	private final String name;
	private final String artist;
	private final String url;

	/**
	 * Create a new song container.
	 *
	 * @param name The name of the song.
	 * @param artist The artist of the song.
	 * @param url The URL of the external song information.
	 */
	public Song(final String name, final String artist, final String url) {
		this.name = name;
		this.artist = artist;
		this.url = url;
	}

	/**
	 * Returns the name of the song.
	 *
	 * @return The song name.
	 */
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
