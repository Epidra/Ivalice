package mod.ivalice.render;

import mod.ivalice.Ivalice;
import mod.ivalice.ModelHandler;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.model.ModelChocobo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChocobo extends MobRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE1 = new ResourceLocation(Ivalice.MODID, "textures/entity/chocobo.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(Ivalice.MODID, "textures/entity/chick.png");





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public RenderChocobo(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelChocobo<>(renderManager.bakeLayer(ModelHandler.CHOCOBO_LAYER)), 0.5F);
        this.addLayer(new RenderChocoboFeather(this));
        this.addLayer(new RenderChocoboEyes(this));
        this.addLayer(new RenderChocoboCollar(this));
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ResourceLocation getTextureLocation(EntityChocobo entity) {
        return entity.AnimYoung() ? TEXTURE2 : TEXTURE1;
    }



}
