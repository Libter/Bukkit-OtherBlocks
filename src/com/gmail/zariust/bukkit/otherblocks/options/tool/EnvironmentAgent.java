package com.gmail.zariust.bukkit.otherblocks.options.tool;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EnvironmentAgent extends Agent {
	private DamageCause dmg;
	
	public EnvironmentAgent() {
		this(null);
	}
	
	public EnvironmentAgent(DamageCause tool) {
		super(ToolType.DAMAGE);
		dmg = tool;
	}
	
	private EnvironmentAgent equalsHelper(Object other) {
		if(!(other instanceof EnvironmentAgent)) return null;
		return (EnvironmentAgent) other;
	}

	private boolean isEqual(EnvironmentAgent tool) {
		if(tool == null) return false;
		return dmg == tool.dmg;
	}

	@Override
	public boolean equals(Object other) {
		EnvironmentAgent tool = equalsHelper(other);
		return isEqual(tool);
	}

	@Override
	public boolean matches(Agent other) {
		EnvironmentAgent tool = equalsHelper(other);
		if(dmg == null) return true;
		return isEqual(tool);
	}
	
	@Override
	protected int getIdHash() {
		return dmg == null ? 0 : dmg.hashCode();
	}
	
	@Override
	protected int getDataHash() {
		return 11;
	}
	
	public DamageCause getDamage() {
		return dmg;
	}
}