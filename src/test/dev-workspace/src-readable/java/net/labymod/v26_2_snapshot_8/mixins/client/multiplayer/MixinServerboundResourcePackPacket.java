package net.labymod.v26_2_snapshot_8.mixins.client.multiplayer;

import java.util.UUID;
import net.labymod.core.main.LabyMod;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/multiplayer/MixinServerboundResourcePackPacket.class */
@Mixin({ServerboundResourcePackPacket.class})
public class MixinServerboundResourcePackPacket {
    @Inject(method = {"<init>(Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V"}, at = {@At("TAIL")})
    private void labyMod$sendServerResourcePackResponse(UUID id, ServerboundResourcePackPacket.Action action, CallbackInfo ci) {
        if (action == ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
