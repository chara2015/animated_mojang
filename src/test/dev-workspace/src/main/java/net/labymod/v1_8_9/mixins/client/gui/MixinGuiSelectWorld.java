package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiSelectWorld.class */
@Mixin({axv.class})
public class MixinGuiSelectWorld {

    @Shadow
    private boolean i;

    @Inject(method = {"initGui"}, at = {@At("TAIL")})
    public void labyMod$allowWorldJoin(CallbackInfo callbackInfo) {
        this.i = false;
    }
}
