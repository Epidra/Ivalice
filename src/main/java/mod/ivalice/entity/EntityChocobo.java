package mod.ivalice.entity;

import mod.ivalice.ShopKeeper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class EntityChocobo extends TameableEntity implements IAngerable, IInventoryChangedListener, IJumpingMount, IEquipable {

    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

    private static final DataParameter<Integer>        DATA_COLOR_FEATHER        = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.INT);
    private static final DataParameter<Integer>        DATA_COLOR_COLLAR         = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.INT);
    private static final DataParameter<Boolean>        DATA_INTERESTED_ID        = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer>        DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.INT);
    private static final DataParameter<Integer>        DATA_ID_NATURE            = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.INT);
    private static final DataParameter<Byte>           DATA_ID_FLAGS             = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> DATA_ID_OWNER_UUID        = EntityDataManager.defineId(EntityChocobo.class, DataSerializers.OPTIONAL_UUID);

    private static final Predicate<LivingEntity> PARENT_SELECTOR = (p_213617_0_) -> { return p_213617_0_ instanceof EntityChocobo && ((EntityChocobo)p_213617_0_).isBred(); };
    private static final EntityPredicate MOMMY_TARGETING = (new EntityPredicate()).range(16.0D).allowInvulnerable().allowSameTeam().allowUnseeable().selector(PARENT_SELECTOR);
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ShopKeeper.FOOD_GYSAHL.get(), ShopKeeper.FOOD_KRAKKA.get(), ShopKeeper.FOOD_MIMETT.get(), ShopKeeper.FOOD_SYLKIS.get(), ShopKeeper.FOOD_TANTAL.get());

    private int eatingCounter;
    private int mouthCounter;
    private int standCounter;
    public int wingCounter;
    public int sprintCounter;
    protected boolean isJumping;
    protected Inventory inventory;
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

    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
    };

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
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public EntityChocobo(EntityType<? extends EntityChocobo> entity, World world) {
        super(entity, world);
        this.setTame(false);
        this.maxUpStep = 1.0F;
        this.createInventory();
    }




    //----------------------------------------REGISTER----------------------------------------//

    protected void registerGoals() {

        this.eatBlockGoal = new GoalEatGrass(this);
        this.goalRunAround = new GoalRunAround(this, 1.1f);


        // From Wolf
        this.goalSelector.addGoal( 0, new SwimGoal(this));
        this.goalSelector.addGoal( 1, new SitGoal(this));
        this.goalSelector.addGoal( 1, new PanicGoal(this, 1.2D));
        //this.goalSelector.addGoal( 2, new AvoidEntityGoal(this, CreeperEntity.class, 24.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal( 3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal( 4, new MeleeAttackGoal(this, 1.0D, true));
        //this.goalSelector.addGoal( 5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal( 6, this.goalRunAround);
        this.goalSelector.addGoal( 7, new GoalBreed(this, 1.0D));
        this.goalSelector.addGoal( 8, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
        this.goalSelector.addGoal( 9, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, this.eatBlockGoal);
        this.goalSelector.addGoal(11, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        //this.goalSelector.addGoal(12, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(13, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(14, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        //this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
        //this.targetSelector.addGoal(6, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
        this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes()
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

    protected void setOffspringAttributes(AgeableEntity p_190681_1_, EntityChocobo p_190681_2_) {
        double d0 = this.getAttributeBaseValue(Attributes.MAX_HEALTH) + p_190681_1_.getAttributeBaseValue(Attributes.MAX_HEALTH) + (double)this.generateRandomMaxHealth();
        p_190681_2_.getAttribute(Attributes.MAX_HEALTH).setBaseValue(d0 / 3.0D);
        double d1 = this.getAttributeBaseValue(Attributes.JUMP_STRENGTH) + p_190681_1_.getAttributeBaseValue(Attributes.JUMP_STRENGTH) + this.generateRandomJumpStrength();
        p_190681_2_.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(d1 / 3.0D);
        double d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + p_190681_1_.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + this.generateRandomSpeed();
        p_190681_2_.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(d2 / 3.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_COLOR_FEATHER, DyeColor.GRAY.getColorValue());
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
            this.updatePersistentAnger((ServerWorld)this.level, true);
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
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }


        this.flapping = (float)((double)this.flapping * 0.9D);
        Vector3d vector3d = this.getDeltaMovement();
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
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnreason, @Nullable ILivingEntityData entity, @Nullable CompoundNBT compound) {
        if(spawnreason == SpawnReason.SPAWN_EGG){
            this.setColorFeather(DyeColor.GRAY.getColorValue());
            this.randomizeAttributes();
        }else if(spawnreason == SpawnReason.BREEDING){
            this.randomizeAttributes();
        } else {
            this.randomizeAttributes();
            this.setColorFeather(getColorRandomized(world.getRandom()).getColorValue());
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
                    int i = (int)(MathHelper.sin((this.shakeAnim - 0.4F) * (float)Math.PI) * 7.0F);
                    Vector3d vector3d = this.getDeltaMovement();

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

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {

            if(this.isFood(itemstack)){
                if(this.isTame()){
                    if (this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                        float scale = isFavouriteFood(item) * 0.5f;
                        this.heal((float) item.getFoodProperties().getNutrition());
                        return ActionResultType.SUCCESS;
                    }
                } else {
                    if (!player.abilities.instabuild) {
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

            } else

            if(item == Items.SADDLE){
                if(this.isTame()){
                    if(!isSaddled() && isSaddleable()){
                        if (!player.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                        this.equipSaddle(SoundCategory.AMBIENT);
                        return ActionResultType.SUCCESS;
                    }
                }
            } else

            if(item instanceof DyeItem){
                DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                if(player.isCreative()){
                    if(player.isCrouching()){
                        this.setColorFeather(dyecolor.getColorValue());
                    } else {
                        this.setColorFeather(getMixedColor(dyecolor.getColorValue(), getColorFeatherData()));
                    }
                    return ActionResultType.SUCCESS;
                } else {
                    if (dyecolor != this.getColorCollar()) {
                        this.setColorCollar(dyecolor);
                        if (!player.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                        return ActionResultType.SUCCESS;
                    }
                }
            } else

            if(player.isCrouching()){
                //if(item == ShopKeeper.STUFF_FEATHER.get()){

                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget((LivingEntity) null);



            } else {

                ActionResultType actionresulttype = super.mobInteract(player, hand);
                if (!this.isBaby() && !actionresulttype.consumesAction() && this.isOwnedBy(player)) {

                    this.doPlayerRide(player);
                    return ActionResultType.SUCCESS;
                }

                return actionresulttype;

            }



            if (this.isTame()) {




                if (!(item instanceof DyeItem)) {

                }



            } else if(isFood(itemstack) && !this.isAngry()){

            }


        }


        //if (!this.isBaby()) {
        //    //if (this.isTamed() && player.isSecondaryUseActive()) {
        //    //    this.openInventory(player);
        //    //    return ActionResultType.sidedSuccess(this.level.isClientSide);
        //    //}
//
        //    if (this.isVehicle()) {
        //        return super.mobInteract(player, hand);
        //    }
        //}

        //if (!itemstack.isEmpty()) {
        //    if (this.isFood(itemstack)) {
        //        return this.fedFood(player, itemstack);
        //    }
//
        //    ActionResultType actionresulttype = itemstack.interactLivingEntity(player, this, hand);
        //    if (actionresulttype.consumesAction()) {
        //        return actionresulttype;
        //    }
//
        //    if (!this.isTamed()) {
        //        this.makeMad();
        //        return ActionResultType.sidedSuccess(this.level.isClientSide);
        //    }
//
        //    boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.getItem() == Items.SADDLE;
        //    if (this.isArmor(itemstack) || flag) {
        //        this.openInventory(player);
        //        return ActionResultType.sidedSuccess(this.level.isClientSide);
        //    }
        //}

        // if (this.isBaby()) {
        //     return super.mobInteract(player, hand);
        // } else {
        //     this.doPlayerRide(player);
        //     return ActionResultType.sidedSuccess(this.level.isClientSide);
        // }

        return super.mobInteract(player, hand);
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("ColorFeather", this.getColorFeatherData());
        compound.putInt("ColorCollar", this.getColorCollar().getId());
        if (compound.contains("EggLayTime")) { this.eggTime = compound.getInt("EggLayTime"); }
        compound.putInt("Nature", this.getNature());
        if (!this.inventory.getItem(1).isEmpty()) { compound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundNBT())); }
        compound.putBoolean("EatingHaystack", this.isEating());
        compound.putBoolean("Bred", this.isBred());
        compound.putInt("Temper", this.getTemper());
        compound.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUUID() != null) { compound.putUUID("Owner", this.getOwnerUUID()); }
        if (!this.inventory.getItem(0).isEmpty()) { compound.put("SaddleItem", this.inventory.getItem(0).save(new CompoundNBT())); }

    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setColorFeather(compound.getInt("ColorFeather"));
        compound.putInt("EggLayTime", this.eggTime);
        if (compound.contains("ColorCollar", 99)) {
            this.setColorCollar(DyeColor.byId(compound.getInt("ColorCollar")));
        }
        if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
            this.readPersistentAngerSaveData((ServerWorld)this.level, compound);
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
            uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), s);
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




    //----------------------------------------ANIMATION_HELPER----------------------------------------//

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatPositionScale(float p_70894_1_) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? ((float)this.eatAnimationTick - p_70894_1_) / 4.0F : -((float)(this.eatAnimationTick - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatAngleScale(float p_70890_1_) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float)(this.eatAnimationTick - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? ((float)Math.PI / 5F) : this.xRot * ((float)Math.PI / 180F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isWet() {
        return this.isWet;
    }

    @OnlyIn(Dist.CLIENT)
    public float getWetShade(float p_70915_1_) {
        return Math.min(0.5F + MathHelper.lerp(p_70915_1_, this.shakeAnimO, this.shakeAnim) / 2.0F * 0.5F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public float getBodyRollAngle(float p_70923_1_, float p_70923_2_) {
        float f = (MathHelper.lerp(p_70923_1_, this.shakeAnimO, this.shakeAnim) + p_70923_2_) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float)Math.PI) * MathHelper.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRollAngle(float p_70917_1_) {
        return MathHelper.lerp(p_70917_1_, this.interestedAngleO, this.interestedAngle) * 0.15F * (float)Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public float getTailAngle() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else {
            return this.isTame() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F);
        }
    }

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
                if (this.gallopSoundCounter > 5 && this.gallopSoundCounter % 3 == 0) {
                    this.playGallopSound(soundtype);
                } else if (this.gallopSoundCounter <= 5) {
                    this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
                }
            } else if (soundtype == SoundType.WOOD) {
                this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
            } else {
                this.playSound(ShopKeeper.SOUND_CHOCOBO_STEP.get(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
            }

        }
    }

    protected void playGallopSound(SoundType sound) {
        //this.playSound(SoundEvents.HORSE_GALLOP, p_190680_1_.getVolume() * 0.15F, p_190680_1_.getPitch());
        ////---------------------
        //super.playGallopSound(p_190680_1_);
        //if (this.random.nextInt(10) == 0) {
        //    this.playSound(SoundEvents.HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        //}
        //ItemStack stack = this.inventory.getItem(1);
        //if (isArmor(stack)) stack.onHorseArmorTick(level, this);
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

    public boolean setSlot(int p_174820_1_, ItemStack p_174820_2_) {
        int i = p_174820_1_ - 400;
        if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
            if (i == 0 && p_174820_2_.getItem() != Items.SADDLE) {
                return false;
            } else if (i != 1 || this.canWearArmor() && this.isArmor(p_174820_2_)) {
                this.inventory.setItem(i, p_174820_2_);
                this.updateContainerEquipment();
                return true;
            } else {
                return false;
            }
        } else {
            int j = p_174820_1_ - 500 + 2;
            if (j >= 2 && j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, p_174820_2_);
                return true;
            } else {
                return false;
            }
        }
    }

    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlotType.CHEST);
    }

    private void setArmor(ItemStack p_213805_1_) {
        this.setItemSlot(EquipmentSlotType.CHEST, p_213805_1_);
        this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            //super.updateContainerEquipment();
            this.setArmorEquipment(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
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

    public void containerChanged(IInventory p_76316_1_) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
        ItemStack itemstack = this.getArmor();
        //super.containerChanged(p_76316_1_);
        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }

    }

    public boolean canWearArmor() {
        return true;
    }

    public boolean isArmor(ItemStack stack) {
        return stack.getItem() instanceof HorseArmorItem;
    }

    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTame();
    }

    public void equipSaddle(@Nullable SoundCategory p_230266_1_) {
        this.inventory.setItem(0, new ItemStack(Items.SADDLE));
        if (p_230266_1_ != null) {
            this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
        }

    }

    protected int getInventorySize() {
        return 2;
    }

    protected void createInventory() {
        Inventory inventory = this.inventory;
        this.inventory = new Inventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }









    public void openInventory(PlayerEntity p_110199_1_) {
        if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(p_110199_1_)) && this.isTamed()) {
            //p_110199_1_.openHorseInventory(this, this.inventory);
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

    private int getOffspringColor(int color1, int color2, /*AnimalEntity Choco1, AnimalEntity Choco2,*/ Random random) {

        // int color1 = ((EntityChocobo)Choco1).getColorFeatherData();
        // int color2 = ((EntityChocobo)Choco2).getColorFeatherData();

        return getMixedColor(color1, color2);

        //DyeColor dyecolor1 = ((EntityChocobo)Choco1).getColorFeatherData();
        //DyeColor dyecolor2 = ((EntityChocobo)Choco2).getColorFeather();

        //this.setColorFeather(getMixedColor(dyecolor1.getColorValue(), getColorFeatherData()));
        //if(dyecolor1 == dyecolor2) return dyecolor1;
        //int r = random.nextInt(10);
        //if(r < 4) return dyecolor1;
        //if(r < 8) return dyecolor2;
        //if(isColor(dyecolor1, dyecolor2, DyeColor.YELLOW) && isColor(dyecolor1, dyecolor2, DyeColor.RED))  return DyeColor.ORANGE;
        //if(isColor(dyecolor1, dyecolor2, DyeColor.YELLOW) && isColor(dyecolor1, dyecolor2, DyeColor.BLUE)) return DyeColor.GREEN;
        //if(isColor(dyecolor1, dyecolor2, DyeColor.BLUE) && isColor(dyecolor1, dyecolor2, DyeColor.RED))    return DyeColor.PURPLE;
        //return random.nextBoolean() ? dyecolor1 : dyecolor2;
        //return DyeColor.GRAY;
    }

    private boolean isColor(DyeColor value1, DyeColor value2, DyeColor test){
        return value1 == test || value2 == test;
    }


    //----------------------------------------OFFSPRING----------------------------------------//

    public EntityChocobo getBreedOffspring(ServerWorld world, AgeableEntity entity) {

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

    public EntityChocobo getBreedOffspring(ServerWorld world, int colorA, int colorB) {

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

    public boolean canMate(AnimalEntity entity) {
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

    protected boolean canParent() {
        return !this.isVehicle() && !this.isPassenger() && this.isTamed() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    public void setIsInterested(boolean p_70918_1_) {
        this.entityData.set(DATA_INTERESTED_ID, p_70918_1_);
    }

    public boolean isInterested() {
        return this.entityData.get(DATA_INTERESTED_ID);
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


        if (p_70103_1_ == 7) {
            //this.spawnTamingParticles(true);
        } else if (p_70103_1_ == 6) {
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
        //this.setSheared(false);
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
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
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


    //----------------------------------------GETTER/SETTER----------------------------------------//

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height * 0.95F;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
        //if (p_225503_1_ > 1.0F) {
        //    this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        //}
        //int i = this.calculateFallDamage(p_225503_1_, p_225503_2_);
        //if (i <= 0) {
        //    return false;
        //} else {
        //    this.hurt(DamageSource.FALL, (float)i);
        //    if (this.isVehicle()) {
        //        for(Entity entity : this.getIndirectPassengers()) {
        //            entity.hurt(DamageSource.FALL, (float)i);
        //        }
        //    }
        //    this.playBlockFallSound();
        //    return true;
        //}
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
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }



    public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
        if (!(p_142018_1_ instanceof CreeperEntity) && !(p_142018_1_ instanceof GhastEntity)) {
            if (p_142018_1_ instanceof WolfEntity) {
                WolfEntity wolfentity = (WolfEntity)p_142018_1_;
                return !wolfentity.isTame() || wolfentity.getOwner() != p_142018_2_;
            } else if (p_142018_1_ instanceof PlayerEntity && p_142018_2_ instanceof PlayerEntity && !((PlayerEntity)p_142018_2_).canHarmPlayer((PlayerEntity)p_142018_1_)) {
                return false;
            } else if (p_142018_1_ instanceof AbstractHorseEntity && ((AbstractHorseEntity)p_142018_1_).isTamed()) {
                return false;
            } else {
                return !(p_142018_1_ instanceof TameableEntity) || !((TameableEntity)p_142018_1_).isTame();
            }
        } else {
            return false;
        }
    }

    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return !this.isAngry() && super.canBeLeashed(p_184652_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }











    // Horse Entity







    private void setNature(int id) {
        this.entityData.set(DATA_ID_NATURE, id);
    }

    private int getNature() {
        return this.entityData.get(DATA_ID_NATURE);
    }

    public CoatColors getVariant() {
        return CoatColors.byId(this.getNature() & 255);
    }

    public CoatTypes getMarkings() {
        return CoatTypes.byId((this.getNature() & '\uff00') >> 8);
    }





    public static class HorseData extends AgeableEntity.AgeableData {
        public final CoatColors variant;

        public HorseData(CoatColors p_i231557_1_) {
            super(true);
            this.variant = p_i231557_1_;
        }
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
        int i = MathHelper.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
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
                this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
        }

    }



    protected int calculateFallDamage(float p_225508_1_, float p_225508_2_) {
        return MathHelper.ceil((p_225508_1_ * 0.5F - 3.0F) * p_225508_2_);
    }



    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    public int getMaxTemper() {
        return 100;
    }

    public ActionResultType fedFood(PlayerEntity p_241395_1_, ItemStack p_241395_2_) {
        boolean flag = this.handleEating(p_241395_1_, p_241395_2_);
        if (!p_241395_1_.abilities.instabuild) {
            p_241395_2_.shrink(1);
        }

        if (this.level.isClientSide) {
            return ActionResultType.CONSUME;
        } else {
            return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
    }

    protected boolean handleEating(PlayerEntity p_190678_1_, ItemStack p_190678_2_) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        Item item = p_190678_2_.getItem();
        if (item == Items.WHEAT) {
            f = 2.0F;
            i = 20;
            j = 3;
        } else if (item == Items.SUGAR) {
            f = 1.0F;
            i = 30;
            j = 3;
        } else if (item == Blocks.HAY_BLOCK.asItem()) {
            f = 20.0F;
            i = 180;
        } else if (item == Items.APPLE) {
            f = 3.0F;
            i = 60;
            j = 3;
        } else if (item == Items.GOLDEN_CARROT) {
            f = 4.0F;
            i = 60;
            j = 5;
            if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(p_190678_1_);
            }
        } else if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
            f = 10.0F;
            i = 240;
            j = 10;
            if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(p_190678_1_);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }

        if (this.isBaby() && i > 0) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.level.isClientSide) {
                this.ageUp(i);
            }

            flag = true;
        }

        if (j > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
            flag = true;
            if (!this.level.isClientSide) {
                this.modifyTemper(j);
            }
        }

        if (flag) {
            this.eating();
        }

        return flag;
    }

    protected void doPlayerRide(PlayerEntity p_110237_1_) {
        this.setEating(false);
        this.setStanding(false);
        if (!this.level.isClientSide) {
            p_110237_1_.yRot = this.yRot;
            p_110237_1_.xRot = this.xRot;
            p_110237_1_.startRiding(this);
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
            LivingEntity livingentity = this.level.getNearestEntity(AbstractHorseEntity.class, MOMMY_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
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

    public void makeMad() {
        if (!this.isStanding()) {
            this.stand();
            SoundEvent soundevent = this.getAngrySound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
            }
        }

    }

    public boolean tameWithName(PlayerEntity p_110263_1_) {
        this.setOwnerUUID(p_110263_1_.getUUID());
        this.setTamed(true);
        if (p_110263_1_ instanceof ServerPlayerEntity) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayerEntity)p_110263_1_, this);
        }

        this.level.broadcastEntityEvent(this, (byte)7);
        return true;
    }

    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.yRot = livingentity.yRot;
                this.yRotO = this.yRot;
                this.xRot = livingentity.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
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
                    double d1;
                    if (this.hasEffect(Effects.JUMP)) {
                        d1 = d0 + (double)((float)(this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
                    } else {
                        d1 = d0;
                    }

                    Vector3d vector3d = this.getDeltaMovement();
                    this.setDeltaMovement(vector3d.x, d1, vector3d.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F));
                        float f3 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((double)(-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double)(0.4F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vector3d((double)f, p_213352_1_.y, (double)f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setDeltaMovement(Vector3d.ZERO);
                }

                if (this.onGround) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.calculateEntityAnimation(this, false);
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }











    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @OnlyIn(Dist.CLIENT)
    public float getEatAnim(float p_110258_1_) {
        return MathHelper.lerp(p_110258_1_, this.eatAnimO, this.eatAnim);
    }

    @OnlyIn(Dist.CLIENT)
    public float getStandAnim(float p_110223_1_) {
        return MathHelper.lerp(p_110223_1_, this.standAnimO, this.standAnim);
    }

    @OnlyIn(Dist.CLIENT)
    public float getMouthAnim(float p_110201_1_) {
        return MathHelper.lerp(p_110201_1_, this.mouthAnimO, this.mouthAnim);
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
        IParticleData iparticledata = p_110216_1_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }



    public void positionRider(Entity p_184232_1_) {
        super.positionRider(p_184232_1_);
        //if (p_184232_1_ instanceof MobEntity) {
        //    MobEntity mobentity = (MobEntity)p_184232_1_;
        //    this.yBodyRot = mobentity.yBodyRot;
        //}
//
        //if (this.standAnimO > 0.0F) {
        //    float f3 = MathHelper.sin(this.yBodyRot * ((float)Math.PI / 180F));
        //    float f = MathHelper.cos(this.yBodyRot * ((float)Math.PI / 180F));
        //    float f1 = 0.7F * this.standAnimO;
        //    float f2 = 0.15F * this.standAnimO;
        //    p_184232_1_.setPos(this.getX() + (double)(f1 * f3), this.getY() + this.getPassengersRidingOffset() + -4d + (double)f2, this.getZ() - (double)(f1 * f));
        //    if (p_184232_1_ instanceof LivingEntity) {
        //        ((LivingEntity)p_184232_1_).yBodyRot = this.yBodyRot;
        //    }
        //}

    }





    public boolean onClimbable() {
        return false;
    }

    public boolean isWearingArmor() {
        return !this.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
    }





    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Nullable
    private Vector3d getDismountLocationInDirection(Vector3d p_234236_1_, LivingEntity p_234236_2_) {
        double d0 = this.getX() + p_234236_1_.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + p_234236_1_.z;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Pose pose : p_234236_2_.getDismountPoses()) {
            blockpos$mutable.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(blockpos$mutable);
                if ((double)blockpos$mutable.getY() + d4 > d3) {
                    break;
                }

                if (TransportationHelper.isBlockFloorValid(d4)) {
                    AxisAlignedBB axisalignedbb = p_234236_2_.getLocalBoundsForPose(pose);
                    Vector3d vector3d = new Vector3d(d0, (double)blockpos$mutable.getY() + d4, d2);
                    if (TransportationHelper.canDismountTo(this.level, p_234236_2_, axisalignedbb.move(vector3d))) {
                        p_234236_2_.setPose(pose);
                        return vector3d;
                    }
                }

                blockpos$mutable.move(Direction.UP);
                if (!((double)blockpos$mutable.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    public Vector3d getDismountLocationForPassenger(LivingEntity p_230268_1_) {
        Vector3d vector3d = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_230268_1_.getBbWidth(), this.yRot + (p_230268_1_.getMainArm() == HandSide.RIGHT ? 90.0F : -90.0F));
        Vector3d vector3d1 = this.getDismountLocationInDirection(vector3d, p_230268_1_);
        if (vector3d1 != null) {
            return vector3d1;
        } else {
            Vector3d vector3d2 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_230268_1_.getBbWidth(), this.yRot + (p_230268_1_.getMainArm() == HandSide.LEFT ? 90.0F : -90.0F));
            Vector3d vector3d3 = this.getDismountLocationInDirection(vector3d2, p_230268_1_);
            return vector3d3 != null ? vector3d3 : this.position();
        }
    }



    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }



    //-------------------------------------------NEW_STUFF------------------------------------------------------

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

    //public double getMyRidingOffset() {
    //    return 0.0D;
    //}

    public double getPassengersRidingOffset() {
        return 1.25D;
    }



    //   ----------------------------------- Getter for Model Animation ---------------------------------------------

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

    public boolean AnimTame(){
        return isTame();
    }

    public boolean AnimFly(){
        return isFlying;
    }

    public boolean AnimWalk(){
        return false;
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
