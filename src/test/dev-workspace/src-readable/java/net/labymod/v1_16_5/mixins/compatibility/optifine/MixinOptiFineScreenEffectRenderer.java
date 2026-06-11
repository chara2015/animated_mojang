package net.labymod.v1_16_5.mixins.compatibility.optifine;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v1_16_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/compatibility/optifine/MixinOptiFineScreenEffectRenderer.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({eaq.class})
public class MixinOptiFineScreenEffectRenderer {

    @Unique
    private static final Stack labymod$STACK = Stack.getDefaultEmptyStack();

    @Unique
    private static FloatVector3 labymod$OFFSET = FloatVector3.ZERO;

    @Inject(method = {"getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;"}, at = {@At("HEAD")})
    @Dynamic
    private static void labyMod$getViewBlockingStateHead(bfw player, CallbackInfoReturnable<ceh> cir) {
        labymod$STACK.push();
        Laby.fireEvent(new CameraSetupEvent(labymod$STACK, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(labymod$STACK, Phase.POST));
        labymod$OFFSET = labymod$STACK.transformVector(0.0f, 0.0f, 0.0f);
        labymod$STACK.pop();
    }

    @Redirect(method = {"getOverlayBlock(Lnet/minecraft/world/entity/player/Player;)Lorg/apache/commons/lang3/tuple/Pair;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(DDD)Lnet/minecraft/core/BlockPos$MutableBlockPos;"))
    @Dynamic
    private static a labyMod$getViewBlockingState(a instance, double x, double y, double z) {
        instance.c(x - ((double) labymod$OFFSET.getX()), y - ((double) labymod$OFFSET.getY()), z - ((double) labymod$OFFSET.getZ()));
        return instance;
    }
}
