package io.arkeus.fatebot.commands.impl.lyrics;

import io.arkeus.fatebot.commands.CommandException;
import io.arkeus.fatebot.util.http.HttpClient;
import io.arkeus.fatebot.util.http.HttpException;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides a searcher class allowing you to find songs by lyrics.
 */
public class LyricsSearcher {
	private static final String ENDPOINT = "http://api.lyricsnmusic.com/songs";
	private static final String API_KEY = "387c2a5e7882754970466007fe227c"; // TODO: Pass through config so people supply their own?

	/**
	 * Given a string of lyrics, searches for the best that matches them.
	 *
	 * @param lyrics The lyrics to search for.
	 * @return A song object with information regarding the top match.
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws HttpException
	 * @throws CommandException
	 */
	public static Song searchLyrics(final String lyrics) throws JsonProcessingException, IOException, HttpException, CommandException {
		final JsonNode root = new ObjectMapper().readTree(HttpClient.get(generateRequestURL(lyrics)));
		final JsonNode topMatch = root.get(0);
		if (topMatch == null) {
			throw new CommandException("No results found for lyrics");
		}
		return new Song(topMatch.get("title").asText(), topMatch.get("artist").get("name").asText(), topMatch.get("url").asText());
	}

	private static String generateRequestURL(final String lyrics) {
		return String.format("%s?api_key=%s&lyrics=%s", ENDPOINT, API_KEY, lyrics);
	}
}
