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
		String text = menu.chocobo.getCustomName() == null ? "CHOCO NAME" : menu.chocobo.getCustomName().getString();
		int w = this.font.width(text) / 2;
		matrix.drawString(font, text, x + 49 - w, y + 10 + 16, 16777215);
		
		matrix.drawString(font, "HP  " + menu.chocobo.getDataHealth(), 9, 31 + 13*1, 16777215);
		matrix.drawString(font, "ATK  " + menu.chocobo.getDataAttack(), 9, 31 + 13*2, 16777215);
		matrix.drawString(font, "GLD  " + menu.chocobo.getDataGlide(), 9, 31 + 13*3, 16777215);
		matrix.drawString(font, "SPD  " + menu.chocobo.getDataSpeed(), 9, 31 + 13*4, 16777215);
		
		matrix.drawString(font, "JMP  " + menu.chocobo.getDataJump(), 9 + 44, 31 + 13*1, 16777215);
		matrix.drawString(font, "" + menu.chocobo.getDataNature(), 9 + 44, 31 + 13*4, 16777215);
		matrix.drawString(font, "" + (menu.chocobo.getDataGender() ? "MALE" : "FEMALE"), 9 + 44, 31 + 13*3, 16777215);
		// matrix.drawString(font, "SPEED  " + menu.chocobo.getDataHealth(), 9 + 44, 31 + 13*3, 16777215);
	}
	
	/** Draws the background layer of this container (behind the items). */
	protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		int x = (this.width  - 176) / 2;
		int y = (this.height - 178) / 2;
		buttonSet.update(x, y, mouseX, mouseY);
		// matrix.blit(TEXTURE, x + 0, y + 0, 0, 0, 176, 178);
		matrix.blit(TEXTURE, x + 0, y + 0, 0, 0, 176, 178);
		
		matrix.blit(TEXTURE, x + 97, y + 7, 3, 181, 72, 72);
		// matrix.blit(TEXTURE, x + 97, y + 7, 78, 181, 72, 72);
		
		matrix.blit(TEXTURE, x + 7 + 44*0, y + 29 + 13*0, 178, 2 + 14*0, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*1, y + 29 + 13*0, 178, 2 + 14*1, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*0, y + 29 + 13*1, 178, 2 + 14*2, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*1, y + 29 + 13*1, 178, 2 + 14*3, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*0, y + 29 + 13*2, 178, 2 + 14*4, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*1, y + 29 + 13*2, 178, 2 + 14*5, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*0, y + 29 + 13*3, 178, 2 + 14*6, 42, 12);
		matrix.blit(TEXTURE, x + 7 + 44*1, y + 29 + 13*3, 178, 2 + 14*7, 42, 12);
		
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
