package net.labymod.v1_12_2.mixins.client.gui;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor;
import net.labymod.v1_12_2.client.gui.screen.widget.converter.TextFieldConverter;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiTextField.class */
@Mixin({bje.class})
public abstract class MixinGuiTextField<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, GuiTextFieldAccessor {
    private final WidgetWatcher<K> labyMod$watcher = new WidgetWatcher<>();
    private Consumer<String> labyMod$textConsumer;

    @Shadow
    @Mutable
    @Final
    private int i;

    @Shadow
    @Mutable
    @Final
    private int j;

    @Shadow
    private boolean o;

    @Shadow
    private String k;

    @Shadow
    private boolean q;

    @Shadow
    public int a;

    @Shadow
    public int f;

    @Shadow
    public abstract void b(boolean z);

    @Insert(method = {"drawTextBox"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWidgetWatcher(InsertInfo ci) {
        Minecraft api = Laby.labyAPI().minecraft();
        this.labyMod$watcher.update(this, null);
        boolean rendered = this.labyMod$watcher.render(VersionedStackProvider.DEFAULT_STACK, api.mouse(), api.getTickDelta());
        if (rendered) {
            ci.cancel();
        }
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$mouseClicked(int mouseX, int mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        AbstractWidgetConverter<?, ?> widgetConverter = this.labyMod$watcher.getWidgetConverter();
        if ((widgetConverter instanceof AbstractMinecraftWidgetConverter) && getWatcher().getWidget() != null) {
            AbstractMinecraftWidgetConverter converter = (AbstractMinecraftWidgetConverter) widgetConverter;
            boolean hovered = mouseX >= this.a && mouseX < this.a + this.i && mouseY >= this.f && mouseY < this.f + this.j;
            AbstractWidget widget = getWatcher().getWidget();
            if (this.o) {
                b(hovered);
                widget.setFocused(hovered);
            }
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
                if (mouseButton == null) {
                    return;
                }
                converter.mouseClicked(widget, mouse, mouseButton);
            });
            cir.setReturnValue(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"textboxKeyTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$charTyped(char c, int var0, CallbackInfoReturnable<Boolean> cir) {
        AbstractWidgetConverter<?, ?> widgetConverter = this.labyMod$watcher.getWidgetConverter();
        AbstractWidget<?> widget = getWatcher().getWidget();
        if ((widgetConverter instanceof AbstractMinecraftWidgetConverter) && widget != null) {
            AbstractMinecraftWidgetConverter abstractMinecraftWidgetConverter = (AbstractMinecraftWidgetConverter) widgetConverter;
            Key key = DefaultKeyMapper.lastPressed();
            if (key == null) {
                return;
            }
            InputType inputType = KeyMapper.getInputType(key);
            if (key.isAction() || inputType != InputType.CHARACTER || !g.a(c)) {
                boolean keyPressed = abstractMinecraftWidgetConverter.keyPressed(widget, key, inputType);
                if (keyPressed && (abstractMinecraftWidgetConverter instanceof TextFieldConverter)) {
                    ((TextFieldConverter) abstractMinecraftWidgetConverter).updateText((bje) this, widget);
                }
                cir.setReturnValue(Boolean.valueOf(keyPressed));
                return;
            }
            boolean charTyped = abstractMinecraftWidgetConverter.charTyped(widget, key, c);
            if (charTyped && (abstractMinecraftWidgetConverter instanceof TextFieldConverter)) {
                ((TextFieldConverter) abstractMinecraftWidgetConverter).updateText((bje) this, widget);
            }
            cir.setReturnValue(Boolean.valueOf(charTyped));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor
    public void setWidth(int width) {
        this.i = width;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor
    public void setHeight(int height) {
        this.j = height;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor
    public int getHeight() {
        return this.j;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor
    public boolean isEnabled() {
        return this.q;
    }

    @Inject(method = {"setText"}, at = {@At("HEAD")})
    private void labyMod$callValueConsumer(String value, CallbackInfo ci) {
        if (this.labyMod$textConsumer == null) {
            return;
        }
        this.labyMod$textConsumer.accept(value);
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor
    public void setTextConsumer(Consumer<String> consumer) {
        this.labyMod$textConsumer = consumer;
    }
}
