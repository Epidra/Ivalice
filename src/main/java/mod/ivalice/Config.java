package mod.ivalice;

import mod.lucky77.util.BiomeDictionaryHelper;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class Config {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ConfigMob CHOCOBO = new ConfigMob(BUILDER, "chocobo", 1, 1, 8);

    //----------------------------------------CONFIG_MOB----------------------------------------//

    public static class ConfigMob {
        public final ForgeConfigSpec.IntValue min;
        public final ForgeConfigSpec.IntValue max;
        public final ForgeConfigSpec.IntValue weight;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> include;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> exclude;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> spawnBlocks;

        ConfigMob(ForgeConfigSpec.Builder builder, String id, int _min, int _max, int _weight) {
            builder.push("spawn chances " + id);
            builder.comment("Configure spawn weight & min/max group size. Set weight to 0 to disable.");
            min = builder.defineInRange("min", _min, 0, 64);
            max = builder.defineInRange("max", _max, 0, 64);
            weight = builder.defineInRange("weight", _weight, 0, 100);
            builder.pop();
            builder.push("spawnable biomes " + id);
            spawnBlocks = builder.defineList("spawn blocks", Collections.singletonList(Blocks.GRASS_BLOCK.getRegistryName().toString()), o -> o instanceof String && ForgeRegistries.BLOCKS.getKeys().contains(new ResourceLocation(o.toString())));
            include = builder.defineList("include", Collections.singletonList(PLAINS.toString()), o -> o instanceof String && (o.equals("") || BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(o.toString()))));
            exclude = builder.defineList("exclude", Arrays.asList(FOREST.toString(), MOUNTAIN.toString(), OCEAN.toString(), NETHER.toString()), o -> o instanceof String && (o.equals("") || BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(o.toString()))));
            builder.pop();
        }
    }

    //----------------------------------------BUILDER----------------------------------------//



    public static final ForgeConfigSpec spec = BUILDER.build();
}
