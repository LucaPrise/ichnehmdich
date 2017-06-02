package eu.unrealdev.clashofclans.utils;

import org.bukkit.entity.Player;

public class RaidTracker {

	
	public int gold = 0;
	public int elixir = 0;
	public int dark_elixir = 0;
	
	public int destroyed = 0;
	public boolean townhall = false;
	
	public long started;
	
	
	public RaidTracker() {
		
		this.started = System.currentTimeMillis();
	}
	
	
	public boolean isTimeOver() {
		
		if(System.currentTimeMillis() - this.started >= 180000) {
			
			return true;
		}
		
		return false;
	}
	
	
	public void setExpTimeLeft(Player player) {
		
		int newlvl = (int)(180000 - (System.currentTimeMillis() - this.started)) / 1000;
		player.setLevel(newlvl);
		
		if(newlvl == 0) {
			
			player.setTotalExperience(0);
			return;
		}
		
		player.setExp(Math.min(1, (1 - (System.currentTimeMillis() - this.started) / 180000f)));
	}
}
