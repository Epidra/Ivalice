package mod.ivalice;

import mod.ivalice.blocks.BlockCrop;
import mod.ivalice.entity.EntityChocobo;
import mod.ivalice.items.ItemSeed;
import mod.ivalice.items.ItemSpawnEgg;
import mod.ivalice.render.RenderChocobo;
import mod.lucky77.items.ItemFood;
import mod.lucky77.items.ItemItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

import static mod.ivalice.Ivalice.MODID;

@SuppressWarnings({"unused", "deprecation"})
public class ShopKeeper {

    private static final DeferredRegister<Block> BLOCKS     = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item>                 ITEMS      = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<ContainerType<?>>     CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,         MODID);
    private static final DeferredRegister<TileEntityType<?>>    TILES      = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,      MODID);
    private static final DeferredRegister<SoundEvent>           SOUNDS     = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);
    private static final DeferredRegister<EntityType<?>>        ENTITIES   = DeferredRegister.create(ForgeRegistries.ENTITIES,           MODID);
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPES    = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    // Stuff
    public static final RegistryObject<Item> STUFF_FEATHER = register("stuff_feather", new ItemItem(ItemGroup.TAB_MISC));

    // Food
    public static final RegistryObject<Item> FOOD_GYSAHL = register("food_gysahl", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_KRAKKA = register("food_krakka", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_MIMETT = register("food_mimett", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_SYLKIS = register("food_sylkis", new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_TANTAL = register("food_tantal", new ItemFood(1, 1, false));

    public static final RegistryObject<Item> FOOD_CHOCOBO_RAW    = register("food_chocobo_raw",    new ItemFood(1, 1, false));
    public static final RegistryObject<Item> FOOD_CHOCOBO_COOKED = register("food_chocobo_cooked", new ItemFood(1, 1, false));

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
    public static final RegistryObject<EntityType<EntityChocobo>>   ENTITY_CHOCOBO   = ENTITIES.register("chocobo",   () -> EntityType.Builder.of(EntityChocobo::new,                    EntityClassification.CREATURE).sized(0.9F, 2.5F).setTrackingRange(10).build(new ResourceLocation(MODID, "chocobo").toString()));
    // Spawn Eggs
    public static final RegistryObject<ItemSpawnEgg> SPAWNEGG_CHOCOBO = ITEMS.register("spawnegg_chocobo", () -> new ItemSpawnEgg(() -> ENTITY_CHOCOBO.get(), 16766720, 6908265, new Item.Properties().tab(ItemGroup.TAB_MISC)));



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

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key)
    {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>()
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
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ENTITY_CHOCOBO.get(), Config.CHOCOBO.weight.get(), Config.CHOCOBO.min.get(), Config.CHOCOBO.max.get()));
        //}
    }

    public static void registerEntities() {
        EntitySpawnPlacementRegistry.register(ENTITY_CHOCOBO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
        GlobalEntityTypeAttributes.put(ENTITY_CHOCOBO.get(), EntityChocobo.registerAttributes().build());
    }
    private static RegistryObject<Block> register(String name, Block block){
        return register(name, block, null);
    }

    private static RegistryObject<Block> register(String name, Block block, ItemGroup itemGroup){
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

    public static ConfiguredFeature<?, ?> buildOreSpawn(String name, BlockState state, int veinSize, int maxHeight, int spawnRate, boolean isNether) {
        OreFeatureConfig config = new OreFeatureConfig(isNether ? OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES : OreFeatureConfig.FillerBlockType.NATURAL_STONE, state, veinSize);
        ConfiguredFeature<?, ?> feature = Feature.ORE.configured(config)
                .range(maxHeight)
                .squared()
                .chance(spawnRate);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Ivalice.MODID, name), feature  );
        return feature;

    }




    //----------------------------------------SETUP----------------------------------------//

    static void setup(FMLCommonSetupEvent event){
        registerEntities();
    }

    @OnlyIn(Dist.CLIENT)
    static void setup(FMLClientSetupEvent event){
        RenderTypeLookup.setRenderLayer(CROP_GYSAHL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CROP_KRAKKA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CROP_MIMETT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CROP_SYLKIS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CROP_TANTAL.get(), RenderType.cutout());

        RenderingRegistry.registerEntityRenderingHandler(ENTITY_CHOCOBO.get(), RenderChocobo::new);
    }

}
