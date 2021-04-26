package mod.ivalice;

import mod.ivalice.system.IvalicePacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ivalice")
public class Ivalice {

    public static final String MODID = "ivalice";




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public Ivalice() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
        MinecraftForge.EVENT_BUS.register(this);
        ShopKeeper.register();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        IvalicePacketHandler.register();
        ShopKeeper.setup(event);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        ShopKeeper.setup(event);
    }

}
