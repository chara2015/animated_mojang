package net.labymod.v1_21_11.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/screen/MixinBookScreenVanillaFont.class */
@Mixin({BookViewScreen.class, BookEditScreen.class})
public class MixinBookScreenVanillaFont {
    @Insert(method = {"render"}, at = @At("HEAD"))
    public void labyMod$enableVanillaFont(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
    }

    @Insert(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.BEFORE))
    public void labyMod$resetFont(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        renderAttributesStack.pop();
    }

    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
