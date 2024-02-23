package mod.ivalice.common.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.ivalice.common.entity.EntityChocobo;
import mod.ivalice.common.entity.model.ModelChocobo;
import mod.ivalice.system.RegisterClient;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.ivalice.Ivalice.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderChocoboFeather extends RenderLayer<EntityChocobo, ModelChocobo<EntityChocobo>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/color.png");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderChocoboFeather(RenderLayerParent<EntityChocobo, ModelChocobo<EntityChocobo>> p_174533_) {
		super(p_174533_);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	public void render(PoseStack p_117720_, MultiBufferSource p_117721_, int p_117722_, EntityChocobo p_117723_, float p_117724_, float p_117725_, float p_117726_, float p_117727_, float p_117728_, float p_117729_) {
		byte[] afloat = p_117723_.getFeatherColor();
		renderColoredCutoutModel(this.getParentModel(), TEXTURE, p_117720_, p_117721_, p_117722_, p_117723_, afloat[0] / 63.0f, afloat[1] / 63.0f, afloat[2] / 63.0f);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public ResourceLocation getTexture(EntityChocobo entity) {
		return TEXTURE;
	}
	
	
	
}
