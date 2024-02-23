package mod.ivalice.system;

import mod.ivalice.Register;
import mod.ivalice.common.entity.model.ModelCactuar;
import mod.ivalice.common.entity.model.ModelChocobo;
import mod.ivalice.common.entity.render.RenderCactuar;
import mod.ivalice.common.entity.render.RenderChocobo;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mod.ivalice.Ivalice.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterClient {
	
	public static ModelLayerLocation CHOCOBO_MODEL = new ModelLayerLocation(new ResourceLocation(MODID, "chocobo"), "chocobo_model");
	public static ModelLayerLocation CACTUAR_MODEL = new ModelLayerLocation(new ResourceLocation(MODID, "cactuar"), "cactuar_model");
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUBSCRIBER  ---------- ---------- ---------- ---------- //
	
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(Register.ENTITY_CHOCOBO.get(), RenderChocobo::new);
		event.registerEntityRenderer(Register.ENTITY_CACTUAR.get(), RenderCactuar::new);
	}
	
	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(CHOCOBO_MODEL, ModelChocobo::createBodyLayer);
		event.registerLayerDefinition(CACTUAR_MODEL, ModelCactuar::createBodyLayer);
	}
	
	
	
}