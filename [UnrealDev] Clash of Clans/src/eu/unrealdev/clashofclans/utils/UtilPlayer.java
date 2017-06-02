package eu.unrealdev.clashofclans.utils;

import java.util.Collection;
import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class UtilPlayer {

	
	public static boolean isOnGround(Player player) {
		
		return ((CraftPlayer)player).isOnGround();
	}
	
	
	private static boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
		
		final double epsilon = 0.0001f;
		
		Vector3D d = p2.subtract(p1).multiply(0.5);
		Vector3D e = max.subtract(min).multiply(0.5);
		Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
		Vector3D ad = d.abs();
		
		if(Math.abs(c.x) > e.x + ad.x)
			return false;
		if(Math.abs(c.y) > e.y + ad.y)
			return false;
		if(Math.abs(c.z) > e.z + ad.z)
			return false;
		
		if(Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
			return false;
		if(Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
			return false;
		if(Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
			return false;
		
		
		return true;
	}
	
	
	private static class Vector3D {
		
		private final double x;
		private final double y;
		private final double z;
		
		
		private Vector3D(double x, double y, double z) {
			
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		
		private Vector3D(Location location) {
			
			this(location.toVector());
		}
		
		
		private Vector3D(Vector vector) {
			
			if(vector == null)
				throw new IllegalArgumentException("Vector cannot be NULL");
			
			this.x = vector.getX();
			this.y = vector.getY();
			this.z = vector.getZ();
		}
		
		
		private Vector3D abs() {
			
			return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
		}
		
		
		private Vector3D add(double x, double y, double z) {
			
			return new Vector3D(this.x + x, this.y + y, this.z + z);
		}
		
		
		private Vector3D add(Vector3D other) {
			
			if (other == null)
				throw new IllegalArgumentException("other cannot be NULL");

			return new Vector3D(x + other.x, y + other.y, z + other.z);
		}

		private Vector3D multiply(double factor) {
			
			return new Vector3D(x * factor, y * factor, z * factor);
		}

		private Vector3D multiply(int factor) {
			
			return new Vector3D(x * factor, y * factor, z * factor);
		}

		private Vector3D subtract(Vector3D other) {
			
			if (other == null)
				throw new IllegalArgumentException("other cannot be NULL");
			return new Vector3D(x - other.x, y - other.y, z - other.z);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static Player getPlayerInSight(Player player, int range, boolean lineOfSight) {
		
		Location observerPos = player.getEyeLocation();
		Vector3D observerDir = new Vector3D(observerPos.getDirection());
		Vector3D observerStart = new Vector3D(observerPos);
		Vector3D observerEnd = observerStart.add(observerDir.multiply(range));
		
		Player hit = null;
		
		for(Entity entity : player.getNearbyEntities(range, range, range)) {
			
			if(entity == player)
				continue;
			
			double theirDist = player.getEyeLocation().distance(entity.getLocation());
			
			if(lineOfSight && player.getLastTwoTargetBlocks(UtilBlock.blockAirFoliageSet,
			(int) Math.ceil(theirDist)).get(0).getLocation().distance(player.getEyeLocation()) + 1 < theirDist) 
				continue;
			
			Vector3D targetPos = new Vector3D(entity.getLocation());
			Vector3D min = targetPos.add(-0.5, 0, -0.5);
			Vector3D max = targetPos.add(0.5, 1.67, 0.5);
			
			if(hasIntersection(observerStart, observerEnd, min, max)) {
				
				if(hit == null || hit.getLocation().distanceSquared(observerPos) > entity.getLocation().distanceSquared(observerPos)) {
					
					hit = (Player) entity;
				}
			}
		}
		
		return hit;
	}
	
	
	public static LinkedList<Player> getNearby(Location location, double maxDist) {
		
		LinkedList<Player> nearbyMap = new LinkedList<Player>();
		
		for(Player player : location.getWorld().getPlayers()) {
			
			if(player.isDead())
				continue;
			
			double dist = location.toVector().subtract(player.getLocation().toVector()).length();
			
			if(dist > maxDist)
				continue;
			
			for(int i = 0; i < nearbyMap.size(); i++) {
				
				if(dist < location.toVector().subtract(nearbyMap.get(i).getLocation().toVector()).length()) {
					
					nearbyMap.add(i, player);
					break;
				}
			}
			
			if(!nearbyMap.contains(player))
				nearbyMap.addLast(player);
		}
		
		
		return nearbyMap;
	}
	
	
	public static Player getCloset(Location location, Collection<Player> ignore) {
		
		Player best = null;
		double bestDist = 0;
		
		for(Player player : location.getWorld().getPlayers()) {
			
			if(player.isDead())
				continue;
			
			if(ignore != null && ignore.contains(player))
				continue;
			
			double dist = UtilMath.offset(player.getLocation(), location);
			
			if(best == null || dist < bestDist) {
				
				best = player;
				bestDist = dist;
			}
		}
		
		return best;
	}
	
	
	public static Player getCloset(Location location, Entity ignore) {
		
		Player best = null;
		double bestDist = 0;
		
		for(Player player : location.getWorld().getPlayers()) {
			
			if(player.isDead())
				continue;
			
			if(ignore != null && ignore.equals(player))
				continue;
			
			double dist = UtilMath.offset(player.getLocation(), location);
			
			if(best == null || dist < bestDist) {
				
				best = player;
				bestDist = dist;
			}
		}
		
		return best;
	}
	
	
	public static void kick(Player player, String module, String message, boolean log) {
		
		if(player == null)
			return;
		
		String out = ChatColor.RED + module + ChatColor.WHITE + " - " + ChatColor.YELLOW + message;
		player.kickPlayer(out);
	}
	
	
	public static void sendPacket(Player player, Packet<?>... packets) {
		
		PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
		
		for(Packet<?> packet : packets) {
			
			connection.sendPacket(packet);
		}
	}
}
