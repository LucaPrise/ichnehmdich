package eu.unrealdev.clashofclans.shop;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.unrealdev.clashofclans.COCMain;

public class ShopManager {

	
	private static COCMain plugin = COCMain.getInstance();
	
	public static Map<String, Inventory> cache = new HashMap<String, Inventory>();
	
	
	public static void put(ItemStack itemStack, String inventory) {
		
		switch (inventory) {
		
		case "main":
			
			//Add item
			break;

		default:
			
			if(!cache.containsKey(inventory)) {
				
				getUpgradeShop(inventory);
			}
			
			cache.get(inventory).addItem(itemStack);
			
			break;
		}
	}
	
	
	public static void put(ItemStack itemStack, String inventory, int position) {
		
		switch (inventory) {
		
		case "main":
			
			//Add item to main shop
			break;

		default:
			
			if(!cache.containsKey(inventory)) {
				
				getUpgradeShop(inventory);
			}
			
			if(position < 0) {
				
				Inventory inv = cache.get(inventory);
				inv.setItem(inv.getSize() + position, itemStack);
			} else {
				
				cache.get(inventory).setItem(position, itemStack);
			}
			break;
		}
	}
	
	
	public static Inventory getShop(String key) {
		
		if(hasShop(key)) {
			
			return cache.get(key);
		} else {
			
			Inventory inventory = plugin.getServer().createInventory(null, 54, key);
			
			return inventory;
		}
	}
	
	
	public static boolean hasShop(String key) {
		
		return cache.containsKey(key);
	}
	
	
	public static Inventory getUpgradeShop(String key) {
		
		return getUpgradeShop(key, 9);
	}
	
	
	public static Inventory getUpgradeShop(String key, int size) {
		
		if(hasShop(key)) {
			
			return cache.get(key);
		} else {
			
			Inventory inventory = plugin.getServer().createInventory(null, size, key);
			
			cache.put(key, inventory);
			
			return inventory;
		}
	}
}
