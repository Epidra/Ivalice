package mod.ivalice;

import mod.ivalice.model.ModelChocobo;
import mod.ivalice.render.RenderChocobo;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ivalice.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelHandler {
    public static ModelLayerLocation CHOCOBO_LAYER = new ModelLayerLocation(new ResourceLocation(Ivalice.MODID, "chocobo"), "chocobo");

    public static void init() {

    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ShopKeeper.ENTITY_CHOCOBO.get(), RenderChocobo::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CHOCOBO_LAYER, ModelChocobo::createBodyLayer);
    }
}
