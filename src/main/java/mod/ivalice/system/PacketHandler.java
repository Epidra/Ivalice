package mod.ivalice.system;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static mod.ivalice.Ivalice.MODID;

public class PacketHandler {
	
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER  ---------- ---------- ---------- ---------- //
	
	public static void register(){
		int disc = 0;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message){
		INSTANCE.send(target, message);
	}
	
	public static void sendToServer(Object message){
		INSTANCE.sendToServer(message);
	}
	
	public static <MSG> void sendTo(MSG msg, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}
	
	public static <MSG> void sendToChunk(MSG msg, LevelChunk chunk) {
		INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
	}
	
	public static <MSG> void sendToAll(MSG msg) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
	}
	
	
	
}