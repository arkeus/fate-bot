package io.arkeus.fatebot.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * A simply client allowing you to make HTTP calls.
 */
public class HttpClient {
	public static String get(final String requestURL) throws HttpException {
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(generateURL(requestURL).openConnection().getInputStream()))) {
			final StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (final MalformedURLException | URISyntaxException e) {
			throw new HttpException("Could not form URL from requestURL '" + requestURL + "'", e);
		} catch (final IOException e) {
			throw new HttpException("Could not connect and receive content from requestURL '" + requestURL + "'", e);
		}
	}

	private static URL generateURL(final String requestURL) throws MalformedURLException, URISyntaxException {
		final URL url = new URL(requestURL);
		final URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		return uri.toURL();
	}
}
