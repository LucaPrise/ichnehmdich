package eu.unrealdev.clashofclans.utils;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.enums.UpdateType;
import eu.unrealdev.clashofclans.utils.event.UpdateEvent;

public class Updater implements Runnable {

	
	private static COCMain plugin;
	
	
	public Updater() {
		
		plugin = COCMain.getInstance();
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
	}
	
	
	@Override
	public void run() {
		
		for(UpdateType updateType : UpdateType.values()) {
			
			if(updateType.elapsed()) {
				
				plugin.getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
			}
		}
	}

}
