package net.labymod.v1_21_8.mixins.client.multiplayer;

import java.util.UUID;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/multiplayer/MixinServerboundResourcePackPacket.class */
@Mixin({aaz.class})
public class MixinServerboundResourcePackPacket {
    @Inject(method = {"<init>(Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V"}, at = {@At("TAIL")})
    private void labyMod$sendServerResourcePackResponse(UUID id, a action, CallbackInfo ci) {
        if (action == a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
