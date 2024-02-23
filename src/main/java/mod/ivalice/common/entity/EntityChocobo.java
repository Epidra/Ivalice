package mod.ivalice.common.entity;

import mod.ivalice.Register;
import mod.ivalice.client.menu.MenuChocoboBasic;
import mod.ivalice.common.entity.goals.*;
import mod.lucky77.util.system.SystemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
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
import java.util.function.Predicate;

public class EntityChocobo extends TamableAnimal implements HasCustomInventoryScreen, NeutralMob, ContainerListener, PlayerRideableJumping {
	
	// --- ATTRIBUTES --- //
	private static final EntityDataAccessor<Integer>        DATA_STATS         = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer>        DATA_COLOR         = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer>        DATA_TIMER_ANGER   = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean>        DATA_INTERESTED_ID = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte>           DATA_ID_FLAGS      = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.OPTIONAL_UUID);
	
	private static final Predicate<LivingEntity> PARENT_SELECTOR = (p_213617_0_) -> { return p_213617_0_ instanceof EntityChocobo && ((EntityChocobo)p_213617_0_).isBred(); };
	private static final TargetingConditions MOMMY_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_SELECTOR);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Register.FOOD_GYSAHL.get(), Register.FOOD_KRAKKA.get(), Register.FOOD_MIMETT.get(), Register.FOOD_SYLKIS.get(), Register.FOOD_TANTAL.get());
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	
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
	private float standAnim;
	private float mouthAnim;
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
	private boolean isWet;
	private boolean isShaking;
	private float shakeAnim;
	private float shakeAnimO;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private UUID persistentAngerTarget;
	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public EntityChocobo(EntityType<? extends EntityChocobo> entity, Level level) {
		super(entity, level);
		this.setTame(false);
		this.setMaxUpStep(1.0F); // <-- Step Height
		this.createInventory();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_STATS,             0);
		this.entityData.define(DATA_COLOR,             0);
		this.entityData.define(DATA_TIMER_ANGER,       0);
		this.entityData.define(DATA_INTERESTED_ID, false);
		this.entityData.define(DATA_ID_FLAGS,    (byte)0);
		this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
	}
	
	protected void registerGoals() {
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
		this.goalSelector.addGoal( 9, new GoalTempt(this, 1.1D, FOOD_ITEMS, false));
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
				.add(Attributes.ATTACK_KNOCKBACK);
	}
	
	private void randomizeAttributes(RandomSource random){
		int[] valuePool = new int[]{0, 0, 0, 0, 0};
		int points = random.nextInt(10, 20);
		while(points > 0){
			valuePool[random.nextInt(5)]++;
			points--;
		}
		generateAttributes(valuePool, random);
	}
	
	public void generateAttributesOffspring(int statsA, int statsB, int colorA, int colorB){
		int[] valuePool = new int[]{0, 0, 0, 0, 0};
		int[] statsDataA = new int[]{
				statsA >>  0 & 31,
				statsA >>  5 & 31,
				statsA >> 10 & 31,
				statsA >> 15 & 31,
				statsA >> 20 & 31,
		};
		int[] statsDataB = new int[]{
				statsB >>  0 & 31,
				statsB >>  5 & 31,
				statsB >> 10 & 31,
				statsB >> 15 & 31,
				statsB >> 20 & 31,
		};
		valuePool[0] = (statsDataA[0] + statsDataB[0]) / 2;
		valuePool[1] = (statsDataA[1] + statsDataB[1]) / 2;
		valuePool[2] = (statsDataA[2] + statsDataB[2]) / 2;
		valuePool[3] = (statsDataA[3] + statsDataB[3]) / 2;
		valuePool[4] = (statsDataA[4] + statsDataB[4]) / 2;
		int points = random.nextInt(0, 10);
		while(points > 0){
			valuePool[random.nextInt(5)]++;
			points--;
		}
		
		// --- Set Statistics --- //
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 + valuePool[0]);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue( 1 + valuePool[1]);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue( (0.23f + valuePool[2])*0.12f);
		this.getAttribute(Attributes.FLYING_SPEED).setBaseValue( 1 + valuePool[3]);
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue( 1 + valuePool[4]);
		this.setMaxUpStep(1.0F); // <-- Step Height
		int nature = random.nextInt(25);
		int gender = random.nextInt(2);
		
		// --- Save Stat Points --- //
		int data = 0;
		data += valuePool[0];
		data += valuePool[1] << 5;
		data += valuePool[2] << 10;
		data += valuePool[3] << 15;
		data += valuePool[4] << 20;
		data += nature       << 25;
		data += gender       << 30;
		this.entityData.set(DATA_STATS, data);
		
		setFeatherColor(mixColors(SystemColor.convert(colorA), SystemColor.convert(colorB)));
	}
	
	protected void generateAttributes(int[] valuePool, RandomSource random) {
		int points = random.nextInt(100);
		
		// --- Generate Feather Color --- //
		int offset = -10 + random.nextInt(20);
		/* BLACK  */      if(points == 0){ valuePool[2]+=2; valuePool[3]+=2; setFeatherColor(new byte[]{ (byte)(10 + offset), (byte)(10 + offset), (byte)(10 + offset) }); }
		/* WHITE  */ else if(points == 1){ valuePool[0]+=2; valuePool[1]+=2; setFeatherColor(new byte[]{ (byte)(50 + offset), (byte)(50 + offset), (byte)(50 + offset) }); }
		/* PURPLE */ else if(points <  5){ valuePool[2]+=2;                  setFeatherColor(new byte[]{ (byte)(30 + offset), (byte)( 0         ), (byte)(60 + offset) }); }
		/* RED    */ else if(points < 15){ valuePool[0]+=2;                  setFeatherColor(new byte[]{ (byte)(50 + offset), (byte)( 0         ), (byte)(30 + offset) }); }
		/* GREEN  */ else if(points < 25){ valuePool[1]+=2;                  setFeatherColor(new byte[]{ (byte)(10 + offset), (byte)(50 + offset), (byte)(10 + offset) }); }
		/* BLUE   */ else if(points < 35){ valuePool[3]+=2;                  setFeatherColor(new byte[]{ (byte)(10 + offset), (byte)(10 + offset), (byte)(50 + offset) }); }
		/* YELLOW */ else {                                                  setFeatherColor(new byte[]{ (byte)(50 + offset), (byte)(50 + offset), (byte)( 0         ) }); }
		
		// --- Set Statistics --- //
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 + valuePool[0]);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue( 1 + valuePool[1]);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue( (0.23f + valuePool[2])*0.12f);
		this.getAttribute(Attributes.FLYING_SPEED).setBaseValue( 1 + valuePool[3]);
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue( 1 + valuePool[4]);
		this.setMaxUpStep(1.0F); // <-- Step Height
		int nature = random.nextInt(25);
		int gender = random.nextInt(2);
		
		// --- Save Stat Points --- //
		int data = 0;
		data += valuePool[0];
		data += valuePool[1] << 5;
		data += valuePool[2] << 10;
		data += valuePool[3] << 15;
		data += valuePool[4] << 20;
		data += nature       << 25;
		data += gender       << 30;
		this.entityData.set(DATA_STATS, data);
		
		// --- Test Loading --- //
		data = this.entityData.get(DATA_STATS);
		int value_1 = (data      ) & 31;
		int value_2 = (data >>  5) & 31;
		int value_3 = (data >> 10) & 31;
		int value_4 = (data >> 15) & 31;
		int value_5 = (data >> 20) & 31;
		int value_6 = (data >> 25) & 31;
		int value_7 = (data >> 30) & 31;
		boolean breakflag = false;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  AI STEP  ---------- ---------- ---------- ---------- //
	
	protected void customServerAiStep() {
		this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
		super.customServerAiStep();
	}
	
	public void aiStep() {
		super.aiStep();
		if (this.level().isClientSide) {
			this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
		}
		if (this.random.nextInt(200) == 0) {
			this.moveWings();
		}
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
		super.tick();
		if (this.isAlive()) {
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
		if (this.isStanding()) {
			this.eatAnim = 0.0F;
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
		if(!this.level().isClientSide){
			if(player.getMainHandItem().isEmpty()){
				
				// --- Access Inventory --- //
				if (player.isShiftKeyDown() && !this.isBaby()) {
					this.openCustomInventoryScreen((ServerPlayer) player);
					return InteractionResult.SUCCESS;
				}
				
				// --- Interaction - Ride --- //
				else {
					InteractionResult actionresulttype = super.mobInteract(player, hand);
					if (!this.isBaby() && !actionresulttype.consumesAction() && this.isOwnedBy(player)) {
						this.doPlayerRide(player);
						return InteractionResult.SUCCESS;
					} return actionresulttype;
				}
			} else {
				ItemStack itemstack = player.getItemInHand(hand);
				Item item = itemstack.getItem();
				
				// --- Attempt to tame --- //
				if(this.isFood(itemstack) && !this.isTame()) {
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
					return InteractionResult.SUCCESS;
				}
				
				// --- Feed Greens --- //
				else if(this.isFood(itemstack) /*&& this.isTame()*/) {
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
				}
				
				// --- Equip Saddle --- //
				else if(item == Items.SADDLE){
					if(this.isTame() && !isSaddled() && isSaddleable()){
						this.inventory.setItem(0, new ItemStack(Items.SADDLE));
						if (!player.getAbilities().instabuild) { itemstack.shrink(1); }
						this.level().playSound((Player) null, this, SoundEvents.HORSE_SADDLE, SoundSource.AMBIENT, 0.5F, 1.0F);
						return InteractionResult.SUCCESS;
					}
				}
				
				// --- Equip Armor --- //
				else if(this.isArmor(itemstack)){
					if(!this.isWearingArmor() && this.isArmor(itemstack)){
						this.inventory.setItem(1, itemstack.copyWithCount(1));
						if (!player.getAbilities().instabuild) { itemstack.shrink(1); }
						this.level().playSound((Player) null, this, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.AMBIENT, 0.5F, 1.0F);
						return InteractionResult.SUCCESS;
					}
				}
				
				// --- Equip Chest --- //
				// else if(this.isArmor(itemstack)){
				// 	if(this.canWearArmor() && !this.isWearingArmor() && this.isArmor(itemstack)){
				// 		this.inventory.setItem(1, itemstack.copyWithCount(1));
				// 		if (!player.getAbilities().instabuild) { itemstack.shrink(1); }
				// 		this.level().playSound((Player) null, this, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.AMBIENT, 0.5F, 1.0F);
				// 		return InteractionResult.SUCCESS;
				// 	}
				// }
				
				// --- Dye Collar --- //
				else if(item instanceof DyeItem){
					DyeColor dyecolor = ((DyeItem) item).getDyeColor();
					if (dyecolor != this.getCollarColor()) {
						this.setCollarColor(dyecolor);
						if (!player.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
					}
					return InteractionResult.SUCCESS;
				}
				
				// --- Interaction - Sit down --- //
				else if(item == Items.FEATHER){
					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					return InteractionResult.SUCCESS;
				}
			}
		}
		
		// --- RETURN State --- //
		return super.mobInteract(player, hand);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("data1", this.entityData.get(DATA_STATS));
		compound.putInt("data2", this.entityData.get(DATA_COLOR));
		if (!this.inventory.getItem(0).isEmpty()) { compound.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag())); }
		if (!this.inventory.getItem(1).isEmpty()) { compound.put("ArmorItem",  this.inventory.getItem(1).save(new CompoundTag())); }
		if (this.getOwnerUUID() != null) { compound.putUUID("Owner", this.getOwnerUUID()); }
	}
	
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.entityData.set(DATA_STATS, compound.getInt("data1"));
		this.entityData.set(DATA_COLOR, compound.getInt("data2"));
		if (compound.contains("SaddleItem", 10)) { ItemStack itemstack = ItemStack.of(compound.getCompound("SaddleItem")); if (itemstack.getItem() == Items.SADDLE) { this.inventory.setItem(0, itemstack); } }
		if (compound.contains("ArmorItem",  10)) { ItemStack itemstack = ItemStack.of(compound.getCompound("ArmorItem" )); if (!itemstack.isEmpty() && this.isArmor(itemstack)) { this.inventory.setItem(1, itemstack); } }
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
		
		this.updateEquipment();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - COLOR  ---------- ---------- ---------- ---------- //
	
	public byte[] getFeatherColor(){
		return new byte[]{
				(byte)((this.entityData.get(DATA_COLOR) >>  4) & 63),
				(byte)((this.entityData.get(DATA_COLOR) >> 10) & 63),
				(byte)((this.entityData.get(DATA_COLOR) >> 16) & 63),
		};
	}
	
	public void setFeatherColor(byte[] color) {
		this.entityData.set(DATA_COLOR, (this.entityData.get(DATA_COLOR) & 15) | (color[0] <<  4) | (color[1] << 10) | (color[2] << 16) );
	}
	
	public DyeColor getCollarColor() {
		return DyeColor.byId(this.entityData.get(DATA_COLOR) & 15);
	}
	
	public void setCollarColor(DyeColor color) {
		int data = this.entityData.get(DATA_COLOR) >> 4;
		this.entityData.set(DATA_COLOR, color.getId() | (data << 4));
	}
	
	public byte[] mixColors(byte[] color1, byte[] color2){
		return new byte[]{
				(byte) Mth.clamp( (color1[0] + color2[0] + getRandom().nextInt(6) - 3) / 2, 0, 63 ),
				(byte) Mth.clamp( (color1[1] + color2[1] + getRandom().nextInt(6) - 3) / 2, 0, 63 ),
				(byte) Mth.clamp( (color1[2] + color2[2] + getRandom().nextInt(6) - 3) / 2, 0, 63 )
		};
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - ANIMATION  ---------- ---------- ---------- ---------- //
	
	@OnlyIn(Dist.CLIENT)
	public Vec3 getLeashOffset() { return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F)); }
	public double getPassengersRidingOffset() { return 1.5D; }
	public double getMyRidingOffset() { return 0.14D; }
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimension) { return dimension.height * 0.95F; }
	public boolean animFly( ){ return isFlying; }
	public boolean animRun( ){ return isRunning && isMoving; }
	public boolean animKweh(){ return false; }
	public boolean animRide(){ return getControllingPassenger() != null; }
	public boolean animPick(){ return eatAnimationTick > 0; }
	public boolean animSit( ){ return isInSittingPose() && getControllingPassenger() == null; }
	public boolean animFemale(){ return this.entityData.get(DATA_STATS) >> 30 == 1; }
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - SPAWN  ---------- ---------- ---------- ---------- //
	
	public int getMaxSpawnClusterSize() {
		return 1;
	}
	
	public static boolean canChocoboSpawn(EntityType<? extends EntityChocobo> animal, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && level.getRawBrightness(pos, 0) > 8 && level.canSeeSky(pos);
	}
	
	@javax.annotation.Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroup, @javax.annotation.Nullable CompoundTag compound) {
		if(spawnType == MobSpawnType.SPAWN_EGG){
			this.generateAttributes(new int[]{5, 5, 5, 5, 5}, level.getRandom());
		} else  if(spawnType == MobSpawnType.BREEDING){
			// this.offspringAttributes(level.getRandom());
		} else {
			this.randomizeAttributes(level.getRandom());
		}
		this.setCollarColor(DyeColor.byId(level.getRandom().nextInt(16)));
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroup, compound);
	}
	
	protected void onOffspringSpawnedFromEgg(Player player, Mob mob) {
	
	}
	
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
		EntityChocobo parent = (EntityChocobo)entity;
		EntityChocobo child = Register.ENTITY_CHOCOBO.get().create(level);
		child.setFeatherColor(this.mixColors(this.getFeatherColor(), parent.getFeatherColor()));
		
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			child.setOwnerUUID(uuid);
			child.setTame(true);
		}
		return child;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - INVENTORY  ---------- ---------- ---------- ---------- //
	
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
		this.updateEquipment();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
	}
	
	
	
	public void containerChanged(Container container) {
		boolean flag = this.isSaddled();
		this.updateEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
		}
		ItemStack itemstack  = this.getArmor();
		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}
	
	protected void updateEquipment() {
		if (!this.level().isClientSide) {
			this.setArmor(this.inventory.getItem(1));
			if (!this.level().isClientSide) {
				this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
				if (this.isArmor(this.inventory.getItem(1))) {
					int i = ((HorseArmorItem)this.inventory.getItem(1).getItem()).getProtection();
					if (i != 0) {
						this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
					}
				}
			}
			this.setDropChance(EquipmentSlot.CHEST, 0.0F);
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
		}
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
	
	protected int getInventorySize() {
		return 2;
	}
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - EQUIPMENT  ---------- ---------- ---------- ---------- //
	
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	
	private void setArmor(ItemStack stack) {
		this.setItemSlot(EquipmentSlot.CHEST, stack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0F);
	}
	
	public boolean isArmor(ItemStack stack) {
		return stack.getItem() instanceof HorseArmorItem;
	}
	
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
	
	public boolean isWearingArmor() {
		return !this.inventory.getItem(1).isEmpty();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public float getDataHealthNow(){ return this.getHealth(); }
	public float getDataHealthMax(){ return (float) this.getAttribute(Attributes.MAX_HEALTH).getValue(); }
	public int getDataAttack( ){ return this.entityData.get(DATA_STATS) >>  5 & 31;  }
	public int getDataDefense(){ return this.entityData.get(DATA_STATS)       & 31;  }
	public int getDataSpeed(  ){ return this.entityData.get(DATA_STATS) >> 10 & 31;  }
	public int getDataGlide(  ){ return this.entityData.get(DATA_STATS) >> 15 & 31;  }
	public int getDataJump(   ){ return this.entityData.get(DATA_STATS) >> 20 & 31;  }
	public int getDataAll(    ){ return this.entityData.get(DATA_STATS);  }
	public boolean getDataGender(){ return animFemale(); }
	public String getDataNature(){
		switch((byte)(this.entityData.get(DATA_STATS) >> 25) & 31){
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
	
	protected boolean getFlag(int flag) {
		return (this.entityData.get(DATA_ID_FLAGS) & flag) != 0;
	}
	
	protected void setFlag(int flag, boolean operation) {
		byte b0 = this.entityData.get(DATA_ID_FLAGS);
		if (operation) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 |  flag));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~flag));
		}
	}
	
	public void setTamed(boolean flag) {
		this.setFlag(2, flag);
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
	
	public void setBred(boolean flag) {
		this.setFlag(8, flag);
	}
	
	public boolean isSaddled() {
		return this.getFlag(4);
	}
	
	public boolean isArmored(){
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}
	
	public boolean isChested(){
		return false;
	}
	
	// Unneeded
	public void setTame(boolean flag) {
		super.setTame(flag);
	}
	
	public boolean canMate(Animal loveInterest) {
		if (loveInterest == this) {
			return false;
		} else if (!this.isTame()) {
			return false;
		} else if (!(loveInterest instanceof EntityChocobo)) {
			return false;
		} else {
			return this.isInLove() && ((EntityChocobo)loveInterest).isInLove();
		}
	}
	
	public boolean isInterested() {
		return this.entityData.get(DATA_INTERESTED_ID);
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
	public void handleEntityEvent(byte data) {
		
		// --- ??? --- //
		if (data == 10) {
			this.eatAnimationTick = 40;
		} else if(data == 11){
			this.isRunning = true;
		} else if(data == 12){
			this.isRunning = false;
		} else {
			super.handleEntityEvent(data);
		}
		
		// --- ??? --- //
		if (data == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(data);
		}
		if (data == 8) {
			this.isShaking = true;
			this.shakeAnim = 0.0F;
			this.shakeAnimO = 0.0F;
		} else if (data == 56) {
			this.cancelShake();
		} else {
			super.handleEntityEvent(data);
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
	
	public void die(DamageSource damage) {
		this.isWet = false;
		this.isShaking = false;
		this.shakeAnimO = 0.0F;
		this.shakeAnim = 0.0F;
		super.die(damage);
	}
	
	public boolean hurt(DamageSource damage, float amount) {
		if (this.isInvulnerableTo(damage)) {
			return false;
		} else {
			Entity entity = damage.getEntity();
			this.setOrderedToSit(false);
			return super.hurt(damage, amount);
		}
	}
	
	public boolean doHurtTarget(Entity entity) {
		return true;
	}
	
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}
	
	public boolean wantsToAttack(LivingEntity monster, LivingEntity player) {
		if (!(monster instanceof Creeper) && !(monster instanceof Ghast)) {
			if (monster instanceof Wolf) {
				Wolf wolf = (Wolf)monster;
				return !wolf.isTame() || wolf.getOwner() != player;
			} else if (monster instanceof Player && player instanceof Player && !((Player)player).canHarmPlayer((Player)monster)) {
				return false;
			} else if (monster instanceof EntityChocobo && ((EntityChocobo)monster).isTamed()) {
				return false;
			} else {
				return !(monster instanceof TamableAnimal) || !((TamableAnimal)monster).isTame();
			}
		} else {
			return false;
		}
	}
	
	public boolean canBeLeashed(Player player) {
		return !this.isAngry() && super.canBeLeashed(player);
	}
	
	private int getNature() {
		return (this.entityData.get(DATA_STATS) >> 25) & 31;
	}
	
	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_ID_OWNER_UUID).orElse((UUID)null);
	}
	
	public void setOwnerUUID(@Nullable UUID owner) {
		this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(owner));
	}
	
	public boolean isJumping() {
		return this.isJumping;
	}
	
	public void setIsJumping(boolean flag) {
		this.isJumping = flag;
	}
	
	protected void onLeashDistance(float distance) {
		if (distance > 6.0F && this.isEating()) {
			this.setEating(false);
		}
	}
	
	public int getTemper() {
		return this.temper;
	}
	
	public void setTemper(int temper) {
		this.temper = temper;
	}
	
	public int modifyTemper(int temper) {
		int i = Mth.clamp(this.getTemper() + temper, 0, this.getMaxTemper());
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
	
	protected int calculateFallDamage(float value1, float value2) {
		return Mth.ceil((value1 * 0.5F - 3.0F) * value2);
	}
	
	public double getCustomJump() {
		return this.getAttributeValue(Attributes.JUMP_STRENGTH);
	}
	
	public int getMaxTemper() {
		return 100;
	}
	
	protected void doPlayerRide(Player player) {
		this.setEating(false);
		this.setStanding(false);
		if (!this.level().isClientSide) {
			player.setYRot(this.getYRot());
			player.setXRot(this.getXRot());
			player.startRiding(this);
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
	
	public void setEating(boolean flag) {
		this.setFlag(16, flag);
	}
	
	public void setStanding(boolean flag) {
		if (flag) {
			this.setEating(false);
		} this.setFlag(32, flag);
	}
	
	private void stand() {
		if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
			this.standCounter = 1;
			this.setStanding(true);
		}
	}
	
	public void travel(Vec3 destination) {
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
					super.travel(new Vec3((double)f, destination.y, (double)f1));
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
				super.travel(destination);
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
	public void onPlayerJump(int value) {
		if (this.isSaddled()) {
			if (value < 0) {
				value = 0;
			} else {
				this.allowStandSliding = true;
				this.stand();
			}
			if (value >= 90) {
				this.playerJumpPendingScale = 1.0F;
			} else {
				this.playerJumpPendingScale = 0.4F + 0.4F * (float)value / 90.0F;
			}
		}
	}
	
	public boolean canJump() {
		return this.isSaddled();
	}
	
	public void handleStartJump(int value) {
		this.allowStandSliding = true;
		this.stand();
		this.playJumpSound();
	}
	
	public void handleStopJump() {
	
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void spawnTamingParticles(boolean flag) {
		ParticleOptions iparticledata = flag ? ParticleTypes.HEART : ParticleTypes.SMOKE;
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
	
	public boolean onClimbable() {
		if (this.isSpectator()) {
			return false;
		} else {
			BlockPos blockpos = this.blockPosition();
			BlockState blockstate = this.getFeetBlockState();
			Optional<BlockPos> ladderPos = net.minecraftforge.common.ForgeHooks.isLivingOnLadder(blockstate, level(), blockpos, this);
			return ladderPos.isPresent();
		}
	}
	
	@Nullable
	private Vec3 getDismountLocationInDirection(Vec3 position, LivingEntity entity) {
		double d0 = this.getX() + position.x;
		double d1 = this.getBoundingBox().minY;
		double d2 = this.getZ() + position.z;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		for(Pose pose : entity.getDismountPoses()) {
			blockpos$mutableblockpos.set(d0, d1, d2);
			double d3 = this.getBoundingBox().maxY + 0.75D;
			while(true) {
				double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
				if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
					break;
				}
				if (DismountHelper.isBlockFloorValid(d4)) {
					AABB aabb = entity.getLocalBoundsForPose(pose);
					Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
					if (DismountHelper.canDismountTo(this.level(), entity, aabb.move(vec3))) {
						entity.setPose(pose);
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
	
	public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
		Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)entity.getBbWidth(), this.getYRot() + (entity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
		Vec3 vec31 = this.getDismountLocationInDirection(vec3, entity);
		if (vec31 != null) {
			return vec31;
		} else {
			Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)entity.getBbWidth(), this.getYRot() + (entity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
			Vec3 vec33 = this.getDismountLocationInDirection(vec32, entity);
			return vec33 != null ? vec33 : this.position();
		}
	}
	
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
	
	public Item getFoodLiked(){ // needs to be rearranged
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
		return Register.SOUND_CHOCOBO_AMBIENT.get();
	}
	
	protected SoundEvent getHurtSound(DamageSource source) {
		return Register.SOUND_CHOCOBO_HURT.get();
	}
	
	protected SoundEvent getDeathSound() {
		return Register.SOUND_CHOCOBO_DEATH.get();
	}
	
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(Register.SOUND_CHOCOBO_STEP.get(), 0.15F, 1.0F);
	}
	
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
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