package mod.ivalice.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.Ivalice;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.model.ModelChocobo;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChocoboCollar extends RenderLayer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE1 = new ResourceLocation(Ivalice.MODID, "textures/entity/collar.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(Ivalice.MODID, "textures/entity/chick_collar.png");





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public RenderChocoboCollar(RenderLayerParent<EntityChocobo, ModelChocobo<EntityChocobo>> p_i232476_1_) {
        super(p_i232476_1_);
    }





    //----------------------------------------RENDER----------------------------------------//

    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, EntityChocobo p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!p_225628_4_.isInvisible() && p_225628_4_.isTame()) {
            float[] afloat = Sheep.getColorArray(p_225628_4_.getColorCollar());
            renderColoredCutoutModel(this.getParentModel(), getTexture(p_225628_4_), p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, afloat[0], afloat[1], afloat[2]);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public ResourceLocation getTexture(EntityChocobo entity) {
        return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
    }



}
