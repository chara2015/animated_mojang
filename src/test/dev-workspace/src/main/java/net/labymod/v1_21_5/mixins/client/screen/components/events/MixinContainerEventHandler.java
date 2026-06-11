package net.labymod.v1_21_5.mixins.client.screen.components.events;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/screen/components/events/MixinContainerEventHandler.class */
@Mixin({fvu.class})
public interface MixinContainerEventHandler {
    @Shadow
    boolean a(int i, int i2, int i3);

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    default void labyMod$fixControlsScreenKeys(char c, int i, CallbackInfoReturnable<Boolean> cir) {
        Key key;
        if ((this instanceof gdb) && (key = DefaultKeyMapper.lastPressed()) != null && a(key.getId(), i, 0)) {
            cir.setReturnValue(true);
        }
    }
}
