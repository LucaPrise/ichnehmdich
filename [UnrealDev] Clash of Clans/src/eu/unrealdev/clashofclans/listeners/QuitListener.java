package eu.unrealdev.clashofclans.listeners;

import java.time.Instant;
import java.util.Date;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;
import eu.unrealdev.clashofclans.user.stats.Stats;

public class QuitListener implements Listener {

	
	private COCMain plugin = COCMain.getInstance();
	
	
	public QuitListener() {
		
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		
		
		GameUser user = GameUser.getUser(event.getPlayer());
		Stats.getStatistics(user).setLastPlayed(Date.from(Instant.now()));
	}

}
