package net.labymod.v1_19_4.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/world/level/chunks/MixinClientLevel.class */
@Mixin({fdj.class})
public class MixinClientLevel {
    @Inject(method = {"unload"}, at = {@At("TAIL")})
    private void labyMod$fireChunkUnloadEvent(ddy chunk, CallbackInfo ci) {
        Laby.fireEvent(ChunkEvent.unload((Chunk) chunk));
    }

    @Inject(method = {"onChunkLoaded"}, at = {@At("TAIL")})
    private void labyMod$fireChunkLoadEvent(clp chunkPos, CallbackInfo ci) {
        ClientWorld level = Laby.references().clientWorld();
        Laby.fireEvent(ChunkEvent.load(level.getChunk(chunkPos.e, chunkPos.f)));
    }
}
