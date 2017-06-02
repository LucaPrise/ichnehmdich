package eu.unrealdev.clashofclans.template;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.unrealdev.clashofclans.COCMain;
import eu.unrealdev.clashofclans.spell.Spell;

public class SpellTemplate {

	
	private static COCMain plugin = COCMain.getInstance();
	
	public static Map<String, SpellTemplate> templates = new HashMap<String, SpellTemplate>();
	
	
	public String name;
	       String descriptionShop;
	       String descriptionSpawn;
	       String descriptionUpgrade;
	       
	       String shop;
	       String upgradeShop;
	       String deployShop;
	       
	       Material material;
	       int data;
	       
	       
	public List<Map<String, Integer>> upgradeValues = new ArrayList<Map<String,Integer>>();
	
	
	public int damage;
	public int healAmount;
	public int radius;
	public int range;
	public int strikes;
	public int durationTicks;
	
	public String spellName;
	
	public int recruitTime;
	public int instantCost;
	public int populationCost;
	
	
	List<Spell> spellLevels = new ArrayList<Spell>();
	
	
	public SpellTemplate(YamlConfiguration ymlConfig) {
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unused")
	public static void loadSpellTemplates() {
		
		File parent = new File(plugin.getDataFolder().getAbsolutePath() + "/spells/");
		File[] files = parent.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return name.toLowerCase().endsWith(".yml");
			}
		});
		
		for(int i = 0; i < files.length; i++) {
			
			File file = files[i];
			YamlConfiguration yml = new YamlConfiguration();
			
			try {
				
				yml.load(file);
				SpellTemplate template = new SpellTemplate(yml);
				
				
			} catch(Exception ex) {
				
				System.out.println("Error while setting up spells: " + ex.getMessage());
			}
		}
	}
}
