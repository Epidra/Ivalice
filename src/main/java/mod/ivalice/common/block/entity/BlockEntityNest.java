package mod.ivalice.common.block.entity;

import mod.ivalice.Register;
import mod.ivalice.common.block.BlockNest;
import mod.ivalice.common.entity.EntityChocobo;
import mod.lucky77.block.entity.BlockEntityBase;
import mod.lucky77.util.content.Dummy;
import mod.lucky77.util.system.SystemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityNest extends BlockEntityBase<Dummy> {
	
	public int colorA = 0;
	public int colorB = 0;
	boolean isHatching = false;
	public int age = 0;
	public int ageMAX = 400;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public BlockEntityNest(BlockPos blockpos, BlockState blockstate) {
		this(Register.TILE_NEST.get(), blockpos, blockstate);
	}
	
	/** 0 - KEY, 1 - MODULE, 2 - TOKEN, 3 - STORAGE-TOKEN, 4 - STORAGE_PRIZE **/
	public BlockEntityNest(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate) {
		super(tileEntityTypeIn, blockpos, blockstate, 1, new Dummy());
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SERVER TICK  ---------- ---------- ---------- ---------- //
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntityNest BE){
		boolean isDirty = false;
		// if(BE.isHatching && BE.age < BE.ageMAX){
		if(level.getBlockState(pos).getValue(BlockNest.AGE) == 1 && BE.age < BE.ageMAX){
			BE.age++;
			if(BE.age >= BE.ageMAX){
				BE.hatch();
				isDirty = true;
			}
		}
		if (isDirty){
			BE.setChanged();
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load(CompoundTag nbt){
		super.load(nbt);
		colorA = nbt.getInt("ColorA");
		colorB = nbt.getInt("ColorB");
		age = nbt.getInt("Age");
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, this.inventory);
	}
	
	public void saveAdditional(CompoundTag compound){
		super.saveAdditional(compound);
		compound.putInt("ColorA", colorA);
		compound.putInt("ColorB", colorB);
		compound.putInt("Age", age);
		ContainerHelper.saveAllItems(compound, this.inventory);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  NETWORK  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public void createEgg(EntityChocobo parentA, EntityChocobo parentB){
		byte[] a1 = parentA.getMatingColor();
		byte[] b1 = parentB.getMatingColor();
		colorA = SystemColor.convert(parentA.getMatingColor());
		colorB = SystemColor.convert(parentB.getMatingColor());
		byte[] a2 = SystemColor.convert(colorA);
		byte[] b2 = SystemColor.convert(colorB);
		isHatching = true;
		age = 0;
		level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockNest.AGE, 1));
	}
	
	public void hatch(){
		if(!level.isClientSide){
			EntityChocobo parent = (EntityChocobo) Register.ENTITY_CHOCOBO.get().create(level);
			// EntityChocobo child;
			ServerLevel SW = (ServerLevel) this.getLevel();
			
			EntityChocobo child = Register.ENTITY_CHOCOBO.get().create(level);
			child.setFeatherColor(child.mixColors(SystemColor.convert(colorA), SystemColor.convert(colorB)));
			
			// if (parent != null) {
			// 	child = (EntityChocobo) parent.getBreedOffspring(SW, colorA, colorB);
			// } else {
			// 	child = (EntityChocobo) Register.ENTITY_CHOCOBO.get().create(level);
			// }
			if (child != null) {
				child.setBaby(true);
				child.moveTo(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 0.0F, 0.0F);
				SW.addFreshEntityWithPassengers(child);
				isHatching = false;
				age = 0;
				level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockNest.AGE, 3));
			}
		}
	}
	
	// @Override
	// public ContainerData getIntArray() {
	// 	return new ContainerData() {
	// 		@Override
	// 		public int get(int p_39284_) {
	// 			return 0;
	// 		}
	//
	// 		@Override
	// 		public void set(int p_39285_, int p_39286_) {
	//
	// 		}
	//
	// 		@Override
	// 		public int getCount() {
	// 			return 0;
	// 		}
	// 	};
	// }
	
	// @Override
	// public TextComponent getName() {
	//     return new TextComponent("nest");
	// }
	
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}
	
	public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
		this.getItems().set(p_70299_1_, p_70299_2_);
		if (p_70299_2_.getCount() > this.getMaxStackSize()) {
			p_70299_2_.setCount(this.getMaxStackSize());
		}
		this.setChanged();
	}
	
	
	
}
