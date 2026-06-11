package net.labymod.v26_1.mixins.client.laby3d;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.v26_1.laby3d.ProjectionAccessor;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/laby3d/MixinProjectionMatrixBuffer.class */
@Mixin({ProjectionMatrixBuffer.class})
public class MixinProjectionMatrixBuffer {

    @Shadow
    @Final
    private Matrix4f tempMatrix;
    private String labyMod$name;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$setName(String name, CallbackInfo ci) {
        this.labyMod$name = name;
    }

    @Inject(method = {"getBuffer(Lnet/minecraft/client/renderer/Projection;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"}, at = {@At("RETURN")})
    private void labyMod$storeMatrix(Projection projection, CallbackInfoReturnable<GpuBufferSlice> cir) {
        ProjectionAccessor accessor = ProjectionAccessor.self(projection);
        ProjectionType projectionType = accessor.labyMod$getProjectionType();
        GameTransformations gameTransformations = Laby.references().gameTransformations();
        switch (AnonymousClass1.$SwitchMap$com$mojang$blaze3d$ProjectionType[projectionType.ordinal()]) {
            case 1:
                if (ParameterType.LEVEL.equalsIgnoreCase(this.labyMod$name)) {
                    gameTransformations.projectionMatrix().set(this.tempMatrix);
                } else {
                    gameTransformations.projectionMatrix().set(this.tempMatrix);
                }
                break;
            case 2:
                gameTransformations.guiProjectionMatrix().set(this.tempMatrix);
                break;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_1.mixins.client.laby3d.MixinProjectionMatrixBuffer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/laby3d/MixinProjectionMatrixBuffer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$blaze3d$ProjectionType = new int[ProjectionType.values().length];

        static {
            try {
                $SwitchMap$com$mojang$blaze3d$ProjectionType[ProjectionType.PERSPECTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$ProjectionType[ProjectionType.ORTHOGRAPHIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Inject(method = {"getBuffer(Lorg/joml/Matrix4f;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"}, at = {@At("TAIL")})
    private void labyMod$updateDefaultProjectionMatrix(Matrix4f projectionMatrix, CallbackInfoReturnable<GpuBufferSlice> cir) {
        GameTransformations gameTransformations = Laby.references().gameTransformations();
        gameTransformations.projectionMatrix().set(projectionMatrix);
    }
}
