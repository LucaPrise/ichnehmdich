package eu.unrealdev.clashofclans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;

public class JoinListener implements Listener {

	
	private COCMain plugin = COCMain.getInstance();
	
	
	public JoinListener() {
		
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		event.setJoinMessage(null);
		
		Player player = event.getPlayer();
		GameUser user = new GameUser(player);
	}
	
	
	

	
}
