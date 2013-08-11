package io.arkeus.fatebot.util.http;

public class HttpException extends Exception {
	private static final long serialVersionUID = -1342184301661678424L;

	public HttpException() {
		super();
	}

	public HttpException(final String message, final Throwable source) {
		super(message, source);
	}

	public HttpException(final String message) {
		super(message);
	}

	public HttpException(final Throwable source) {
		super(source);
	}
}
