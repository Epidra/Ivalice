package mod.ivalice.common.entity.goals;

import mod.ivalice.common.entity.EntityCactuar;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GoalRunAround extends Goal {
	
	private final EntityChocobo chocobo;
	private final double speedModifier;
	private double posX;
	private double posY;
	private double posZ;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalRunAround(EntityChocobo entity, double speedModifier) {
		this.chocobo = entity;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		if (!this.chocobo.isTamed() && this.chocobo.isVehicle()) {
			Vec3 vec3 = DefaultRandomPos.getPos(this.chocobo, 5, 4);
			if (vec3 == null) {
				return false;
			} else {
				this.posX = vec3.x;
				this.posY = vec3.y;
				this.posZ = vec3.z;
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean canContinueToUse() {
		return !this.chocobo.isTamed() && !this.chocobo.getNavigation().isDone() && this.chocobo.isVehicle();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void start() {
		this.chocobo.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
	}
	
	public void stop(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		if (!this.chocobo.isTamed() && this.chocobo.getRandom().nextInt(this.adjustedTickDelay(50)) == 0) {
			Entity entity = this.chocobo.getPassengers().get(0);
			if (entity == null) {
				return;
			}
			
			if (entity instanceof Player) {
				// int i = this.chocobo.getTemper();
				// int j = this.chocobo.getMaxTemper();
				// if (j > 0 && this.chocobo.getRandom().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(horse, (Player)entity)) {
				// 	this.chocobo.tameWithName((Player)entity);
				// 	return;
				// }
				
				this.chocobo.modifyTemper(5);
			}
			
			this.chocobo.ejectPassengers();
			// this.chocobo.makeMad();
			this.chocobo.level().broadcastEntityEvent(this.chocobo, (byte)6);
		}
		
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
