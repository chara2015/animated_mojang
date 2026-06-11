package net.labymod.v26_1.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/world/level/chunks/MixinClientLevel.class */
@Mixin({ClientLevel.class})
public class MixinClientLevel {
    @Inject(method = {"unload"}, at = {@At("TAIL")})
    private void labyMod$fireChunkUnloadEvent(LevelChunk chunk, CallbackInfo ci) {
        Laby.fireEvent(ChunkEvent.unload((Chunk) chunk));
    }

    @Inject(method = {"onChunkLoaded"}, at = {@At("TAIL")})
    private void labyMod$fireChunkLoadEvent(ChunkPos chunkPos, CallbackInfo ci) {
        ClientWorld level = Laby.references().clientWorld();
        Laby.fireEvent(ChunkEvent.load(level.getChunk(chunkPos.x(), chunkPos.z())));
    }
}
