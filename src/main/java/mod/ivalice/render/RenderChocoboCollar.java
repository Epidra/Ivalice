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
public class RenderChocoboCollar extends LayerRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Ivalice.MODID, "textures/entity/collar.png");

    public RenderChocoboCollar(IEntityRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> p_i232476_1_) {
        super(p_i232476_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, EntityChocobo p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!p_225628_4_.isInvisible() && p_225628_4_.isTame()) {
            IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucent(TEXTURE));
            float[] afloat = SheepEntity.getColorArray(p_225628_4_.getColorCollar());
            renderColoredCutoutModel(this.getParentModel(), TEXTURE, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, afloat[0], afloat[1], afloat[2]);
        }
    }
}
