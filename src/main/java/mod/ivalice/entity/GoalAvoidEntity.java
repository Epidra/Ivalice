package mod.ivalice.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;

class GoalAvoidEntity<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
    private final EntityChocobo wolf;

    public GoalAvoidEntity(EntityChocobo p_i47251_2_, Class<T> p_i47251_3_, float p_i47251_4_, double p_i47251_5_, double p_i47251_7_) {
        super(p_i47251_2_, p_i47251_3_, p_i47251_4_, p_i47251_5_, p_i47251_7_);
        this.wolf = p_i47251_2_;
    }

    public boolean canUse() {
        if (super.canUse() && this.toAvoid instanceof LlamaEntity) {
            return !this.wolf.isTame() && this.avoidLlama((LlamaEntity)this.toAvoid);
        } else {
            return false;
        }
    }

    private boolean avoidLlama(LlamaEntity p_190854_1_) {
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