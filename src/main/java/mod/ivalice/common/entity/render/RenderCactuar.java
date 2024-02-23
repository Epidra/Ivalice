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
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/cactuar.png");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderCactuar(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ModelCactuar<>(renderManager.bakeLayer(RegisterClient.CACTUAR_MODEL)), 0.5F);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public ResourceLocation getTextureLocation(EntityCactuar entity) {
		return TEXTURE;
	}
	
	
	
}
