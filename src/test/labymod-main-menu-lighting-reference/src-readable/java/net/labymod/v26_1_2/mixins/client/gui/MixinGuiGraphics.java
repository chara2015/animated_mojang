package net.labymod.v26_1_2.mixins.client.gui;

import net.labymod.v26_1_2.client.gui.GuiGraphicsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Style;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/gui/MixinGuiGraphics.class */
@Mixin({GuiGraphicsExtractor.class})
public abstract class MixinGuiGraphics implements GuiGraphicsAccessor {

    @Mutable
    @Shadow
    @Final
    private Matrix3x2fStack pose;

    @Shadow
    protected abstract void componentHoverEffect(Font font, Style style, int i, int i2);

    @Override // net.labymod.v26_1_2.client.gui.GuiGraphicsAccessor
    public void setPose(Matrix3x2fStack pose) {
        this.pose = pose;
    }

    @Override // net.labymod.v26_1_2.client.gui.GuiGraphicsAccessor
    public void labyMod$renderHoverEffect(Style hoveredStyle, int xMouse, int yMouse) {
        componentHoverEffect(Minecraft.getInstance().font, hoveredStyle, xMouse, yMouse);
    }

    @ModifyConstant(method = {"<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/state/gui/GuiRenderState;II)V"}, constant = {@Constant(intValue = 16)})
    private static int labyMod$increaseStackSize(int constant) {
        return constant + 128;
    }
}
