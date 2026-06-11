package net.labymod.v26_1_1.mixins.client.gui.components;

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
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/gui/components/MixinGuiEventListener.class */
@Mixin({GuiEventListener.class})
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
    default boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();
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
    default boolean mouseReleased(MouseButtonEvent event) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();
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
    default boolean mouseScrolled(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
        return handleConvertedWidget((converter, widget) -> {
            return Boolean.valueOf(minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return converter.mouseScrolled(widget, mouse, scrollDeltaY);
            }));
        });
    }

    @Overwrite
    default boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();
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
    default boolean charTyped(CharacterEvent event) {
        return handleConvertedWidget((converter, widget) -> {
            boolean flag = false;
            char[] arr$ = Character.toChars(event.codepoint());
            for (char c : arr$) {
                flag |= converter.charTyped(widget, DefaultKeyMapper.lastPressed(), c);
            }
            return Boolean.valueOf(flag);
        });
    }

    @Overwrite
    default boolean keyPressed(KeyEvent event) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastPressed();
            return Boolean.valueOf(converter.keyPressed(widget, key, KeyMapper.getInputType(key)));
        });
    }

    @Overwrite
    default boolean keyReleased(KeyEvent event) {
        return handleConvertedWidget((converter, widget) -> {
            Key key = DefaultKeyMapper.lastReleased();
            return Boolean.valueOf(converter.keyReleased(widget, key, KeyMapper.getInputType(key)));
        });
    }

    private default Minecraft minecraft() {
        return Laby.labyAPI().minecraft();
    }
}
