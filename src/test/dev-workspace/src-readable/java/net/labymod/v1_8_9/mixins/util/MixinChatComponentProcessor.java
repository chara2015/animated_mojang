package net.labymod.v1_8_9.mixins.util;

import net.labymod.v1_8_9.client.component.VersionedIconComponent;
import net.labymod.v1_8_9.client.component.VersionedKeybindComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinChatComponentProcessor.class */
@Mixin({ev.class})
public class MixinChatComponentProcessor {
    @Inject(method = {"processComponent"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$processIconComponent(m sender, eu component, pk entity, CallbackInfoReturnable<eu> cir) {
        if ((component instanceof VersionedIconComponent) || (component instanceof VersionedKeybindComponent)) {
            cir.setReturnValue(component);
        }
    }
}
