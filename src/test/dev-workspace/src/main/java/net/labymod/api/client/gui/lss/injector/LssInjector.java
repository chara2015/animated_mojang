package net.labymod.api.client.gui.lss.injector;

import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.widget.StyledWidget;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/injector/LssInjector.class */
@Referenceable
public interface LssInjector {
    void registerStyleSheet(@NotNull Object obj, @NotNull String str, @NotNull String str2);

    void registerWidget(@NotNull Object obj, @NotNull String str, @NotNull String str2);

    boolean unregister(@NotNull Object obj, @NotNull String str);

    @NotNull
    List<StyleSheet> getMatchedStyleSheetInjectors(@NotNull StyleSheet styleSheet);

    @NotNull
    List<StyleSheet> getMatchedWidgetInjectors(@NotNull StyledWidget styledWidget);

    @Deprecated
    default void register(@NotNull Object instance, @NotNull String fileName, @NotNull String targetFileName) {
        registerStyleSheet(instance, fileName, targetFileName);
    }
}
