package mod.ivalice.common.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.EnumSet;
import java.util.function.Predicate;

public class GoalEatGrass extends Goal {
	
	private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
	private final Mob mob;
	private final Level level;
	private int eatAnimationTick;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalEatGrass(Mob p_i45314_1_) {
		this.mob = p_i45314_1_;
		this.level = p_i45314_1_.level();
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  USAGE  ---------- ---------- ---------- ---------- //
	
	public boolean canUse() {
		if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
			return false;
		} else {
			BlockPos blockpos = this.mob.blockPosition();
			if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
				return true;
			} else {
				return this.level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK);
			}
		}
	}
	
	public boolean canContinueToUse() {
		return this.eatAnimationTick > 0;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void start() {
		this.eatAnimationTick = 40;
		this.level.broadcastEntityEvent(this.mob, (byte)10);
		this.mob.getNavigation().stop();
	}
	
	public void stop() {
		this.eatAnimationTick = 0;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
		if (this.eatAnimationTick == 4) {
			BlockPos blockpos = this.mob.blockPosition();
			if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
				if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
					this.level.destroyBlock(blockpos, false);
				}
				this.mob.ate();
			} else {
				BlockPos blockpos1 = blockpos.below();
				if (this.level.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
					this.mob.ate();
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public int getEatAnimationTick() {
		return this.eatAnimationTick;
	}
	
	
	
}

