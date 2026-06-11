package net.labymod.v26_2_snapshot_8.mixins.client.gui.components;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v26_2_snapshot_8.client.gui.EditBoxAccessor;
import net.labymod.v26_2_snapshot_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/components/MixinEditBox.class */
@Mixin({EditBox.class})
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor {

    @Shadow
    private boolean canLoseFocus;

    @Shadow
    private boolean isEditable;

    @Shadow
    private Component hint;

    @Shadow
    private boolean bordered;
    private Consumer<String> labyMod$valueConsumer;

    @Shadow
    public abstract void setFocused(boolean z);

    @Insert(method = {"extractWidgetRenderState"}, at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        if (!this.bordered) {
            return;
        }
        getWatcher().update(this, ((AbstractWidget) this).getMessage());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"onClick"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfo ci) {
        WidgetWatcher<?> watcher;
        net.labymod.api.client.gui.screen.widget.AbstractWidget widget;
        if (!this.bordered || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        double mouseX = event.x();
        double mouseY = event.y();
        AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
        int x = ((EditBox) this).getX();
        int y = ((EditBox) this).getY();
        int width = ((EditBox) this).getWidth();
        int height = ((EditBox) this).getHeight();
        boolean hovered = mouseX >= ((double) x) && mouseX < ((double) (x + width)) && mouseY >= ((double) y) && mouseY < ((double) (y + height));
        if (this.canLoseFocus) {
            setFocused(hovered);
            widget.setFocused(hovered);
        }
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            widgetConverter.mouseClicked(widget, mouse, DefaultKeyMapper.pressMouse(0));
            ci.cancel();
        });
    }

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$charTyped(CharacterEvent event, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher;
        net.labymod.api.client.gui.screen.widget.AbstractWidget widget;
        if (!this.bordered || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().charTyped(widget, key, (char) event.codepoint())));
    }

    @Inject(method = {"keyPressed"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$keyPressed(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher;
        net.labymod.api.client.gui.screen.widget.AbstractWidget widget;
        if (!this.bordered || (widget = (watcher = getWatcher()).getWidget()) == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().keyPressed(widget, key, KeyMapper.getInputType(key))));
    }

    @Inject(method = {"onValueChange"}, at = {@At("HEAD")})
    private void labyMod$callValueConsumer(String value, CallbackInfo ci) {
        if (!this.bordered || this.labyMod$valueConsumer == null) {
            return;
        }
        this.labyMod$valueConsumer.accept(value);
    }

    @Override // net.labymod.v26_2_snapshot_8.client.gui.EditBoxAccessor
    public void setValueConsumer(Consumer<String> valueConsumer) {
        this.labyMod$valueConsumer = valueConsumer;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.gui.EditBoxAccessor
    public boolean isEditable() {
        return this.isEditable;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.gui.EditBoxAccessor
    public Component getHint() {
        return this.hint;
    }
}
