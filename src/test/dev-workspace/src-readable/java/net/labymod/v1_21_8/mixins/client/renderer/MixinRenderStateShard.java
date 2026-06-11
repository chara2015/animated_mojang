package net.labymod.v1_21_8.mixins.client.renderer;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/MixinRenderStateShard.class */
@Mixin({gxy.class})
public class MixinRenderStateShard implements RenderStateShardAttachment {
    private final List<Runnable> labyMod$attachments = new ArrayList();

    @Inject(method = {"clearRenderState"}, at = {@At("TAIL")})
    private void labyMod$callAttachments(CallbackInfo ci) {
        for (Runnable runnable : this.labyMod$attachments) {
            if (runnable != null) {
                runnable.run();
            }
        }
        this.labyMod$attachments.clear();
    }

    @Override // net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment
    public void addAttachment(Runnable renderer) {
        this.labyMod$attachments.add(renderer);
    }
}
