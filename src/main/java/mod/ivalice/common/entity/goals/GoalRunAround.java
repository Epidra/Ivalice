package mod.ivalice.common.entity.goals;

import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class GoalRunAround extends Goal {
	
	private final EntityChocobo horse;
	private final double speedModifier;
	private double posX;
	private double posY;
	private double posZ;
	private boolean isRunning = false;
	private final Level level;
	private int cooldown = 0;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalRunAround(EntityChocobo p_i1653_1_, double p_i1653_2_) {
		this.horse = p_i1653_1_;
		this.level = p_i1653_1_.level();
		this.speedModifier = p_i1653_2_;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		// if (!this.horse.isTamed() && !this.horse.AnimYoung() && --cooldown <= 0) {
		// 	Vec3 vector3d = DefaultRandomPos.getPos(this.horse, 100, 100);
		// 	if (vector3d == null) {
		// 		return false;
		// 	} else {
		// 		this.posX = vector3d.x;
		// 		this.posY = vector3d.y;
		// 		this.posZ = vector3d.z;
		// 		return true;
		// 	}
		// } else {
			return false;
		// }
	}
	
	public boolean canContinueToUse() {
		return false; // !this.horse.isTamed() && !this.horse.getNavigation().isDone();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void start() {
		this.horse.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
		this.level.broadcastEntityEvent(this.horse, (byte)11);
		this.isRunning = true;
	}
	
	public void stop(){
		isRunning = false;
		cooldown = 400;
		this.level.broadcastEntityEvent(this.horse, (byte)12);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public boolean isRunning(){
		return isRunning;
	}
	
	
	
}
