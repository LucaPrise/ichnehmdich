package eu.unrealdev.clashofclans.user;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.unrealdev.clashofclans.user.economy.Economy;
import eu.unrealdev.clashofclans.user.stats.Stats;

public class GameUser {

	private Player player = null;
	private Player spectator;
	private UUID uuid = null;
	private boolean fighting, spectating;
	public static HashMap<UUID, GameUser> cache = new HashMap<UUID, GameUser>();

	public GameUser(Player player) {

		this.player = player;
		this.uuid = player.getUniqueId();
		initialize();
	}

	public GameUser(UUID uuid) {

		this.uuid = uuid;

		Player player = Bukkit.getPlayer(uuid);

		if (player != null)
			this.player = player;

		initialize();
	}

	public static GameUser getUser(Player player) {

		if (cache.containsKey(player.getUniqueId())) {

			return cache.get(player.getUniqueId());
		} else {

			return new GameUser(player);
		}
	}

	private void initialize() {
		// load statistics
		Stats.getStatistics(this);
		// load economy
		Economy.getEconomy(this);
		if (player == null) {
			Player player = Bukkit.getPlayer(this.uuid);

			if (player != null) {

				this.player = player;
			}

			cache.put(uuid, this);
		}

	}

	public boolean isSpectating() {

		return spectating;
	}

	public void setSpectating(boolean spectating) {

		this.spectating = spectating;
	}

	public void sendMessage(String arg0, ChatColor color) {

		player.sendMessage(ChatColor.GRAY + " < " + ChatColor.WHITE + ChatColor.BOLD.toString() + "COC" + ChatColor.GRAY
				+ " > " + color + arg0);
	}

	public Player getSpectator() {

		return spectator;
	}

	public void setSpectator(Player spectator) {

		this.spectator = spectator;
	}

	public boolean isFighting() {

		return fighting;
	}

	public void setFighting(boolean fighting) {

		this.fighting = fighting;
	}

	public Player getPlayer() {
		return this.player;
	}
}
