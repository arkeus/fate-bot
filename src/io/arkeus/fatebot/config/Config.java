package io.arkeus.fatebot.config;

import java.io.File;
import java.util.Set;

/**
 * A class containing all the config required to run the bot.
 */
public class Config {
	/** The nick to connect to the server with. */
	private String nick;
	/** The alternative nick to connect to the server with, if the main nick is taken. */
	private String altNick;
	/** The password to identify with to nickserv on the server. */
	private String password;
	/** The server to connect to. */
	private String server;
	/** The channel for the bot to join. */
	private String channel;
	/** Whether or not to run in verbose mode and log extra information. */
	private Boolean verbose;
	/** The login for the bot to use when connecting to the server. */
	private String login;
	/** The prefix that must be used before a command in order to activate it. */
	private String prefix;
	/** Root directory for the bot, where data should be stored. */
	private File root;
	/** The set of users who have administrator privileges to run restricted commands. */
	private Set<String> administrators;

	/**
	 * Returns the nick of the bot.
	 *
	 * @return The bot's nick.
	 */
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
