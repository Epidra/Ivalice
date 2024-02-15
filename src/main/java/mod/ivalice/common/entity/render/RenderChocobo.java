package mod.ivalice.common.entity.render;

import mod.ivalice.common.entity.EntityChocobo;
import mod.ivalice.common.entity.model.ModelChocobo;
import mod.ivalice.system.RegisterClient;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.ivalice.Ivalice.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderChocobo extends MobRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> {
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(MODID, "textures/entity/chocobo_overlay.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(MODID, "textures/entity/chocochick_overlay.png");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderChocobo(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ModelChocobo<>(renderManager.bakeLayer(RegisterClient.CHOCOBO_MODEL)), 0.5F);
		this.addLayer(new RenderChocoboFeather(this, renderManager.getModelSet()));
		this.addLayer(new RenderChocoboEyes(this));
		this.addLayer(new RenderChocoboCollar(this, renderManager.getModelSet()));
		this.addLayer(new RenderChocoboArmor(this));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public ResourceLocation getTextureLocation(EntityChocobo entity) {
		return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
	}
	
	
	
}
