package mod.ivalice.client.menu;

import mod.ivalice.Register;
import mod.ivalice.common.entity.EntityChocobo;
import mod.lucky77.block.entity.BlockEntityBase;
import mod.lucky77.menu.MenuBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class MenuChocoboBasic extends MenuBase {
	
	public EntityChocobo chocobo;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public MenuChocoboBasic(int windowID, Inventory playerInventory, EntityChocobo entity) {
		super(Register.MENU_CHOCOBO_BASIC.get(), windowID, playerInventory, entity.getInventory());
		chocobo = entity;
	}
	
	/** Forge Registry Constructor **/
	public MenuChocoboBasic(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_CHOCOBO_BASIC.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	protected void createInventory(Container tile, Inventory player) {
		// this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*0)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*0)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*0)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*0)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*1)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*1)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*1)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*1)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*2)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*2)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*2)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*2)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*3)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*3)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*3)); // INPUT
		// this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*3)); // INPUT
		
		// this.addSlot(new Slot(tile, 1,  75, 57)); // OUTPUT 1
		// this.addSlot(new Slot(tile, 2,  91, 57)); // OUTPUT 2
		// this.addSlot(new Slot(tile, 3, 107, 57)); // OUTPUT 3
		// this.addSlot(new Slot(tile, 4, 123, 57)); // OUTPUT 4
		// this.addSlot(new Slot(tile, 5, 139, 57)); // OUTPUT 5
		addPlayerSlots(player, 8, 56 + 8 + 16 + 1);
	}
	
	@Override
	public SimpleContainer generateSimpleContainer(Inventory playerInventory, FriendlyByteBuf packetBuffer){
		UUID uuid = packetBuffer.readUUID();
		List<EntityChocobo> chocobos = playerInventory.player.level().getEntitiesOfClass(EntityChocobo.class, playerInventory.player.getBoundingBox().inflate(16.0D),
				(test) -> test.getUUID().equals(uuid));
		chocobo = chocobos.isEmpty() ? null : chocobos.get(0);
		return chocobo.getInventory();
	}
	
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 2) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
					return ItemStack.EMPTY;
				}
				if (index >= 3 && index < 30) {
					if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}
	
	@Override
	public boolean stillValid(Player player) {
		return this.chocobo.isAlive() && this.chocobo.distanceTo(player) < 8.0F;
	}
	
	
}
