package mod.ivalice.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.ivalice.Ivalice;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.model.ModelChocobo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChocoboEyes extends LayerRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE1 = new ResourceLocation(Ivalice.MODID, "textures/entity/eyes.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(Ivalice.MODID, "textures/entity/chick_eyes.png");

    public RenderChocoboEyes(IEntityRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> p_i232476_1_) {
        super(p_i232476_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, EntityChocobo p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!p_225628_4_.isInvisible()) {
            IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucent(getTexture(p_225628_4_)));
            this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, LivingRenderer.getOverlayCoords(p_225628_4_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTexture(EntityChocobo entity) {
        return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
    }
}
