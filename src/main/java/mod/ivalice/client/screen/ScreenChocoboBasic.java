package mod.ivalice.client.screen;

import mod.ivalice.client.menu.MenuChocoboBasic;
import mod.lucky77.screen.ScreenBase;
import mod.lucky77.screen.ScreenBook;
import mod.lucky77.util.Vector2;
import mod.lucky77.util.button.ButtonSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenChocoboBasic extends ScreenBase<MenuChocoboBasic> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("ivalice" + ":" + "textures/gui/inventory.png");
	
	protected ButtonSet buttonSet = new ButtonSet();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ScreenChocoboBasic(MenuChocoboBasic container, Inventory player, Component name) {
		super(container, player, name, 176, 204);
		createButtons();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	private void createButtons(){
		// buttonSet.addButton(0, new Vector2( 32-2, 154+2), new Vector2(215, 246), new Vector2(215, 246), new Vector2(238, 246), new Vector2(18, 10), -1, () -> this.currentPage     >                  0, () -> this.commandPageBack());
		// buttonSet.addButton(1, new Vector2(206+2, 154+2), new Vector2(215, 233), new Vector2(215, 233), new Vector2(238, 233), new Vector2(18, 10), -1, () -> this.currentPage + 2 < item.getMaxPages(), () -> this.commandPageForward());
	}
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	/** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int x = (this.width  - 176) / 2;
		int y = (this.height - 178) / 2;
		buttonSet.interact(x, y, mouseX, mouseY);
		return false;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	/** Draw the foreground layer for the GuiContainer (everything in front of the items) */
	protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY){
		int x = 0; // (this.width  - 176) / 2;
		int y = 0; // (this.height - 178) / 2;
		
		// --- Health Meter --- //
		String text = "" + (int)menu.chocobo.getDataHealthNow() + " / " + (int)menu.chocobo.getDataHealthMax();
		matrix.drawString(font, text, 50 -  (this.font.width(text) / 2), 7+16-4+2-1, 16777215);
		
		// --- Nature --- //
		text = "" + menu.chocobo.getDataNature();
		matrix.drawString(font, text, 59 -  (this.font.width(text) / 2), 71+16-4+1, 16777215);
	}
	
	/** Draws the background layer of this container (behind the items). */
	protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		int x = (this.width  - 176) / 2;
		int y = (this.height - 178) / 2;
		buttonSet.update(x, y, mouseX, mouseY);
		
		// --- Background & Chest Field --- //
		matrix.blit(TEXTURE, x     , y    , 0,   0, 176, 178);
		matrix.blit(TEXTURE, x + 97, y + 7, 3, 181,  72,  72);
		
		// --- Health Meter --- //
		int scale = (int) (menu.chocobo.getDataHealthNow() / menu.chocobo.getDataHealthMax() * 92);
		matrix.blit(TEXTURE, x + 4, y + 5, 163, 230,    92, 12);
		matrix.blit(TEXTURE, x + 4, y + 5, 163, 243, scale, 12);
		
		// --- Stat Values --- //
		matrix.blit(TEXTURE, x + 26, y + 18, 189,  1, 3 + menu.chocobo.getDataAttack( )*2, 9);
		matrix.blit(TEXTURE, x + 26, y + 28, 189, 11, 3 + menu.chocobo.getDataDefense()*2, 9);
		matrix.blit(TEXTURE, x + 26, y + 38, 189, 21, 3 + menu.chocobo.getDataSpeed(  )*2, 9);
		matrix.blit(TEXTURE, x + 26, y + 48, 189, 31, 3 + menu.chocobo.getDataGlide(  )*2, 9);
		matrix.blit(TEXTURE, x + 26, y + 58, 189, 51, 3 + menu.chocobo.getDataJump(   )*2, 9);
		
		// --- Gender Icon --- //
		matrix.blit(TEXTURE, x + 8, y + 68, menu.chocobo.getDataGender() ? 242 : 228, 61, 13, 13);
		
		// --- Buttons --- //
		while (buttonSet.next()){
			if(buttonSet.isVisible()    ){ matrix.blit(TEXTURE, x + buttonSet.pos().X, y + buttonSet.pos().Y, buttonSet.map().X,       buttonSet.map().Y,       buttonSet.sizeX(), buttonSet.sizeY()); }
			if(buttonSet.isHighlighted()){ matrix.blit(TEXTURE, x + buttonSet.pos().X, y + buttonSet.pos().Y, buttonSet.highlight().X, buttonSet.highlight().Y, buttonSet.sizeX(), buttonSet.sizeY()); }
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
