package mod.ivalice.entity;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class GoalRunAround extends Goal {
    private final EntityChocobo horse;
    private final double speedModifier;
    private double posX;
    private double posY;
    private double posZ;
    private boolean isRunning = false;
    private final World level;
    private int cooldown = 0;

    public GoalRunAround(EntityChocobo p_i1653_1_, double p_i1653_2_) {
        this.horse = p_i1653_1_;
        this.level = p_i1653_1_.level;
        this.speedModifier = p_i1653_2_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.horse.isTamed() && !this.horse.AnimYoung() && --cooldown <= 0) {
            Vector3d vector3d = RandomPositionGenerator.getPos(this.horse, 100, 100);
            if (vector3d == null) {
                return false;
            } else {
                this.posX = vector3d.x;
                this.posY = vector3d.y;
                this.posZ = vector3d.z;
                return true;
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.horse.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
        this.level.broadcastEntityEvent(this.horse, (byte)11);
        this.isRunning = true;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void stop(){
        isRunning = false;
        cooldown = 400;
        this.level.broadcastEntityEvent(this.horse, (byte)12);
    }

    public boolean canContinueToUse() {
        return !this.horse.isTamed() && !this.horse.getNavigation().isDone();
    }

    public void tick() {
        if (!this.horse.isTamed() && this.horse.getRandom().nextInt(50) == 0) {
            //Entity entity = this.horse.getPassengers().get(0);
            //if (entity == null) {
            //    return;
            //}
//
            //if (entity instanceof PlayerEntity) {
            //    int i = this.horse.getTemper();
            //    int j = this.horse.getMaxTemper();
            //    if (j > 0 && this.horse.getRandom().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(horse, (PlayerEntity)entity)) {
            //        this.horse.tameWithName((PlayerEntity)entity);
            //        return;
            //    }
//
            //    this.horse.modifyTemper(5);
            //}
//
            //this.horse.ejectPassengers();
            //this.horse.makeMad();
            //this.horse.level.broadcastEntityEvent(this.horse, (byte)6);
        }

    }
}
