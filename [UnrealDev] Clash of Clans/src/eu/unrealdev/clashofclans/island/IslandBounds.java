package eu.unrealdev.clashofclans.island;

public class IslandBounds {

	
	public int aX,bX;
	public int aZ,bZ;
	public int tY;
	
	
	public IslandBounds(int aX, int bX, int aZ, int bZ, int tY) {
		
		this.aX = aX;
		this.bX = bX;
		this.aZ = aZ;
		this.bZ = bZ;
		this.tY = tY;
		
		if(this.bX < this.aX) {
			
			int temporary = this.aX;
			
			this.aX = this.bX;
			this.bX = temporary;
		}
		
		if(this.bZ < this.aZ) {
			
			int temporary = this.aZ;
			
			this.aZ = this.bZ;
			this.bZ = temporary;
		}
	}
	
	
	public boolean isEqualTo(IslandBounds bounds) {
		
		return (bounds.aX == aX && bounds.bX == bX && bounds.aZ == aZ && bounds.bZ == bZ && bounds.tY == tY);
	}
}
