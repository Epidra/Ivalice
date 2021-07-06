package mod.ivalice.entity;

import mod.ivalice.blocks.BlockNest;
import mod.ivalice.tileentities.TileEntityNest;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GoalBreed extends Goal {
    private static final EntityPredicate PARTNER_TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final AnimalEntity animal;
    private final Class<? extends AnimalEntity> partnerClass;
    protected final World level;
    protected AnimalEntity partner;
    protected BlockPos nest;
    private int loveTime;
    private final double speedModifier;

    public GoalBreed(AnimalEntity p_i1619_1_, double p_i1619_2_) {
        this(p_i1619_1_, p_i1619_2_, p_i1619_1_.getClass());
    }

    public GoalBreed(AnimalEntity p_i47306_1_, double p_i47306_2_, Class<? extends AnimalEntity> p_i47306_4_) {
        this.animal = p_i47306_1_;
        this.level = p_i47306_1_.level;
        this.partnerClass = p_i47306_4_;
        this.speedModifier = p_i47306_2_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

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
        return this.partner.isAlive() && this.partner.isInLove() && level.getBlockState(nest).getBlock() instanceof BlockNest && this.loveTime < 60;
    }

    public void stop() {
        this.partner = null;
        this.nest = null;
        this.loveTime = 0;
    }

    public void tick() {
        this.animal.getLookControl().setLookAt(nest.getX(), nest.getY(), nest.getZ(), 10.0F, (float)this.animal.getMaxHeadXRot());
        //this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
        this.animal.getNavigation().moveTo(nest.getX(), nest.getY(), nest.getZ(), 1);
        ++this.loveTime;
        if (this.loveTime >= 60 && this.animal.distanceToSqr(this.partner) < 9.0D) {
            this.breed();
        }

    }

    @Nullable
    private AnimalEntity getFreePartner() {
        List<AnimalEntity> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        AnimalEntity animalentity = null;

        for(AnimalEntity animalentity1 : list) {
            if (this.animal.canMate(animalentity1) && this.animal.distanceToSqr(animalentity1) < d0) {
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
        TileEntityNest tile = (TileEntityNest) level.getBlockEntity(nest);
        if(tile != null){
            tile.createEgg((EntityChocobo) this.animal, (EntityChocobo) this.partner);
            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
        }
        //this.animal.spawnChildFromBreeding((ServerWorld)this.level, this.partner);
    }
}