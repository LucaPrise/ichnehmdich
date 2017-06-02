package eu.unrealdev.clashofclans.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import eu.unrealdev.clashofclans.template.SpellTemplate;

public class SpellManager {

	
	public List<SpellLoader> processing = new ArrayList<SpellLoader>();
	public Map<String, Integer> cache = new HashMap<String, Integer>();
	
	
	public SpellManager() {
		
	}
	
	
	public void loadNewSpell(String id, long readyAt) {
		
		this.processing.add(new SpellLoader(id, readyAt));
	}
	
	
	public void processQueue() {
		
		long current = System.currentTimeMillis();
		
		Iterator<SpellLoader> spells = this.processing.iterator();
		
		while(spells.hasNext()) {
			
			SpellLoader spell = spells.next();
			
			if(spell.isReady(current)) {
				
				String s = spell.potionId;
				
				if(this.cache.containsKey(s)) {
					
					this.cache.put(s, this.cache.get(s) + 1);
				} else {
					
					this.cache.put(s, 1);
				}
				
				spells.remove();
			} 
		}
	}
	
	
	public int calculateCurrentSpellPopulation() {
		
		Map<String, Integer> spells = new HashMap<String, Integer>();
		
		for(String s : this.cache.keySet()) {
			
			spells.put(s, this.cache.get(s));
		}
		
		for(SpellLoader loader : this.processing) {
			
			if(spells.containsKey(loader.potionId)) {
				
				spells.put(loader.potionId, spells.get(loader.potionId) + 1);
			} else {
				
				spells.put(loader.potionId, 1);
			}
		}
		
		int current = 0;
		
		for(String spell : spells.keySet()) {
			
			if(SpellTemplate.templates.containsKey(spell)) {
				
				current += SpellTemplate.templates.get(spell).populationCost * spells.get(spell);
			}
		}
		
		return current;
	}
}
