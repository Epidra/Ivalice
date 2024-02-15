package mod.ivalice.common.entity;

import mod.ivalice.Register;
import mod.ivalice.client.menu.MenuChocoboBasic;
import mod.ivalice.common.entity.goals.GoalBreed;
import mod.ivalice.common.entity.goals.GoalBuildNest;
import mod.ivalice.common.entity.goals.GoalEatGrass;
import mod.ivalice.common.entity.goals.GoalRunAround;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class EntityChocobo extends TamableAnimal implements HasCustomInventoryScreen, NeutralMob, ContainerListener, PlayerRideableJumping {
	
	// --- ATTRIBUTES --- //
	private static final EntityDataAccessor<Byte> DATA_STAT_1 = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_STAT_2 = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_STAT_3 = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_STAT_4 = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	
	private static final EntityDataAccessor<Byte> DATA_OTHER_NATURE  = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> DATA_OTHER_MALE = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> DATA_COLOR_1       = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_COLOR_2       = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_COLOR_3       = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> DATA_COLOR_SADDLE  = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	
	private static final EntityDataAccessor<Integer> DATA_TIMER_ANGER   = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	// private static final EntityDataAccessor<Integer>        DATA_COLOR_FEATHER        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	// private static final EntityDataAccessor<Integer>        DATA_COLOR_COLLAR         = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean>        DATA_INTERESTED_ID        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BOOLEAN);
	// private static final EntityDataAccessor<Integer>        DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte>           DATA_ID_FLAGS             = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final Predicate<LivingEntity> PARENT_SELECTOR = (p_213617_0_) -> { return p_213617_0_ instanceof EntityChocobo && ((EntityChocobo)p_213617_0_).isBred(); };
	private static final TargetingConditions MOMMY_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_SELECTOR);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Register.FOOD_GYSAHL.get(), Register.FOOD_KRAKKA.get(), Register.FOOD_MIMETT.get(), Register.FOOD_SYLKIS.get(), Register.FOOD_TANTAL.get());
	
	private int eatingCounter;
	private int mouthCounter;
	private int standCounter;
	public int wingCounter;
	public int sprintCounter;
	protected boolean isJumping;
	protected SimpleContainer inventory;
	protected int temper;
	protected float playerJumpPendingScale;
	private boolean allowStandSliding;
	private float eatAnim;
	private float eatAnimO;
	private float standAnim;
	private float standAnimO;
	private float mouthAnim;
	private float mouthAnimO;
	protected boolean canGallop = true;
	protected int gallopSoundCounter;
	private int eatAnimationTick;
	private GoalEatGrass eatBlockGoal;
	private GoalRunAround goalRunAround;
	public boolean isFlying = false;
	public boolean isRunning = false;
	public boolean isMoving = false;
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	public int eggTime = this.random.nextInt(6000) + 6000;
	private float interestedAngle;
	private float interestedAngleO;
	private boolean isWet;
	private boolean isShaking;
	private float shakeAnim;
	private float shakeAnimO;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private UUID persistentAngerTarget;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public EntityChocobo(EntityType<? extends EntityChocobo> entity, Level level) {
		super(entity, level);
		this.setTame(false);
		this.setMaxUpStep(1.0F); // <-- Step Height
		this.createInventory();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	protected void registerGoals() {
		// // this.eatBlockGoal = new EatBlockGoal(this);
		// this.goalSelector.addGoal(0, new FloatGoal(this));
		// // this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
		// this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		// this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		// this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT), false));
		// this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		// // this.goalSelector.addGoal(5, this.eatBlockGoal);
		// this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		// this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		// this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		// // this.targetSelector.addGoal(1, new EntityAlpaca.LlamaHurtByTargetGoal(this));
		
		this.eatBlockGoal = new GoalEatGrass(this);
		this.goalRunAround = new GoalRunAround(this, 1.1f);
		this.goalSelector.addGoal( 0, new FloatGoal(this));
		this.goalSelector.addGoal( 1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal( 1, new PanicGoal(this, 1.2D));
		this.goalSelector.addGoal( 3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal( 4, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal( 6, this.goalRunAround);
		this.goalSelector.addGoal( 7, new GoalBreed(this, 1.0D));
		this.goalSelector.addGoal( 8, new GoalBuildNest(this, 1.0D));
		this.goalSelector.addGoal( 9, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(10, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(11, this.eatBlockGoal);
		this.goalSelector.addGoal(12, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
		this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH)
				.add(Attributes.MOVEMENT_SPEED)
				.add(Attributes.FLYING_SPEED)
				.add(Attributes.JUMP_STRENGTH)
				.add(Attributes.ATTACK_DAMAGE)
				.add(Attributes.ATTACK_SPEED)
				.add(Attributes.ATTACK_KNOCKBACK)
				//.add(Attributes.ARMOR)
				//.add(Attributes.ARMOR_TOUGHNESS)
				;
	}
	
	protected int randomizeAttributes(RandomSource random) {
		int[] valuePool = new int[]{0, 0, 0, 0};
		int points = random.nextInt(2, 5);
		while(points > 0){
			valuePool[random.nextInt(4)]++;
			points--;
		}
		
		int color = 0;
		points = random.nextInt(100);
		if(points == 0){ // Black
			color = 6;
			valuePool[2]+=2;
			valuePool[3]+=2;
			setFeatherColor(1, 1, 1);
		} else if(points == 1){ // White
			color = 5;
			valuePool[0]+=2;
			valuePool[1]+=2;
			setFeatherColor(4, 4, 4);
		} else if(points < 10){ // Purple
			color = 4;
			valuePool[2]+=2;
			setFeatherColor(2, 1, 4);
		} else if(points < 30){ // Red
			color = 3;
			valuePool[0]+=2;
			setFeatherColor(4, 1, 1);
		} else if(points < 50){ // Green
			color = 2;
			valuePool[1]+=2;
			setFeatherColor(1, 4, 1);
		} else if(points < 70){ // Blue
			color = 1;
			valuePool[3]+=2;
			setFeatherColor(1, 1, 4);
		} else {
			setFeatherColor(4, 4, 1);
		}
		
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 + valuePool[0] + valuePool[1] + valuePool[2] + valuePool[3]);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue( 1 + valuePool[0]);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue( (0.23f + valuePool[1])*0.12f);
		this.getAttribute(Attributes.FLYING_SPEED).setBaseValue( 1 + valuePool[2]);
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue( 1 + valuePool[3]);
		
		// this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)generateMaxHealth(p_218815_::nextInt));
		// this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(generateSpeed(p_218815_::nextDouble));
		// this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength(p_218815_::nextDouble));
		
		this.entityData.set(DATA_STAT_1, (byte) ( valuePool[0]));
		this.entityData.set(DATA_STAT_2, (byte) ( valuePool[1]));
		this.entityData.set(DATA_STAT_3, (byte) ( valuePool[2]));
		this.entityData.set(DATA_STAT_4, (byte) ( valuePool[3]));
		
		this.entityData.set(DATA_OTHER_NATURE, (byte) ( random.nextInt(25)));
		
		this.entityData.set(DATA_OTHER_MALE,  ( random.nextBoolean()));
		
		this.setMaxUpStep(1.0F); // <-- Step Height
		
		// - Add Color Variation -HERE-
		
		// --- Coloring --- //
		// Default color -YELLOW- no special attributes
		// Basic color -RED- highly offensive (high attack)
		// Basic color -BLUE- able to cross rivers and streams, but typically cannot fly
		// Basic color -GREEN- variable abilities and act as healer and supports
		// Advanced color -PURPLE- are Synergists
		// Advanced color -WHITE- can restore mp (High HP)
		// Advanced color -BLACK- able to fly (High Flight and Jump)
		
		// if(false) return 5;
		// if(false) return 5;
		// if(false) return 5;
		// if(valuePool[0] >= 2) return 0;
		// if(valuePool[1] >= 2) return 1;
		// if(valuePool[2] >= 2) return 2;
		// if(valuePool[3] >= 2) return 3;
		// if(valuePool[4] >= 3) return 4;
		// return 0; // Yellow Color
		
		return color;
	}
	
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_STAT_1, (byte)0);
		this.entityData.define(DATA_STAT_2, (byte)0);
		this.entityData.define(DATA_STAT_3, (byte)0);
		this.entityData.define(DATA_STAT_4, (byte)0);
		this.entityData.define(DATA_OTHER_NATURE, (byte)0);
		this.entityData.define(DATA_OTHER_MALE, true);
		this.entityData.define(DATA_TIMER_ANGER, (int)0);
		this.entityData.define(DATA_COLOR_1, (byte)0);
		this.entityData.define(DATA_COLOR_2, (byte)0);
		this.entityData.define(DATA_COLOR_3, (byte)0);
		this.entityData.define(DATA_COLOR_SADDLE, (byte)0);
		
		this.entityData.define(DATA_INTERESTED_ID, false);
		this.entityData.define(DATA_ID_FLAGS, (byte)0);
		this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
		
		////    super.defineSynchedData();
		////    this.entityData.define(DATA_COLOR_FEATHER, DyeColor.GRAY.getTextColor());
		////    this.entityData.define(DATA_INTERESTED_ID, false);
		////    this.entityData.define(DATA_COLOR_COLLAR, DyeColor.RED.getId());
		////    this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
		////    this.entityData.define(DATA_ID_NATURE, 0);
		////    this.entityData.define(DATA_ID_FLAGS, (byte)0);
		////    this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
	}
	
	// Can be moved somewhere central
	protected static float generateMaxHealth(IntUnaryOperator operator) {
		return 15.0F + (float)operator.applyAsInt(8) + (float)operator.applyAsInt(9);
	}
	
	// Can be moved somewhere central
	protected static double generateJumpStrength(DoubleSupplier p_272718_) {
		return (double)0.4F + p_272718_.getAsDouble() * 0.2D + p_272718_.getAsDouble() * 0.2D + p_272718_.getAsDouble() * 0.2D;
	}
	
	// Can be moved somewhere central
	protected static double generateSpeed(DoubleSupplier p_273691_) {
		return ((double)0.45F + p_273691_.getAsDouble() * 0.3D + p_273691_.getAsDouble() * 0.3D + p_273691_.getAsDouble() * 0.3D) * 0.25D;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  AI STEP  ---------- ---------- ---------- ---------- //
	
	protected void customServerAiStep() {
		// super.customServerAiStep();
		
		this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
		this.isRunning = this.goalRunAround.isRunning();
		super.customServerAiStep();
	}
	
	public void aiStep() {
		// super.aiStep();
		
		if (this.level().isClientSide) {
			this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
		}
		if (this.random.nextInt(200) == 0) {
			this.moveWings();
		}
		super.aiStep();
		if (!this.level().isClientSide && this.isWet && !this.isShaking && !this.isPathFinding() && this.onGround()) {
			this.isShaking = true;
			this.shakeAnim = 0.0F;
			this.shakeAnimO = 0.0F;
			this.level().broadcastEntityEvent(this, (byte)8);
		}
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		if (!this.level().isClientSide && this.isAlive()) {
			if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
				this.heal(1.0F);
			}
			if (this.canEatGrass()) {
				if (!this.isEating() && !this.isVehicle() && this.random.nextInt(300) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
					this.setEating(true);
				}
				if (this.isEating() && ++this.eatingCounter > 50) {
					this.eatingCounter = 0;
					this.setEating(false);
				}
			}
			this.followMommy();
		}
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround() ? -1 : 4) * 0.3D);
		this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
		if (!this.onGround() && this.flapping < 1.0F) {
			this.flapping = 1.0F;
		}
		this.flapping = (float)((double)this.flapping * 0.9D);
		Vec3 vector3d = this.getDeltaMovement();
		isMoving = vector3d.x != 0 || vector3d.z != 0;
		if (!this.onGround() && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.05D, 0.6D, 1.05D));
			isFlying = true;
		} else {
			isFlying = false;
		}
		this.flap += this.flapping * 2.0F;
		if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
			this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(Items.EGG);
			this.eggTime = this.random.nextInt(6000) + 6000;
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TICK  ---------- ---------- ---------- ---------- //
	
	public void tick(){
		// super.tick();
		
		super.tick();
		if (this.isAlive()) {
			this.interestedAngleO = this.interestedAngle;
			if (this.isInterested()) {
				this.interestedAngle += (1.0F - this.interestedAngle) * 0.4F;
			} else {
				this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
			}
			if (this.isInWaterRainOrBubble()) {
				this.isWet = true;
				if (this.isShaking && !this.level().isClientSide) {
					this.level().broadcastEntityEvent(this, (byte)56);
					this.cancelShake();
				}
			} else if ((this.isWet || this.isShaking) && this.isShaking) {
				if (this.shakeAnim == 0.0F) {
					this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				}
				this.shakeAnimO = this.shakeAnim;
				this.shakeAnim += 0.05F;
				if (this.shakeAnimO >= 2.0F) {
					this.isWet = false;
					this.isShaking = false;
					this.shakeAnimO = 0.0F;
					this.shakeAnim = 0.0F;
				}
				if (this.shakeAnim > 0.4F) {
					float f = (float)this.getY();
					int i = (int)(Mth.sin((this.shakeAnim - 0.4F) * (float)Math.PI) * 7.0F);
					Vec3 vector3d = this.getDeltaMovement();
					for(int j = 0; j < i; ++j) {
						float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
						float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
						this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double)f1, (double)(f + 0.8F), this.getZ() + (double)f2, vector3d.x, vector3d.y, vector3d.z);
					}
				}
			}
		}
		if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
			this.mouthCounter = 0;
			this.setFlag(64, false);
		}
		if ((this.isControlledByLocalInstance() || this.isEffectiveAi()) && this.standCounter > 0 && ++this.standCounter > 20) {
			this.standCounter = 0;
			this.setStanding(false);
		}
		if (this.wingCounter > 0 && ++this.wingCounter > 8) {
			this.wingCounter = 0;
		}
		if (this.sprintCounter > 0) {
			++this.sprintCounter;
			if (this.sprintCounter > 300) {
				this.sprintCounter = 0;
			}
		}
		this.eatAnimO = this.eatAnim;
		if (this.isEating()) {
			this.eatAnim += (1.0F - this.eatAnim) * 0.4F + 0.05F;
			if (this.eatAnim > 1.0F) {
				this.eatAnim = 1.0F;
			}
		} else {
			this.eatAnim += (0.0F - this.eatAnim) * 0.4F - 0.05F;
			if (this.eatAnim < 0.0F) {
				this.eatAnim = 0.0F;
			}
		}
		this.standAnimO = this.standAnim;
		if (this.isStanding()) {
			this.eatAnim = 0.0F;
			this.eatAnimO = this.eatAnim;
			this.standAnim += (1.0F - this.standAnim) * 0.4F + 0.05F;
			if (this.standAnim > 1.0F) {
				this.standAnim = 1.0F;
			}
		} else {
			this.allowStandSliding = false;
			this.standAnim += (0.8F * this.standAnim * this.standAnim * this.standAnim - this.standAnim) * 0.6F - 0.05F;
			if (this.standAnim < 0.0F) {
				this.standAnim = 0.0F;
			}
		}
		this.mouthAnimO = this.mouthAnim;
		if (this.getFlag(64)) {
			this.mouthAnim += (1.0F - this.mouthAnim) * 0.7F + 0.05F;
			if (this.mouthAnim > 1.0F) {
				this.mouthAnim = 1.0F;
			}
		} else {
			this.mouthAnim += (0.0F - this.mouthAnim) * 0.7F - 0.05F;
			if (this.mouthAnim < 0.0F) {
				this.mouthAnim = 0.0F;
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INTERACTION  ---------- ---------- ---------- ---------- //
	
	@Override
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
			ServerPlayer serverPlayer = (ServerPlayer) player;
			if (player.containerMenu != player.inventoryMenu) {
				player.closeContainer();
			}
			
			serverPlayer.nextContainerCounter();
			
			NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((ix, playerInventory, playerEntityx) ->
					new MenuChocoboBasic(ix, playerInventory, this), this.getDisplayName()), buf -> {
				buf.writeUUID(getUUID());
			});
		}
	}
	
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		// return super.mobInteract(player, hand);
		
		// if(this.isTamed() && player.getMainHandItem().getItem() == Items.FEATHER){
		// 	player.openHorseInventory(this, this.inventory);
		// }
		//
		// if (this.isTamed() && player.isSecondaryUseActive()) {
		// 	if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTamed()) {
		// 		player.openHorseInventory(this, this.inventory);
		// 		// -->
		//
		// 		// public void openHorseInventory(AbstractHorse p_9059_, Container p_9060_) {
		// 		// 	if (this.containerMenu != this.inventoryMenu) {
		// 		// 		this.closeContainer();
		// 		// 	}
		// 		//
		// 		// 	this.nextContainerCounter();
		// 		// 	this.connection.send(new ClientboundHorseScreenOpenPacket(this.containerCounter, p_9060_.getContainerSize(), p_9059_.getId()));
		// 		// 	this.containerMenu = new HorseInventoryMenu(this.containerCounter, this.getInventory(), p_9060_, p_9059_);
		// 		// 	this.initMenu(this.containerMenu);
		// 		// 	net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(this, this.containerMenu));
		// 		// }
		//
		// 	}
		// 	return InteractionResult.sidedSuccess(this.level().isClientSide);
		// }
		
		if (!this.level().isClientSide && player.isShiftKeyDown() && !this.isBaby() && player.getMainHandItem().getItem() == Items.FEATHER) {
			this.openCustomInventoryScreen((ServerPlayer) player);
			return InteractionResult.SUCCESS;
		}
		
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (this.level().isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			if(this.isFood(itemstack)){
				if(this.isTame()){
					if(item == getFoodLiked()){
						if(this.isBaby()){
							this.ageUp(10);
						} else {
							this.setInLove(player);
						}
					}
					if(item == getFoodHated()){
						if(this.isBaby()){
							this.ageUp(-10);
						}
					}
					if (this.getHealth() < this.getMaxHealth()) {
						float scale = isFavouriteFood(item) * 0.5f;
						this.heal((float) item.getFoodProperties().getNutrition() * scale);
					}
					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}
					this.eating();
					this.gameEvent(GameEvent.EAT);
					return InteractionResult.SUCCESS;
				} else {
					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}
					boolean success = false;
					int tamechance = this.random.nextInt(10);
					if(getFoodLiked() == item){
						if(tamechance < 9)
							success = true;
					} else if(getFoodHated() == item){
						if(tamechance == 0)
							success = true;
					} else {
						if(tamechance % 2 == 0)
							success = true;
					}
					if(success && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)){
						this.tame(player);
						this.navigation.stop();
						this.setTarget((LivingEntity)null);
						this.setOrderedToSit(true);
						this.level().broadcastEntityEvent(this, (byte)7);
					} else {
						this.level().broadcastEntityEvent(this, (byte)6);
					}
				}
			} else if(item == Items.SADDLE){
				if(this.isTame()){
					if(!isSaddled() && isSaddleable()){
						if (!player.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
						this.equipSaddle(SoundSource.AMBIENT);
						return InteractionResult.SUCCESS;
					}
				}
			} else if(item instanceof DyeItem){
				DyeColor dyecolor = ((DyeItem) item).getDyeColor();
				// if(player.isCreative()){
				// 	if(player.isCrouching()){
				// 		this.setFeatherColor(dyecolor.getTextColor());
				// 	} else {
				// 		this.setFeatherColor(mixColors(dyecolor.getTextColor(), setFeatherColor()));
				// 	}
				// 	return InteractionResult.SUCCESS;
				// } else
				{
					if (dyecolor != this.getCollarColor()) {
						this.setCollarColor(dyecolor);
						if (!player.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
						return InteractionResult.SUCCESS;
					}
				}
			} else if(this.isArmor(itemstack)){
				if(this.canWearArmor() && !this.isWearingArmor()){
					this.equipArmor(player, itemstack, SoundSource.AMBIENT);
					return InteractionResult.SUCCESS;
					// return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
			}
			
			// 	if (this.canWearArmor() && this.isArmor(itemstack) && !this.isWearingArmor()) {
			// 		this.equipArmor(p_252289_, itemstack);
			// 		return InteractionResult.sidedSuccess(this.level().isClientSide);
			// 	}
			
			else if(player.isCrouching()){
				this.setOrderedToSit(!this.isOrderedToSit());
				this.jumping = false;
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
			} else {
				InteractionResult actionresulttype = super.mobInteract(player, hand);
				if (!this.isBaby() && !actionresulttype.consumesAction() && this.isOwnedBy(player)) {
					this.doPlayerRide(player);
					return InteractionResult.SUCCESS;
				}
				return actionresulttype;
			}
			
			// ItemStack itemstack = p_252289_.getItemInHand(p_248927_);
			// if (!itemstack.isEmpty()) {
			// 	InteractionResult interactionresult = itemstack.interactLivingEntity(p_252289_, this, p_248927_);
			// 	if (interactionresult.consumesAction()) {
			// 		return interactionresult;
			// 	}
			//
			// 	if (this.canWearArmor() && this.isArmor(itemstack) && !this.isWearingArmor()) {
			// 		this.equipArmor(p_252289_, itemstack);
			// 		return InteractionResult.sidedSuccess(this.level().isClientSide);
			// 	}
			// }
		}
		return super.mobInteract(player, hand);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("stat_1", this.entityData.get(DATA_STAT_1)); // this.entityData.define(DATA_STAT_HEALTH, (int)0);
		compound.putInt("stat_2", this.entityData.get(DATA_STAT_2)); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		compound.putInt("stat_3", this.entityData.get(DATA_STAT_3)); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		compound.putInt("stat_4", this.entityData.get(DATA_STAT_4)); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		compound.putInt("nature", this.entityData.get(DATA_OTHER_NATURE)); // this.entityData.define(DATA_OTHER_NATURE, (int)0);
		compound.putBoolean("gender", this.entityData.get(DATA_OTHER_MALE)); // this.entityData.define(DATA_OTHER_NATURE, (int)0);
		// compound.putInt("angertime", this.entityData.get(DATA_TIMER_ANGER)); // this.entityData.define(DATA_TIMER_ANGER, (int)0);
		compound.putInt("color_1", this.entityData.get(DATA_COLOR_1)); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		compound.putInt("color_2", this.entityData.get(DATA_COLOR_2)); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		compound.putInt("color_3", this.entityData.get(DATA_COLOR_3)); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		compound.putInt("color_collar", this.entityData.get(DATA_COLOR_SADDLE)); // this.entityData.define(DATA_COLOR_SADDLE, (int)0);
		// compound.putByte(   "Color", (byte)this.getColor().getId());
		
		////    super.addAdditionalSaveData(compound);
		////    compound.putInt("ColorFeather", this.getColorFeatherData());
		////    compound.putInt("ColorCollar", this.getColorCollar().getId());
		////    if (compound.contains("EggLayTime")) { this.eggTime = compound.getInt("EggLayTime"); }
		////    compound.putInt("Nature", this.getNature());
		if (!this.inventory.getItem(1).isEmpty()) { compound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag())); }
		////    compound.putBoolean("EatingHaystack", this.isEating());
		////    compound.putBoolean("Bred", this.isBred());
		////    compound.putInt("Temper", this.getTemper());
		////    compound.putBoolean("Tame", this.isTamed());
		if (this.getOwnerUUID() != null) { compound.putUUID("Owner", this.getOwnerUUID()); }
		if (!this.inventory.getItem(0).isEmpty()) { compound.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag())); }
	}
	
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.entityData.set(DATA_STAT_1, compound.getByte("stat_1")); // this.entityData.define(DATA_STAT_HEALTH, (int)0);
		this.entityData.set(DATA_STAT_2, compound.getByte("stat_2")); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		this.entityData.set(DATA_STAT_3, compound.getByte("stat_3")); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		this.entityData.set(DATA_STAT_4, compound.getByte("stat_4")); // this.entityData.define(DATA_STAT_SPEED, (int)0);
		this.entityData.set(DATA_OTHER_NATURE, compound.getByte("nature")); // this.entityData.define(DATA_OTHER_NATURE, (int)0);
		this.entityData.set(DATA_OTHER_MALE, compound.getBoolean("gender")); // this.entityData.define(DATA_OTHER_NATURE, (int)0);
		// this.entityData.set(DATA_TIMER_ANGER, compound.getInt("angertime")); // this.entityData.define(DATA_TIMER_ANGER, (int)0);
		this.entityData.set(DATA_COLOR_1, compound.getByte("color_1")); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		this.entityData.set(DATA_COLOR_2, compound.getByte("color_2")); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		this.entityData.set(DATA_COLOR_3, compound.getByte("color_3")); // this.entityData.define(DATA_COLOR_FEATHER, (int)0);
		this.entityData.set(DATA_COLOR_SADDLE, compound.getByte("color_collar")); // this.entityData.define(DATA_COLOR_SADDLE, (int)0);
		// this.setColor(DyeColor.byId( compound.getByte(   "Color") ));
		
		////    super.readAdditionalSaveData(compound);
		////    this.setColorFeather(compound.getInt("ColorFeather"));
		////    compound.putInt("EggLayTime", this.eggTime);
		////    if (compound.contains("ColorCollar", 99)) {
		////    	this.setColorCollar(DyeColor.byId(compound.getInt("ColorCollar")));
		////    }
		////    if(!level().isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
		////    	this.readPersistentAngerSaveData((ServerLevel)this.level(), compound);
		////    this.setNature(compound.getInt("Nature"));
		if (compound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}
		////    this.setEating(compound.getBoolean("EatingHaystack"));
		////    this.setBred(compound.getBoolean("Bred"));
		////    this.setTemper(compound.getInt("Temper"));
		////    this.setTamed(compound.getBoolean("Tame"));
		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}
		if (uuid != null) {
			this.setOwnerUUID(uuid);
		}
		if (compound.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("SaddleItem"));
			if (itemstack.getItem() == Items.SADDLE) {
				this.inventory.setItem(0, itemstack);
			}
		}
		this.updateContainerEquipment();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - COLOR  ---------- ---------- ---------- ---------- //
	
	public byte[] getMatingColor(){
		return new byte[]{
				this.entityData.get(DATA_COLOR_1),
				this.entityData.get(DATA_COLOR_2),
				this.entityData.get(DATA_COLOR_3)
		};
	}
	
	public float[] getFeatherColor() {
		//return SystemColor.createColorArray(this.entityData.get(DATA_COLOR_FEATHER));
		return new float[]{
				this.entityData.get(DATA_COLOR_1) / 255.0f,
				this.entityData.get(DATA_COLOR_2) / 255.0f,
				this.entityData.get(DATA_COLOR_3) / 255.0f
		};
	}
	
	public void setFeatherColor(byte p_30398_) {
		this.entityData.set(DATA_COLOR_1, p_30398_);
	}
	
	public void setFeatherColor(byte[] color) {
		this.entityData.set(DATA_COLOR_1, (byte) (color[0]));
		this.entityData.set(DATA_COLOR_2, (byte) (color[1]));
		this.entityData.set(DATA_COLOR_3, (byte) (color[2]));
	}
	
	public void setFeatherColor(int col1, int col2, int col3) {
		this.entityData.set(DATA_COLOR_1, (byte) (col1 * 32 - 1));
		this.entityData.set(DATA_COLOR_2, (byte) (col2 * 32 - 1));
		this.entityData.set(DATA_COLOR_3, (byte) (col3 * 32 - 1));
	}
	
	public DyeColor getCollarColor() {
		return DyeColor.byId(this.entityData.get(DATA_COLOR_SADDLE));
	}
	
	public void setCollarColor(DyeColor p_30398_) {
		this.entityData.set(DATA_COLOR_SADDLE, (byte) p_30398_.getId());
	}
	
	public byte[] mixColors(byte[] color1, byte[] color2){
		int rand = getRandom().nextInt();
		return new byte[]{
				(byte) ((color1[0] + color2[0]) / 2),
				(byte) ((color1[1] + color2[1]) / 2),
				(byte) ((color1[2] + color2[2]) / 2)
		};
	}
	
	//  public int getMixedColor(int color1, int color2){
	//  	float[] f1;
	//  	float[] f2;
	//  	{
	//  		int i = (color1 & 16711680) >> 16;
	//  		int j = (color1 & '\uff00') >> 8;
	//  		int k = (color1 & 255) >> 0;
	//  		f1 = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
	//  	}
	//  	{
	//  		int i = (color2 & 16711680) >> 16;
	//  		int j = (color2 & '\uff00') >> 8;
	//  		int k = (color2 & 255) >> 0;
	//  		f2 = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
	//  	}
	//  	float[] f3 = new float[]{
	//  			(f1[0] + f2[0]) / 2.00f,
	//  			(f1[1] + f2[1]) / 2.00f,
	//  			(f1[2] + f2[2]) / 2.00f};
	//  	int x = (int)(f3[0] * 255);
	//  	int y = (int)(f3[1] * 255);
	//  	int z = (int)(f3[2] * 255);
	//  	return x << 16 | y << 8 | z << 0;
	//  }
	
	//  private int getOffspringColor(int color1, int color2, RandomSource random) {
	//  	return getMixedColor(color1, color2);
	//  }
	
	// public float[] getCollarColor() {
	// 	return SystemColor.createColorArray(this.entityData.get(DATA_COLOR_SADDLE));
	// }
	
	// public void setCollarColor(DyeColor p_30398_) {
	// 	this.entityData.set(DATA_COLOR_FEATHER, p_30398_.getTextColor());
	// }
	
	
	
	// private static float[] createSheepColor(DyeColor color) {
	// 	if (color == DyeColor.WHITE) {
	// 		return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
	// 	} else {
	// 		float[] afloat = color.getTextureDiffuseColors();
	// 		float f = 0.75F;
	// 		return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
	// 	}
	// }
	
	// public static float[] getColorArray(DyeColor color) {
	// 	return COLORARRAY_BY_COLOR.get(color);
	// }
	
	// private DyeColor getOffspringColor(Animal parent1, Animal parent2) {
	// 	DyeColor dyecolor1 = ((EntityChocobo)parent1).getFeatherColor();
	// 	DyeColor dyecolor2 = ((EntityChocobo)parent2).getFeatherColor();
	// 	CraftingContainer craftingcontainer = makeContainer(dyecolor1, dyecolor2);
	// 	return this.level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainer, this.level()).map((recipe) -> recipe.assemble(craftingcontainer, this.level().registryAccess())).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> this.level().random.nextBoolean() ? dyecolor1 : dyecolor2);
	// }
	
	// public static DyeColor createSpawnColor(int statValue) {
	// 	int i = random.nextInt(100);
	// 	if (i <  5) { return DyeColor.PURPLE;      }
	// 	else if (i < 10) { return DyeColor.BLUE;       }
	// 	else if (i < 15) { return DyeColor.GREEN; }
	// 	else if (i < 18) { return DyeColor.RED;     }
	// 	else             { return DyeColor.YELLOW;
	// 	}
	// }
	
	// public DyeColor getColor() {
	// 	return DyeColor.byFireworkColor(this.entityData.get(DATA_COLOR_FEATHER));
	// }
	
	// public void setColor(DyeColor color) {
	// 	// byte b0 = this.entityData.get(DATA_WOOL_ID);
	// 	// this.entityData.set(DATA_WOOL_ID, (byte)(b0 & 240 | color.getId() & 15));
	// }
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - ANIMATION  ---------- ---------- ---------- ---------- //
	
	public int getMaxHeadXRot() {
		return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
	}
	
	public boolean AnimFly(){
		return isFlying;
	}
	
	public boolean AnimRun(){
		return isRunning && isMoving;
	}
	
	public boolean AnimKweh(){
		return false;
	}
	
	public boolean AnimRide(){
		return getControllingPassenger() != null;
	}
	
	public boolean AnimSaddle(){
		return isSaddled();
	}
	
	public ItemStack AnimArmor(){
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	
	public boolean AnimChest(){
		return false;
	}
	
	public boolean AnimPick(){
		return eatAnimationTick > 0;
	}
	
	public boolean AnimSit(){
		return isInSittingPose() && getControllingPassenger() == null;
	}
	
	public boolean AnimFemale(){
		return this.entityData.get(DATA_OTHER_MALE);
	}
	
	public boolean AnimYoung(){
		return isBaby();
	}
	
	public double getPassengersRidingOffset() {
		return 1.5D;
	}
	
	// maybe not needed
	public double getMyRidingOffset() {
		return 0.14D;
	}
	
	protected float getStandingEyeHeight(Pose p_30578_, EntityDimensions p_30579_) {
		return p_30579_.height * 0.95F;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - SPAWN  ---------- ---------- ---------- ---------- //
	
	public int getMaxSpawnClusterSize() {
		return 1;
	}
	
	public static boolean canChocoboSpawn(EntityType<? extends EntityChocobo> animal, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && level.getRawBrightness(pos, 0) > 8 && level.canSeeSky(pos);
	}
	
	@javax.annotation.Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroup, @javax.annotation.Nullable CompoundTag compound) {
		int unused = this.randomizeAttributes(level.getRandom());
		this.setCollarColor(DyeColor.byId(unused));
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroup, compound);
	}
	
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
		EntityChocobo parent = (EntityChocobo)entity;
		EntityChocobo child = Register.ENTITY_CHOCOBO.get().create(level);
		child.setFeatherColor(this.mixColors(this.getMatingColor(), parent.getMatingColor()));
		// child.setNature(random.nextInt(25));
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			child.setOwnerUUID(uuid);
			child.setTame(true);
		}
		return child;
	}
	
	// @Nullable
	// public AgeableMob getBreedOffspring(ServerLevel level, int colorA, int colorB) {
	// 	EntityChocobo child = Register.ENTITY_CHOCOBO.get().create(level);
	// 	child.setFeatherColor(this.mixColors(new byte[]{(byte) colorA, (byte) colorA, (byte) colorA}, new byte[]{(byte) colorB, (byte) colorB, (byte) colorB}, getRandom()));
	// 	// child.setNature(random.nextInt(25));
	// 	UUID uuid = this.getOwnerUUID();
	// 	if (uuid != null) {
	// 		child.setOwnerUUID(uuid);
	// 		child.setTame(true);
	// 	}
	// 	return child;
	// }
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - OFFSPRING  ---------- ---------- ---------- ---------- //
	
	// public EntityChocobo getBreedOffspring(ServerLevel world, AgeableMob entity) {
	// 	EntityChocobo parent = (EntityChocobo)entity;
	// 	EntityChocobo child = ShopKeeper.ENTITY_CHOCOBO.get().create(world);
	// 	child.setColorFeather(this.getOffspringColor(this.getColorFeatherData(), parent.getColorFeatherData(), getRandom()));
	// 	child.setNature(random.nextInt(25));
	// 	UUID uuid = this.getOwnerUUID();
	// 	if (uuid != null) {
	// 		child.setOwnerUUID(uuid);
	// 		child.setTame(true);
	// 	}
	// 	return child;
	// }
	
	// public EntityChocobo getBreedOffspring(ServerLevel world, int colorA, int colorB) {
	// 	EntityChocobo child = ShopKeeper.ENTITY_CHOCOBO.get().create(world);
	// 	child.setColorFeather(this.getOffspringColor(colorA, colorB, getRandom()));
	// 	child.setNature(random.nextInt(25));
	// 	UUID uuid = this.getOwnerUUID();
	// 	if (uuid != null) {
	// 		child.setOwnerUUID(uuid);
	// 		child.setTame(true);
	// 	}
	// 	return child;
	// }
	
	
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - INVENTORY  ---------- ---------- ---------- ---------- //
	
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	
	private void setArmor(ItemStack p_213805_1_) {
		this.setItemSlot(EquipmentSlot.CHEST, p_213805_1_);
		this.setDropChance(EquipmentSlot.CHEST, 0.0F);
	}
	
	protected void updateContainerEquipment() {
		if (!this.level().isClientSide) {
			this.setArmorEquipment(this.inventory.getItem(1));
			this.setDropChance(EquipmentSlot.CHEST, 0.0F);
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
		}
	}
	private void setArmorEquipment(ItemStack p_213804_1_) {
		this.setArmor(p_213804_1_);
		if (!this.level().isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isArmor(p_213804_1_)) {
				int i = ((HorseArmorItem)p_213804_1_.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}
	public void containerChanged(Container p_76316_1_) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
		}
		ItemStack itemstack = this.getArmor();
		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}
	public boolean isArmor(ItemStack stack) {
		return stack.getItem() instanceof HorseArmorItem;
	}
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	public void equipSaddle(@Nullable SoundSource p_230266_1_) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
		if (p_230266_1_ != null) {
			this.level().playSound((Player) null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
		}
	}
	protected int getInventorySize() {
		return 2;
	}
	protected void createInventory() {
		SimpleContainer simplecontainer = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());
		if (simplecontainer != null) {
			simplecontainer.removeListener(this);
			int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());
			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = simplecontainer.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}
		this.inventory.addListener(this);
		this.updateContainerEquipment();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
	}
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}
		}
	}
	
	// public boolean canWearArmor() {
	// 	return false;
	// }
	
	public boolean canWearArmor() {
		return true;
	}
	
	// public boolean isWearingArmor() {
	// 	return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	// }
	
	public boolean isWearingArmor() {
		return !this.inventory.getItem(1).isEmpty();
	}
	
	// public boolean isArmor(ItemStack p_30645_) {
	// 	return false;
	// }
	
	public void equipArmor(Player p_251330_, ItemStack p_248855_, SoundSource sound) {
		if (this.isArmor(p_248855_)) {
			this.inventory.setItem(1, p_248855_.copyWithCount(1));
			if (!p_251330_.getAbilities().instabuild) {
				p_248855_.shrink(1);
			}
			if (sound != null) {
				this.level().playSound((Player) null, this, SoundEvents.ARMOR_EQUIP_GENERIC, sound, 0.5F, 1.0F);
			}
		}
		
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public int getDataHealth(){ return (int) this.getAttribute(Attributes.MAX_HEALTH).getValue(); }
	public int getDataAttack(){ return (int) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue(); }
	public int getDataSpeed(){ return (int) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue(); }
	public int getDataGlide(){ return (int) this.getAttribute(Attributes.FLYING_SPEED).getValue(); }
	public int getDataJump(){ return (int) this.getAttribute(Attributes.JUMP_STRENGTH).getValue(); }
	public boolean getDataGender(){ return (boolean) this.entityData.get(DATA_OTHER_MALE); }
	// public int getDataNature(){ return (int) this.entityData.get(DATA_OTHER_NATURE); }
	// public int getDataNature(){ return this.getNature(); }
	public String getDataNature(){
		switch(this.entityData.get(DATA_OTHER_NATURE)){
			default:
			case  0: return "Hardy";
			case  1: return "Lonely";
			case  2: return "Adamant";
			case  3: return "Naughty";
			case  4: return "Brave";
			case  5: return "Bold";
			case  6: return "Docile";
			case  7: return "Impish";
			case  8: return "Lax";
			case  9: return "Relaxed";
			case 10: return "Modest";
			case 11: return "Mild";
			case 12: return "Bashful";
			case 13: return "Rash";
			case 14: return "Quiet";
			case 15: return "Calm";
			case 16: return "Gentle";
			case 17: return "Careful";
			case 18: return "Quirky";
			case 19: return "Sassy";
			case 20: return "Timid";
			case 21: return "Hasty";
			case 22: return "Jolly";
			case 23: return "Naive";
			case 24: return "Serious";
		}
	}
	public int getDataFoodLiked(){ return (int) this.getAttribute(Attributes.MAX_HEALTH).getValue(); }
	public int getDataFoodHated(){ return (int) this.getAttribute(Attributes.MAX_HEALTH).getValue(); }
	
	protected boolean getFlag(int p_110233_1_) {
		return (this.entityData.get(DATA_ID_FLAGS) & p_110233_1_) != 0;
	}
	
	protected void setFlag(int p_110208_1_, boolean p_110208_2_) {
		byte b0 = this.entityData.get(DATA_ID_FLAGS);
		if (p_110208_2_) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | p_110208_1_));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~p_110208_1_));
		}
	}
	
	public void setTamed(boolean p_110234_1_) {
		this.setFlag(2, p_110234_1_);
	}
	
	public boolean isTamed() {
		return this.getFlag(2);
	}
	
	public boolean isEating() {
		return this.getFlag(16);
	}
	
	public boolean isStanding() {
		return this.getFlag(32);
	}
	
	public boolean isBred() {
		return this.getFlag(8);
	}
	
	public void setBred(boolean p_110242_1_) {
		this.setFlag(8, p_110242_1_);
	}
	
	public boolean isSaddled() {
		return this.getFlag(4);
	}
	
	public boolean isArmored(){
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
		// return this.getFlag(4);
	}
	
	// Unneeded
	public void setTame(boolean p_70903_1_) {
		super.setTame(p_70903_1_);
		// if (p_70903_1_) {
		// 	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
		// 	this.setHealth(20.0F);
		// } else {
		// 	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
		// }
		// this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}
	
	public boolean canMate(Animal entity) {
		if (entity == this) {
			return false;
		} else if (!this.isTame()) {
			return false;
		} else if (!(entity instanceof EntityChocobo)) {
			return false;
		} else {
			// return this.isInLove() && ((EntityChocobo)entity).isInLove();
			return this.isInLove();
		}
	}
	
	public boolean isInterested() {
		return this.entityData.get(DATA_INTERESTED_ID);
	}
	
	// Candidate for Super Class
	private static CraftingContainer makeContainer(DyeColor color1, DyeColor color2) {
		CraftingContainer craftingcontainer = new TransientCraftingContainer(new AbstractContainerMenu(null, -1) {
			public ItemStack quickMoveStack(Player player, int value) {
				return ItemStack.EMPTY;
			}
			public boolean stillValid(Player player) {
				return false;
			}
		}, 2, 1);
		craftingcontainer.setItem(0, new ItemStack(DyeItem.byColor(color1)));
		craftingcontainer.setItem(1, new ItemStack(DyeItem.byColor(color2)));
		return craftingcontainer;
	}
	
	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_TIMER_ANGER);
	}
	
	@Override
	public void setRemainingPersistentAngerTime(int value) {
		this.entityData.set(DATA_TIMER_ANGER, value);
	}
	
	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}
	
	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		this.persistentAngerTarget = uuid;
	}
	
	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}
	
	
	
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte p_70103_1_) {
		
		// ???
		if (p_70103_1_ == 10) {
			this.eatAnimationTick = 40;
		} else if(p_70103_1_ == 11){
			this.isRunning = true;
		} else if(p_70103_1_ == 12){
			this.isRunning = false;
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
		
		
		if (p_70103_1_ == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
		if (p_70103_1_ == 8) {
			this.isShaking = true;
			this.shakeAnim = 0.0F;
			this.shakeAnimO = 0.0F;
		} else if (p_70103_1_ == 56) {
			this.cancelShake();
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}
	
	public void ate() {
		if (this.isBaby()) {
			this.ageUp(60);
		}
	}
	
	private void cancelShake() {
		this.isShaking = false;
		this.shakeAnim = 0.0F;
		this.shakeAnimO = 0.0F;
	}
	
	public void die(DamageSource p_70645_1_) {
		this.isWet = false;
		this.isShaking = false;
		this.shakeAnimO = 0.0F;
		this.shakeAnim = 0.0F;
		super.die(p_70645_1_);
	}
	
	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (this.isInvulnerableTo(p_70097_1_)) {
			return false;
		} else {
			Entity entity = p_70097_1_.getEntity();
			this.setOrderedToSit(false);
			// if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
			// 	p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
			// }
			return super.hurt(p_70097_1_, p_70097_2_);
		}
	}
	
	public boolean doHurtTarget(Entity p_70652_1_) {
		// boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		// if (flag) {
		//     this.doEnchantDamageEffects(this, p_70652_1_);
		// }
		// return flag;
		return true;
	}
	
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}
	
	public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
		if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
			if (p_30389_ instanceof Wolf) {
				Wolf wolf = (Wolf)p_30389_;
				return !wolf.isTame() || wolf.getOwner() != p_30390_;
			} else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).canHarmPlayer((Player)p_30389_)) {
				return false;
			} else if (p_30389_ instanceof EntityChocobo && ((EntityChocobo)p_30389_).isTamed()) {
				return false;
			} else {
				return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal)p_30389_).isTame();
			}
		} else {
			return false;
		}
	}
	
	public boolean canBeLeashed(Player p_184652_1_) {
		return !this.isAngry() && super.canBeLeashed(p_184652_1_);
	}
	
	
	private void setNature(byte id) {
		this.entityData.set(DATA_OTHER_NATURE, id);
	}
	
	private int getNature() {
		return this.entityData.get(DATA_OTHER_NATURE);
	}
	
	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_ID_OWNER_UUID).orElse((UUID)null);
	}
	
	public void setOwnerUUID(@Nullable UUID p_184779_1_) {
		this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(p_184779_1_));
	}
	
	public boolean isJumping() {
		return this.isJumping;
	}
	
	public void setIsJumping(boolean p_110255_1_) {
		this.isJumping = p_110255_1_;
	}
	
	protected void onLeashDistance(float p_142017_1_) {
		if (p_142017_1_ > 6.0F && this.isEating()) {
			this.setEating(false);
		}
	}
	
	public int getTemper() {
		return this.temper;
	}
	
	public void setTemper(int p_110238_1_) {
		this.temper = p_110238_1_;
	}
	
	public int modifyTemper(int p_110198_1_) {
		int i = Mth.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
		this.setTemper(i);
		return i;
	}
	
	public boolean isPushable() {
		return !this.isVehicle();
	}
	
	private void eating() {
		this.openMouth();
		if (!this.isSilent()) {
			SoundEvent soundevent = this.getEatingSound();
			if (soundevent != null) {
				this.level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			}
		}
	}
	
	protected int calculateFallDamage(float p_225508_1_, float p_225508_2_) {
		return Mth.ceil((p_225508_1_ * 0.5F - 3.0F) * p_225508_2_);
	}
	
	public double getCustomJump() {
		return this.getAttributeValue(Attributes.JUMP_STRENGTH);
	}
	
	public int getMaxTemper() {
		return 100;
	}
	
	protected void doPlayerRide(Player p_30634_) {
		this.setEating(false);
		this.setStanding(false);
		if (!this.level().isClientSide) {
			p_30634_.setYRot(this.getYRot());
			p_30634_.setXRot(this.getXRot());
			p_30634_.startRiding(this);
		}
	}
	
	protected boolean isImmobile() {
		return super.isImmobile() && this.isVehicle() && this.isSaddled() || this.isEating() || this.isStanding();
	}
	
	private void moveWings() {
		this.wingCounter = 1;
	}
	
	protected void followMommy() {
		if (this.isBred() && this.isBaby() && !this.isEating()) {
			LivingEntity livingentity = this.level().getNearestEntity(EntityChocobo.class, MOMMY_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
			if (livingentity != null && this.distanceToSqr(livingentity) > 4.0D) {
				this.navigation.createPath(livingentity, 0);
			}
		}
	}
	
	public boolean canEatGrass() {
		return true;
	}
	
	private void openMouth() {
		if (!this.level().isClientSide) {
			this.mouthCounter = 1;
			this.setFlag(64, true);
		}
	}
	
	public void setEating(boolean p_110227_1_) {
		this.setFlag(16, p_110227_1_);
	}
	
	public void setStanding(boolean p_110219_1_) {
		if (p_110219_1_) {
			this.setEating(false);
		}
		this.setFlag(32, p_110219_1_);
	}
	
	private void stand() {
		if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
			this.standCounter = 1;
			this.setStanding(true);
		}
	}
	
	public void travel(Vec3 p_30633_) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
				LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
				this.setYRot(livingentity.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot(livingentity.getXRot() * 0.5F);
				this.setRot(this.getYRot(), this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
					this.gallopSoundCounter = 0;
				}
				if (this.onGround() && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
					f = 0.0F;
					f1 = 0.0F;
				}
				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround()) {
					double d0 = this.getCustomJump() * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
					double d1 = d0 + this.getJumpBoostPower();
					Vec3 vec3 = this.getDeltaMovement();
					this.setDeltaMovement(vec3.x, d1, vec3.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
						float f3 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add((double)(-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double)(0.4F * f3 * this.playerJumpPendingScale)));
					}
					this.playerJumpPendingScale = 0.0F;
				}
				// this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vec3((double)f, p_30633_.y, (double)f1));
				} else if (livingentity instanceof Player) {
					this.setDeltaMovement(Vec3.ZERO);
				}
				if (this.onGround()) {
					this.playerJumpPendingScale = 0.0F;
					this.setIsJumping(false);
				}
				// this.calculateEntityAnimation(this, false);
				this.tryCheckInsideBlocks();
			} else {
				// this.flyingSpeed = 0.02F;
				super.travel(p_30633_);
			}
		}
	}
	
	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}
	
	@javax.annotation.Nullable
	public LivingEntity getControllingPassenger() {
		Entity entity = this.getFirstPassenger();
		if (entity instanceof Mob) {
			return (Mob)entity;
		} else {
			if (this.isSaddled()) {
				entity = this.getFirstPassenger();
				if (entity instanceof Player) {
					return (Player)entity;
				}
			}
			
			return null;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public void onPlayerJump(int p_110206_1_) {
		if (this.isSaddled()) {
			if (p_110206_1_ < 0) {
				p_110206_1_ = 0;
			} else {
				this.allowStandSliding = true;
				this.stand();
			}
			if (p_110206_1_ >= 90) {
				this.playerJumpPendingScale = 1.0F;
			} else {
				this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_110206_1_ / 90.0F;
			}
		}
	}
	
	public boolean canJump() {
		return this.isSaddled();
	}
	
	public void handleStartJump(int p_184775_1_) {
		this.allowStandSliding = true;
		this.stand();
		this.playJumpSound();
	}
	
	public void handleStopJump() {
	
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void spawnTamingParticles(boolean p_110216_1_) {
		ParticleOptions iparticledata = p_110216_1_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
	
	// public void positionRider(Entity p_184232_1_) {
	//     super.positionRider(p_184232_1_);
	// }
	
	public boolean onClimbable() {
		if (this.isSpectator()) {
			return false;
		} else {
			BlockPos blockpos = this.blockPosition();
			BlockState blockstate = this.getFeetBlockState();
			Optional<BlockPos> ladderPos = net.minecraftforge.common.ForgeHooks.isLivingOnLadder(blockstate, level(), blockpos, this);
			// if (ladderPos.isPresent()) this.lastClimbablePos = ladderPos;
			return ladderPos.isPresent();
		}
	}
	
	// @Nullable
	// public Entity getControllingPassenger() {
	//     return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	// }
	
	@Nullable
	private Vec3 getDismountLocationInDirection(Vec3 p_30562_, LivingEntity p_30563_) {
		double d0 = this.getX() + p_30562_.x;
		double d1 = this.getBoundingBox().minY;
		double d2 = this.getZ() + p_30562_.z;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		for(Pose pose : p_30563_.getDismountPoses()) {
			blockpos$mutableblockpos.set(d0, d1, d2);
			double d3 = this.getBoundingBox().maxY + 0.75D;
			while(true) {
				double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
				if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
					break;
				}
				if (DismountHelper.isBlockFloorValid(d4)) {
					AABB aabb = p_30563_.getLocalBoundsForPose(pose);
					Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
					if (DismountHelper.canDismountTo(this.level(), p_30563_, aabb.move(vec3))) {
						p_30563_.setPose(pose);
						return vec3;
					}
				}
				blockpos$mutableblockpos.move(Direction.UP);
				if (!((double)blockpos$mutableblockpos.getY() < d3)) {
					break;
				}
			}
		}
		return null;
	}
	
	public Vec3 getDismountLocationForPassenger(LivingEntity p_30576_) {
		Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
		Vec3 vec31 = this.getDismountLocationInDirection(vec3, p_30576_);
		if (vec31 != null) {
			return vec31;
		} else {
			Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
			Vec3 vec33 = this.getDismountLocationInDirection(vec32, p_30576_);
			return vec33 != null ? vec33 : this.position();
		}
	}
	
	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;
	// @Override
	// public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
	//     if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
	//         return itemHandler.cast();
	//     return super.getCapability(capability, facing);
	// }
	
	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null) {
			net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
			itemHandler = null;
			oldHandler.invalidate();
		}
	}
	
	private float isFavouriteFood(Item food){
		float effect = 2;
		int nat = getNature();
		if(food == Register.FOOD_GYSAHL.get()){ if(nat % 5 == 0) effect++; if(nat / 5 == 0) effect--; }
		if(food == Register.FOOD_KRAKKA.get()){ if(nat % 5 == 1) effect++; if(nat / 5 == 1) effect--; }
		if(food == Register.FOOD_MIMETT.get()){ if(nat % 5 == 2) effect++; if(nat / 5 == 2) effect--; }
		if(food == Register.FOOD_SYLKIS.get()){ if(nat % 5 == 3) effect++; if(nat / 5 == 3) effect--; }
		if(food == Register.FOOD_TANTAL.get()){ if(nat % 5 == 4) effect++; if(nat / 5 == 4) effect--; }
		return effect;
	}
	
	private Item getFoodLiked(){ // needs to be rearranged
		int nat = getNature();
		if(nat % 5 == 0) return Register.FOOD_GYSAHL.get();
		if(nat % 5 == 1) return Register.FOOD_KRAKKA.get();
		if(nat % 5 == 2) return Register.FOOD_MIMETT.get();
		if(nat % 5 == 3) return Register.FOOD_SYLKIS.get();
		if(nat % 5 == 4) return Register.FOOD_TANTAL.get();
		return Items.BEETROOT;
	}
	private Item getFoodHated(){ // needs to be rearranged
		int nat = getNature();
		if(nat / 5 == 0) return Register.FOOD_GYSAHL.get();
		if(nat / 5 == 1) return Register.FOOD_KRAKKA.get();
		if(nat / 5 == 2) return Register.FOOD_MIMETT.get();
		if(nat / 5 == 3) return Register.FOOD_SYLKIS.get();
		if(nat / 5 == 4) return Register.FOOD_TANTAL.get();
		return Items.BEETROOT;
	}
	
	public SimpleContainer getInventory(){
		return this.inventory;
	}
	
	
	
	
	// ---------- ---------- ---------- ----------  SOUND  ---------- ---------- ---------- ---------- //
	
	protected SoundEvent getAmbientSound() {
		return SoundEvents.LLAMA_AMBIENT;
		
		////    if (this.isAngry()) {
		////    	return ShopKeeper.SOUND_CHOCOBO_ANGRY.get();
		////    } else if (this.random.nextInt(3) == 0) {
		////    	return this.isTame() && this.getHealth() < 10.0F ? ShopKeeper.SOUND_CHOCOBO_HEALTH_LOW.get() : ShopKeeper.SOUND_CHOCOBO_HEALTH_HIGH.get();
		////    } else {
		////    	return ShopKeeper.SOUND_CHOCOBO_AMBIENT.get();
		////    }
	}
	
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.LLAMA_HURT;
		
		////    return ShopKeeper.SOUND_CHOCOBO_HURT.get();
	}
	
	protected SoundEvent getDeathSound() {
		return SoundEvents.LLAMA_DEATH;
		
		////    return ShopKeeper.SOUND_CHOCOBO_DEATH.get();
	}
	
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.LLAMA_STEP, 0.15F, 1.0F);
		
		////    if (!state.getMaterial().isLiquid()) {
		////        BlockState blockstate = this.level().getBlockState(pos.above());
		////        SoundType soundtype = state.getSoundType(level, pos, this);
		////        if (blockstate.is(Blocks.SNOW)) {
		////            soundtype = blockstate.getSoundType(level, pos, this);
		////        }
		////        if (this.isVehicle() && this.canGallop) {
		////            ++this.gallopSoundCounter;
		////            if (this.gallopSoundCounter <= 5) {
		////                this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
		////            }
		////        } else if (soundtype == SoundType.WOOD) {
		////            this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
		////        } else {
		////            this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
		////        }
		////    }
	}
	
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}
	
	@Nullable
	protected SoundEvent getAngrySound() {
		return SoundEvents.HORSE_ANGRY;
		////    return ShopKeeper.SOUND_CHOCOBO_ANGRY.get();
	}
	
	protected float getSoundVolume() {
		return 0.8F;
	}
	
	public int getAmbientSoundInterval() {
		return 400;
	}
	
	protected void playJumpSound() {
		this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  GOALS  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
