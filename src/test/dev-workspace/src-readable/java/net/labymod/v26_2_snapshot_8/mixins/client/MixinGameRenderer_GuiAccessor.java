package net.labymod.v26_2_snapshot_8.mixins.client;

import net.labymod.v26_2_snapshot_8.client.renderer.GameRendererAccessor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinGameRenderer_GuiAccessor.class */
@Mixin({GameRenderer.class})
public class MixinGameRenderer_GuiAccessor implements GameRendererAccessor {

    @Shadow
    @Final
    private GameRenderState gameRenderState;

    @Override // net.labymod.v26_2_snapshot_8.client.renderer.GameRendererAccessor
    public GuiRenderState labyMod$getGuiRenderState() {
        return this.gameRenderState.guiRenderState;
    }
}
