package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_8_9.client.gui.GuiSliderAccessor;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiButton.class */
@Mixin({avs.class})
public abstract class MixinGuiButton<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K> {
    private final WidgetWatcher<K> labyMod$watcher = new WidgetWatcher<>();

    @Insert(method = {"drawButton"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWidgetWatcher(ave minecraft, int mouseX, int mouseY, InsertInfo ci) {
        Minecraft api = Laby.labyAPI().minecraft();
        api.updateMouse(mouseX, mouseY, mouse -> {
            this.labyMod$watcher.update(this, ((avs) this).j);
            boolean rendered = this.labyMod$watcher.render(VersionedStackProvider.DEFAULT_STACK, mouse, api.getTickDelta());
            if (rendered) {
                ci.cancel();
                if (this instanceof GuiSliderAccessor) {
                    ((GuiSliderAccessor) this).labymod$mouseDragged(minecraft, mouse);
                }
            }
        });
    }

    @Inject(method = {"playPressSound"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$playCustomDownSound(bpz soundManager, CallbackInfo ci) {
        AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }
}
