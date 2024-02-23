package mod.ivalice.common.entity.goals;

import mod.ivalice.common.block.BlockNest;
import mod.ivalice.common.block.entity.BlockEntityNest;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GoalBreed extends Goal {
	
	private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
	protected final Animal animal;
	private final Class<? extends Animal> partnerClass;
	protected final Level level;
	protected Animal partner;
	protected BlockPos nest;
	private int loveTime;
	private final double speedModifier;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalBreed(Animal animal, double speedModifier) {
		this(animal, speedModifier, animal.getClass());
	}
	
	public GoalBreed(Animal animal, double speedModifier, Class<? extends Animal> partner) {
		this.animal = animal;
		this.level = animal.level();
		this.partnerClass = partner;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		if (!this.animal.isInLove()) {
			return false;
		} else {
			this.partner = this.getFreePartner();
			this.nest = this.getNearestNest();
			return this.partner != null && this.nest != null;
		}
	}
	
	public boolean canContinueToUse() {
		// return this.partner.isAlive() && this.partner.isInLove() && level.getBlockState(nest).getBlock() instanceof BlockNest && this.loveTime < 60;
		return this.partner.isAlive() && level.getBlockState(nest).getBlock() instanceof BlockNest && this.loveTime < 60;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void stop() {
		this.partner = null;
		this.nest = null;
		this.loveTime = 0;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		this.animal.getLookControl().setLookAt(nest.getX(), nest.getY(), nest.getZ(), 10.0F, (float)this.animal.getMaxHeadXRot());
		this.animal.getNavigation().moveTo(nest.getX(), nest.getY(), nest.getZ(), 1);
		++this.loveTime;
		if (this.loveTime >= 60 && this.animal.distanceToSqr(this.partner) < 9.0D) {
			this.breed();
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Nullable
	private Animal getFreePartner() {
		List<? extends Animal> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Animal animalentity = null;
		for(Animal animalentity1 : list) {
			// if (this.animal.canMate(animalentity1) && this.animal.distanceToSqr(animalentity1) < d0) {
			if (this.animal.distanceToSqr(animalentity1) < d0) {
				animalentity = animalentity1;
				d0 = this.animal.distanceToSqr(animalentity1);
			}
		}
		return animalentity;
	}
	
	@Nullable
	private BlockPos getNearestNest(){
		BlockPos posStart = animal.blockPosition();
		BlockPos pos = null;
		for(int x = -4; x < 5; x++){
			for(int z = -4; z < 5; z++){
				if(pos == null){
					if(level.getBlockState(posStart.offset(x, 0, z)).getBlock() instanceof BlockNest){
						if(level.getBlockState(posStart.offset(x, 0, z)).getValue(BlockNest.AGE) == 0){
							pos = posStart.offset(x, 0, z);
						}
					}
				}
			}
		}
		return pos;
	}
	
	protected void breed() {
		BlockEntityNest tile = (BlockEntityNest) level.getBlockEntity(nest);
		if(tile != null){
			tile.createEgg((EntityChocobo) this.animal, (EntityChocobo) this.partner);
			this.animal.setAge(6000);
			this.partner.setAge(6000);
			this.animal.resetLove();
			this.partner.resetLove();
		}
	}
	
	
	
}