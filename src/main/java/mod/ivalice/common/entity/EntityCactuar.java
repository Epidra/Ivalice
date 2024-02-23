package mod.ivalice.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityCactuar extends Monster implements NeutralMob {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public EntityCactuar(EntityType<? extends EntityCactuar> entity, Level level) {
		super(entity, level);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}
	
	protected void customServerAiStep() {
		super.customServerAiStep();
	}
	
	public void aiStep() {
		super.aiStep();
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, (double)0.6F).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.FOLLOW_RANGE, 64.0D);
	}
	
	protected void defineSynchedData() {
		super.defineSynchedData();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INTERACTION  ---------- ---------- ---------- ---------- //
	
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		return super.mobInteract(player, hand);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
	}
	
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - COLOR  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - SHEARING  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public static boolean canCactuarSpawn(EntityType<? extends EntityCactuar> animal, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(Blocks.SAND) && /*level.getRawBrightness(pos, 0) > 8 &&*/ level.canSeeSky(pos);
	}
	
	@javax.annotation.Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroup, @javax.annotation.Nullable CompoundTag compound) {
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroup, compound);
	}
	
	@Override
	public int getRemainingPersistentAngerTime() {
		return 0;
	}
	
	@Override
	public void setRemainingPersistentAngerTime(int value) {
	
	}
	
	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return null;
	}
	
	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
	
	}
	
	@Override
	public void startPersistentAngerTimer() {
	
	}
	
	
	
	
	// ---------- ---------- ---------- ----------  SOUND  ---------- ---------- ---------- ---------- //
	
	protected SoundEvent getAmbientSound() {
		return SoundEvents.LLAMA_AMBIENT;
	}
	
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.LLAMA_HURT;
	}
	
	protected SoundEvent getDeathSound() {
		return SoundEvents.LLAMA_DEATH;
	}
	
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.LLAMA_STEP, 0.15F, 1.0F);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  GOALS  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
