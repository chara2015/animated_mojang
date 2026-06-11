package net.labymod.v1_18_2.mixins.client.gui.components;

import java.util.function.BiFunction;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/components/MixinGuiEventListener.class */
@Mixin({ebd.class})
public interface MixinGuiEventListener {
    default boolean handleConvertedWidget(BiFunction<AbstractWidgetConverter, AbstractWidget<?>, Boolean> converterFunction) {
        if (this instanceof ConvertableMinecraftWidget) {
            ConvertableMinecraftWidget<?> convertable = (ConvertableMinecraftWidget) this;
            WidgetWatcher watcher = convertable.getWatcher();
            AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
            if (widgetConverter == null) {
                return false;
            }
            return converterFunction.apply(widgetConverter, watcher.getWidget()).booleanValue();
        }
        return false;
    }

    @Overwrite
    default boolean a(double mouseX, double mouseY, int button) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
                if (mouseButton == null) {
                    return false;
                }
                return converter.mouseClicked(widget, mouse, mouseButton);
            }));
        });
    }

    @Overwrite
    default boolean c(double mouseX, double mouseY, int button) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
                if (mouseButton == null) {
                    return false;
                }
                return converter.mouseReleased(widget, mouse, mouseButton);
            }));
        });
    }

    @Overwrite
    default boolean a(double mouseX, double mouseY, double delta) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return converter.mouseScrolled(widget, mouse, delta);
            }));
        });
    }

    @Overwrite
    default boolean a(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
                if (mouseButton == null) {
                    return false;
                }
                return converter.mouseDragged(widget, mouse, mouseButton, deltaX, deltaY);
            }));
        });
    }

    @Overwrite
    default boolean a(char c, int param1) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(converter.charTyped(widget, DefaultKeyMapper.lastPressed(), c));
        });
    }

    @Overwrite
    default boolean a(int keyCode, int param1, int param2) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastPressed();
            return Boolean.valueOf(converter.keyPressed(widget, key, KeyMapper.getInputType(key)));
        });
    }

    @Overwrite
    default boolean b(int keyCode, int param1, int param2) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastReleased();
            return Boolean.valueOf(converter.keyReleased(widget, key, KeyMapper.getInputType(key)));
        });
    }

    private default Minecraft minecraft() {
        return Laby.labyAPI().minecraft();
    }
}
