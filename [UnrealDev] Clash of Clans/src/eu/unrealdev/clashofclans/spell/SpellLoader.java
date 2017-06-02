package eu.unrealdev.clashofclans.spell;

public class SpellLoader {

	public String potionId;
	public long readyAt;
	
	
	public SpellLoader(String potionId, long readyAt) {
		
		this.potionId = potionId;
		this.readyAt = readyAt;
	}
	
	
	public boolean isReady(long current) {
		
		return (current >= readyAt);
	}
	
	
	public boolean isReady() {
		
		return isReady(System.currentTimeMillis());
	}
}
