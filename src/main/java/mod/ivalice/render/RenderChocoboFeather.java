package mod.ivalice.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.Ivalice;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.model.ModelChocobo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChocoboFeather extends RenderLayer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE1 = new ResourceLocation(Ivalice.MODID, "textures/entity/feather.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(Ivalice.MODID, "textures/entity/chick_feather.png");

    public RenderChocoboFeather(RenderLayerParent<EntityChocobo, ModelChocobo<EntityChocobo>> p_i232476_1_) {
        super(p_i232476_1_);
    }

    public void render(PoseStack p_117421_, MultiBufferSource p_117422_, int p_117423_, EntityChocobo p_117424_, float p_117425_, float p_117426_, float p_117427_, float p_117428_, float p_117429_, float p_117430_) {
        if (p_117424_.isInvisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(p_117424_);
            if (flag) {
                this.getParentModel().copyPropertiesTo(this.getParentModel());
                this.getParentModel().prepareMobModel(p_117424_, p_117425_, p_117426_, p_117427_);
                this.getParentModel().setupAnim(p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_);
                VertexConsumer vertexconsumer = p_117422_.getBuffer(RenderType.outline(getTexture(p_117424_)));
                this.getParentModel().renderToBuffer(p_117421_, vertexconsumer, p_117423_, LivingEntityRenderer.getOverlayCoords(p_117424_, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }

        } else {
            float f;
            float f1;
            float f2;
            if (p_117424_.hasCustomName() && "jeb_".equals(p_117424_.getName().getContents())) {
                int i1 = 25;
                int i = p_117424_.tickCount / 25 + p_117424_.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float)(p_117424_.tickCount % 25) + p_117427_) / 25.0F;
                float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
                float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = p_117424_.getColorFeather();
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }

            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), getTexture(p_117424_), p_117421_, p_117422_, p_117423_, p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_, p_117427_, f, f1, f2);
        }
    }

    public ResourceLocation getTexture(EntityChocobo entity) {
        return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
    }
}
