package eu.unrealdev.clashofclans.user.economy;

import java.util.HashMap;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.enums.CurrencyType;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.utils.file.ConfigFile;

public class Economy {

	private GameUser gameUser;
	private int gold, elixir, dark_elixir, gems;
	private ConfigFile userEconomy = COCMain.getConfigManager().getUserEconomy();
	private static HashMap<GameUser, Economy> cache = new HashMap<GameUser, Economy>();

	public Economy(GameUser user) {

		cache.put(user, this);
		load();
	}

	public static HashMap<GameUser, Economy> getCache() {
		return cache;
	}

	public static Economy getEconomy(GameUser user) {
		if (cache.containsKey(user)) {
			return cache.get(user);
		} else {
			return new Economy(user);
		}
	}

	public void load() {

		// check if mysql is being used

		boolean mysql = COCMain.getConfigManager().getConfig().getBoolean("mysql.use-mysql");

		if (mysql) {

			// Set and fetch result from database

		} else {
			String uuid = gameUser.getPlayer().getUniqueId().toString();
			if (!userEconomy.getConfig().contains(uuid.toString())) {
				this.gems = 0;
				this.elixir = 0;
				this.dark_elixir = 0;
				this.gold = 0;
				userEconomy.getConfig().set(uuid.toString() + ".gold", this.gold);
				userEconomy.getConfig().set(uuid.toString() + ".gems", this.gems);
				userEconomy.getConfig().set(uuid.toString() + ".elixir", this.elixir);
				userEconomy.getConfig().set(uuid.toString() + ".dark-elixir", this.dark_elixir);
				userEconomy.saveConfig();
			} else {
				if (!GameUser.cache.containsKey(uuid)) {
					this.gems = userEconomy.getConfig().getInt(uuid.toString() + ".gems");
					this.gold = userEconomy.getConfig().getInt(uuid.toString() + ".gold");
					this.elixir = userEconomy.getConfig().getInt(uuid.toString() + ".elixir");
					this.dark_elixir = userEconomy.getConfig().getInt(uuid.toString() + ".dark-elixir");
				}
			}
		}
	}

	public int getCurrency(CurrencyType type) {
		switch (type) {
		case GOLD:
			return this.gold;
		case GEMS:
			return this.gems;
		case ELIXIR:
			return this.elixir;
		case DARK_ELIXIR:
			return this.dark_elixir;
		}
		return -1;
	}

	public boolean hasCurrency(CurrencyType type, int amount) {
		return getCurrency(type) >= amount;
	}

	public void setCurrency(CurrencyType type, int amount) {
		assert (amount >= 0);
		switch (type) {
		case GOLD:
			this.gold = amount;
			updateCurrency(type);
			break;
		case GEMS:
			this.gems = amount;
			updateCurrency(type);
			break;
		case ELIXIR:
			this.elixir = amount;
			updateCurrency(type);
			break;
		case DARK_ELIXIR:
			this.dark_elixir = amount;
			updateCurrency(type);
			break;
		}
	}

	public void updateCurrency(CurrencyType type) {
		String uuid = gameUser.getPlayer().getUniqueId().toString();
		switch (type) {
		case GOLD:
			userEconomy.getConfig().set(uuid.toString() + ".gold", this.gold);
			userEconomy.saveConfig();
			break;
		case GEMS:
			userEconomy.getConfig().set(uuid.toString() + ".gems", this.gems);
			userEconomy.saveConfig();
			break;
		case ELIXIR:
			userEconomy.getConfig().set(uuid.toString() + ".elixir", this.elixir);
			userEconomy.saveConfig();
			break;
		case DARK_ELIXIR:
			userEconomy.getConfig().set(uuid.toString() + ".dark-elixir", this.dark_elixir);
			userEconomy.saveConfig();
			break;
		}

	}

	public void updateAll() {
		for (CurrencyType type : CurrencyType.values()) {
			updateCurrency(type);
		}
	}

}
