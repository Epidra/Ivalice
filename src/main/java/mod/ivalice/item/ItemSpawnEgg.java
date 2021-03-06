package mod.ivalice.item;

import mod.ivalice.Ivalice;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.world.entity.MobSpawnType.DISPENSER;

public class ItemSpawnEgg {

}

// @Mod.EventBusSubscriber(modid = Ivalice.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
// public class ItemSpawnEgg extends SpawnEggItem {
//
//     private static final Logger LOGGER = LogManager.getLogger();
//     private static final Map<Supplier<EntityType<?>>, SpawnEggItem> MOD_EGGS = new HashMap<>();
//     private static final DefaultDispenseItemBehavior SPAWN_EGG_BEHAVIOR = new DefaultDispenseItemBehavior() {
//         public ItemStack dispenseStack(BlockSource source, ItemStack stack) {
//             Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
//             ((SpawnEggItem) stack.getItem()).getType(stack.getTag()).spawn(source.getLevel(), stack, null,
//                     source.getPos().relative(direction), DISPENSER, direction != Direction.UP, false);
//             stack.shrink(1);
//             return stack;
//         }
//     };
//     private Supplier<EntityType<?>> type;
//
//
//
//
//
//     //----------------------------------------CONSTRUCTOR----------------------------------------//
//
//     public ItemSpawnEgg(Supplier<EntityType<?>> typeIn, int primaryColorIn, int secondaryColorIn,
//                         Properties builder) {
//         super(null, primaryColorIn, secondaryColorIn, builder);
//         this.type = typeIn;
//         DispenserBlock.registerBehavior(this, SPAWN_EGG_BEHAVIOR);
//         MOD_EGGS.put(typeIn, this);
//     }
//
//
//
//
//
//     //----------------------------------------SUPPORT----------------------------------------//
//
//     @Override
//     public EntityType<?> getType(CompoundTag nbt) {
//         if (nbt != null && nbt.contains("EntityTag", 10)) {
//             CompoundTag compoundnbt = nbt.getCompound("EntityTag");
//             if (compoundnbt.contains("id", 8)) {
//                 return EntityType.byString(compoundnbt.getString("id")).orElse(type.get());
//             }
//         }
//         return type.get();
//     }
//
//     // Use reflection to add the mod spawn eggs to the EGGS map in SpawnEggItem
//     @SubscribeEvent
//     public static void setup(FMLCommonSetupEvent event) {
//         event.enqueueWork(() -> {
//             try {
//                 Map<EntityType<?>, SpawnEggItem> eggs = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
//                         null, "field_195987_b");
//                 for (Map.Entry<Supplier<EntityType<?>>, SpawnEggItem> entry : MOD_EGGS.entrySet())
//                     eggs.put(entry.getKey().get(), entry.getValue());
//             } catch (Exception e) {
//                 LOGGER.warn("Unable to access SpawnEggItem.EGGS");
//             }
//         });
//     }
//
//
//
// }
