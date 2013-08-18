package io.arkeus.fatebot.config;

import java.io.File;
import java.util.Set;

public class Config {
	private String nick;
	private String altNick;
	private String password;
	private String server;
	private String channel;
	private Boolean verbose;
	private String login;
	private String prefix;
	private File root;
	private Set<String> administrators;

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(final File root) {
		this.root = root;
	}

	public void setRoot(final String root) {
		this.root = new File(root);
	}

	public Set<String> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(final Set<String> administrators) {
		this.administrators = administrators;
	}
}
