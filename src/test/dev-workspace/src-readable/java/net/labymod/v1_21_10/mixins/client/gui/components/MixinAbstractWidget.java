package net.labymod.v1_21_10.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/components/MixinAbstractWidget.class */
@Mixin({gdn.class})
public abstract class MixinAbstractWidget<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, AbstractWidgetAccessor {

    @Shadow
    protected int f;

    @Shadow
    protected int g;
    private final WidgetWatcher<K> labyMod$watcher = new WidgetWatcher<>();
    private int labyMod$lastFrame;

    @Shadow
    public abstract boolean b();

    @Shadow
    public abstract boolean C();

    @Shadow
    public abstract int aT_();

    @Shadow
    public abstract int aU_();

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$mouseClicked(gti event, boolean doubleClick, CallbackInfoReturnable<Boolean> ci) {
        AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget == null) {
            return;
        }
        double mouseX = event.t();
        double mouseY = event.u();
        boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, (float) mouseX, (float) mouseY);
        if (!widget.isVisible() || !widget.pressable().get().booleanValue() || !isInBounds) {
            ci.setReturnValue(false);
        } else if (this.labyMod$watcher.hasReplacement() && isInBounds) {
            widget.onPress();
            ci.setReturnValue(true);
        }
    }

    @Inject(method = {"playDownSound"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$playCustomDownSound(ihq soundManager, CallbackInfo ci) {
        AbstractWidget widget = this.labyMod$watcher.getWidget();
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
        if (this.f != width) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setHeight"}, at = {@At("HEAD")})
    private void labyMod$invalidateHeight(int height, CallbackInfo ci) {
        if (this.g != height) {
            labyMod$invalidate();
        }
    }

    @Inject(method = {"setSize"}, at = {@At("HEAD")})
    private void labyMod$invalidateSize(int width, int height, CallbackInfo ci) {
        if (this.f != width || this.g != height) {
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

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }

    @Override // net.labymod.core.client.accessor.gui.AbstractWidgetAccessor
    public void setHeight(int height) {
        this.g = height;
    }
}
