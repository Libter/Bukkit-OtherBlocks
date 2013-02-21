package com.gmail.zariust.otherdrops.data.entities;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.Log;
import com.gmail.zariust.otherdrops.OtherDropsConfig;
import com.gmail.zariust.otherdrops.data.CreatureData;
import com.gmail.zariust.otherdrops.data.Data;
import com.gmail.zariust.otherdrops.drop.ItemDrop;

public class LivingEntityData extends CreatureData {
	Integer maxHealth = null;
	CreatureEquipment equip = null;
	
	public LivingEntityData(Integer maxHealth, CreatureEquipment equip) {
		this.maxHealth = maxHealth;
		this.equip = equip;
	}

	@Override
	public void setOn(Entity mob, Player owner) {
		if (mob instanceof LivingEntity) {
			LivingEntity z = (LivingEntity)mob;
			if (maxHealth != null) {
				z.setMaxHealth(maxHealth);
				z.setHealth(maxHealth);
			}
			if (equip != null) {
				if (equip.head != null) z.getEquipment().setHelmet(equip.head);
				if (equip.headChance != null) z.getEquipment().setHelmetDropChance(equip.headChance);
				if (equip.hands != null) z.getEquipment().setItemInHand(equip.hands);
				if (equip.handsChance != null) z.getEquipment().setItemInHandDropChance(equip.handsChance);
				if (equip.chest != null) z.getEquipment().setChestplate(equip.chest);
				if (equip.chestChance != null) z.getEquipment().setChestplateDropChance(equip.chestChance);
				if (equip.legs != null) z.getEquipment().setLeggings(equip.legs);
				if (equip.legsChance != null) z.getEquipment().setLeggingsDropChance(equip.legsChance);
				if (equip.boots != null) z.getEquipment().setBoots(equip.boots);
				if (equip.bootsChance != null) z.getEquipment().setBootsDropChance(equip.bootsChance);
				
				setDefaultEq((LivingEntity)mob);
			} else {
				setDefaultEq((LivingEntity)mob);
			}
		}
	}

	private void setDefaultEq(LivingEntity mob) {
		if (mob instanceof Skeleton) {
			Skeleton skellie = (Skeleton)mob;
			if (skellie.getSkeletonType() == SkeletonType.WITHER)
				if (equip.hands == null) skellie.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
			else 
				if (equip.hands == null) skellie.getEquipment().setItemInHand(new ItemStack(Material.BOW));
		}
	}

	@Override
	public boolean matches(Data d) {
		if(!(d instanceof LivingEntityData)) return false;
		LivingEntityData vd = (LivingEntityData)d;

		if (this.maxHealth != null)
			if (this.maxHealth != vd.maxHealth) return false; 
		
		// compare equipment
		if (!this.equip.matches(vd.equip)) return false;
		
		return true;
	}

	public static CreatureData parseFromEntity(Entity entity) {
		if (entity instanceof LivingEntity) {
			return new LivingEntityData(((LivingEntity)entity).getMaxHealth(), null);
		} else {
			Log.logInfo("LivingEntityData: error, parseFromEntity given different creature - this shouldn't happen.");
			return null;
		}
		
	}
	
	public static CreatureData parseFromString(String state) {
		Log.logInfo("LivingEntityData: parsing from string.", Verbosity.HIGHEST);
		Integer maxHealth = null;
		CreatureEquipment equip = null;

		if (!state.isEmpty() && !state.equals("0")) {
			String split[] = state.split(OtherDropsConfig.CreatureDataSeparator);

			for (String sub : split) {

				if (sub.matches("[0-9]+")) { // need to check numbers before any .toLowerCase()
					maxHealth = Integer.valueOf(sub);
				} else {
					sub = sub.toLowerCase().replaceAll("[\\s-_]",  "");
					if (sub.startsWith("eq:")) {
						if (equip == null) equip = new CreatureEquipment();
						equip = parseEquipmentString(sub, equip);
					}
				}
			}
		}

		return new LivingEntityData(maxHealth, equip);
	}
	
	private static CreatureEquipment parseEquipmentString(String sub, CreatureEquipment passEquip) {
		CreatureEquipment equip = passEquip;
		String subSplit[] = sub.split(":");

		if (subSplit.length == 3) {
			String split[] = subSplit[2].split("%"); // split out the drop chance, if any
			String slot = split[0];
			float chance = 100; // default to 100% drop chance
			if (split.length > 1) {
				chance = Float.valueOf(split[1]) /100; 
			}
			
			if (subSplit[1].matches("head")) {
				equip.head = getItemStack(slot);
				equip.headChance = chance;
			} else if (subSplit[1].matches("(hands|holding)")) {
				equip.hands = getItemStack(slot);
				equip.handsChance = chance;
			} else if (subSplit[1].matches("(chest|chestplate)")) {
				equip.chest = getItemStack(slot);
				equip.chestChance = chance;
			} else if (subSplit[1].matches("(legs|leggings)")) {
				equip.legs = getItemStack(slot);
				equip.legsChance = chance;
			} else if (subSplit[1].matches("(feet|boots)")) {
				equip.boots = getItemStack(slot);
				equip.bootsChance = chance;
			}
			
		}
		return equip;
		
	}

	private static ItemStack getItemStack(String slot) {
		
		// this section doesn't work yet - need to save a list of itemstacks and choose one at spawn time
		/*
		if (slot.startsWith("any")) { // material group
			Log.logInfo("Checking materialgroup...");
			MaterialGroup group = MaterialGroup.get(slot);
			if (group != null)
			{
				Material mat = group.getOneRandom();
				if (mat != null) {
					Log.logInfo("Checking materialgroup...MAT = "+mat.toString());
					return new ItemStack(mat);
				}
			}
		} else {*/
			//Log.logInfo("Checking itemdrop...");
			ItemDrop item = (ItemDrop)ItemDrop.parse(slot, ""); 
			if (item != null)
				return item.getItem();
		//}
		
		return null;
	}
	
	public String toString() {
		String val = "";
		if (maxHealth != null) {
			val += "!!" + maxHealth.toString();
		}
		if (equip != null) {
			val += "!!" + equip.toString();
		}
		return val;
	}
	
	@Override
	public String get(Enum<?> creature) {
		if(creature instanceof EntityType) return this.toString();
		return "";
	}
	
}