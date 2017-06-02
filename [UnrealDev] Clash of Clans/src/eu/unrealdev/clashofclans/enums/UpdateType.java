package eu.unrealdev.clashofclans.enums;

import eu.unrealdev.clashofclans.utils.UtilTime;

public enum UpdateType {

	MIN_64(3840000),
	MIN_32(1920000),
	MIN_16(960000),
	MIN_08(480000),
	MIN_04(240000),
	MIN_02(120000),
	MIN_01(60000),
	SLOWEST(32000),
	SLOWER(16000),
	SLOW(4000),
	TWOSEC(2000),
	SEC(1000),
	FAST(500),
	FASTER(250),
	FASTEST(125),
	TICK(49);
	
	
	private long time;
	private long last;
	private long timeSpent;
	private long timeCount;
	
	UpdateType(long time) {
		
		this.time = time;
		this.last = System.currentTimeMillis();
	}
	
	
	public boolean elapsed() {
		
		if(UtilTime.elapsed(last, time)) {
			
			this.last = System.currentTimeMillis();
			return true;
		}
		
		return false;
	}
	
	
	public void startTime() {
		
		this.timeCount = System.currentTimeMillis();
	}
	
	
	public void stopTime() {
		
		this.timeSpent += System.currentTimeMillis() - this.timeCount;
	}
	
	
	public void printAndResetTime() {
		
		System.out.println(this.name() + " in a second: " + this.timeSpent);
		this.timeSpent = 0;
	}
}
