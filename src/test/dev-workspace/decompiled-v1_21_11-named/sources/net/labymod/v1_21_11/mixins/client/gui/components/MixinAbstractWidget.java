package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinAbstractWidget.class */
@Mixin({AbstractWidget.class})
public abstract class MixinAbstractWidget<K extends net.labymod.api.client.gui.screen.widget.AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, AbstractWidgetAccessor {

    @Shadow
    protected int g;

    @Shadow
    protected int h;
    private final WidgetWatcher<K> labyMod$watcher = new WidgetWatcher<>();
    private int labyMod$lastFrame;

    @Shadow
    public abstract boolean b();

    @Shadow
    public abstract boolean D();

    @Shadow
    public abstract int aT_();

    @Shadow
    public abstract int aU_();

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> ci) {
        net.labymod.api.client.gui.screen.widget.AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget == null) {
            return;
        }
        double mouseX = event.x();
        double mouseY = event.y();
        boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, (float) mouseX, (float) mouseY);
        if (!widget.isVisible() || !((Boolean) widget.pressable().get()).booleanValue() || !isInBounds) {
            ci.setReturnValue(false);
        } else if (this.labyMod$watcher.hasReplacement() && isInBounds) {
            widget.onPress();
            ci.setReturnValue(true);
        }
    }

    @Inject(method = {"playDownSound"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$playCustomDownSound(SoundManager soundManager, CallbackInfo ci) {
        net.labymod.api.client.gui.screen.widget.AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }

    @Inject(method = {"setX"}, at = {@At("HEAD")})
    private void labyMod$invalidateX(int x, CallbackInfo ci) {
        if (aT_() != x) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setY"}, at = {@At("HEAD")})
    private void labyMod$invalidateY(int y, CallbackInfo ci) {
        if (aU_() != y) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setWidth"}, at = {@At("HEAD")})
    private void labyMod$invalidateWidth(int width, CallbackInfo ci) {
        if (this.g != width) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setHeight"}, at = {@At("HEAD")})
    private void labyMod$invalidateHeight(int height, CallbackInfo ci) {
        if (this.h != height) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setSize"}, at = {@At("HEAD")})
    private void labyMod$invalidateSize(int width, int height, CallbackInfo ci) {
        if (this.g != width || this.h != height) {
            labyMod$invalidate();
        }
    }

    @Unique
    private void labyMod$invalidate() {
        int currentFrame = Laby.references().frameTimer().getFrame();
        if (this.labyMod$lastFrame == currentFrame) {
            return;
        }
        this.labyMod$lastFrame = currentFrame;
        this.labyMod$watcher.invalidate();
    }

    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }

    public void setHeight(int height) {
        this.h = height;
    }
}
