package eu.unrealdev.clashofclans.user.scoreboard;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.enums.CurrencyType;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.user.economy.Economy;
import eu.unrealdev.clashofclans.user.stats.Stats;

public class UserScoreboard {
	private GameUser gameUser;
	private Scoreboard sb;
	private Objective obj;
	private ScoreboardType type;
	private int sched;
	private int pos;
	HashMap<Integer, String> is;
	private static HashMap<GameUser, UserScoreboard> cache = new HashMap<GameUser, UserScoreboard>();

	public UserScoreboard(GameUser user) {
		this.gameUser = user;
		is = new HashMap<Integer, String>();
	}

	public static UserScoreboard getUserScoreboard(GameUser user) {
		if (cache.containsKey(user)) {
			return cache.get(user);
		} else {
			return new UserScoreboard(user);
		}
	}

	public void setScoreboard(ScoreboardType type) {
		try {
			Bukkit.getScheduler().cancelTask(sched);
		} catch (Exception ex) {
		}
		if (!is.isEmpty()) {
			for (int i : is.keySet())
				if (sb != null)
					sb.resetScores(is.get(i));
		}
		this.type = type;
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = sb.registerNewObjective("coc", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		set();
	}

	private void set() {
		pos = 0;
		String title = replaceVariables(
				COCMain.getConfigManager().getConfig().getString("Scoreboard." + type.toString() + ".title"));
		List<String> v = COCMain.getConfigManager().getConfig()
				.getStringList("Scoreboard." + type.toString() + ".lines");
		int updateInt = COCMain.getConfigManager().getConfig()
				.getInt("Scoreboard." + type.toString() + ".update-interval");
		obj.setDisplayName(inColors(title));
		for (int i = 0; i < v.size(); i++) {
			int line = 20 - i;
			String l = replaceVariables(v.get(i));
			obj.getScore(inColors(l)).setScore(line);
			is.put(i, inColors(l));
		}
		gameUser.getPlayer().setScoreboard(sb);
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(COCMain.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (pos < v.size()) {
					sb.resetScores(is.get(pos));
					int line = 20 - pos;
					String l = replaceVariables(v.get(pos));
					obj.getScore(inColors(l)).setScore(line);
					is.put(pos, inColors(l));
					pos++;
				} else {
					pos = 0;
				}

			}

		}, 1, updateInt);
	}

	public String replaceVariables(String s) {
		s = s.replaceAll("%gems%", Integer.toString(Economy.getEconomy(gameUser).getCurrency(CurrencyType.GEMS)));
		s = s.replaceAll("%gold%", Integer.toString(Economy.getEconomy(gameUser).getCurrency(CurrencyType.GOLD)));
		s = s.replaceAll("%elixir%", Integer.toString(Economy.getEconomy(gameUser).getCurrency(CurrencyType.ELIXIR)));
		s = s.replaceAll("%dark_elixir%",
				Integer.toString(Economy.getEconomy(gameUser).getCurrency(CurrencyType.DARK_ELIXIR)));
		;
		s = s.replaceAll("%wins%", Integer.toString(Stats.getStatistics(gameUser).getWins()));
		s = s.replaceAll("%defeat%", Integer.toString(Stats.getStatistics(gameUser).getDefeat()));
		s = s.replaceAll("%online%", Integer.toString(Bukkit.getOnlinePlayers().size()));
		s = s.replace("%trophies%", Integer.toString(Stats.getStatistics(gameUser).getTrophies()));
		s = inColors(s);
		return s;
	}

	private String inColors(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public enum ScoreboardType {
		VILLAGE, FIGHT;

	}

}
