package eu.unrealdev.clashofclans.utils.world;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.generator.ChunkGenerator;

import net.minecraft.server.v1_8_R3.Convertable;
import net.minecraft.server.v1_8_R3.ServerNBTManager;
import net.minecraft.server.v1_8_R3.WorldLoaderServer;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import net.minecraft.server.v1_8_R3.WorldType;

@SuppressWarnings("unused")
public class WorldUtil {

	
	/*
	 * 
	 * UNFINISHED AT THE MOMENT
	 * 
	 * 
	 */

	public static World loadWorld(WorldCreator worldCreator) {
		
		CraftServer craftServer = (CraftServer)Bukkit.getServer();
		
		if(craftServer == null) {
			
			throw new IllegalArgumentException("WorldCreator may not be null");
		}
		
		String name = worldCreator.name();
		
		System.out.println("Loading world '" + name + "'...");
		
		ChunkGenerator generator = worldCreator.generator();
		File folder = new File(craftServer.getWorldContainer(), name);
		World world = craftServer.getWorld(name);
		WorldType type = WorldType.getType(worldCreator.type().getName());
		boolean generateStructures = world.canGenerateStructures();
		
		if(world != null) {
			
			return world;
		}
		
		
		if((folder.exists()) && (!folder.isDirectory())) {
			
			throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder!");
		}
		
		
		if(generator == null) {
			
			generator = craftServer.getGenerator(name);
		}
		
		
		Convertable converter = new WorldLoaderServer(craftServer.getWorldContainer());
		
		if(converter.isConvertable(name)) {
			
			craftServer.getLogger().info("Converting world '" + name + "'...");
			//converter.convert(name, new ConvertProgressUpdater)
		}
		
		
		int dimension = craftServer.getWorlds().size() + 1;
		boolean used = false;
		
		do {
			
			for(WorldServer worldServer : craftServer.getServer().worlds) {
				
				used = worldServer.dimension == dimension;
				
				if(used) {
					
					dimension++;
					break;
				}
			}
			
			
		} while(used);
		
		boolean hardcore = false;
		
		System.out.println("Loading world with dimension: " + dimension);
		
		//@SuppressWarnings("deprecation")
		//WorldServer internal = new WorldServer(craftServer.getServer(), name, dimension, new WorldSettings(worldCreator.seed(), EnumGamemode.getById(craftServer.getDefaultGameMode().getValue()), generateStructures, hardcore, type), craftServer.getServer().methodProfiler, worldCreator.environment(), generator);
	 return null;
	}
}
