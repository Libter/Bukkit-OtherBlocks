package com.gmail.zariust.otherdrops.parameters.conditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.ConfigurationNode;
import com.gmail.zariust.otherdrops.Log;
import com.gmail.zariust.otherdrops.event.OccurredEvent;

public class MobSpawnerCheck extends Condition {
	private Integer radius;
	
	String name = "MobSpawnerCheck";
	
	public MobSpawnerCheck(Integer radius) {
		this.radius = radius;
	}

	@Override
	public boolean checkInstance(OccurredEvent occurrence) {
		Player player = occurrence.getPlayerAttacker();
		if (player == null || radius == null) return true; // no player to check so don't fail condition
		return MobSpawnerCheck.mobSpawnerNear(player.getLocation(), radius);
	}

	public static List<Condition> parse(ConfigurationNode node) {
		Integer radius = node.getInteger("mobspawnerinradius", "mobspawnerwithinradius", "msir");
		if (radius == null) return null;

		List<Condition> conditionList = new ArrayList<Condition>();
		conditionList.add(new MobSpawnerCheck(radius));
		return conditionList;
	}	

	
	

	/** mobSpawnerNear - determines if a mobspawner is within radius of a given location
	 *                   (used under Public Domain licence from MobBounty by IchigoKyger) 
	 * @param loc - location to check for spawners within given radius 
	 * @param radius - distance from location to check
	 * @return true if spawner within radius, otherwise false
	 */
	private static boolean mobSpawnerNear(Location loc, int radius) {
		if (radius == 0 || loc == null) return false;
		
		World world;
		int x1, x2, y1, y2, z1, z2;
	
		world = loc.getWorld();
		x1 = (int) (loc.getX());
		y1 = (int) (loc.getY());
		z1 = (int) (loc.getZ());
	
		for (x2 = 0 - radius; x2 <= radius; x2++) {
			for (y2 = 0 - radius; y2 <= radius; y2++) {
				for (z2 = 0 - radius; z2 <= radius; z2++) {
					Block block = world.getBlockAt(x1 + x2, y1 + y2, z1 + z2);
	
					if (block.getType() == Material.MOB_SPAWNER)
						return true;
				}
			}
		}
		return false;
	}

}
