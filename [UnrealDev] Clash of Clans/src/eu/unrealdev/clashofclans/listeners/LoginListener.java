package eu.unrealdev.clashofclans.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.user.GameUser;

public class LoginListener implements Listener {

	
	private COCMain plugin = COCMain.getInstance();
	
	
	public LoginListener() {
		
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	
	// temp. variable
	private boolean underAttack = false;
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		GameUser gameUser = GameUser.getUser(event.getPlayer());
		
		if(underAttack) {
			
			event.disallow(null, ChatColor.RED + "You are under attack!\n"
					           + ChatColor.RED + "Come back in %%%%% minutes.");
			
		}
	}
}
