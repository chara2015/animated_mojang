package net.labymod.v26_1.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/renderer/MixinGameRenderer_GuiGraphicsTracking.class */
@Mixin({GameRenderer.class})
public class MixinGameRenderer_GuiGraphicsTracking {
    @WrapOperation(method = {"extractGui"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/state/gui/GuiRenderState;II)Lnet/minecraft/client/gui/GuiGraphicsExtractor;")})
    private GuiGraphicsExtractor labyMod$setGuiGraphicsTracking(Minecraft minecraft, GuiRenderState guiRenderState, int mouseX, int mouseY, Operation<GuiGraphicsExtractor> original) {
        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) original.call(new Object[]{minecraft, guiRenderState, Integer.valueOf(mouseX), Integer.valueOf(mouseY)});
        MinecraftUtil.setCurrentGuiGraphics(guiGraphics);
        return guiGraphics;
    }
}
