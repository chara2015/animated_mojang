package net.labymod.v1_8_9.mixins.network;

import java.nio.charset.StandardCharsets;
import net.labymod.core.client.worldsharing.Worldsharing;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/network/MixinNetHandlerPlayClient.class */
@Mixin({bcy.class})
public class MixinNetHandlerPlayClient {

    @Shadow
    @Final
    private ek c;

    @Shadow
    private ave f;

    @Inject(method = {"handleCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$interceptCustomPayload(gg payload, CallbackInfo ci) {
        bde serverData;
        if (Worldsharing.CHANNEL.equals(payload.a())) {
            em buffer = payload.b();
            if (buffer.readableBytes() > 128 || (serverData = this.f.D()) == null) {
                return;
            }
            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);
            String target = new String(bytes, StandardCharsets.UTF_8);
            if (!Worldsharing.checkAddr(target)) {
                return;
            }
            buffer.clear();
            this.c.a(new im(payload.a(), buffer));
            Worldsharing.handle(serverData.b, target);
            ci.cancel();
        }
    }
}
