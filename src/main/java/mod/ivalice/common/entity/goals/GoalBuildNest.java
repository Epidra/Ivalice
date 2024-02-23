package mod.ivalice.common.entity.goals;

import mod.ivalice.Register;
import mod.ivalice.common.block.BlockNest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HayBlock;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GoalBuildNest extends Goal {
	
	protected final Animal animal;
	private final Class<? extends Animal> partnerClass;
	protected final Level level;
	protected BlockPos straw;
	private int loveTime;
	private final double speedModifier;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public GoalBuildNest(Animal animal, double speedModifier) {
		this(animal, speedModifier, animal.getClass());
	}
	
	public GoalBuildNest(Animal animal, double speedModifier, Class<? extends Animal> partner) {
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
			BlockPos nest = this.getNearestNest();
			this.straw = this.getNearestStraw();
			return nest == null && this.straw != null;
		}
	}
	
	public boolean canContinueToUse() {
		return level.getBlockState(straw).getBlock() instanceof HayBlock && this.loveTime < 60;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / STOP  ---------- ---------- ---------- ---------- //
	
	public void stop() {
		this.straw = null;
		this.loveTime = 0;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick() {
		this.animal.getLookControl().setLookAt(straw.getX(), straw.getY(), straw.getZ(), 10.0F, (float)this.animal.getMaxHeadXRot());
		this.animal.getNavigation().moveTo(straw.getX(), straw.getY(), straw.getZ(), 1);
		++this.loveTime;
		if (this.loveTime >= 10 && this.animal.distanceToSqr(straw.getX(), straw.getY(), straw.getZ()) < 9.0D) {
			this.buildNest();
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
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
	
	@Nullable
	private BlockPos getNearestStraw(){
		BlockPos posStart = animal.blockPosition();
		BlockPos pos = null;
		for(int x = -4; x < 5; x++){
			for(int z = -4; z < 5; z++){
				if(pos == null){
					if(level.getBlockState(posStart.offset(x, 0, z)).getBlock() instanceof HayBlock){
						pos = posStart.offset(x, 0, z);
					}
				}
			}
		}
		return pos;
	}
	
	protected void buildNest() {
		level.setBlockAndUpdate(straw, Register.BLOCK_NEST.get().defaultBlockState());
	}
	
	
	
}