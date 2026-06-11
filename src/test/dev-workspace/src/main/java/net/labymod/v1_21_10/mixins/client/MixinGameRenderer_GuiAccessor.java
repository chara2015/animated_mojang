package net.labymod.v1_21_10.mixins.client;

import net.labymod.v1_21_10.client.renderer.GameRendererAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/MixinGameRenderer_GuiAccessor.class */
@Mixin({hfk.class})
public class MixinGameRenderer_GuiAccessor implements GameRendererAccessor {

    @Shadow
    @Final
    private gko F;

    @Shadow
    @Final
    private gjx E;

    @Shadow
    @Final
    private hxw D;

    @Override // net.labymod.v1_21_10.client.renderer.GameRendererAccessor
    public gko labyMod$getGuiRenderState() {
        return this.F;
    }

    @Override // net.labymod.v1_21_10.client.renderer.GameRendererAccessor
    public void renderGui() {
        this.E.a(this.D.a(a.a));
    }
}
