package eu.unrealdev.clashofclans.clan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.utils.file.ConfigFile;

public class Clan {

	private String name;
	private String[] description;

	private int trophies;
	private int state;

	boolean mysql;

	private ConfigFile clansData;
	private List<GameUser> members = new ArrayList<GameUser>();

	public Clan(String name) {
		mysql = COCMain.getConfigManager().getConfig().getBoolean("mysql.use-mysql");
		clansData = COCMain.getConfigManager().getClansData();

		if (mysql) {

			// load from databse
			
		} else {

			// load it from config

			this.name = name;
			this.description = (String[]) clansData.getConfig().getStringList(name + ".description").toArray();
			this.trophies = clansData.getConfig().getInt(name + ".trophies");
			this.state = clansData.getConfig().getInt(name + ".state");
			for (String s : clansData.getConfig().getStringList(name + ".members")) {
				GameUser user = GameUser.getUser(Bukkit.getPlayer(UUID.fromString(s)));
				members.add(user);
			}
		}
	}

	public String[] getDescription() {

		return description;
	}

	public String getName() {

		return name;
	}

	public List<GameUser> getMembers() {

		return members;
	}

	public int getState() {

		return state;
	}

	public int getTrophies() {

		return trophies;
	}

	public void removeMember(GameUser user) {
		if (!members.contains(user))
			return;
		members.remove(user);
		ArrayList<String> s = new ArrayList<String>();
		for (GameUser u : members) {
			s.add(u.getPlayer().getUniqueId().toString());
		}
		clansData.getConfig().set(name + ".members", s);
		clansData.saveConfig();
	}

	public void addMember(GameUser user) {
		if (COCMain.getClansManager().getClan(user) != null) {
			COCMain.getClansManager().getClan(user).removeMember(user);
		}
		members.add(user);
		user.updateClanConfig();
		ArrayList<String> s = new ArrayList<String>();
		for (GameUser u : members) {
			s.add(u.getPlayer().getUniqueId().toString());
		}
		clansData.getConfig().set(name + ".members", s);
		clansData.saveConfig();

	}

}
