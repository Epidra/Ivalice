package mod.ivalice.common.entity.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;

import mod.ivalice.common.block.BlockNest;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

public class GoalTempt extends Goal {
	private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
	private final TargetingConditions targetingConditions;
	protected final EntityChocobo mob;
	private final double speedModifier;
	private double px;
	private double py;
	private double pz;
	private double pRotX;
	private double pRotY;
	@Nullable
	protected Player player;
	private int calmDown;
	private boolean isRunning;
	private final Ingredient items;
	private final boolean canScare;
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalTempt(EntityChocobo p_25939_, double p_25940_, Ingredient p_25941_, boolean p_25942_) {
		this.mob = p_25939_;
		this.speedModifier = p_25940_;
		this.items = p_25941_;
		this.canScare = p_25942_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		if (this.calmDown > 0) {
			--this.calmDown;
			return false;
		} else {
			this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
			return this.player != null;
		}
	}
	
	public boolean canContinueToUse() {
		if (this.canScare()) {
			if (this.mob.distanceToSqr(this.player) < 36.0D) {
				if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
					return false;
				}
				
				if (Math.abs((double)this.player.getXRot() - this.pRotX) > 5.0D || Math.abs((double)this.player.getYRot() - this.pRotY) > 5.0D) {
					return false;
				}
			} else {
				this.px = this.player.getX();
				this.py = this.player.getY();
				this.pz = this.player.getZ();
			}
			
			this.pRotX = (double)this.player.getXRot();
			this.pRotY = (double)this.player.getYRot();
		}
		
		return this.canUse();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void start() {
		this.px = this.player.getX();
		this.py = this.player.getY();
		this.pz = this.player.getZ();
		this.isRunning = true;
	}
	
	public void stop() {
		this.player = null;
		this.mob.getNavigation().stop();
		this.calmDown = reducedTickDelay(100);
		this.isRunning = false;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());
		if (this.mob.distanceToSqr(this.player) < 6.25D) {
			this.mob.getNavigation().stop();
		} else {
			this.mob.getNavigation().moveTo(this.player, this.speedModifier);
		}
		
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public boolean isRunning() {
		return this.isRunning;
	}
	protected boolean canScare() {
		return this.canScare;
	}
	
	private boolean shouldFollow(LivingEntity p_148139_) {
		return this.mob.getFoodLiked() == p_148139_.getMainHandItem().getItem() || this.mob.getFoodLiked() == p_148139_.getOffhandItem().getItem();
		// return this.items.test(p_148139_.getMainHandItem()) || this.items.test(p_148139_.getOffhandItem());
	}
	
	
	
}