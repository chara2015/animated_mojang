package net.labymod.v1_21_8.mixins.client;

import net.labymod.v1_21_8.client.renderer.GameRendererAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/MixinGameRenderer_GuiAccessor.class */
@Mixin({gxb.class})
public class MixinGameRenderer_GuiAccessor implements GameRendererAccessor {

    @Shadow
    @Final
    private gcy F;

    @Shadow
    @Final
    private gcg E;

    @Shadow
    @Final
    private hnm D;

    @Override // net.labymod.v1_21_8.client.renderer.GameRendererAccessor
    public gcy labyMod$getGuiRenderState() {
        return this.F;
    }

    @Override // net.labymod.v1_21_8.client.renderer.GameRendererAccessor
    public void renderGui() {
        this.E.a(this.D.a(a.a));
    }
}
