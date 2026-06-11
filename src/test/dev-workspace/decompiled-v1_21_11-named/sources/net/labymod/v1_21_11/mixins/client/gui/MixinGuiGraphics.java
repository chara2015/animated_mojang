package net.labymod.v1_21_11.mixins.client.gui;

import net.labymod.v1_21_11.client.gui.GuiGraphicsAccessor;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/MixinGuiGraphics.class */
@Mixin({GuiGraphics.class})
public class MixinGuiGraphics implements GuiGraphicsAccessor {

    @Mutable
    @Shadow
    @Final
    private Matrix3x2fStack c;

    @Override // net.labymod.v1_21_11.client.gui.GuiGraphicsAccessor
    public void setPose(Matrix3x2fStack pose) {
        this.c = pose;
    }

    @ModifyConstant(method = {"<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/render/state/GuiRenderState;II)V"}, constant = {@Constant(intValue = 16)})
    private static int labyMod$increaseStackSize(int constant) {
        return constant + 128;
    }
}
