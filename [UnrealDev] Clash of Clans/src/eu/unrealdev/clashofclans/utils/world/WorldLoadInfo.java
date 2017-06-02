package eu.unrealdev.clashofclans.utils.world;

import org.bukkit.World;

public class WorldLoadInfo {
	

	private World world;
	
	private int minChunkX;
	private int minChunkZ;
	private int maxChunkX;
	private int maxChunkZ;
	
	public int currentChunkX;
	public int currentChunkZ;
	
	
	public WorldLoadInfo(World world, int minChunkX, int minChunkZ, int maxChunkX, int maxChunkZ) {
		
		this.world = world;
		this.minChunkX = minChunkX;
		this.minChunkZ = minChunkZ;
		this.maxChunkX = maxChunkX;
		this.maxChunkZ = maxChunkZ;
		
		this.currentChunkX = minChunkX;
		this.currentChunkZ = minChunkZ;
	}
	
	
	public World getWorld() {
	
		return world;
	}
	
	
	public int getMinChunkX() {
	
		return minChunkX;
	}
	
	
	public int getMinChunkZ() {
	
		return minChunkZ;
	}
	
	
	public int getMaxChunkX() {
	
		return maxChunkX;
	}
	
	
	public int getMaxChunkZ() {
	
		return maxChunkZ;
	}
}
