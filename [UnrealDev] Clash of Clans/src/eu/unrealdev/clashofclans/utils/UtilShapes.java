package eu.unrealdev.clashofclans.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class UtilShapes {
	
	
	private final static BlockFace[] radial = {
			
			BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH,
			BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST
	};
	
	
	public static ArrayList<Location> getCircleBlocks(Location location, boolean hollow, double radius) {
		
		return getCircleBlocks(location, radius, 0, hollow, false);
	}
	
	
	public static ArrayList<Location> getSphereBlocks(Location location, double radius, double height, boolean hollow) {
		
		return  getCircleBlocks(location, radius, height, hollow, true);
	}
	
	
	private static ArrayList<Location> getCircleBlocks(Location location, double radius, double height, boolean hollow, boolean sphere) {
		
		ArrayList<Location> circleBlocks = new ArrayList<Location>();
		
		double cx = location.getBlockX();
		double cy = location.getBlockY();
		double cz = location.getBlockZ();
		
		for(double y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height + 1); y++) {
			
			for(double x = cx - radius; x <= cx + radius; x++) {
				
				for(double z = cz - radius; z <= cz + radius; z++) {
					
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					
					if(dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
						
						Location loc = new Location(location.getWorld(), x, y, z);
						circleBlocks.add(loc);
					}
				}
			}
		}
		
		return circleBlocks;
	}
	
	
	/*
	 * Gets the block at the exact corners, will return a diagonal
	 * 
	 */
	
	public static BlockFace[] getCornerBlockFaces(Block block, BlockFace facing) {
		
		
		BlockFace left = null;
		BlockFace right = null;
		
		for(int i = 0; i < radial.length; i++) {
			
			if(radial[i] == facing) {
				
				int high = i + 2;
				if(high >= radial.length)
					high = high - radial.length;
				
				int low = i - 2;
				if(low < 0)
					low = radial.length + low;
				
				left = radial[low];
				right = radial[high];
				
				return new BlockFace[] {
						
						left, right
				};
			}
		}
		
		return null;
	}
	
	
	public static Block[] getCornerBlocks(Block block, BlockFace facing) {
		
		BlockFace[] faces = getSideBlockFaces(facing);
		
		return new Block[] {
				
				block.getRelative(faces[0]), block.getRelative(faces[1])
		};
	}
	
	
	public static BlockFace getFacing(float yaw) {
		
		return radial[Math.round(yaw / 45f) & 0x7];
	}
	
	
	public static ArrayList<Location> getLinesDistancedPoints(Location startingPoint, Location endingPoint, double distance) {
		
		return getLinesLimitedPoints(startingPoint, endingPoint, (int) Math.ceil(startingPoint.distance(endingPoint) / distance));
	}
	
	
	public static ArrayList<Location> getLinesLimitedPoints(Location startingPoint, Location endingPoint, int amountOfPoints) {
		
		startingPoint = startingPoint.clone();
		Vector vector = endingPoint.toVector().subtract(startingPoint.toVector());
		vector.normalize();
		vector.multiply(startingPoint.distance(endingPoint) / (amountOfPoints + 1D));
		
		ArrayList<Location> locations = new ArrayList<Location>();
		
		for(int i = 0; i < amountOfPoints; i++) {
			
			locations.add(startingPoint.add(vector).clone());
		}
		
		return locations;
	}
	
	
	public static ArrayList<Location> getPointsInCircle(Location center, int amount, double radius) {
		
		ArrayList<Location> locations = new ArrayList<Location>();
		
		for(int i = 0; i < amount; i++) {
			
			double angle = ((2 * Math.PI) / amount) * i;
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Location location = center.clone().add(x, 2, z);
			locations.add(location);
		}
		
		return locations;
	}
	
	
	public static ArrayList<Location> getDistancedCircle(Location center, double distance, double radius) {
		
		return getPointsInCircle(center, (int) ((radius * Math.PI * 2) / distance), radius);
	}
	
	
	public static BlockFace[] getSideBlockFaces(BlockFace facing) {
		
		return getSideBlockFaces(facing, true);
	}
	
	
	public static BlockFace[] getSideBlockFaces(BlockFace facing, boolean allowDiagonal) {
		
		int[][] facesXZ;
		allowDiagonal = !allowDiagonal && (facing.getModX() != 0 && facing.getModZ() != 0);
		
		facesXZ = new int[][] {
			
			new int[] {
					
					allowDiagonal ? facing.getModX() : facing.getModZ(), allowDiagonal ? 0 : -facing.getModX()
			},
			
			new int[] {
					
					allowDiagonal ? 0 : -facing.getModZ(), allowDiagonal ? facing.getModZ() : facing.getModX()
			}
		};
		
		
		BlockFace[] faces = new BlockFace[2];
		
		for(int i = 0; i < 2; i++) {
			
			int[] f = facesXZ[i];
			
			for(BlockFace face : BlockFace.values()) {
				
				if(face.getModY() == 0) {
					
					if(f[0] == face.getModX() && f[1] == face.getModZ()) {
						
						faces[i] = face;
						break;
					}
				}
			}
		}
		
		if(allowDiagonal && (facing == BlockFace.NORTH_EAST || facing == BlockFace.SOUTH_EAST)) {
			
			faces = new BlockFace[] {
					
					faces[1], faces[2]
			};
		}
		
		return faces;
	}
	
	
	public static ArrayList<Block> getDiagonalBlocks(Block block, BlockFace facing, int width) {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		if(facing.getModX() == 0 || facing.getModZ() == 0) {
			
			return blocks;
		}
		
		
		BlockFace[] faces = getSideBlockFaces(facing);
		
		for(BlockFace face : faces) {
			
			Location location = block.getLocation().add(0.5 + (facing.getModX() / 2D), 0, 0.5 + (facing.getModZ() / 2D));
			
			blocks.add(location.add(face.getModX() / 2D, 0, face.getModZ() / 2D).getBlock());
			
			for(int i = 1; i < width; i++) {
				
				blocks.add(location.add(face.getModX(), 0, face.getModZ()).getBlock());
			}
		}
		
		return blocks;
	}
	
	
	public static Block[] getSideBlocks(Block block, BlockFace facing) {
		
		BlockFace[] faces = getSideBlockFaces(facing);
		
		return new Block[] {
				
			block.getRelative(faces[0]), block.getRelative(faces[1])	
		};
	}
}
