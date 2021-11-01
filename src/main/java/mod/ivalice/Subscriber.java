package mod.ivalice;

import mod.ivalice.crafting.ModifierAddItem;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Set;

public class Subscriber {

    // ...





    //----------------------------------------EVENT_FORGE----------------------------------------//

    @ObjectHolder(Ivalice.MODID)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEventsForge {

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void onBiomesLoading(final BiomeLoadingEvent event) {
            if (event.getName() != null) {
                RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
                Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
                ShopKeeper.registerEntity(event, types);
            }
        }
    }





    //----------------------------------------EVENT_MOD----------------------------------------//

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @ObjectHolder(Ivalice.MODID)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEventsMod {
        @SubscribeEvent
        public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> ev) {
            ev.getRegistry().register(new ModifierAddItem.Serializer().setRegistryName(Ivalice.MODID, "add_item"));
        }
    }



}
