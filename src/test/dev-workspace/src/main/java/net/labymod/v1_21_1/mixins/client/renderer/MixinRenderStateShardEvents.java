package net.labymod.v1_21_1.mixins.client.renderer;

import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import net.labymod.core.event.client.render.RenderTypeAttachmentEventCaller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/MixinRenderStateShardEvents.class */
@Mixin({gfg.class})
public class MixinRenderStateShardEvents {

    @Shadow
    @Final
    protected String b;

    @Inject(method = {"setupRenderState"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireApplyPreRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof gfh)) {
            return;
        }
        boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.b, RenderTypeAttachmentEvent.State.APPLY);
        if (cancelled) {
            ci.cancel();
        }
    }

    @Inject(method = {"setupRenderState"}, at = {@At("TAIL")})
    private void labyMod$fireApplyPostRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof gfh)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.b, RenderTypeAttachmentEvent.State.APPLY);
    }

    @Inject(method = {"clearRenderState"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireClearPreRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof gfh)) {
            return;
        }
        boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.b, RenderTypeAttachmentEvent.State.CLEAR);
        if (cancelled) {
            ci.cancel();
        }
    }

    @Inject(method = {"clearRenderState"}, at = {@At("TAIL")})
    private void labyMod$fireClearPostRenderTypeAttachmentEvent(CallbackInfo ci) {
        if (!(this instanceof gfh)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.b, RenderTypeAttachmentEvent.State.CLEAR);
    }
}
