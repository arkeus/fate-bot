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

	/**
	 * Sets the nick of the bot.
	 *
	 * @param nick The bot's nick.
	 */
	public void setNick(final String nick) {
		this.nick = nick;
	}

	/**
	 * Returns the alternate nick of the bot.
	 *
	 * @return The bot's alternate nick.
	 */
	public String getAltNick() {
		return altNick;
	}

	/**
	 * Sets the alternative nick of the bot.
	 *
	 * @param altNick The bot's alternative nick.
	 */
	public void setAltNick(final String altNick) {
		this.altNick = altNick;
	}

	/**
	 * Returns the password for the bot's nick.
	 *
	 * @return The bot's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password for the bot's nick.
	 *
	 * @param password The bot's password.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Returns the server the bot should connect to.
	 *
	 * @return The bot's server.
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Sets the server the bot should connect to.
	 *
	 * @param server The bot's server.
	 */
	public void setServer(final String server) {
		this.server = server;
	}

	/**
	 * Gets the channel the bot should run in.
	 *
	 * @return The channel of the bot.
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel the bot should run in.
	 *
	 * @param channel The channel of the bot.
	 */
	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/**
	 * Returns whether or not the bot is running in verbose mode.
	 *
	 * @return The verbose mode flag.
	 */
	public Boolean getVerbose() {
		return verbose;
	}

	/**
	 * Sets the verbose flag for the bot to run in verbose mode.
	 *
	 * @param verbose Whether or not to run in verbose mode.
	 */
	public void setVerbose(final Boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * Returns the login of the bot.
	 *
	 * @return The bot's login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login of the bot.
	 *
	 * @param login The bot's login.
	 */
	public void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * Gets the prefix required to input commands.
	 *
	 * @return The command prefix.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix required to input commands.
	 *
	 * @param prefix The command prefix.
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the root workspace directory for the bot.
	 *
	 * @return The workspace directory.
	 */
	public File getRoot() {
		return root;
	}

	/**
	 * Sets the root workspace directory for the bot.
	 *
	 * @param root The workspace directory.
	 */
	public void setRoot(final File root) {
		this.root = root;
	}

	/**
	 * Sets the root workspace directory for the bot.
	 *
	 * @param root The workspace directory.
	 */
	public void setRoot(final String root) {
		this.root = new File(root);
	}

	/**
	 * Gets the list of administrator nicks.
	 *
	 * @return The administrators for the bot.
	 */
	public Set<String> getAdministrators() {
		return administrators;
	}

	/**
	 * Sets the list of administrators.
	 *
	 * @param administrators The administrators for the bot.
	 */
	public void setAdministrators(final Set<String> administrators) {
		this.administrators = administrators;
	}
}
