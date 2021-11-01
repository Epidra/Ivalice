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





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GoalRunAround(EntityChocobo entity, double speed) {
        this.horse = entity;
        this.level = entity.level;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }





    //----------------------------------------USAGE----------------------------------------//

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





    //----------------------------------------START/STOP----------------------------------------//

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





    //----------------------------------------TICK----------------------------------------//

    public void tick() {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
