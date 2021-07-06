package mod.ivalice.tileentities;

import mod.ivalice.ShopKeeper;
import mod.ivalice.blocks.BlockNest;
import mod.ivalice.entity.EntityChocobo;
import mod.lucky77.tileentities.TileBase;
import mod.lucky77.util.LogicBase;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class TileEntityNest extends TileBase<LogicBase> {

    public int colorA = 0;
    public int colorB = 0;

    boolean isHatching = false;

    public int age = 0;
    public int ageMAX = 400;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public TileEntityNest() {
        this(ShopKeeper.TILE_NEST.get());
    }

    /** 0 - KEY, 1 - MODULE, 2 - TOKEN, 3 - STORAGE-TOKEN, 4 - STORAGE_PRIZE **/
    public TileEntityNest(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, 1, new LogicBase());
    }




    //----------------------------------------UPDATE----------------------------------------//

    @Override
    public void tick(){
        boolean isDirty = false;

        if(isHatching && age < ageMAX){
            age++;
            if(age >= ageMAX){
                hatch();
                isDirty = true;
            }
        }

        if (isDirty){
            this.setChanged();
        }
    }




    //----------------------------------------NETWORK----------------------------------------//

    // ...




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load(BlockState state, CompoundNBT nbt){
        super.load(state, nbt);

        colorA = nbt.getInt("ColorA");
        colorB = nbt.getInt("ColorB");
        age = nbt.getInt("Age");

        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
    }

    public CompoundNBT save(CompoundNBT compound){
        super.save(compound);

        compound.putInt("ColorA", colorA);
        compound.putInt("ColorB", colorB);
        compound.putInt("Age", age);

        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public void createEgg(EntityChocobo parentA, EntityChocobo parentB){
        colorA = parentA.getColorFeatherData();
        colorB = parentB.getColorFeatherData();
        isHatching = true;
        age = 0;
        level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockNest.AGE, 1));
    }

    public void hatch(){
        if(!level.isClientSide){
            EntityChocobo parent = ShopKeeper.ENTITY_CHOCOBO.get().create(level);
            EntityChocobo child;
            ServerWorld SW = (ServerWorld) this.getLevel();
            if (parent != null) {
                child = parent.getBreedOffspring(SW, colorA, colorB);
            } else {
                child = ShopKeeper.ENTITY_CHOCOBO.get().create(level);
            }

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




    //----------------------------------------GET/SET----------------------------------------//

    @Override
    public IIntArray getIntArray() {
        return new IntArray(0);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("nest");
    }

    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        //this.unpackLootTable((PlayerEntity)null);
        this.getItems().set(p_70299_1_, p_70299_2_);
        if (p_70299_2_.getCount() > this.getMaxStackSize()) {
            p_70299_2_.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

}
