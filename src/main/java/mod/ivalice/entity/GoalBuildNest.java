package mod.ivalice.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GoalBuildNest extends Goal {

    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    protected final Animal animal;
    private final Class<? extends Animal> partnerClass;
    protected final Level level;
    protected Animal partner;
    private int loveTime;
    private final double speedModifier;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GoalBuildNest(Animal p_i1619_1_, double p_i1619_2_) {
        this(p_i1619_1_, p_i1619_2_, p_i1619_1_.getClass());
    }

    public GoalBuildNest(Animal p_i47306_1_, double p_i47306_2_, Class<? extends Animal> p_i47306_4_) {
        this.animal = p_i47306_1_;
        this.level = p_i47306_1_.level;
        this.partnerClass = p_i47306_4_;
        this.speedModifier = p_i47306_2_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }





    //----------------------------------------USAGE----------------------------------------//

    public boolean canUse() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null;
        }
    }

    public boolean canContinueToUse() {
        return this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60;
    }





    //----------------------------------------START/STOP----------------------------------------//

    public void stop() {
        this.partner = null;
        this.loveTime = 0;
    }





    //----------------------------------------TICK----------------------------------------//

    public void tick() {
        this.animal.getLookControl().setLookAt(this.partner, 10.0F, (float)this.animal.getMaxHeadXRot());
        this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= 60 && this.animal.distanceToSqr(this.partner) < 9.0D) {
            this.breed();
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Nullable
    private Animal getFreePartner() {
        List<? extends Animal> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        Animal animalentity = null;
        for(Animal animalentity1 : list) {
            if (this.animal.canMate(animalentity1) && this.animal.distanceToSqr(animalentity1) < d0) {
                animalentity = animalentity1;
                d0 = this.animal.distanceToSqr(animalentity1);
            }
        }
        return animalentity;
    }

    protected void breed() {
        this.animal.spawnChildFromBreeding((ServerLevel) this.level, this.partner);
    }



}