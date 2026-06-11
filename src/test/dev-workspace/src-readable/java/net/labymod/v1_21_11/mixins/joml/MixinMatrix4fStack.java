package net.labymod.v1_21_11.mixins.joml;

import net.labymod.core.laby3d.stack.MatrixStackAccessor;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/joml/MixinMatrix4fStack.class */
@Mixin({Matrix4fStack.class})
public class MixinMatrix4fStack implements MatrixStackAccessor {

    @Shadow
    private int curr;

    public int getCurrentIndex() {
        return this.curr;
    }
}
