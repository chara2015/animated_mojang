package net.labymod.v1_21_11.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinScreenEffectRenderer.class */
@Mixin({hpc.class})
public class MixinScreenEffectRenderer {

    @Unique
    private static final Stack labymod$STACK = Stack.create((StackProvider) new DefaultStackProvider());

    @Unique
    private static FloatVector3 labymod$OFFSET = FloatVector3.ZERO;

    @Inject(method = {"getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;"}, at = {@At("HEAD")})
    private static void labyMod$getViewBlockingStateHead(ddm player, CallbackInfoReturnable<eoh> cir) {
        labymod$STACK.push();
        Laby.fireEvent(new CameraSetupEvent(labymod$STACK, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(labymod$STACK, Phase.POST));
        labymod$OFFSET = labymod$STACK.transformVector(0.0f, 0.0f, 0.0f);
        labymod$STACK.pop();
    }

    @Redirect(method = {"getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(DDD)Lnet/minecraft/core/BlockPos$MutableBlockPos;"))
    private static a labyMod$getViewBlockingState(a instance, double x, double y, double z) {
        instance.b(x - ((double) labymod$OFFSET.getX()), y - ((double) labymod$OFFSET.getY()), z - ((double) labymod$OFFSET.getZ()));
        return instance;
    }
}
