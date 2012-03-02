// OtherDrops - a Bukkit plugin
// Copyright (C) 2011 Robert Sargant, Zarius Tularial, Celtic Minstrel
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.	 If not, see <http://www.gnu.org/licenses/>.

package com.gmail.zariust.otherdrops.data;

import static com.gmail.zariust.common.Verbosity.EXTREME;

import com.gmail.zariust.common.CommonMaterial;
import com.gmail.zariust.otherdrops.OtherDrops;

import org.bukkit.CoalType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemData implements Data, RangeableData {
	private int data;
	private String dataString;
	
	public ItemData(int d) {
		data = d;
	}

	public ItemData(int d, String state) {
		data = d;
		setDataString(state);
	}
	
	public ItemData(ItemStack item) {
		data = item.getDurability();
	}

	@Override
	public int getData() {
		return data;
	}
	
	@Override
	public void setData(int d) {
		data = d;
	}
	
	@Override
	public boolean matches(Data d) {
		return data == d.getData();
	}
	
	@Override
	public String get(Enum<?> mat) {
		if(mat instanceof Material) return get((Material)mat);
		return "";
	}
	
	@SuppressWarnings("incomplete-switch")
	private String get(Material mat) {
		if (data == -1) return "THIS";
		if(mat.isBlock()) return CommonMaterial.getBlockOrItemData(mat, data);
		switch(mat) {
		case COAL:
			return CoalType.getByData((byte)data).toString();
		case INK_SACK:
			return DyeColor.getByData((byte)(0xF - data)).toString();
		}
		if(data > 0) return Integer.toString(data);
		return "";
	}
	
	@Override // Items aren't blocks, so nothing to do here
	public void setOn(BlockState state) {}
	
	@Override // Items aren't entities, so nothing to do here
	public void setOn(Entity entity, Player witness) {}

	public static Data parse(Material mat, String state) throws IllegalArgumentException {
		if(state == null || state.isEmpty()) return null;
		if (state.equalsIgnoreCase("THIS")) return new ItemData(-1, state);
		if(state.startsWith("RANGE") || state.matches("[0-9]+-[0-9]+")) return RangeData.parse(state);
		Integer data = 0;
		switch(mat) {
		case INK_SACK:
			DyeColor dye = DyeColor.valueOf(state);
			if(dye != null) data = CommonMaterial.getDyeColor(dye);
			break;
		case COAL:
			CoalType coal = CoalType.valueOf(state);
			if(coal != null) data = Integer.valueOf(coal.getData());
			break;
		case MOB_SPAWNER:
			return SpawnerData.parse(state);
		default:
			if(mat.isBlock() || mat.toString().equalsIgnoreCase("MONSTER_EGG")) {
				data = CommonMaterial.parseBlockOrItemData(mat, state);
				if(mat == Material.LEAVES) data |= 4;
				break;
			}
			if(!state.isEmpty()) throw new IllegalArgumentException("Illegal data for " + mat + ": " + state);
		}
		return (data == null) ? null : new ItemData(data, state);
	}
	
	@Override
	public String toString() {
		// TODO: Should probably make sure this is not used, and always use the get method instead
		OtherDrops.logWarning("ItemData.toString() was called! Is this right?", EXTREME);
		OtherDrops.stackTrace();
		return String.valueOf(data);
	}
	
	@Override
	public int hashCode() {
		return data;
	}

	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

	public String getDataString() {
		return dataString;
	}

	@Override
	public Boolean getSheared() {
		// TODO Auto-generated method stub
		return null;
	}
}
