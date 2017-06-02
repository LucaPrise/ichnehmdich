package eu.unrealdev.clashofclans.island;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Island implements Serializable {

	
	public static Map<UUID, Island> cache = new ConcurrentHashMap<UUID, Island>();
	public Map<UUID, Long> lastLooked = new HashMap<UUID, Long>();
	
	public int aX, bX;
	public int aZ, bZ;
	public int tY;
	
	private UUID playerId;
	
	public int builders = 2; //default value
	
	public int lastLookAX = Integer.MIN_VALUE;
	public int lastLookBX;
	public int lastLookAZ;
	public int lastLookBZ;
	public int lastLookY;
	public int lastLookRange;
	
	public boolean attacking = false;
	public boolean underAttack = false;
	public boolean lFraid = false;
	
	public Island lookingAt = null;
	public long lookAtTime = 0;
	
	public long lastAttack = 0;
	
	public boolean holoOn = true;
	
	
	public int[] currencies = {0,0,0,0};
	public int[] maxCurrencies = {0,0,0,0};
	public int maxPopulation = 0;
	public int currentPopulation = 0;
	
	public int maxPotionPopulation = 0;
	public int currentPotionPopulation = 0;
	
	public long lastSaved = 0;
	
	
	public String lastName = "Uknown name";
	
	public Map<String, Integer> upgrades = new HashMap<String, Integer>();
	public Map<String, Integer> potUpgrades = new HashMap<String, Integer>();
	
	
	private static final long serialVersionUID = 42L;

	
	public Island(UUID playerId, int aX, int bX, int aZ, int bZ, int tY) {
		
		this.aX = aX;
		this.bX = bX;
		this.aZ = aZ;
		this.bZ = bZ;
		this.tY = tY;
		
		this.playerId = playerId;
		
		
		this.currencies[0] = 0;
		this.currencies[1] = 0;
		this.currencies[2] = 0;
		this.currencies[3] = 0;
		
		cache.put(playerId, this);
	}
	
	
	public UUID getUniqueId() {
		
		return playerId;
	}
	
	
	public void setOwner(UUID playerId) {
		
		this.playerId = playerId;
	}
	
	
	public boolean contains(Location location) {
		
		if(this.aX <= location.getX() && location.getX() <= this.bX &&
				this.aZ <= location.getZ() && location.getZ() <= this.bZ) {
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean containsWithLeeway(Location location, double leeway) {
		
		if(this.aX - leeway <= location.getX() && location.getX() <= this.bX + leeway &&
				this.aZ - leeway <= location.getZ() && location.getZ() <= this.bZ + leeway) {
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean castSpell(Player player, String potion) {
		
		if(!attacking) {
			
			return false;
		}
		
		if(lookingAt == null) {
			
			return false;
		}
		
		//TODO: Cast spells
		
		return false;
	}
	
	
	public void setHungerPopulation(Player player) {
		
		if(maxPopulation <= 0) {
			
			player.setFoodLevel(4);
			return;
		}
		
		
		int foodLevel = (int)(0.5 + 16 - Math.max(0, currentPopulation) / (0.0 + maxPopulation) * 16) + 4;
		
		player.setFoodLevel(foodLevel);
	}
}
