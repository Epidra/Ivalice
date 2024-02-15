package mod.ivalice.common.entity.render;

import mod.ivalice.common.entity.EntityCactuar;
import mod.ivalice.common.entity.model.ModelCactuar;
import mod.ivalice.system.RegisterClient;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.ivalice.Ivalice.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderCactuar extends MobRenderer<EntityCactuar, ModelCactuar<EntityCactuar>> {
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(MODID, "textures/entity/cactuar_texture.png");
	// private static final ResourceLocation TEXTURE2 = new ResourceLocation(MODID, "textures/entity/chick.png");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderCactuar(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ModelCactuar<>(renderManager.bakeLayer(RegisterClient.CACTUAR_MODEL)), 0.5F);
		// this.addLayer(new RenderChocoboFeather(this));
		// this.addLayer(new RenderChocoboEyes(this));
		// this.addLayer(new RenderChocoboCollar(this));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public ResourceLocation getTextureLocation(EntityCactuar entity) {
		return TEXTURE1; // entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
	}
	
	
	
}
