package mod.ivalice.blocks;

import mod.lucky77.blocks.BlockBase;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockStraw extends BlockBase {

    private static final VoxelShape AABB0 = Block.box(0, 0, 0, 16,  2, 16);




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockStraw() {
        super(Properties.copy(Blocks.WHEAT));
    }

    @Override
    public void interact(World world, BlockPos blockPos, PlayerEntity playerEntity, TileBase tileBase) {

    }


    //----------------------------------------HELPER----------------------------------------//

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return AABB0;
    }

}
