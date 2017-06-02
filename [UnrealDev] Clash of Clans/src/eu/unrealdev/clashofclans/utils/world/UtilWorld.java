package eu.unrealdev.clashofclans.utils.world;

import java.util.Collection;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.util.Vector;

import eu.unrealdev.clashofclans.utils.UtilMath;
import eu.unrealdev.clashofclans.utils.UtilServer;

public class UtilWorld {

	
	public static World getWorld(String world) {
		
		return UtilServer.getServer().getWorld(world);
	}
	
	
	public static String chunkToString(Chunk chunk) {
		
		if(chunk == null)
			return "";
		
		return chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
	}
	
	
	public static String chunkToStringClean(Chunk chunk) {
		
		if(chunk == null)
			return "";
		
		return "(" + chunk.getX() + "," + chunk.getZ() + ")";
	}
	
	
	public static Chunk stringToChunk(String string) {
		
		try {
			
			String[] tokens = string.split(",");
			
			return getWorld(tokens[0]).getChunkAt(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
			
		} catch(Exception ex) {
			
			return null;
		}
	}
	
	
	public static String locationToString(Location location) {
		
		if(location == null)
			return "";
		
		return location.getWorld().getName() + "," +
		UtilMath.trim(1, location.getX()) + "," +
		UtilMath.trim(1, location.getY()) + "," +
		UtilMath.trim(1, location.getZ());
	}
	
	
	public static String locationToStringClean(Location location) {
		
		if(location == null)
			return "";
		
		return "(" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ")";
	}
	
	
	public static Location stringToLocation(String string) {
		
		if(string.length() == 0)
			return null;
		
		String[] tokens = string.split(",");
		
		for(World current : UtilServer.getServer().getWorlds()) {
			
			if(current.getName().equalsIgnoreCase(tokens[0])) {
				
				return new Location(current, Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
			}
		}
		
		try {
			
		} catch(Exception ex) {
			
			return null;
		}
		
		return null;
	}
	
	
	public static Location mergeLocation(Location locA, Location locB) {
		
		locA.setX(locB.getX());
		locA.setY(locB.getY());
		locA.setZ(locB.getZ());
		
		return locA;
	}
	
	
	public static String environmentToString(Environment environment) {
		
		if(environment == Environment.NORMAL)  return "Overworld";
		if(environment == Environment.NETHER)  return "Nether";
		if(environment == Environment.THE_END) return "The End";
		return "Unknow";
	}
	
	
	public static World getWorldType(Environment environment) {
		
		for(World current : UtilServer.getServer().getWorlds()) {
			
			if(current.getEnvironment() == environment) {
				
				return current;
			}
			
		}
		
		return null;
	}
	
	
	public static Location averageLocation(Collection<Location> locations) {
		
		if(locations.isEmpty())
			return null;
		
		Vector vector = new Vector(0, 0, 0);
		double count = 0;
		
		World world = null;
		
		for(Location spawn : locations) {
			
			count++;
			vector.add(spawn.toVector());
			
			world = spawn.getWorld();
		}
		
		vector.multiply(1d/count);
		
		return vector.toLocation(world);
	}
}
