package eu.unrealdev.clashofclans.user.stats;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.utils.AsyncMySQL;
import eu.unrealdev.clashofclans.utils.file.ConfigFile;

public class Stats {

	private GameUser gameUser;
	private Date firstPlayed, lastPlayed;
	private int trophies, wins, defeat;
	private ConfigFile userStats = COCMain.getConfigManager().getUserStats();
	private static HashMap<GameUser, Stats> cache = new HashMap<GameUser, Stats>();

	public Stats(GameUser gameUser) {

		// set User

		cache.put(gameUser, this);

		// Load data

		load();
	}

	public static Stats getStatistics(GameUser gameUser) {

		if (cache.containsKey(gameUser)) {

			return cache.get(gameUser);
		} else {

			return new Stats(gameUser);
		}
	}

	public static HashMap<GameUser, Stats> getCache() {

		return cache;
	}

	public void load() {
		// Check if a player uses mysql or not
		boolean mysql = COCMain.getConfigManager().getConfig().getBoolean("mysql.use-mysql");

		if (mysql) {

		} else {
			String uuid = gameUser.getPlayer().getUniqueId().toString();
			if (!userStats.getConfig().contains(uuid.toString())) {
				this.wins = 0;
				this.trophies = 0;

				this.defeat = 0;
				lastPlayed = Date.from(Instant.now());
				userStats.getConfig().set(uuid.toString() + ".wins", this.wins);
				userStats.getConfig().set(uuid.toString() + ".trophies", this.trophies);
				userStats.getConfig().set(uuid.toString() + ".defeat", this.defeat);
				userStats.getConfig().set(uuid.toString() + ".lastPlayed", this.lastPlayed.toInstant().toEpochMilli());
				userStats.saveConfig();
			} else {
				if (!GameUser.cache.containsKey(uuid)) {
					this.wins = userStats.getConfig().getInt(uuid.toString() + ".wins");
					this.defeat = userStats.getConfig().getInt(uuid.toString() + ".defeat");
					this.trophies = userStats.getConfig().getInt(uuid.toString() + ".trophies");
					this.lastPlayed = null;
				}
			}

		}

	}

	public void updateAll() {
		
		boolean mysql = COCMain.getConfigManager().getConfig().getBoolean("mysql.use-mysql");
		
		if(mysql) {
			
			AsyncMySQL sql = COCMain.getMySQL();
			
			sql.
			
		} else {
		
		String uuid = gameUser.getPlayer().getUniqueId().toString();
		userStats.getConfig().set(uuid.toString() + ".wins", this.wins);
		userStats.getConfig().set(uuid.toString() + ".trophies", this.trophies);
		userStats.getConfig().set(uuid.toString() + ".defeat", this.defeat);
		userStats.getConfig().set(uuid.toString() + ".lastPlayed", this.lastPlayed.toInstant().toEpochMilli());
		userStats.saveConfig();
		
		}

	}

	public Date getFirstPlayed() {
		return firstPlayed;
	}

	public Date getLastPlayed() {
		return lastPlayed;
	}

	public int getTrophies() {
		return trophies;
	}

	public int getWins() {
		return wins;
	}

	public int getDefeat() {
		return defeat;
	}

	public void setFirstPlayed(Date firstPlayed) {
		this.firstPlayed = firstPlayed;
		updateAll();
	}

	public void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
		updateAll();
	}

	public void setTrophies(int trophies) {
		this.trophies = trophies;
		updateAll();
	}

	public void setWins(int wins) {
		this.wins = wins;
		 updateAll();
	}

	public void setDefeat(int defeat) {
		this.defeat = defeat;
		 updateAll();
	}
	
	
	
}
