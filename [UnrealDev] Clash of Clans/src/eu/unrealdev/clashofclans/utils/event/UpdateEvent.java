package eu.unrealdev.clashofclans.utils.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import eu.unrealdev.clashofclans.enums.UpdateType;

public class UpdateEvent extends Event {

	
	private static final HandlerList handlers = new HandlerList();
	private UpdateType updateType;
	
	
	public UpdateEvent(UpdateType type) {
		
		this.updateType = type;
	}
	
	
	public UpdateType getType() {
		return updateType;
	}
	
	
	
	public HandlerList getHandlers() {
		
		return handlers;
	}
	
	
	public static HandlerList getHandlerList() {
		
		return handlers;
	}
}
