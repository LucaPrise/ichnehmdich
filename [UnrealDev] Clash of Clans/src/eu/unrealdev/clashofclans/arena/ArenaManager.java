package eu.unrealdev.clashofclans.arena;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.utils.file.ConfigFile;
import eu.unrealdev.clashofclans.utils.world.UtilWorld;

public class ArenaManager {


	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	   public ArenaManager(){
	       FileConfiguration arena_config = COCMain.getConfigManager().getArenas().getConfig();
	       for (String s: arena_config.getKeys(false)){
	           String uuid = s;
	           OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	           arenas.add(new Arena(player));
	           
	       }
	   }
	   
	   
	   
	   public Arena getArena(OfflinePlayer p){
	       for (Arena a: arenas){
	           if (a.getPlayer().equals(p)){
	               return a;
	           }
	       }
	       return null;
	   }
	   
	   public boolean hasArena(OfflinePlayer p){
	       return getArena(p) !=null;
	   }
	   
	   public void createArena(OfflinePlayer player) throws Exception {
	       if (hasArena(player)) throw new Exception("Player " + player.getName() + " already has an arena... A Player cant have 2 arenas...");
	       if (!player.isOnline()) throw new Exception("The Player must be online to create an arena..");
	        Player p = player.getPlayer();
	        ConfigFile file =  COCMain.getConfigManager().getArenas();
	        file.getConfig().set(p.getUniqueId().toString() + ".location", UtilWorld.locationToString(p.getLocation()));
	        file.saveConfig();
	        arenas.add(new Arena(player));
	   }
	}
