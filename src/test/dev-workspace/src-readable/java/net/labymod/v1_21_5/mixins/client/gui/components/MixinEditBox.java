package net.labymod.v1_21_5.mixins.client.gui.components;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_21_5.client.gui.EditBoxAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/gui/components/MixinEditBox.class */
@Mixin({fuh.class})
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor {

    @Shadow
    private boolean s;

    @Shadow
    private boolean u;

    @Shadow
    private xg E;
    private Consumer<String> labyMod$valueConsumer;

    @Shadow
    public abstract void a(boolean z);

    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(ftk graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((ftw) this).B());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"onClick"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$mouseClicked(double mouseX, double mouseY, CallbackInfo ci) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
        int x = ((fuh) this).F();
        int y = ((fuh) this).G();
        int width = ((fuh) this).A();
        int height = ((fuh) this).y();
        boolean hovered = mouseX >= ((double) x) && mouseX < ((double) (x + width)) && mouseY >= ((double) y) && mouseY < ((double) (y + height));
        if (this.s) {
            a(hovered);
            widget.setFocused(hovered);
        }
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            widgetConverter.mouseClicked(widget, mouse, DefaultKeyMapper.pressMouse(0));
            ci.cancel();
        });
    }

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$charTyped(char c, int param1, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().charTyped(widget, key, c)));
    }

    @Inject(method = {"keyPressed"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$keyPressed(int keyCode, int param1, int param2, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().keyPressed(widget, key, KeyMapper.getInputType(key))));
    }

    @Inject(method = {"onValueChange"}, at = {@At("HEAD")})
    private void labyMod$callValueConsumer(String value, CallbackInfo ci) {
        if (this.labyMod$valueConsumer == null) {
            return;
        }
        this.labyMod$valueConsumer.accept(value);
    }

    @Override // net.labymod.v1_21_5.client.gui.EditBoxAccessor
    public void setValueConsumer(Consumer<String> valueConsumer) {
        this.labyMod$valueConsumer = valueConsumer;
    }

    @Override // net.labymod.v1_21_5.client.gui.EditBoxAccessor
    public boolean isEditable() {
        return this.u;
    }

    @Override // net.labymod.v1_21_5.client.gui.EditBoxAccessor
    public xg getHint() {
        return this.E;
    }
}
