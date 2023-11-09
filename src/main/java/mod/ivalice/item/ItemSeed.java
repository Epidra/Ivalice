package mod.ivalice.item;

import mod.ivalice.ShopKeeper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;

public class ItemSeed extends Item {

    private final String id;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor */
    public ItemSeed(String id) {
        super(new Properties());
        this.id = id;
    }





    //----------------------------------------INTERACTION----------------------------------------//

    /** Called when this item is used when targetting a Block */
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionresulttype = this.place(new BlockPlaceContext(context));
        return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public InteractionResult place(BlockPlaceContext p_40577_) {
        if (!p_40577_.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(p_40577_);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, p_40577_.getPlayer());
                    level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, p_40577_.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    @Deprecated //Forge: Use more sensitive version {@link BlockItem#getPlaceSound(BlockState, IBlockReader, BlockPos, Entity) }
    protected SoundEvent getPlaceSound(BlockState p_40588_) {
        return p_40588_.getSoundType().getPlaceSound();
    }

    //Forge: Sensitive version of BlockItem#getPlaceSound
    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext p_40609_) {
        return p_40609_;
    }

    protected boolean updateCustomBlockEntityTag(BlockPos p_40597_, Level p_40598_, @Nullable Player p_40599_, ItemStack p_40600_, BlockState p_40601_) {
        return updateCustomBlockEntityTag(p_40598_, p_40599_, p_40597_, p_40600_);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext p_40613_) {
        BlockState blockstate = this.getBlock().getStateForPlacement(p_40613_);
        return blockstate != null && this.canPlace(p_40613_, blockstate) ? blockstate : null;
    }

    protected boolean canPlace(BlockPlaceContext p_40611_, BlockState p_40612_) {
        Player player = p_40611_.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || p_40612_.canSurvive(p_40611_.getLevel(), p_40611_.getClickedPos())) && p_40611_.getLevel().isUnobstructed(p_40612_, p_40611_.getClickedPos(), collisioncontext);
    }

    protected boolean mustSurvive() {
        return true;
    }

    private BlockState updateBlockStateFromTag(BlockPos p_40603_, Level p_40604_, ItemStack p_40605_, BlockState p_40606_) {
        BlockState blockstate = p_40606_;
        CompoundTag compoundtag = p_40605_.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = p_40606_.getBlock().getStateDefinition();

            for(String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }
        if (blockstate != p_40606_) {
            p_40604_.setBlock(p_40603_, blockstate, 2);
        }
        return blockstate;
    }

    protected boolean placeBlock(BlockPlaceContext p_40578_, BlockState p_40579_) {
        return p_40578_.getLevel().setBlock(p_40578_.getClickedPos(), p_40579_, 11);
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState p_40594_, Property<T> p_40595_, String p_40596_) {
        return p_40595_.getValue(p_40596_).map((p_40592_) -> {
            return p_40594_.setValue(p_40595_, p_40592_);
        }).orElse(p_40594_);
    }

    public static boolean updateCustomBlockEntityTag(Level p_40583_, @Nullable Player p_40584_, BlockPos p_40585_, ItemStack p_40586_) {
        MinecraftServer minecraftserver = p_40583_.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CompoundTag compoundtag = p_40586_.getTagElement("BlockEntityTag");
            if (compoundtag != null) {
                BlockEntity blockentity = p_40583_.getBlockEntity(p_40585_);
                if (blockentity != null) {
                    if (!p_40583_.isClientSide && blockentity.onlyOpCanSetNbt() && (p_40584_ == null || !p_40584_.canUseGameMasterBlocks())) {
                        return false;
                    }
                    CompoundTag compoundtag1 = blockentity.saveWithoutMetadata();
                    CompoundTag compoundtag2 = compoundtag1.copy();
                    compoundtag1.merge(compoundtag);
                    compoundtag1.putInt("x", p_40585_.getX());
                    compoundtag1.putInt("y", p_40585_.getY());
                    compoundtag1.putInt("z", p_40585_.getZ());
                    if (!compoundtag1.equals(compoundtag2)) {
                        blockentity.load(compoundtag1);
                        blockentity.setChanged();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public Block getBlock() {
        return this.getBlockRaw() == null ? null : this.getBlockRaw();
    }

    private Block getBlockRaw() {
        if(id.matches("gysahl")) return ShopKeeper.CROP_GYSAHL.get();
        if(id.matches("krakka")) return ShopKeeper.CROP_KRAKKA.get();
        if(id.matches("mimett")) return ShopKeeper.CROP_MIMETT.get();
        if(id.matches("sylkis")) return ShopKeeper.CROP_SYLKIS.get();
        if(id.matches("tantal")) return ShopKeeper.CROP_TANTAL.get();
        return ShopKeeper.CROP_GYSAHL.get();
    }



}
