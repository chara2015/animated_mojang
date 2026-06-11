package net.labymod.v1_21_11.mixins.client;

import net.labymod.v1_21_11.client.renderer.GameRendererAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinGameRenderer_GuiAccessor.class */
@Mixin({hob.class})
public class MixinGameRenderer_GuiAccessor implements GameRendererAccessor {

    @Shadow
    @Final
    private gqg F;

    @Shadow
    @Final
    private gpp E;

    @Shadow
    @Final
    private igq D;

    @Override // net.labymod.v1_21_11.client.renderer.GameRendererAccessor
    public gqg labyMod$getGuiRenderState() {
        return this.F;
    }

    @Override // net.labymod.v1_21_11.client.renderer.GameRendererAccessor
    public void renderGui() {
        this.E.a(this.D.a(a.a));
    }
}
