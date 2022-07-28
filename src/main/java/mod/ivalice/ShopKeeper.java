package mod.ivalice;

import mod.ivalice.block.BlockCrop;
import mod.ivalice.block.BlockNest;
import mod.ivalice.block.BlockStraw;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.item.ItemSeed;
import mod.ivalice.item.ItemSpawnEgg;
import mod.ivalice.blockentity.BlockEntityNest;
import mod.lucky77.item.ItemFood;
import mod.lucky77.item.ItemItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import static mod.ivalice.Ivalice.MODID;
import static net.minecraft.world.entity.MobCategory.CREATURE;

@SuppressWarnings({"unused", "deprecation"})
public class ShopKeeper {

    private static final DeferredRegister<Block>               BLOCKS     = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item>                ITEMS      = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<MenuType<?>>         CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,         MODID);
    private static final DeferredRegister<BlockEntityType<?>>  TILES      = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<SoundEvent>          SOUNDS     = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);
    private static final DeferredRegister<EntityType<?>>       ENTITIES   = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,       MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES    = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);





    // Stuff
    public static final RegistryObject<Item> STUFF_FEATHER = ITEMS.register("stuff_feather", () -> new ItemItem(CreativeModeTab.TAB_MISC));

    // Food
    public static final RegistryObject<Item> FOOD_GYSAHL = ITEMS.register("food_gysahl", () -> new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_KRAKKA = ITEMS.register("food_krakka", () -> new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_MIMETT = ITEMS.register("food_mimett", () -> new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_SYLKIS = ITEMS.register("food_sylkis", () -> new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_TANTAL = ITEMS.register("food_tantal", () -> new ItemFood(1, 1, false));

    public static final RegistryObject<Item> FOOD_CHOCOBO_RAW    = ITEMS.register("food_chocobo_raw",    () -> new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_CHOCOBO_COOKED = ITEMS.register("food_chocobo_cooked", () -> new ItemFood(1, 1, false));

    // Straw
    public static final RegistryObject<Block> BLOCK_STRAW = BLOCKS.register("block_straw", () -> new BlockStraw());     static final RegistryObject<Item> I_BLOCK_STRAW = fromBlock(BLOCK_STRAW, CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLOCK_NEST  = BLOCKS.register("block_nest",  () -> new BlockNest() );     static final RegistryObject<Item> I_BLOCK_NEST  = fromBlock(BLOCK_NEST,  CreativeModeTab.TAB_DECORATIONS);

    // Crop
    public static final RegistryObject<Block> CROP_GYSAHL = BLOCKS.register("crop_gysahl", () -> new BlockCrop("gysahl", Blocks.WHEAT));     static final RegistryObject<Item> I_CROP_GYSAHL = fromBlock(CROP_GYSAHL);
    public static final RegistryObject<Block> CROP_KRAKKA = BLOCKS.register("crop_krakka", () -> new BlockCrop("krakka", Blocks.WHEAT));     static final RegistryObject<Item> I_CROP_KRAKKA = fromBlock(CROP_KRAKKA);
    public static final RegistryObject<Block> CROP_MIMETT = BLOCKS.register("crop_mimett", () -> new BlockCrop("mimett", Blocks.WHEAT));     static final RegistryObject<Item> I_CROP_MIMETT = fromBlock(CROP_MIMETT);
    public static final RegistryObject<Block> CROP_SYLKIS = BLOCKS.register("crop_sylkis", () -> new BlockCrop("sylkis", Blocks.WHEAT));     static final RegistryObject<Item> I_CROP_SYLKIS = fromBlock(CROP_SYLKIS);
    public static final RegistryObject<Block> CROP_TANTAL = BLOCKS.register("crop_tantal", () -> new BlockCrop("tantal", Blocks.WHEAT));     static final RegistryObject<Item> I_CROP_TANTAL = fromBlock(CROP_TANTAL);

    // Seeds
    public static final RegistryObject<Item> SEEDS_GYSAHL = ITEMS.register("seeds_gysahl", () -> new ItemSeed("gysahl"));
    public static final RegistryObject<Item> SEEDS_KRAKKA = ITEMS.register("seeds_krakka", () -> new ItemSeed("krakka"));
    public static final RegistryObject<Item> SEEDS_MIMETT = ITEMS.register("seeds_mimett", () -> new ItemSeed("mimett"));
    public static final RegistryObject<Item> SEEDS_SYLKIS = ITEMS.register("seeds_sylkis", () -> new ItemSeed("sylkis"));
    public static final RegistryObject<Item> SEEDS_TANTAL = ITEMS.register("seeds_tantal", () -> new ItemSeed("tantal"));

    // Sounds
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_AMBIENT     = SOUNDS.register("ivalice.chocobo.ambient",    () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.ambient")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HURT        = SOUNDS.register("ivalice.chocobo.hurt",       () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.hurt")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_DEATH       = SOUNDS.register("ivalice.chocobo.death",      () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.death")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_ANGRY       = SOUNDS.register("ivalice.chocobo.angry",      () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.angry")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_LOW  = SOUNDS.register("ivalice.chocobo.healthlow",  () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthlow")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_HIGH = SOUNDS.register("ivalice.chocobo.healthhigh", () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthhigh")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_STEP        = SOUNDS.register("ivalice.chocobo.step",       () -> new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.step")));

    // Entities
    public static final RegistryObject<EntityType<EntityChocobo>>   ENTITY_CHOCOBO   = ENTITIES.register("chocobo",   () -> EntityType.Builder.of(EntityChocobo::new, CREATURE).sized(0.9F, 2.5F).setTrackingRange(10).build(new ResourceLocation(MODID, "chocobo").toString()));

    // Spawn Eggs
    public static final RegistryObject<Item> SPAWNEGG_CHOCOBO = ITEMS.register("spawnegg_chocobo", () -> new ForgeSpawnEggItem(ENTITY_CHOCOBO, 16766720, 6908265, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Block Entities
    public static  final RegistryObject<BlockEntityType<BlockEntityNest>> TILE_NEST = TILES.register("nest", () -> BlockEntityType.Builder.of(BlockEntityNest::new, BLOCK_NEST.get() ).build(null));





    //----------------------------------------REGISTER----------------------------------------//

    static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, Item.Properties prop) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), prop));
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, CreativeModeTab CreativeModeTab) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), (new Item.Properties()).tab(CreativeModeTab) ));
    }

    static <T extends Recipe<?>> RecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new RecipeType<T>()
        {
            @Override
            public String toString()
            {
                return key;
            }
        });
    }

    // public static void registerEntity(BiomeLoadingEvent event, Set<BiomeDictionary.Type> types) {
    //     event.getSpawns().getSpawner(CREATURE).add(new MobSpawnSettings.SpawnerData(ENTITY_CHOCOBO.get(), Config.CHOCOBO.weight.get(), Config.CHOCOBO.min.get(), Config.CHOCOBO.max.get()));
    // }

    private static RegistryObject<Block> register(String name, Block block){
        return register(name, block, null);
    }

    private static RegistryObject<Block> register(String name, Block block, CreativeModeTab itemGroup){
        if(itemGroup != null){ ITEMS.register(name, () -> new BlockItem(block, (new Item.Properties()).tab(itemGroup))); }
        return BLOCKS.register(name, () -> block);
    }

    private static RegistryObject<Item> register(String name, Item item){
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<SoundEvent> register(String name, SoundEvent sound){
        return SOUNDS.register(name, () -> sound);
    }

    private static RegistryObject<EntityType<?>> register(String name, EntityType entity){
        return ENTITIES.register(name, () -> entity);
    }





    //----------------------------------------SETUP----------------------------------------//

    static void setup(FMLCommonSetupEvent event){

    }

    @OnlyIn(Dist.CLIENT)
    static void setup(FMLClientSetupEvent event){
        //ItemBlockRenderTypes.setRenderLayer(CROP_GYSAHL.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(CROP_KRAKKA.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(CROP_MIMETT.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(CROP_SYLKIS.get(), RenderType.cutout());
        //ItemBlockRenderTypes.setRenderLayer(CROP_TANTAL.get(), RenderType.cutout());
    }



}
