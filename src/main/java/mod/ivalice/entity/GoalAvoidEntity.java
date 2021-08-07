package mod.ivalice.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.horse.Llama;

class GoalAvoidEntity<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final EntityChocobo wolf;

    public GoalAvoidEntity(EntityChocobo p_i47251_2_, Class<T> p_i47251_3_, float p_i47251_4_, double p_i47251_5_, double p_i47251_7_) {
        super(p_i47251_2_, p_i47251_3_, p_i47251_4_, p_i47251_5_, p_i47251_7_);
        this.wolf = p_i47251_2_;
    }

    public boolean canUse() {
        if (super.canUse() && this.toAvoid instanceof Llama) {
            return !this.wolf.isTame() && this.avoidLlama((Llama)this.toAvoid);
        } else {
            return false;
        }
    }

    private boolean avoidLlama(Llama p_190854_1_) {
        return p_190854_1_.getStrength() >= wolf.getRandom().nextInt(5);
    }

    public void start() {
        wolf.setTarget((LivingEntity)null);
        super.start();
    }

    public void tick() {
        wolf.setTarget((LivingEntity)null);
        super.tick();
    }
}