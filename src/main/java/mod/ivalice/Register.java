package mod.ivalice;

import mod.ivalice.client.menu.MenuChocoboBasic;
import mod.ivalice.client.menu.MenuChocoboChest;
import mod.ivalice.client.screen.ScreenChocoboBasic;
import mod.ivalice.client.screen.ScreenChocoboChest;
import mod.ivalice.common.block.BlockNest;
import mod.ivalice.common.block.entity.BlockEntityNest;
import mod.ivalice.common.entity.EntityCactuar;
import mod.ivalice.common.entity.EntityChocobo;
import mod.lucky77.block.BlockCropSingle;
import mod.lucky77.item.ItemFood;
import mod.lucky77.item.ItemSeed;
import mod.lucky77.register.RegisterMod;
import mod.lucky77.register.RegisterSeed;
import mod.lucky77.util.system.SystemStructurePoolAdditions;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static mod.ivalice.Ivalice.MODID;
import static net.minecraft.world.entity.MobCategory.CREATURE;

@SuppressWarnings("unused")
public class Register {
	
	// Create Deferred Registers to hold whatever is written in <...> which will all be registered under the MODID namespace
	private static final DeferredRegister<Block>               BLOCKS         = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
	private static final DeferredRegister<Item>                ITEMS          = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
	private static final DeferredRegister<MenuType<?>>         MENUS          = DeferredRegister.create(ForgeRegistries.MENU_TYPES,         MODID);
	private static final DeferredRegister<BlockEntityType<?>>  BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	private static final DeferredRegister<SoundEvent>          SOUNDS         = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);
	private static final DeferredRegister<EntityType<?>>       ENTITIES       = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,       MODID);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCKS / ITEMS  ---------- ---------- ---------- ---------- //
	
	// ----- Blocks ----- //
	public static final RegistryObject<Block> BLOCK_NEST           = registerBlock("block_nest",           BlockNest::new,  false);
	
	// ----- Food ----- //
	public static final RegistryObject<Item> FOOD_CHOCOBO_RAW    = registerItem("food_chocobo_raw",    () -> new ItemFood(1, 1f,  true));
	public static final RegistryObject<Item> FOOD_CHOCOBO_COOKED = registerItem("food_chocobo_cooked", () -> new ItemFood(1, 1f,  true));
	public static final RegistryObject<Item> FOOD_GYSAHL         = registerItem("food_gysahl",         () -> new ItemFood(1, 1f, false));
	public static final RegistryObject<Item> FOOD_KRAKKA         = registerItem("food_krakka",         () -> new ItemFood(1, 1f, false));
	public static final RegistryObject<Item> FOOD_MIMETT         = registerItem("food_mimett",         () -> new ItemFood(1, 1f, false));
	public static final RegistryObject<Item> FOOD_SYLKIS         = registerItem("food_sylkis",         () -> new ItemFood(1, 1f, false));
	public static final RegistryObject<Item> FOOD_TANTAL         = registerItem("food_tantal",         () -> new ItemFood(1, 1f, false));
	
	// ----- Crop ----- //
	public static final RegistryObject<Block> CROP_GYSAHL = registerBlock("crop_gysahl", () -> new BlockCropSingle(Blocks.WHEAT, "gysahl"   ), false);
	public static final RegistryObject<Block> CROP_KRAKKA = registerBlock("crop_krakka", () -> new BlockCropSingle(Blocks.WHEAT, "krakka"   ), false);
	public static final RegistryObject<Block> CROP_MIMETT = registerBlock("crop_mimett", () -> new BlockCropSingle(Blocks.WHEAT, "mimett"   ), false);
	public static final RegistryObject<Block> CROP_SYLKIS = registerBlock("crop_sylkis", () -> new BlockCropSingle(Blocks.WHEAT, "sylkis"   ), false);
	public static final RegistryObject<Block> CROP_TANTAL = registerBlock("crop_tantal", () -> new BlockCropSingle(Blocks.WHEAT, "tantal"   ), false);
	
	// ----- Seeds ----- //
	public static final RegistryObject<Item> SEED_GYSAHL = registerItem("seed_gysahl", () -> new ItemSeed("gysahl"));
	public static final RegistryObject<Item> SEED_KRAKKA = registerItem("seed_krakka", () -> new ItemSeed("krakka"));
	public static final RegistryObject<Item> SEED_MIMETT = registerItem("seed_mimett", () -> new ItemSeed("mimett"));
	public static final RegistryObject<Item> SEED_SYLKIS = registerItem("seed_sylkis", () -> new ItemSeed("sylkis"));
	public static final RegistryObject<Item> SEED_TANTAL = registerItem("seed_tantal", () -> new ItemSeed("tantal"));
	
	// ----- Books ----- //
	// public static final RegistryObject<Item> BOOK_CHOCOBO_1 = registerItem("book_chocobo_1", () -> new ItemBookChocobo(5, 0));
	// public static final RegistryObject<Item> BOOK_CHOCOBO_2 = registerItem("book_chocobo_2", () -> new ItemBookChocobo(5, 1));
	// public static final RegistryObject<Item> BOOK_CHOCOBO_3 = registerItem("book_chocobo_3", () -> new ItemBookChocobo(5, 2));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SOUNDS  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_AMBIENT     = SOUNDS.register("ivalice.chocobo.ambient",    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.ambient"   )));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HURT        = SOUNDS.register("ivalice.chocobo.hurt",       () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.hurt"      )));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_DEATH       = SOUNDS.register("ivalice.chocobo.death",      () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.death"     )));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_ANGRY       = SOUNDS.register("ivalice.chocobo.angry",      () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.angry"     )));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_LOW  = SOUNDS.register("ivalice.chocobo.healthlow",  () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthlow" )));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_HIGH = SOUNDS.register("ivalice.chocobo.healthhigh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthhigh")));
	public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_STEP        = SOUNDS.register("ivalice.chocobo.step",       () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ivalice.chocobo.step"      )));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCK ENTITES  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<BlockEntityType<?>> TILE_NEST = BLOCK_ENTITIES.register("nest", () -> BlockEntityType.Builder.of(BlockEntityNest::new, BLOCK_NEST.get()).build(null));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  MENUS  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<MenuType<MenuChocoboBasic>> MENU_CHOCOBO_BASIC = MENUS.register("chocobo_basic", () -> IForgeMenuType.create(MenuChocoboBasic::new));
	public static final RegistryObject<MenuType<MenuChocoboChest>> MENU_CHOCOBO_CHEST = MENUS.register("chocobo_chest", () -> IForgeMenuType.create(MenuChocoboChest::new));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ENTITES  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<EntityType<EntityChocobo>> ENTITY_CHOCOBO = ENTITIES.register("chocobo", () -> EntityType.Builder.of(EntityChocobo::new, CREATURE).sized(0.9F, 2.5F).setTrackingRange(10).build("chocobo"));
	public static final RegistryObject<EntityType<EntityCactuar>> ENTITY_CACTUAR = ENTITIES.register("cactuar", () -> EntityType.Builder.of(EntityCactuar::new, CREATURE).sized(0.9F, 2.5F).setTrackingRange(10).build("cactuar"));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SPAWN EGGS  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<Item> SPAWNEGG_CHOCOBO = registerItem("spawnegg_chocobo", () -> new ForgeSpawnEggItem(ENTITY_CHOCOBO, 16766720, 6908265, new Item.Properties()));
	public static final RegistryObject<Item> SPAWNEGG_CACTUAR = registerItem("spawnegg_cactuar", () -> new ForgeSpawnEggItem(ENTITY_CACTUAR, 11118213, 1118265, new Item.Properties()));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	static void setup(FMLCommonSetupEvent event){
		RegisterMod.register("ivalice");
		
		RegisterSeed.AddToMap("gysahl", CROP_GYSAHL.get(), SEED_GYSAHL.get(), FOOD_GYSAHL.get( ));
		RegisterSeed.AddToMap("krakka", CROP_KRAKKA.get(), SEED_KRAKKA.get(), FOOD_KRAKKA.get( ));
		RegisterSeed.AddToMap("mimett", CROP_MIMETT.get(), SEED_MIMETT.get(), FOOD_MIMETT.get( ));
		RegisterSeed.AddToMap("sylkis", CROP_SYLKIS.get(), SEED_SYLKIS.get(), FOOD_SYLKIS.get( ));
		RegisterSeed.AddToMap("tantal", CROP_TANTAL.get(), SEED_TANTAL.get(), FOOD_TANTAL.get( ));
	}
	
	@OnlyIn(Dist.CLIENT)
	static void setup(FMLClientSetupEvent event){
		event.enqueueWork(() -> {
			MenuScreens.register(MENU_CHOCOBO_BASIC.get(), ScreenChocoboBasic::new);
			MenuScreens.register(MENU_CHOCOBO_CHEST.get(), ScreenChocoboChest::new);
		});
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER  ---------- ---------- ---------- ---------- //
	
	// registers all deferred registries to the game
	public static void register(IEventBus bus){
		BLOCKS.register(        bus);
		ITEMS.register(         bus);
		MENUS.register(         bus);
		BLOCK_ENTITIES.register(bus);
		SOUNDS.register(        bus);
		ENTITIES.register(      bus);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER CREATIVE TABS  ---------- ---------- ---------- ---------- //
	
	public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event){
		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
			event.accept(Register.FOOD_CHOCOBO_RAW);
			event.accept(Register.FOOD_CHOCOBO_COOKED);
			event.accept(Register.FOOD_GYSAHL);
			event.accept(Register.FOOD_KRAKKA);
			event.accept(Register.FOOD_MIMETT);
			event.accept(Register.FOOD_SYLKIS);
			event.accept(Register.FOOD_TANTAL);
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
			event.accept(Register.SEED_GYSAHL);
			event.accept(Register.SEED_KRAKKA);
			event.accept(Register.SEED_MIMETT);
			event.accept(Register.SEED_SYLKIS);
			event.accept(Register.SEED_TANTAL);
			// event.accept(Register.BOOK_CHOCOBO_1);
			// event.accept(Register.BOOK_CHOCOBO_2);
			// event.accept(Register.BOOK_CHOCOBO_3);
		}
		if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
			event.accept(Register.SPAWNEGG_CHOCOBO);
			event.accept(Register.SPAWNEGG_CACTUAR);
		}
	}
	
	
	
	
	// ---------- ---------- ---------- ----------  EXTRA REGISTER  ---------- ---------- ---------- ---------- //
	
	public static void registerJigsaws(MinecraftServer server) {
		Registry<StructureTemplatePool>  templatePoolRegistry  = server.registryAccess().registry(Registries.TEMPLATE_POOL ).orElseThrow();
		Registry<StructureProcessorList> processorListRegistry = server.registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();
		
		SystemStructurePoolAdditions.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/plains/houses"),  "acecraft:village/plains_farm_gysahl",  1);
		SystemStructurePoolAdditions.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/snowy/houses"),   "acecraft:village/snowy_farm_gysahl",   1);
		SystemStructurePoolAdditions.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/savanna/houses"), "acecraft:village/savanna_farm_gysahl", 1);
		SystemStructurePoolAdditions.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/desert/houses"),  "acecraft:village/desert_farm_gysahl",  1);
		SystemStructurePoolAdditions.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/taiga/houses"),   "acecraft:village/taiga_farm_gysahl",   1);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// creates a block for the registry
	// sets up a creative tab for an additional registry later
	// if no creative tab, it does not create an fitting itemblock
	protected static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block, boolean registerItemBlock){
		RegistryObject<Block> supply = BLOCKS.register(name, block);
		if(registerItemBlock){
			ITEMS.register(name, () -> new BlockItem(supply.get(), new Item.Properties()));
		}
		return supply;
	}
	
	protected static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block){
		return registerBlock(name, block, true);
	}
	
	// creates an item fo the registry
	protected static RegistryObject<Item> registerItem(String name, Supplier<? extends Item> item){
		return ITEMS.register(name, item);
	}
	
	
	
}