package mod.ivalice;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraft.world.level.biome.Biomes.*;

@SuppressWarnings("unused")
public class Configuration {
	
	// public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	//
	// public static final ConfigMob CHOCOBO = new ConfigMob(BUILDER, "chocobo", 1, 1, 8);
	//
	// public static class ConfigMob {
	// 	public final ForgeConfigSpec.IntValue min;
	// 	public final ForgeConfigSpec.IntValue max;
	// 	public final ForgeConfigSpec.IntValue weight;
	// 	public final ForgeConfigSpec.ConfigValue<List<? extends String>> include;
	// 	public final ForgeConfigSpec.ConfigValue<List<? extends String>> exclude;
	// 	public final ForgeConfigSpec.ConfigValue<List<? extends String>> spawnBlocks;
	//
	// 	ConfigMob(ForgeConfigSpec.Builder builder, String id, int _min, int _max, int _weight) {
	// 		builder.push("spawn chances " + id);
	// 		builder.comment("Configure spawn weight & min/max group size. Set weight to 0 to disable.");
	// 		min = builder.defineInRange("min", _min, 0, 64);
	// 		max = builder.defineInRange("max", _max, 0, 64);
	// 		weight = builder.defineInRange("weight", _weight, 0, 100);
	// 		builder.pop();
	// 		builder.push("spawnable biomes " + id);
	// 		spawnBlocks = builder.defineList("spawn blocks", Collections.singletonList(Blocks.GRASS_BLOCK.getDescriptionId().toString()), o -> o instanceof String && ForgeRegistries.BLOCKS.getKeys().contains(new ResourceLocation(o.toString())));
	// 		include = builder.defineList("include", Arrays.asList(SAVANNA.toString(), BADLANDS.toString()), o -> o instanceof String && (o.equals("")));
	// 		exclude = builder.defineList("exclude", Arrays.asList(FOREST.toString(), SNOWY_PLAINS.toString(), OCEAN.toString()), o -> o instanceof String && (o.equals("")));
	// 		builder.pop();
	// 	}
	// }
	//
	// public static final ForgeConfigSpec spec = BUILDER.build();
	
}