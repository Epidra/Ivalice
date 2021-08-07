package mod.ivalice;

import mod.ivalice.block.BlockCrop;
import mod.ivalice.block.BlockNest;
import mod.ivalice.block.BlockStraw;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.item.ItemSeed;
import mod.ivalice.item.ItemSpawnEgg;
import mod.ivalice.render.RenderChocobo;
import mod.ivalice.blockentity.BlockEntityNest;
import mod.lucky77.item.ItemFood;
import mod.lucky77.item.ItemItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

import static mod.ivalice.Ivalice.MODID;
import static net.minecraft.data.BuiltinRegistries.CONFIGURED_FEATURE;
import static net.minecraft.world.entity.MobCategory.CREATURE;
import static net.minecraft.world.entity.SpawnPlacements.Type.ON_GROUND;
import static net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.Predicates.NATURAL_STONE;
import static net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES;

@SuppressWarnings({"unused", "deprecation"})
public class ShopKeeper {

    private static final DeferredRegister<Block> BLOCKS     = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item>                 ITEMS      = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<MenuType<?>>     CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,         MODID);
    private static final DeferredRegister<BlockEntityType<?>>    TILES      = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,      MODID);
    private static final DeferredRegister<SoundEvent>           SOUNDS     = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);
    private static final DeferredRegister<EntityType<?>>        ENTITIES   = DeferredRegister.create(ForgeRegistries.ENTITIES,           MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES    = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    // Stuff
    public static final RegistryObject<Item> STUFF_FEATHER = register("stuff_feather", new ItemItem(CreativeModeTab.TAB_MISC));

    // Food
    public static final RegistryObject<Item> FOOD_GYSAHL = register("food_gysahl", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_KRAKKA = register("food_krakka", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_MIMETT = register("food_mimett", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_SYLKIS = register("food_sylkis", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_TANTAL = register("food_tantal", new ItemFood(1, 1, false));

    public static final RegistryObject<Item> FOOD_CHOCOBO_RAW    = register("food_chocobo_raw",    new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_CHOCOBO_COOKED = register("food_chocobo_cooked", new ItemFood(1, 1, false));

    // Straw
    public static final RegistryObject<Block> BLOCK_STRAW = register("block_straw", new BlockStraw(), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLOCK_NEST  = register("block_nest",  new BlockNest(), CreativeModeTab.TAB_DECORATIONS);

    // Crop
    public static final RegistryObject<Block> CROP_GYSAHL = register("crop_gysahl", new BlockCrop("gysahl", Blocks.WHEAT));
    public static final RegistryObject<Block> CROP_KRAKKA = register("crop_krakka", new BlockCrop("krakka", Blocks.WHEAT));
    public static final RegistryObject<Block> CROP_MIMETT = register("crop_mimett", new BlockCrop("mimett", Blocks.WHEAT));
    public static final RegistryObject<Block> CROP_SYLKIS = register("crop_sylkis", new BlockCrop("sylkis", Blocks.WHEAT));
    public static final RegistryObject<Block> CROP_TANTAL = register("crop_tantal", new BlockCrop("tantal", Blocks.WHEAT));

    // Seeds
    public static final RegistryObject<Item> SEEDS_GYSAHL = register("seeds_gysahl", new ItemSeed("gysahl"));
    public static final RegistryObject<Item> SEEDS_KRAKKA = register("seeds_krakka", new ItemSeed("krakka"));
    public static final RegistryObject<Item> SEEDS_MIMETT = register("seeds_mimett", new ItemSeed("mimett"));
    public static final RegistryObject<Item> SEEDS_SYLKIS = register("seeds_sylkis", new ItemSeed("sylkis"));
    public static final RegistryObject<Item> SEEDS_TANTAL = register("seeds_tantal", new ItemSeed("tantal"));

    // Sounds
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_AMBIENT     = register("ivalice.chocobo.ambient",    new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.ambient")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HURT        = register("ivalice.chocobo.hurt",       new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.hurt")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_DEATH       = register("ivalice.chocobo.death",      new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.death")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_ANGRY       = register("ivalice.chocobo.angry",      new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.angry")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_LOW  = register("ivalice.chocobo.healthlow",  new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthlow")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_HEALTH_HIGH = register("ivalice.chocobo.healthhigh", new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.healthhigh")));
    public static final RegistryObject<SoundEvent> SOUND_CHOCOBO_STEP        = register("ivalice.chocobo.step",       new SoundEvent(new ResourceLocation(MODID, "ivalice.chocobo.step")));

    // Entities
    public static final RegistryObject<EntityType<EntityChocobo>>   ENTITY_CHOCOBO   = ENTITIES.register("chocobo",   () -> EntityType.Builder.of(EntityChocobo::new, CREATURE).sized(0.9F, 2.5F).setTrackingRange(10).build(new ResourceLocation(MODID, "chocobo").toString()));
    //public static final RegistryObject<EntityType<AdeliePenguinEntity>> ADELIE_PENGUIN = createEntity("adelie_penguin", AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);
    // Spawn Eggs
    public static final RegistryObject<ItemSpawnEgg> SPAWNEGG_CHOCOBO = ITEMS.register("spawnegg_chocobo", () -> new ItemSpawnEgg(() -> ENTITY_CHOCOBO.get(), 16766720, 6908265, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

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

    static <T extends Recipe<?>> RecipeType<T> register(final String key)
    {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new RecipeType<T>()
        {
            @Override
            public String toString()
            {
                return key;
            }
        });
    }

    public static void registerEntity(BiomeLoadingEvent event, Set<BiomeDictionary.Type> types) {
        //if (event.getCategory() == Biome.Category.PLAINS) {
        event.getSpawns().getSpawner(CREATURE).add(new MobSpawnSettings.SpawnerData(ENTITY_CHOCOBO.get(), Config.CHOCOBO.weight.get(), Config.CHOCOBO.min.get(), Config.CHOCOBO.max.get()));
        //}
    }

    //public static void registerEntities() {
    //    SpawnPlacements.register(ENTITY_CHOCOBO.get(), ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
    //    GlobalEntityTypeAttributes.put(ENTITY_CHOCOBO.get(), EntityChocobo.registerAttributes().build());
    //}
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

    //public static ConfiguredFeature<?, ?> buildOreSpawn(String name, BlockState state, int veinSize, int maxHeight, int spawnRate, boolean isNether) {
    //    OreConfiguration config = new OreConfiguration(isNether ? NETHER_ORE_REPLACEABLES : NATURAL_STONE, state, veinSize);
    //    ConfiguredFeature<?, ?> feature = Feature.ORE.configured(config)
    //            .range(new RangeDecoratorConfiguration(new ConstantHeight(1)))
    //            .squared()
    //            .chance(spawnRate);
    //    Registry.register(CONFIGURED_FEATURE, new ResourceLocation(Ivalice.MODID, name), feature  );
    //    return feature;
    //}




    //----------------------------------------SETUP----------------------------------------//

    static void setup(FMLCommonSetupEvent event){
        //registerEntities();
    }

    @OnlyIn(Dist.CLIENT)
    static void setup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(CROP_GYSAHL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CROP_KRAKKA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CROP_MIMETT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CROP_SYLKIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CROP_TANTAL.get(), RenderType.cutout());

        //RenderingRegistry.registerEntityRenderingHandler(ENTITY_CHOCOBO.get(), RenderChocobo::new);
    }

}
