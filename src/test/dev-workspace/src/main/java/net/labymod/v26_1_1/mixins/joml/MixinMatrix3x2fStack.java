package net.labymod.v26_1_1.mixins.joml;

import net.labymod.core.laby3d.stack.MatrixStackAccessor;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/joml/MixinMatrix3x2fStack.class */
@Mixin({Matrix3x2fStack.class})
public class MixinMatrix3x2fStack implements MatrixStackAccessor {

    @Shadow
    private int curr;

    @Override // net.labymod.core.laby3d.stack.MatrixStackAccessor
    public int getCurrentIndex() {
        return this.curr;
    }
}
