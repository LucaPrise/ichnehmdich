package eu.unrealdev.clashofclans.enums;

import org.bukkit.Material;

public enum CurrencyType {

	
	GOLD(" Gold", Material.DOUBLE_PLANT),
	ELIXIR(" Elixir", Material.RED_MUSHROOM),
	DARK_ELIXIR(" Dark Elixir", Material.COAL),
	GEMS(" Gems", Material.EMERALD);
	
	
	private String displayName;
	private Material displayMaterial;
	
	
    CurrencyType(String displayName, Material displayMaterial) {
		
    	this.displayName = displayName;
    	this.displayMaterial = displayMaterial;
	}
    
    
    public String getDisplayName() {
	
    	return displayName;
	}
    
    
    public Material getDisplayMaterial() {
	
    	return displayMaterial;
	}
}
