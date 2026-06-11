package net.labymod.v1_8_9.mixins.client.renderer;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinGlStateManager_ClearCacheFix.class */
@Mixin({bfl.class})
public class MixinGlStateManager_ClearCacheFix {
    @Overwrite
    public static void a(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Overwrite
    public static void a(double depth) {
        GL11.glClearDepth(depth);
    }
}
