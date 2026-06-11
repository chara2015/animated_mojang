package net.labymod.v1_21.mixins.client.screen;

import java.util.function.IntSupplier;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/screen/MixinLoadingOverlayDarkBackground.class */
@Mixin({fnt.class})
public class MixinLoadingOverlayDarkBackground {

    @Shadow
    @Final
    private fgo m;

    @Shadow
    @Final
    private static int e;

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"), require = 0)
    private int labyMod$darkModeSetting(IntSupplier supplier) {
        return Laby.labyAPI().config().appearance().darkLoadingScreen().get().booleanValue() ? e : supplier.getAsInt();
    }
}
