package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiScreenBook.class */
@Mixin({ayo.class})
public class MixinGuiScreenBook {
    @Insert(method = {"drawScreen"}, at = @At("HEAD"))
    public void labyMod$enableVanillaFont(int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(true);
    }

    @Insert(method = {"drawScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(IIF)V", shift = At.Shift.BEFORE))
    public void labyMod$resetFont(int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        RenderAttributesStack renderAttributesStack = renderAttributesStack();
        renderAttributesStack.pop();
    }

    private RenderAttributesStack renderAttributesStack() {
        return Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
