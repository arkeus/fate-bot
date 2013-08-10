package io.arkeus.fatebot.config;

public class Config {
	private String nick;
	private String altNick;
	private String password;
	private String server;
	private String channel;
	private Boolean verbose;
	private String login;

	public String getNick() {
		return nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	public String getAltNick() {
		return altNick;
	}

	public void setAltNick(final String altNick) {
		this.altNick = altNick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(final String server) {
		this.server = server;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(final Boolean verbose) {
		this.verbose = verbose;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}
}
