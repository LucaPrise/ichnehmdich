package eu.unrealdev.clashofclans.clan;

import java.util.ArrayList;
import java.util.Arrays;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.utils.file.ConfigFile;

public class ClanManager {

	ArrayList<Clan> clans = new ArrayList<Clan>();
	private ConfigFile clansData;
	boolean mysql;

	public ClanManager() {
		mysql = COCMain.getConfigManager().getConfig().getBoolean("mysql.use-mysql");

		clansData = COCMain.getConfigManager().getClansData();

		if (mysql) {
			
			// load from database
			
		} else {
			
			// load from config
			
			for (String name : clansData.getConfig().getKeys(false)) {
				clans.add(new Clan(name));
			}
		}
	}

	public Clan createClan(String name) {
		if (ExistsClan(name))
			return null;

		if (mysql) {
			
			// load from databse
			
		} else {
			
			// load from config
			
			clansData.getConfig().set(name + ".description", Arrays.asList(name + " Offers the best clan Experience!"));
			clansData.getConfig().set(name + ".trophies", 0);
			clansData.getConfig().set(name + ".state", 0);
			clansData.getConfig().set(name + ".members", new ArrayList<String>());
			clansData.saveConfig();
		}
		Clan c = new Clan(name);
		clans.add(c);
		return c;
	}

	public Clan getClan(String name) {
		for (Clan clan : clans) {
			if (clan.getName().equalsIgnoreCase(name)) {
				return clan;
			}
		}
		return null;
	}

	public boolean ExistsClan(String name) {
		return getClan(name) != null;
	}

	public Clan getClan(GameUser user) {
		for (Clan c : clans) {
			if (c.getMembers().contains(user)) {
				return c;
			}
		}
		return null;
	}
}
