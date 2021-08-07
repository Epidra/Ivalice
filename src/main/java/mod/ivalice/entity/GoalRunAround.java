package mod.ivalice.entity;

import com.mojang.math.Vector3d;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GoalRunAround extends Goal {
    private final EntityChocobo horse;
    private final double speedModifier;
    private double posX;
    private double posY;
    private double posZ;
    private boolean isRunning = false;
    private final Level level;
    private int cooldown = 0;

    public GoalRunAround(EntityChocobo p_i1653_1_, double p_i1653_2_) {
        this.horse = p_i1653_1_;
        this.level = p_i1653_1_.level;
        this.speedModifier = p_i1653_2_;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.horse.isTamed() && !this.horse.AnimYoung() && --cooldown <= 0) {
            Vec3 vector3d = DefaultRandomPos.getPos(this.horse, 100, 100);
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
