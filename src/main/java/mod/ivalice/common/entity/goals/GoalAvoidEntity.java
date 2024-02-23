package mod.ivalice.common.entity.goals;

import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.horse.Llama;

class GoalAvoidEntity<T extends LivingEntity> extends AvoidEntityGoal<T> {
	
	private final EntityChocobo chocobo;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalAvoidEntity(EntityChocobo entity, Class<T> avoidClass, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
		super(entity, avoidClass, maxDistance, walkSpeedModifier, sprintSpeedModifier);
		this.chocobo = entity;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		if (super.canUse() && this.toAvoid instanceof Llama) {
			return !this.chocobo.isTame() && this.avoidLlama((Llama)this.toAvoid);
		} else {
			return false;
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void start() {
		chocobo.setTarget((LivingEntity)null);
		super.start();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		chocobo.setTarget((LivingEntity)null);
		super.tick();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private boolean avoidLlama(Llama entity) {
		return entity.getStrength() >= chocobo.getRandom().nextInt(5);
	}
	
	
	
}