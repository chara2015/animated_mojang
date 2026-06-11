package net.labymod.v1_21_10.mixins.client.screen.components.events;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/screen/components/events/MixinContainerEventHandler.class */
@Mixin({ggv.class})
public interface MixinContainerEventHandler {
    @Shadow
    boolean a(gth gthVar);

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    default void labyMod$fixControlsScreenKeys(gte event, CallbackInfoReturnable<Boolean> cir) {
        Key key;
        if ((this instanceof gqn) && (key = DefaultKeyMapper.lastPressed()) != null && a(new gth(key.getId(), event.d(), 0))) {
            cir.setReturnValue(true);
        }
    }
}
