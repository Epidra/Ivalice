package mod.ivalice.blocks;

import mod.ivalice.ShopKeeper;
import mod.ivalice.tileentities.TileEntityNest;
import mod.lucky77.blocks.BlockBase;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockNest extends BlockBase {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape AABB0 = Block.box(0, 0, 0, 16,  4, 16);
    private static final VoxelShape AABB1 = Block.box(0, 0, 0, 16, 12, 16);









    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockNest() {
        super(Properties.copy(Blocks.WHEAT));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }




    //----------------------------------------HELPER----------------------------------------//

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isClientSide() && player instanceof ServerPlayerEntity) {
            interact(world, pos, player, (TileBase) world.getBlockEntity(pos));
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void interact(World world, BlockPos blockPos, PlayerEntity playerEntity, TileBase tileBase) {
        if(world.getBlockState(blockPos).getValue(AGE) == 3){
            world.setBlockAndUpdate(blockPos, world.getBlockState(blockPos).setValue(AGE, 0));
        }
        // Take Egg
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int age = state.getValue(AGE);
        switch(age) {
            case 0: return AABB0;
            case 1:
            case 2:
            case 3: return AABB1;
            default:
                return VoxelShapes.block();
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityNest(ShopKeeper.TILE_NEST.get());
    }
}
