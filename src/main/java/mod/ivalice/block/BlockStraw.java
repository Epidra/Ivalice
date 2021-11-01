package mod.ivalice.block;

import mod.lucky77.block.BlockBase;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockStraw extends BlockBase {

    private static final VoxelShape AABB0 = Block.box(0, 0, 0, 16,  2, 16);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockStraw() {
        super(Properties.copy(Blocks.WHEAT));
    }





    //----------------------------------------INTERACTION----------------------------------------//

    @Override
    public void interact(Level world, BlockPos pos, Player player, BlockEntityBase tile) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return AABB0;
    }



}
