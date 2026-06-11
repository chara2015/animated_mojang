package net.labymod.v1_21_11.mixins.client;

import net.labymod.v1_21_11.client.renderer.GameRendererAccessor;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinGameRenderer_GuiAccessor.class */
@Mixin({GameRenderer.class})
public class MixinGameRenderer_GuiAccessor implements GameRendererAccessor {

    @Shadow
    @Final
    private GuiRenderState guiRenderState;

    @Shadow
    @Final
    private GuiRenderer guiRenderer;

    @Shadow
    @Final
    private FogRenderer fogRenderer;

    @Override // net.labymod.v1_21_11.client.renderer.GameRendererAccessor
    public GuiRenderState labyMod$getGuiRenderState() {
        return this.guiRenderState;
    }

    @Override // net.labymod.v1_21_11.client.renderer.GameRendererAccessor
    public void renderGui() {
        this.guiRenderer.render(this.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
    }
}

