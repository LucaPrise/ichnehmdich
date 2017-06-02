package eu.unrealdev.clashofclans.arena;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;



import org.bukkit.configuration.file.FileConfiguration;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.utils.world.UtilWorld;

public class Arena {

	private OfflinePlayer player;
	private Location center;
	public Arena(OfflinePlayer p){
	this.player = p;
	  // LOAD FROM ARENAS FILE
	FileConfiguration arena_config = COCMain.getConfigManager().getArenas().getConfig();
	String center_string = arena_config.getString(player.getUniqueId().toString()+".location");
	  
	center = UtilWorld.stringToLocation(center_string);
	   
	  }
	  
	  
	public OfflinePlayer getPlayer(){
	return this.player;
	}
	  
	  
	  /**
	   * returns the center of the arena
	   */
    public Location getCenter(){
	    return center;
	  }
	}
