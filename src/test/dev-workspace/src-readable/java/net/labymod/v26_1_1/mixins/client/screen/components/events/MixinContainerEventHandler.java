package net.labymod.v26_1_1.mixins.client.screen.components.events;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/components/events/MixinContainerEventHandler.class */
@Mixin({ContainerEventHandler.class})
public interface MixinContainerEventHandler {
    @Shadow
    boolean keyPressed(KeyEvent keyEvent);

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    default void labyMod$fixControlsScreenKeys(CharacterEvent event, CallbackInfoReturnable<Boolean> cir) {
        Key key;
        if ((this instanceof ControlsScreen) && (key = DefaultKeyMapper.lastPressed()) != null && keyPressed(new KeyEvent(key.getId(), 0, 0))) {
            cir.setReturnValue(true);
        }
    }
}
