package net.labymod.v1_18_2.mixins.client.renderer;

import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import net.labymod.core.event.client.render.RenderTypeAttachmentEventCaller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/MixinRenderStateShardEvents.class */
@Mixin({eqz.class})
public class MixinRenderStateShardEvents {

    @Shadow
    @Final
    protected String a;

    @Inject(method = {"setupRenderState"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireApplyPreRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.a, RenderTypeAttachmentEvent.State.APPLY);
        if (cancelled) {
            ci.cancel();
        }
    }

    @Inject(method = {"setupRenderState"}, at = {@At("TAIL")})
    private void labyMod$fireApplyPostRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.a, RenderTypeAttachmentEvent.State.APPLY);
    }

    @Inject(method = {"clearRenderState"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireClearPreRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.a, RenderTypeAttachmentEvent.State.CLEAR);
        if (cancelled) {
            ci.cancel();
        }
    }

    @Inject(method = {"clearRenderState"}, at = {@At("TAIL")})
    private void labyMod$fireClearPostRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.a, RenderTypeAttachmentEvent.State.CLEAR);
    }
}
