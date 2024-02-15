package mod.ivalice.common.block;

import mod.ivalice.Register;
import mod.ivalice.common.block.entity.BlockEntityNest;
import mod.lucky77.block.base.BlockBase;
import mod.lucky77.block.entity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockNest extends BlockBase implements EntityBlock {
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	
	private static final VoxelShape AABB0 = Block.box(0, 0, 0, 16,  4, 16);
	private static final VoxelShape AABB1 = Block.box(0, 0, 0, 16, 12, 16);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Contructor with predefined BlockProperty */
	public BlockNest() {
		super(Properties.copy(Blocks.WHEAT));
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  PLACEMENT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INTERACTION  ---------- ---------- ---------- ---------- //
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!world.isClientSide() && player instanceof ServerPlayer) {
			interact(world, pos, player, (BlockEntityBase) world.getBlockEntity(pos));
		}
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public void interact(Level world, BlockPos pos, Player player, BlockEntityBase tile) {
		if(world.getBlockState(pos).getValue(AGE) == 3){
			world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(AGE, 0));
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		int age = state.getValue(AGE);
		return switch(age) {
			case 0       -> AABB0;
			case 1, 2, 3 -> AABB1;
			default      -> Shapes.block();
		};
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCK ENTITY  ---------- ---------- ---------- ---------- //
	
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlockEntityNest(pos, state);
	}
	
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTicker(level, type, (BlockEntityType<? extends BlockEntityNest>) Register.TILE_NEST.get());
	}
	
	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends BlockEntityNest> typeCustom) {
		return createTickerHelper(type, typeCustom, BlockEntityNest::serverTick);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCKSTATE  ---------- ---------- ---------- ---------- //
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	
	
	
}
