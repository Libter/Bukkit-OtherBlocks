package com.gmail.zariust.otherdrops.subject;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;
import org.junit.Test;

import static org.junit.Assert.*;


public class ExplosionAgentTest {
	
	
	// TODO: test EXPLOSION_CREEPER@POWERED
	
	@Test
	public void testAny() {
		String configString = "EXPLOSION_ANY";

		// Test by Material name
		ExplosionAgent agent = new ExplosionAgent();
		assertTrue("Explosion is: "+agent.toString()+" should be "+configString, agent.toString().equalsIgnoreCase(configString));

		// Test by parsing
		Agent agent2 = ExplosionAgent.parse(configString, "");
		//assertTrue("agent.isCreature() failed", agent.isCreature());  // creaturetype will not be a creature
		assertTrue(agent2.toString()+" should be "+configString, agent2.toString().equalsIgnoreCase(configString));
	}

	@Test
	public void testMatchCreepers() {
		// Test by parsing
		Agent agent2 = ExplosionAgent.parse("EXPLOSION_CREEPER", "");
		//assertTrue("agent.isCreature() failed", agent.isCreature());  // creaturetype will not be a creature
		assertTrue(agent2.toString()+" should be EXPLOSION_CREEPER.", agent2.toString().equalsIgnoreCase("EXPLOSION_CREEPER"));

		// Then test CreatureType constructor
		ExplosionAgent agent = new ExplosionAgent(CreatureType.CREEPER);
		assertTrue(agent.toString()+" should be EXPLOSION_CREEPER.", agent.toString().equalsIgnoreCase("EXPLOSION_CREEPER"));

		// Create a "creeper entity" as would happen in a explosion event and make sure it matches the config agent
		ExplosionAgent eventAgent = new ExplosionAgent(getCreeperTestEntity());		
		assertTrue(agent.matches(eventAgent));
	}



	@Test
	public void testMatchTnt() {
		String configString = "EXPLOSION_TNT";

		// Test by Material constructor
		ExplosionAgent agent = new ExplosionAgent(Material.TNT);
		assertTrue("Explosion is: "+agent.toString()+" should be "+configString, agent.toString().equalsIgnoreCase(configString));
		

		// Test by parsing
		Agent configAgent = ExplosionAgent.parse(configString, "");


		Entity eventEntity = getTntPrimedTestEntity();
		ExplosionAgent eventAgent = new ExplosionAgent(eventEntity);

		assertTrue(configAgent.toString()+" should be "+configString, configAgent.toString().equalsIgnoreCase(configString));
		assertTrue(eventAgent.toString()+" should be "+configString, eventAgent.toString().equalsIgnoreCase(configString));
		
		assertTrue(configAgent.matches(eventAgent));
	}


	@Test
	public void testTntMatchesAny() {
		String configString = "EXPLOSION_ANY";

		// Test by parsing
		Agent configAgent = ExplosionAgent.parse(configString, "");


		Entity eventEntity = getTntPrimedTestEntity();
		ExplosionAgent eventAgent = new ExplosionAgent(eventEntity);

		assertTrue(configAgent.toString()+" should be "+configString, configAgent.toString().equalsIgnoreCase(configString));
		
		assertTrue(eventAgent.toString()+" should be "+"EXPLOSION_TNT", eventAgent.toString().equalsIgnoreCase("EXPLOSION_TNT"));
		
		assertTrue(configAgent.matches(eventAgent));
	}

	@Test
	public void testCreeperMatchesAny() {
		// Test by parsing
		String configString = "EXPLOSION_ANY";
		Agent configAgent = ExplosionAgent.parse(configString, "");


		Entity eventEntity = getCreeperTestEntity();
		ExplosionAgent eventAgent = new ExplosionAgent(eventEntity);

		assertTrue(configAgent.toString()+" should be "+configString, configAgent.toString().equalsIgnoreCase(configString));
		
		assertTrue(eventAgent.toString()+" should be "+"EXPLOSION_CREEPER", eventAgent.toString().equalsIgnoreCase("EXPLOSION_CREEPER"));
		
		assertTrue(configAgent.matches(eventAgent));
	}
	
	
	
	
	private Entity getTntPrimedTestEntity() {
		// TODO Auto-generated method stub
		return new TNTPrimed() {
			
			@Override
			public boolean teleport(Entity arg0, TeleportCause arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Location arg0, TeleportCause arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Entity arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Location arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setVelocity(Vector arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTicksLived(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean setPassenger(Entity arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setLastDamageCause(EntityDamageEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFireTicks(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFallDistance(float arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void playEffect(EntityEffect arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDead() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public World getWorld() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Vector getVelocity() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public UUID getUniqueId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTicksLived() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Server getServer() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Entity getPassenger() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMaxFireTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Location getLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public EntityDamageEvent getLastDamageCause() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getFireTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public float getFallDistance() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getEntityId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean eject() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setYield(float arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setIsIncendiary(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isIncendiary() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public float getYield() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void setFuseTicks(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getFuseTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	private Entity getCreeperTestEntity() {
		// TODO Auto-generated method stub
		return new Creeper() {
			
			@Override
			public boolean teleport(Entity arg0, TeleportCause arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Location arg0, TeleportCause arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Entity arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean teleport(Location arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setVelocity(Vector arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTicksLived(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean setPassenger(Entity arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setLastDamageCause(EntityDamageEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFireTicks(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFallDistance(float arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void playEffect(EntityEffect arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDead() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public World getWorld() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Vector getVelocity() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public UUID getUniqueId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTicksLived() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Server getServer() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Entity getPassenger() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMaxFireTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Location getLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public EntityDamageEvent getLastDamageCause() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getFireTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public float getFallDistance() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getEntityId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean eject() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Snowball throwSnowball() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Egg throwEgg() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Arrow shootArrow() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setRemainingAir(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setNoDamageTicks(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setMaximumNoDamageTicks(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setMaximumAir(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLastDamage(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setHealth(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean leaveVehicle() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isInsideVehicle() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Vehicle getVehicle() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getTargetBlock(HashSet<Byte> arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getRemainingAir() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNoDamageTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getMaximumNoDamageTicks() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getMaximumAir() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getMaxHealth() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLastDamage() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Player getKiller() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getHealth() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Location getEyeLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public double getEyeHeight(boolean arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getEyeHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void damage(int arg0, Entity arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void damage(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTarget(LivingEntity arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public LivingEntity getTarget() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setPowered(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isPowered() {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
