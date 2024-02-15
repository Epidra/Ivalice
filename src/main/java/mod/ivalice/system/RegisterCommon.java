package mod.ivalice.system;

import mod.ivalice.Register;
import mod.ivalice.common.entity.EntityCactuar;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mod.ivalice.Ivalice.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterCommon {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUBSCRIBER  ---------- ---------- ---------- ---------- //
	
	@SubscribeEvent
	public void onServerAboutToStartEvent(ServerAboutToStartEvent event) {
		Register.registerJigsaws(event.getServer());
	}
	
	@SubscribeEvent
	public static void entityAttributes(EntityAttributeCreationEvent event){
		event.put(Register.ENTITY_CHOCOBO.get(), EntityChocobo.createAttributes().build());
		event.put(Register.ENTITY_CACTUAR.get(), EntityChocobo.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event){
		event.register(Register.ENTITY_CHOCOBO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, EntityChocobo::canChocoboSpawn, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Register.ENTITY_CACTUAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, EntityCactuar::canCactuarSpawn, SpawnPlacementRegisterEvent.Operation.OR);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
