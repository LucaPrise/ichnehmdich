package eu.unrealdev.clashofclans;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.unrealdev.clashofclans.clan.ClanManager;
import eu.unrealdev.clashofclans.island.IslandManager;
import eu.unrealdev.clashofclans.listeners.JoinListener;
import eu.unrealdev.clashofclans.listeners.LoginListener;
import eu.unrealdev.clashofclans.listeners.QuitListener;
import eu.unrealdev.clashofclans.utils.AsyncMySQL;
import eu.unrealdev.clashofclans.utils.file.ConfigurationManager;

public class COCMain extends JavaPlugin {

	private static COCMain instance;

	private static ConfigurationManager configManager;
	private static ClanManager clanManager;
	private static IslandManager islandManager;

	private static AsyncMySQL mySQL = null;

	@Override
	public void onEnable() {
		instance = this;
		// Dont put anything above this method
		// any classes required to be loaded should be put here
		load();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	private void load() {
		// Config Manager must be at the top, so all the FileConfigurations load
		// before any other classes
		configManager = new ConfigurationManager();

		boolean mysql = configManager.getConfig().getBoolean("mysql.use-mysql");

		if (mysql) {

			FileConfiguration config = configManager.getConfig();
			System.out.println("Connecting to Databse.....");
			mySQL = new AsyncMySQL(config.getString("mysql.host"), Integer.parseInt(config.getString("mysql.port")),
					config.getString("mysql.user"), config.getString("mysql.pass"), config.getString("mysql.database"));
			
		}

		islandManager = new IslandManager();
		clanManager = new ClanManager();

		// load listeners
		new JoinListener();
		new LoginListener();
		new QuitListener();
	}

	/**
	 * @returns the Plugin
	 */
	public static COCMain getInstance() {
		return instance;
	}

	/**
	 * @returns the ConfigurationManager
	 *          <p>
	 *          Used to edit and save YamlConfiguration Files
	 *          </p>
	 */
	public static ConfigurationManager getConfigManager() {
		return configManager;

	}

	/**
	 * @returns the IslandManager
	 *          <p>
	 *          Used to Manage islands
	 *          </p>
	 */
	public static IslandManager getIslandManager() {
		return islandManager;
	}

	/**
	 * @returns the ClanManager
	 *          <p>
	 *          Used to Manage Clans
	 *          </p>
	 */
	public static ClanManager getClansManager() {
		return clanManager;
	}

	/**
	 * 
	 * @returns AsyncMySQL class
	 */
	public static AsyncMySQL getMySQL() {
		return mySQL;
	}

}
