package mod.ivalice.entity;

import mod.ivalice.ShopKeeper;
import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import static net.minecraft.sounds.SoundSource.AMBIENT;

public class EntityChocobo extends TamableAnimal implements NeutralMob, ContainerListener, PlayerRideableJumping, Saddleable {

    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

    private static final EntityDataAccessor<Integer>        DATA_COLOR_FEATHER        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>        DATA_COLOR_COLLAR         = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean>        DATA_INTERESTED_ID        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>        DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>        DATA_ID_NATURE            = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte>           DATA_ID_FLAGS             = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID        = SynchedEntityData.defineId(EntityChocobo.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final Predicate<LivingEntity> PARENT_SELECTOR = (p_213617_0_) -> { return p_213617_0_ instanceof EntityChocobo && ((EntityChocobo)p_213617_0_).isBred(); };
    private static final TargetingConditions MOMMY_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_SELECTOR);
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ShopKeeper.FOOD_GYSAHL.get(), ShopKeeper.FOOD_KRAKKA.get(), ShopKeeper.FOOD_MIMETT.get(), ShopKeeper.FOOD_SYLKIS.get(), ShopKeeper.FOOD_TANTAL.get());

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





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public EntityChocobo(EntityType<? extends EntityChocobo> entity, Level world) {
        super(entity, world);
        this.setTame(false);
        this.maxUpStep = 1.0F;
        this.createInventory();
    }





    //----------------------------------------REGISTER----------------------------------------//

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
        this.goalSelector.addGoal( 8, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
        this.goalSelector.addGoal( 9, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, this.eatBlockGoal);
        this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.JUMP_STRENGTH)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
    }

    protected float generateRandomMaxHealth() {
        return 15.0F + (float)this.random.nextInt(8) + (float)this.random.nextInt(9);
    }

    protected double generateRandomJumpStrength() {
        return (double)0.8F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    protected double generateRandomSpeed() {
        return ((double)0.45F + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.5D;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_COLOR_FEATHER, DyeColor.GRAY.getTextColor());
        this.entityData.define(DATA_INTERESTED_ID, false);
        this.entityData.define(DATA_COLOR_COLLAR, DyeColor.RED.getId());
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(DATA_ID_NATURE, 0);
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
        this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
    }





    //----------------------------------------AI----------------------------------------//

    public void aiStep() {
        if (this.level.isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        if (this.random.nextInt(200) == 0) {
            this.moveWings();
        }
        super.aiStep();
        if (!this.level.isClientSide && this.isWet && !this.isShaking && !this.isPathFinding() && this.onGround) {
            this.isShaking = true;
            this.shakeAnim = 0.0F;
            this.shakeAnimO = 0.0F;
            this.level.broadcastEntityEvent(this, (byte)8);
        }
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level, true);
        }
        if (!this.level.isClientSide && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
            if (this.canEatGrass()) {
                if (!this.isEating() && !this.isVehicle() && this.random.nextInt(300) == 0 && this.level.getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
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
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping = (float)((double)this.flapping * 0.9D);
        Vec3 vector3d = this.getDeltaMovement();
        isMoving = vector3d.x != 0 || vector3d.z != 0;
        if (!this.onGround && vector3d.y < 0.0D) {
            this.setDeltaMovement(vector3d.multiply(1.05D, 0.6D, 1.05D));
            isFlying = true;
        } else {
            isFlying = false;
        }
        this.flap += this.flapping * 2.0F;
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.EGG);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }
    }

    protected void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        this.isRunning = this.goalRunAround.isRunning();
        super.customServerAiStep();
    }





    //----------------------------------------SPAWN----------------------------------------//

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnreason, @Nullable SpawnGroupData entity, @Nullable CompoundTag compound) {
        if(spawnreason == MobSpawnType.SPAWN_EGG){
            this.setColorFeather(DyeColor.GRAY.getTextColor());
            this.randomizeAttributes();
        }else if(spawnreason == MobSpawnType.BREEDING){
            this.randomizeAttributes();
        } else {
            this.randomizeAttributes();
            this.setColorFeather(getColorRandomized(world.getRandom()).getTextColor());
        }
        return super.finalizeSpawn(world, difficulty, spawnreason, entity, compound);
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void tick() {
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
                if (this.isShaking && !this.level.isClientSide) {
                    this.level.broadcastEntityEvent(this, (byte)56);
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
                        this.level.addParticle(ParticleTypes.SPLASH, this.getX() + (double)f1, (double)(f + 0.8F), this.getZ() + (double)f2, vector3d.x, vector3d.y, vector3d.z);
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





    //----------------------------------------INTERACTION----------------------------------------//

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if(this.isFood(itemstack)){
                if(this.isTame()){
                    if (this.getHealth() < this.getMaxHealth()) {
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        float scale = isFavouriteFood(item) * 0.5f;
                        this.heal((float) item.getFoodProperties().getNutrition());
                        return InteractionResult.SUCCESS;
                    }
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
                        this.level.broadcastEntityEvent(this, (byte)7);
                    } else {
                        this.level.broadcastEntityEvent(this, (byte)6);
                    }
                }
            } else if(item == Items.SADDLE){
                if(this.isTame()){
                    if(!isSaddled() && isSaddleable()){
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        this.equipSaddle(AMBIENT);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if(item instanceof DyeItem){
                DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                if(player.isCreative()){
                    if(player.isCrouching()){
                        this.setColorFeather(dyecolor.getTextColor());
                    } else {
                        this.setColorFeather(getMixedColor(dyecolor.getTextColor(), getColorFeatherData()));
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    if (dyecolor != this.getColorCollar()) {
                        this.setColorCollar(dyecolor);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if(player.isCrouching()){
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
        }
        return super.mobInteract(player, hand);
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("ColorFeather", this.getColorFeatherData());
        compound.putInt("ColorCollar", this.getColorCollar().getId());
        if (compound.contains("EggLayTime")) { this.eggTime = compound.getInt("EggLayTime"); }
        compound.putInt("Nature", this.getNature());
        if (!this.inventory.getItem(1).isEmpty()) { compound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag())); }
        compound.putBoolean("EatingHaystack", this.isEating());
        compound.putBoolean("Bred", this.isBred());
        compound.putInt("Temper", this.getTemper());
        compound.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUUID() != null) { compound.putUUID("Owner", this.getOwnerUUID()); }
        if (!this.inventory.getItem(0).isEmpty()) { compound.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag())); }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setColorFeather(compound.getInt("ColorFeather"));
        compound.putInt("EggLayTime", this.eggTime);
        if (compound.contains("ColorCollar", 99)) {
            this.setColorCollar(DyeColor.byId(compound.getInt("ColorCollar")));
        }
        if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
            this.readPersistentAngerSaveData((ServerLevel)this.level, compound);
        this.setNature(compound.getInt("Nature"));
        if (compound.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
        this.setEating(compound.getBoolean("EatingHaystack"));
        this.setBred(compound.getBoolean("Bred"));
        this.setTemper(compound.getInt("Temper"));
        this.setTamed(compound.getBoolean("Tame"));
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





    //----------------------------------------ANIMATION_EXTRA----------------------------------------//

    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }





    //----------------------------------------SOUND----------------------------------------//

    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return ShopKeeper.SOUND_CHOCOBO_ANGRY.get();
        } else if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? ShopKeeper.SOUND_CHOCOBO_HEALTH_LOW.get() : ShopKeeper.SOUND_CHOCOBO_HEALTH_HIGH.get();
        } else {
            return ShopKeeper.SOUND_CHOCOBO_AMBIENT.get();
        }
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ShopKeeper.SOUND_CHOCOBO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ShopKeeper.SOUND_CHOCOBO_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!state.getMaterial().isLiquid()) {
            BlockState blockstate = this.level.getBlockState(pos.above());
            SoundType soundtype = state.getSoundType(level, pos, this);
            if (blockstate.is(Blocks.SNOW)) {
                soundtype = blockstate.getSoundType(level, pos, this);
            }
            if (this.isVehicle() && this.canGallop) {
                ++this.gallopSoundCounter;
                if (this.gallopSoundCounter <= 5) {
                    this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
                }
            } else if (soundtype == SoundType.WOOD) {
                this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
            } else {
                this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
            }
        }
    }

    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }

    @Nullable
    protected SoundEvent getAngrySound() {
        return ShopKeeper.SOUND_CHOCOBO_ANGRY.get();
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





    //----------------------------------------INVENTORY----------------------------------------//

    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }

    private void setArmor(ItemStack p_213805_1_) {
        this.setItemSlot(EquipmentSlot.CHEST, p_213805_1_);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setArmorEquipment(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        }
    }

    private void setArmorEquipment(ItemStack p_213804_1_) {
        this.setArmor(p_213804_1_);
        if (!this.level.isClientSide) {
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
            this.level.playSound((Player) null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
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





    //----------------------------------------TAMING----------------------------------------//

    public void setTame(boolean p_70903_1_) {
        super.setTame(p_70903_1_);
        if (p_70903_1_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }





    //----------------------------------------COLORING----------------------------------------//

    public float[] getColorFeather() {
        int c = this.entityData.get(DATA_COLOR_FEATHER);
        int i = (c & 16711680) >> 16;
        int j = (c & '\uff00') >> 8;
        int k = (c & 255) >> 0;
        return new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
    }

    public int getColorFeatherData(){
        return this.entityData.get(DATA_COLOR_FEATHER);
    }

    public void setColorFeather(int i) {
        this.entityData.set(DATA_COLOR_FEATHER, i);
    }

    public static DyeColor getColorRandomized(Random random) {
        int i = random.nextInt(100);
        if(i <   5) return DyeColor.BLACK;
        if(i <  10) return DyeColor.WHITE;
        if(i <  40) return DyeColor.YELLOW;
        if(i <  70) return DyeColor.BLUE;
        if(i < 100) return DyeColor.RED;
        return DyeColor.GRAY;
    }
    public DyeColor getColorCollar() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR_COLLAR));
    }

    public int getMixedColor(int color1, int color2){
        float[] f1;
        float[] f2;
        {
            int i = (color1 & 16711680) >> 16;
            int j = (color1 & '\uff00') >> 8;
            int k = (color1 & 255) >> 0;
            f1 = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
        }
        {
            int i = (color2 & 16711680) >> 16;
            int j = (color2 & '\uff00') >> 8;
            int k = (color2 & 255) >> 0;
            f2 = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
        }
        float[] f3 = new float[]{
                (f1[0] + f2[0]) / 2.00f,
                (f1[1] + f2[1]) / 2.00f,
                (f1[2] + f2[2]) / 2.00f};
        int x = (int)(f3[0] * 255);
        int y = (int)(f3[1] * 255);
        int z = (int)(f3[2] * 255);
        return x << 16 | y << 8 | z << 0;
    }

    public void setColorCollar(DyeColor dyeColor) {
        this.entityData.set(DATA_COLOR_COLLAR, dyeColor.getId());
    }

    private int getOffspringColor(int color1, int color2, Random random) {
        return getMixedColor(color1, color2);
    }





    //----------------------------------------OFFSPRING----------------------------------------//

    public EntityChocobo getBreedOffspring(ServerLevel world, AgeableMob entity) {
        EntityChocobo parent = (EntityChocobo)entity;
        EntityChocobo child = ShopKeeper.ENTITY_CHOCOBO.get().create(world);
        child.setColorFeather(this.getOffspringColor(this.getColorFeatherData(), parent.getColorFeatherData(), getRandom()));
        child.setNature(random.nextInt(25));
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            child.setOwnerUUID(uuid);
            child.setTame(true);
        }
        return child;
    }

    public EntityChocobo getBreedOffspring(ServerLevel world, int colorA, int colorB) {
        EntityChocobo child = ShopKeeper.ENTITY_CHOCOBO.get().create(world);
        child.setColorFeather(this.getOffspringColor(colorA, colorB, getRandom()));
        child.setNature(random.nextInt(25));
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            child.setOwnerUUID(uuid);
            child.setTame(true);
        }
        return child;
    }

    public boolean canMate(Animal entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof EntityChocobo)) {
            return false;
        } else {
            return this.isInLove() && ((EntityChocobo)entity).isInLove();
        }
    }

    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID);
    }





    //----------------------------------------FLAG----------------------------------------//

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





    //----------------------------------------SUPPORT----------------------------------------//

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
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
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }
            return super.hurt(p_70097_1_, p_70097_2_);
        }
    }

    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, p_70652_1_);
        }
        return flag;
    }

    protected float getStandingEyeHeight(Pose p_30578_, EntityDimensions p_30579_) {
        return p_30579_.height * 0.95F;
    }

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    public int getMaxSpawnClusterSize() {
        return 4;
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_230260_1_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }

    public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
        if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
            if (p_30389_ instanceof Wolf) {
                Wolf wolf = (Wolf)p_30389_;
                return !wolf.isTame() || wolf.getOwner() != p_30390_;
            } else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).canHarmPlayer((Player)p_30389_)) {
                return false;
            } else if (p_30389_ instanceof AbstractHorse && ((AbstractHorse)p_30389_).isTamed()) {
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

    @OnlyIn(Dist.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }

    private void setNature(int id) {
        this.entityData.set(DATA_ID_NATURE, id);
    }

    private int getNature() {
        return this.entityData.get(DATA_ID_NATURE);
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
                this.level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
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
        if (!this.level.isClientSide) {
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
            LivingEntity livingentity = this.level.getNearestEntity(AbstractHorse.class, MOMMY_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
            if (livingentity != null && this.distanceToSqr(livingentity) > 4.0D) {
                this.navigation.createPath(livingentity, 0);
            }
        }
    }

    public boolean canEatGrass() {
        return true;
    }

    private void openMouth() {
        if (!this.level.isClientSide) {
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
                if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
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
                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double)f, p_30633_.y, (double)f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                if (this.onGround) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }
                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(p_30633_);
            }
        }
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
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
            this.level.addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public void positionRider(Entity p_184232_1_) {
        super.positionRider(p_184232_1_);
    }

    public boolean onClimbable() {
        return false;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

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
                double d4 = this.level.getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }
                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = p_30563_.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level, p_30563_, aabb.move(vec3))) {
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

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
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
        if(food == ShopKeeper.FOOD_GYSAHL.get()){ if(nat % 5 == 0) effect++; if(nat / 5 == 0) effect--; }
        if(food == ShopKeeper.FOOD_KRAKKA.get()){ if(nat % 5 == 1) effect++; if(nat / 5 == 1) effect--; }
        if(food == ShopKeeper.FOOD_MIMETT.get()){ if(nat % 5 == 2) effect++; if(nat / 5 == 2) effect--; }
        if(food == ShopKeeper.FOOD_SYLKIS.get()){ if(nat % 5 == 3) effect++; if(nat / 5 == 3) effect--; }
        if(food == ShopKeeper.FOOD_TANTAL.get()){ if(nat % 5 == 4) effect++; if(nat / 5 == 4) effect--; }
        return effect;
    }

    private Item getFoodLiked(){
        int nat = getNature();
        if(nat % 5 == 0) return ShopKeeper.FOOD_GYSAHL.get();
        if(nat % 5 == 1) return ShopKeeper.FOOD_KRAKKA.get();
        if(nat % 5 == 2) return ShopKeeper.FOOD_MIMETT.get();
        if(nat % 5 == 3) return ShopKeeper.FOOD_SYLKIS.get();
        if(nat % 5 == 4) return ShopKeeper.FOOD_TANTAL.get();
        return Items.BEETROOT;
    }

    private Item getFoodHated(){
        int nat = getNature();
        if(nat / 5 == 0) return ShopKeeper.FOOD_GYSAHL.get();
        if(nat / 5 == 1) return ShopKeeper.FOOD_KRAKKA.get();
        if(nat / 5 == 2) return ShopKeeper.FOOD_MIMETT.get();
        if(nat / 5 == 3) return ShopKeeper.FOOD_SYLKIS.get();
        if(nat / 5 == 4) return ShopKeeper.FOOD_TANTAL.get();
        return Items.BEETROOT;
    }

    public double getPassengersRidingOffset() {
        return 1.25D;
    }

    public static boolean canSpawnHere(EntityType<? extends EntityChocobo> animal, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, Random random) {
        return levelAccessor.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && levelAccessor.getRawBrightness(pos, 0) > 8 && levelAccessor.canSeeSky(pos);
    }

    public boolean AnimSaddle(){
        return isSaddled();
    }

    public boolean AnimFemale(){
        return false;
    }

    public boolean AnimYoung(){
        return isBaby();
    }

    public boolean AnimPick(){
        return eatAnimationTick > 0;
    }

    public boolean AnimFly(){
        return isFlying;
    }

    public boolean AnimKweh(){
        return false;
    }

    public boolean AnimRide(){
        return getControllingPassenger() != null;
    }

    public boolean AnimSit(){
        return isInSittingPose() && getControllingPassenger() == null;
    }

    public boolean AnimRun(){
        return isRunning && isMoving;
    }



}
