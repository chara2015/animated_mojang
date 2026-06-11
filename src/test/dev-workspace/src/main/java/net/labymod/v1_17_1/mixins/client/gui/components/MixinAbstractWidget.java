package net.labymod.v1_17_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/components/MixinAbstractWidget.class */
@Mixin({dwy.class})
public abstract class MixinAbstractWidget<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, AbstractWidgetAccessor {

    @Shadow
    protected int j;

    @Shadow
    protected int k;

    @Shadow
    public int l;

    @Shadow
    public int m;

    @Shadow
    private os a;
    private final WidgetWatcher<K> labyMod$watcher = new WidgetWatcher<>();

    @Shadow
    protected abstract void d(boolean z);

    @Insert(method = {"renderButton"}, at = @At("HEAD"), cancellable = true)
    public void render(dql stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$watcher.update(this, this.a);
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = this.labyMod$watcher.render(((VanillaStackAccessor) stack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> ci) {
        AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget == null) {
            return;
        }
        boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, (float) mouseX, (float) mouseY);
        if (!widget.isVisible() || !widget.pressable().get().booleanValue() || !isInBounds) {
            ci.setReturnValue(false);
        } else if (this.labyMod$watcher.hasReplacement() && isInBounds) {
            widget.onPress();
            ci.setReturnValue(true);
        }
    }

    @Inject(method = {"playDownSound"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$playCustomDownSound(fbd soundManager, CallbackInfo ci) {
        AbstractWidget widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }

    @Override // net.labymod.core.client.accessor.gui.AbstractWidgetAccessor
    public void setHeight(int height) {
        this.k = height;
    }
}
