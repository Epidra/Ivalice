package mod.ivalice.render;

import mod.ivalice.Ivalice;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.model.ModelChocobo;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChocobo extends MobRenderer<EntityChocobo, ModelChocobo<EntityChocobo>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Ivalice.MODID, "textures/entity/chocobo.png");




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public RenderChocobo(EntityRendererManager renderManager) {
        super(renderManager, new ModelChocobo<>(), 0.5F);
        this.addLayer(new RenderChocoboFeather(this));
        this.addLayer(new RenderChocoboEyes(this));
        this.addLayer(new RenderChocoboCollar(this));
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ResourceLocation getTextureLocation(EntityChocobo entity) {
        return TEXTURE;
    }

}
