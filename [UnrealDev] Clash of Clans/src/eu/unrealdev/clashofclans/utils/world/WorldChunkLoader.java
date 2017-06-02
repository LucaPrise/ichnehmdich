package eu.unrealdev.clashofclans.utils.world;

import java.util.Iterator;

import org.bukkit.Bukkit;

import eu.unrealdev.clashofclans.utils.COCHashMap;

public class WorldChunkLoader implements Runnable {

	
	private static WorldChunkLoader worldChunkLoader = null;
	
	private COCHashMap<WorldLoadInfo, Runnable> worldRunnableMap = new COCHashMap<WorldLoadInfo, Runnable>();
	
	private long loadPassStart;
	private long maxPassTime = 25;
	
	private WorldChunkLoader() {
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0], this, 0, 1L);
	}
	
	
	public static void addWorld(WorldLoadInfo worldInfo, Runnable runnable) {
		
		if(worldChunkLoader == null) {
			
			worldChunkLoader = new WorldChunkLoader();
		}
		
		worldChunkLoader.worldRunnableMap.put(worldInfo, runnable);
	}
	
	
	@Override
	public void run() {
		
		loadPassStart = System.currentTimeMillis();
		
		Iterator<WorldLoadInfo> worldInfoIterator = worldRunnableMap.keySet().iterator();
		
		while(worldInfoIterator.hasNext()) {
			
			WorldLoadInfo worldInfo = worldInfoIterator.next();
			System.out.println("Loading chunks for " + worldInfo.getWorld().getName());
			
			while(worldInfo.currentChunkX <= worldInfo.getMaxChunkX()) {
				
				while(worldInfo.currentChunkZ <= worldInfo.getMaxChunkZ()) {
					
					if(System.currentTimeMillis() - loadPassStart >= maxPassTime)
						return;
					
					worldInfo.getWorld().loadChunk(worldInfo.currentChunkX, worldInfo.currentChunkZ);
					worldInfo.currentChunkZ++;
				}
				
				worldInfo.currentChunkZ = worldInfo.getMinChunkZ();
				worldInfo.currentChunkX++;
			}
			
			worldRunnableMap.get(worldInfo).run();
			worldInfoIterator.remove();
		}
	}

}
