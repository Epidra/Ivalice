package mod.ivalice.common.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.common.entity.EntityChocobo;
import mod.ivalice.common.entity.model.ModelChocobo;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.ivalice.Ivalice.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderChocoboArmor extends RenderLayer<EntityChocobo, ModelChocobo<EntityChocobo>> {
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(MODID, "textures/entity/armor_1.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(MODID, "textures/entity/armor_2.png");
	private static final ResourceLocation TEXTURE3 = new ResourceLocation(MODID, "textures/entity/armor_3.png");
	private static final ResourceLocation TEXTURE4 = new ResourceLocation(MODID, "textures/entity/armor_4.png");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public RenderChocoboArmor(RenderLayerParent<EntityChocobo, ModelChocobo<EntityChocobo>> p_i232476_1_) {
		super(p_i232476_1_);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, EntityChocobo p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		if (!p_225628_4_.isInvisible() && !p_225628_4_.isBred() && p_225628_4_.isArmored()) {
			VertexConsumer ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucent(getTexture(p_225628_4_)));
			this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, LivingEntityRenderer.getOverlayCoords(p_225628_4_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public ResourceLocation getTexture(EntityChocobo entity) {
		return entity.getArmor().getItem() == Items.DIAMOND_HORSE_ARMOR ? TEXTURE4
			:  entity.getArmor().getItem() == Items.GOLDEN_HORSE_ARMOR  ? TEXTURE3
			:  entity.getArmor().getItem() == Items.IRON_HORSE_ARMOR    ? TEXTURE2
			:                                                             TEXTURE1;
	}
	
	
	
}
