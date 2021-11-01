package mod.ivalice.system;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class IvalicePacketHandler {

    private static final String PROTOCOL_VERSION = Integer.toString(1);





    //----------------------------------------INSTANCE----------------------------------------//

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("ivalice", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );





    //----------------------------------------REGISTER----------------------------------------//

    public static void register(){
        int disc = 0;
    }





    //----------------------------------------SEND----------------------------------------//

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
