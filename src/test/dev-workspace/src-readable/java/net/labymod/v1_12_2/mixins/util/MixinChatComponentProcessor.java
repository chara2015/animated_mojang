package net.labymod.v1_12_2.mixins.util;

import net.labymod.v1_12_2.client.component.VersionedIconComponent;
import net.labymod.v1_12_2.client.component.VersionedKeybindComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/util/MixinChatComponentProcessor.class */
@Mixin({hi.class})
public class MixinChatComponentProcessor {
    @Inject(method = {"processComponent"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$processIconComponent(bn sender, hh component, vg entity, CallbackInfoReturnable<hh> cir) {
        if ((component instanceof VersionedIconComponent) || (component instanceof VersionedKeybindComponent)) {
            cir.setReturnValue(component);
        }
    }
}
