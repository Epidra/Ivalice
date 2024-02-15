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
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(MODID, "textures/entity/chocobo_color.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(MODID, "textures/entity/chocochick_color.png");
	
	private final ModelChocobo<EntityChocobo> model;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderChocoboFeather(RenderLayerParent<EntityChocobo, ModelChocobo<EntityChocobo>> p_174533_, EntityModelSet ems) {
		super(p_174533_);
		this.model = new ModelChocobo<>(ems.bakeLayer(RegisterClient.CHOCOBO_MODEL));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	public void render(PoseStack p_117720_, MultiBufferSource p_117721_, int p_117722_, EntityChocobo p_117723_, float p_117724_, float p_117725_, float p_117726_, float p_117727_, float p_117728_, float p_117729_) {
		// if (p_117723_.isTame() && !p_117723_.isInvisible()) {
			float[] afloat = p_117723_.getFeatherColor();
			renderColoredCutoutModel(this.getParentModel(), TEXTURE1, p_117720_, p_117721_, p_117722_, p_117723_, afloat[0], afloat[1], afloat[2]);
		// }
		
		// if (true) {
		// 	if (p_117424_.isInvisible()) {
		// 		Minecraft minecraft = Minecraft.getInstance();
		// 		boolean flag = minecraft.shouldEntityAppearGlowing(p_117424_);
		// 		if (flag) {
		// 			this.getParentModel().copyPropertiesTo(this.model);
		// 			this.model.prepareMobModel(p_117424_, p_117425_, p_117426_, p_117427_);
		// 			this.model.setupAnim(p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_);
		// 			VertexConsumer vertexconsumer = p_117422_.getBuffer(RenderType.outline(TEXTURE1));
		// 			this.model.renderToBuffer(p_117421_, vertexconsumer, p_117423_, LivingEntityRenderer.getOverlayCoords(p_117424_, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
		// 		}
		// 	} else {
		// 		float f;
		// 		float f1;
		// 		float f2;
		// 		if (p_117424_.hasCustomName() && "jeb_".equals(p_117424_.getName().getContents())) {
		// 			int i1 = 25;
		// 			int i = p_117424_.tickCount / 25 + p_117424_.getId();
		// 			int j = DyeColor.values().length;
		// 			int k = i % j;
		// 			int l = (i + 1) % j;
		// 			float f3 = ((float)(p_117424_.tickCount % 25) + p_117427_) / 25.0F;
		// 			float[] afloat1 = EntityAlpaca.getColorArray(DyeColor.byId(k));
		// 			float[] afloat2 = EntityAlpaca.getColorArray(DyeColor.byId(l));
		// 			f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
		// 			f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
		// 			f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
		// 		} else {
		// 			float[] afloat = EntityChocobo.getColorArray(p_117424_.getColor());
		// 			f = afloat[0];
		// 			f1 = afloat[1];
		// 			f2 = afloat[2];
		// 		}
		// 		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, TEXTURE1, p_117421_, p_117422_, p_117423_, p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_, p_117427_, f, f1, f2);
		// 	}
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public ResourceLocation getTexture(EntityChocobo entity) {
		return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
	}
	
	
	
}
