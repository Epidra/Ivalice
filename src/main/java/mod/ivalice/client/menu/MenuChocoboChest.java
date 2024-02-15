package mod.ivalice.client.menu;

import mod.ivalice.Register;
import mod.ivalice.common.entity.EntityChocobo;
import mod.lucky77.block.entity.BlockEntityBase;
import mod.lucky77.menu.MenuBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuChocoboChest extends MenuBase {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public MenuChocoboChest(int windowID, Inventory playerInventory, EntityChocobo entity) {
		super(Register.MENU_CHOCOBO_CHEST.get(), windowID, playerInventory, entity.getInventory());
	}
	
	/** Forge Registry Constructor **/
	public MenuChocoboChest(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_CHOCOBO_CHEST.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	protected void createInventory(Container tile, Inventory player) {
		this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*0)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*0)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*0)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*0)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*1)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*1)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*1)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*1)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*2)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*2)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*2)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*2)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*0, 21 + 18*3)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*1, 21 + 18*3)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*2, 21 + 18*3)); // INPUT
		this.addSlot(new Slot(tile, 0,  98 + 18*3, 21 + 18*3)); // INPUT
		addPlayerSlots(player, 8, 56 + 8 + 16 + 1);
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
	
	
	
}
