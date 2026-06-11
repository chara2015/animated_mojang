package net.labymod.v1_12_2.mixins.world.level.chunks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/world/level/chunks/MixinChunkProviderClient.class */
@Mixin({brx.class})
public class MixinChunkProviderClient {
    @WrapOperation(method = {"unloadChunk"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;onUnload()V")})
    private void labyMod$fireChunkUnloadEvent(axw instance, Operation<Void> original) {
        original.call(new Object[]{instance});
        Laby.fireEvent(ChunkEvent.unload((Chunk) instance));
    }

    @Inject(method = {"loadChunk"}, at = {@At("TAIL")})
    private void labyMod$fireChunkEvent(int chunkX, int chunkZ, CallbackInfoReturnable<axw> cir) {
        Laby.fireEvent(ChunkEvent.load((Chunk) cir.getReturnValue()));
    }
}
