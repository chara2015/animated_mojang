package net.labymod.v26_1_1.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/MixinBookScreenVanillaFont.class */
@Mixin({BookViewScreen.class, BookEditScreen.class})
public class MixinBookScreenVanillaFont {
    @Inject(method = {"extractRenderState"}, at = {@At("HEAD")})
    public void labyMod$enableVanillaFont(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
    }

    @Inject(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", shift = At.Shift.BEFORE)})
    public void labyMod$resetFont(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        renderAttributesStack.pop();
    }

    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
