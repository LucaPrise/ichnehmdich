package eu.unrealdev.clashofclans.utils.file;

import org.bukkit.configuration.file.FileConfiguration;

import eu.unrealdev.clashofclans.COCMain;

public class ConfigurationManager {

	private ConfigFile arenas, userStats, userEconomy, clanData;

	public ConfigurationManager() {
		COCMain.getInstance().saveDefaultConfig();
		COCMain.getInstance().reloadConfig();
		arenas = new ConfigFile("Arenas");
		userStats = new ConfigFile("UserStats");
		userEconomy = new ConfigFile("UserEconomy");
		clanData = new ConfigFile("ClanData");
		
	}

	/**
	 * @return the Main server config
	 */
	public FileConfiguration getConfig() {
		return COCMain.getInstance().getConfig();
	}

	/**
	 * saves the Main server Config
	 */
	public void saveConfig() {
		try {
			COCMain.getInstance().saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the config class for Arenas.yml from that class, Arenas.yml can be
	 * saved, and edited
	 * 
	 * @return arenas.yml file manager
	 */
	public ConfigFile getArenas() {
		return arenas;
	}

	/**
	 * Gets the config class for UserStats.yml from that class, UserStats.yml can
	 * be saved, and edited
	 * 
	 * @return UserStats.yml file manager
	 */
	public ConfigFile getUserStats() {
		return userStats;
	}
	
	/**
	 * Gets the config class for UserEconomy.yml from that class, UserStats.yml can
	 * be saved, and edited
	 * 
	 * @return UserEconomy.yml file manager
	 */
	public ConfigFile getUserEconomy() {
		return userEconomy;
	}

	/**
	 * Gets the config class for ClanData.yml from that class, ClanData.yml can
	 * be saved, and edited
	 * 
	 * @return ClanData.yml file manager
	 */
	public ConfigFile getClansData() {
		return clanData;
	}
}
