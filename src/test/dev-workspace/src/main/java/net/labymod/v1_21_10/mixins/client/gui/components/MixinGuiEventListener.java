package net.labymod.v1_21_10.mixins.client.gui.components;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/components/MixinGuiEventListener.class */
@Mixin({ggw.class})
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
    default boolean a(gti event, boolean doubleClick) {
        double mouseX = event.t();
        double mouseY = event.u();
        int button = event.s();
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
    default boolean a(gti event) {
        double mouseX = event.t();
        double mouseY = event.u();
        int button = event.s();
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
    default boolean a(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return converter.mouseScrolled(widget, mouse, scrollDeltaY);
            }));
        });
    }

    @Overwrite
    default boolean a(gti event, double deltaX, double deltaY) {
        double mouseX = event.t();
        double mouseY = event.u();
        int button = event.s();
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
    default boolean a(gte event) {
        return handleConvertedWidget((converter, widget) -> {
            boolean flag = false;
            char[] arr$ = Character.toChars(event.c());
            for (char c : arr$) {
                flag |= converter.charTyped(widget, DefaultKeyMapper.lastPressed(), c);
            }
            return Boolean.valueOf(flag);
        });
    }

    @Overwrite
    default boolean a(gth event) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastPressed();
            return Boolean.valueOf(converter.keyPressed(widget, key, KeyMapper.getInputType(key)));
        });
    }

    @Overwrite
    default boolean b(gth event) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastReleased();
            return Boolean.valueOf(converter.keyReleased(widget, key, KeyMapper.getInputType(key)));
        });
    }

    private default Minecraft minecraft() {
        return Laby.labyAPI().minecraft();
    }
}
