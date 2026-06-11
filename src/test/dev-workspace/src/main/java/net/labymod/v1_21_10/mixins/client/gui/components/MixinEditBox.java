package net.labymod.v1_21_10.mixins.client.gui.components;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_21_10.client.gui.EditBoxAccessor;
import net.labymod.v1_21_10.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/components/MixinEditBox.class */
@Mixin({gdy.class})
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor {

    @Shadow
    private boolean t;

    @Shadow
    private boolean u;

    @Shadow
    private xx G;

    @Shadow
    private boolean s;
    private Consumer<String> labyMod$valueConsumer;

    @Shadow
    public abstract void b(boolean z);

    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(gdd graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        if (!this.s) {
            return;
        }
        getWatcher().update(this, ((gdn) this).A());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"onClick"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$mouseClicked(gti event, boolean doubleClick, CallbackInfo ci) {
        WidgetWatcher<?> watcher;
        AbstractWidget widget;
        if (!this.s || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        double mouseX = event.t();
        double mouseY = event.u();
        AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
        int x = ((gdy) this).aT_();
        int y = ((gdy) this).aU_();
        int width = ((gdy) this).aS_();
        int height = ((gdy) this).aR_();
        boolean hovered = mouseX >= ((double) x) && mouseX < ((double) (x + width)) && mouseY >= ((double) y) && mouseY < ((double) (y + height));
        if (this.t) {
            b(hovered);
            widget.setFocused(hovered);
        }
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            widgetConverter.mouseClicked(widget, mouse, DefaultKeyMapper.pressMouse(0));
            ci.cancel();
        });
    }

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$charTyped(gte event, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher;
        AbstractWidget widget;
        if (!this.s || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().charTyped(widget, key, (char) event.c())));
    }

    @Inject(method = {"keyPressed"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$keyPressed(gth event, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher;
        AbstractWidget widget;
        if (!this.s || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().keyPressed(widget, key, KeyMapper.getInputType(key))));
    }

    @Inject(method = {"onValueChange"}, at = {@At("HEAD")})
    private void labyMod$callValueConsumer(String value, CallbackInfo ci) {
        if (!this.s || this.labyMod$valueConsumer == null) {
            return;
        }
        this.labyMod$valueConsumer.accept(value);
    }

    @Override // net.labymod.v1_21_10.client.gui.EditBoxAccessor
    public void setValueConsumer(Consumer<String> valueConsumer) {
        this.labyMod$valueConsumer = valueConsumer;
    }

    @Override // net.labymod.v1_21_10.client.gui.EditBoxAccessor
    public boolean isEditable() {
        return this.u;
    }

    @Override // net.labymod.v1_21_10.client.gui.EditBoxAccessor
    public xx getHint() {
        return this.G;
    }
}
