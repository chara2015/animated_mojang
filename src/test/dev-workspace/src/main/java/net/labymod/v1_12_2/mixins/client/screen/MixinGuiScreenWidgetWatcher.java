package net.labymod.v1_12_2.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/screen/MixinGuiScreenWidgetWatcher.class */
@Mixin({blk.class})
public abstract class MixinGuiScreenWidgetWatcher {
    @Shadow
    protected abstract void a(bja bjaVar);

    @Redirect(method = {"mouseClicked"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V"))
    private void labyMod$watchWidget(blk instance, bja button) {
        if (!(button instanceof ConvertableMinecraftWidget)) {
            a(button);
            return;
        }
        ConvertableMinecraftWidget<?> convertableMinecraftWidget = (ConvertableMinecraftWidget) button;
        WidgetWatcher watcher = convertableMinecraftWidget.getWatcher();
        AbstractWidget<?> widget = watcher.getWidget();
        if (widget == null) {
            a(button);
            return;
        }
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, mouse.getX(), mouse.getY());
        if (!widget.isVisible() || !widget.pressable().get().booleanValue() || !isInBounds) {
            return;
        }
        if (watcher.hasReplacement() && isInBounds) {
            widget.onPress();
        } else {
            a(button);
        }
    }
}
