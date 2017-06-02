package eu.unrealdev.clashofclans.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.WorldServer;

public class UtilBlock {

	
	public static HashSet<Byte> blockAirFoliageSet = new HashSet<Byte>();
	
	
	static {
		
		blockAirFoliageSet.add((byte)0);    
        blockAirFoliageSet.add((byte)6);    
        blockAirFoliageSet.add((byte)31);   
        blockAirFoliageSet.add((byte)32);   
        blockAirFoliageSet.add((byte)37);   
        blockAirFoliageSet.add((byte)38);   
        blockAirFoliageSet.add((byte)39);   
        blockAirFoliageSet.add((byte)40);   
        blockAirFoliageSet.add((byte)51);   
        blockAirFoliageSet.add((byte)59);   
        blockAirFoliageSet.add((byte)104);  
        blockAirFoliageSet.add((byte)105);  
        blockAirFoliageSet.add((byte)115);  
        blockAirFoliageSet.add((byte)141);  
        blockAirFoliageSet.add((byte)142);
	}
	
	
	public static HashMap<Block, Double> getInRadius(Location location, double dR) {
		
		return getInRadius(location, dR, 9999);
	}
	
	
	public static HashMap<Block, Double> getInRadius(Location location, double dR, double maxHeight) {
		
		HashMap<Block, Double> blockList = new HashMap<Block, Double>();
		int iR = (int)dR + 1;
		
		
		for(int x = -iR; x <= iR; x++)
			for(int z = -iR; z <= iR; z++)
				for(int y = -iR; y <= iR; y++) {
					
					if(Math.abs(y) > maxHeight) 
						continue;
					
					Block current = location.getWorld().getBlockAt((int)(location.getX()+x), (int)(location.getY()+y), (int)(location.getZ()+z));
					
					double offset = UtilMath.offset(location, current.getLocation().add(0.5, 0.5, 0.5));
					
					if(offset <= dR)
						blockList.put(current, 1 - (offset/dR));
				}
		
		
		return blockList;
	}
	
	
	@SuppressWarnings("deprecation")
	public static boolean isBlock(ItemStack itemStack) {
		
		if(itemStack == null)
			return false;
		
		return itemStack.getTypeId() > 0 && itemStack.getTypeId() < 256;
	}
	
	
	public static HashMap<Block, Double> getInRadius(Block block, double dR) {
		
		return getInRadius(block, dR, false);
	}
	
	
	public static HashMap<Block, Double> getInRadius(Block block, double dR, boolean hollow) {
		
		HashMap<Block, Double> blockList = new HashMap<Block, Double>();
		int iR = (int)dR + 1;
		
		
		for(int x = -iR; x <= iR; x++)
			for(int z = -iR; z <= iR; z++)
				for(int y = -iR; y <= iR; y++) {
					
					Block current = block.getRelative(x, y, z);
					
					double offset = UtilMath.offset(block.getLocation(), current.getLocation());
					
					if(offset <= dR && !(hollow && offset < dR - 1)) {
						
						blockList.put(current, 1 - (offset / dR));
					}
				}
		
		
		return blockList;
	}
	
	
	public static ArrayList<Block> getInSquare(Block block, double dR) {
		
		ArrayList<Block> blockList = new ArrayList<Block>();
		int iR = (int) dR + 1;
		
		for(int x = -iR; x <= iR; x++)
			for(int z = -iR; z <= iR; z++)
				for(int y = -iR; y <= iR; y++) {
					
					blockList.add(block.getRelative(x, y, z));
				}
		
		
		return blockList;
	}
	
	
	/*
	 * 
	 * @param location of explosion
	 * @param strength of explosion
	 * @param damageBlocksEqually - Treat all blocks as durability of dirt
	 * @param ensureDestruction - Ensure that the closest blocks are destroyed at least
	 * @return
	 */
	
	@SuppressWarnings("unused")
	public static ArrayList<Block> getExplosionBlocks(Location location, float strength, boolean damageBlocksEqually) {
		
		ArrayList<Block> blockList = new ArrayList<Block>();
		WorldServer world = ((CraftWorld)location.getWorld()).getHandle();
		
		for(int i = 0; i < 16; i++) {
			
			for(int j = 0; j < 16; j++) {
				
				for(int k = 0; k < 16; k++) {
					
					if((i == 0) || (i == 16 - 1) || (j == 0) || (j == 16 - 1) || (k == 0) || (k == 16 - 1)) {
						
						double d3 = i / (16 - 1.0F) * 2.0F - 1.0F;
						double d4 = j / (16 - 1.0F) * 2.0F - 1.0F;
						double d5 = k / (16 - 1.0F) * 2.0F - 1.0F;
						double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
						
						d3 /= d6;
						d4 /= d6;
						d5 /= d6;
						float f1 = strength * (0.7F + UtilMath.random.nextFloat() * 0.6F);
						
						double d0 = location.getX();
						double d1 = location.getY();
						double d2 = location.getZ();
						
						for(float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
							
							int l = MathHelper.floor(d0);
							int i1 = MathHelper.floor(d1);
							int j1 = MathHelper.floor(d2);
							Block block = location.getWorld().getBlockAt(l, i1, j1);
							
							if(block.getType() != Material.AIR) {
								
								float f3 = 0;//(damageBlocksEqually ? Blocks.DIRT : world.getType(new BlockPosition(block.getX(), block.getY(), block.getZ())));
								
								f1 -= (f3 + 0.3F) * f2;
							}
							
							if((f1 > 0.0F) && (i1 < 256) && (i1 >= 0)) {
								
								blockList.add(block);
							}
								
							d0 += d3 * f2;
							d1 += d4 * f2;
							d2 += d5 * f2;
						}
					}
				}
			}
		}
		
		return blockList;
	}
	
	
	public static ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		if(diagonals) {
			
			for(int x = -1; x <= 1; x++)
				for(int y = -1; y <= 1; y++)
					for(int z = -1; z <= 1; z++) {
						
						if(x == 0 && y == 0 && z == 0)
							continue;
						
						
						blocks.add(block.getRelative(x, y, z));
					}
		}
		else
		{
			
			blocks.add(block.getRelative(BlockFace.UP));
			blocks.add(block.getRelative(BlockFace.DOWN));
			blocks.add(block.getRelative(BlockFace.NORTH));
			blocks.add(block.getRelative(BlockFace.SOUTH));
			blocks.add(block.getRelative(BlockFace.EAST));
			blocks.add(block.getRelative(BlockFace.WEST));
		}
		
		return blocks;
	}
	
	
	public static boolean isVisible(Block block) {
		
		for(Block other : getSurrounding(block, false)) {
			
			if(!other.getType().isOccluding()) {
				
				return true;
			}
		}
		
		return false;
	}
	
	
	public static ArrayList<Block> getInBoundingBox(Location a, Location b) {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		for(int x = Math.min(a.getBlockX(), b.getBlockX()); x <= Math.max(a.getBlockX(), b.getBlockX()); x++)
			for(int y = Math.min(a.getBlockY(), b.getBlockY()); y <= Math.max(a.getBlockY(), b.getBlockY()); y++)
				for(int z = Math.min(a.getBlockZ(), b.getBlockZ()); z <= Math.max(a.getBlockZ(), b.getBlockZ()); z++) {
					
					Block block = a.getWorld().getBlockAt(x, y, z);
					
					if(block.getType() != Material.AIR)
						blocks.add(block);
			}
		
		
		return blocks;
   }
}
