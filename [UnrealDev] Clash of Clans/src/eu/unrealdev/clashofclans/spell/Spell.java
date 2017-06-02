package eu.unrealdev.clashofclans.spell;

import org.bukkit.entity.Player;

import eu.unrealdev.clashofclans.island.Island;

public interface Spell {

	/**
	 * 
	 * @return successful or not
	 */
	public boolean cast(Player player, Island attacker, Island underAttack);
}
