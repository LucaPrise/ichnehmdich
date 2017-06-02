package eu.unrealdev.clashofclans.utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class UtilServer {

	
	public static Player[] getPlayers() {
		
		return getServer().getOnlinePlayers().toArray(new Player[0]);
	}
	
	
	public static Server getServer() {
		
		return Bukkit.getServer();
	}
	
	
	public static double getFilledPercent() {
		
		return (double) getPlayers().length / (double) getServer().getMaxPlayers();
	}
}
