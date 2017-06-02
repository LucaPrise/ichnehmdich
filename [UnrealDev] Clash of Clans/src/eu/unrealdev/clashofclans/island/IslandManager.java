package eu.unrealdev.clashofclans.island;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class IslandManager {

	
	private Map<IslandBounds, UUID> islands = new HashMap<IslandBounds, UUID>();
	
	
	public boolean hasAvailableIsland() {
		
		for(UUID uuid : islands.values()) {
			
			if(uuid == null) {
				
				return true;
			}
		}
		
		return false;
	}
	
	
	public IslandBounds getAvailableIsland() {
		
		for(IslandBounds bounds : islands.keySet()) {
			
			if(islands.get(bounds) == null) {
				
				return bounds;
			}
		}
		
		return null;
	}
	
	
	public IslandBounds takeAvailableIsland(UUID uuid) {
		
		IslandBounds bounds = null;
		
		for(Entry<IslandBounds, UUID> entry : islands.entrySet()) {
			
			if(entry.getValue() == null) {
				
				bounds = entry.getKey();
				break;
			}
		}
		
		if(bounds != null) {
			
			islands.put(bounds, uuid);
		}
		
		return bounds;
	}
	
	
	public void release(final UUID uuid) {
		
		IslandBounds bounds = null;
		
		for(Entry<IslandBounds, UUID> entry : islands.entrySet()) {
			
			if(entry.getValue() != null) {
				
				if(entry.getValue().equals(uuid)) {
					
					bounds = entry.getKey();
					break;
				}
			}
		}
		
		if(bounds != null) {
			
			islands.put(bounds, uuid);
		}
	}
}
