package net.labymod.api.client.gui.screen.widget.overlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/ScreenOverlayHandler.class */
@Referenceable
public interface ScreenOverlayHandler {
    void registerOverlay(ScreenOverlay screenOverlay);

    void unregisterOverlay(ScreenOverlay screenOverlay);

    @Nullable
    <T extends ScreenOverlay> T screenOverlay(Class<T> cls);

    <T extends ScreenOverlay> Optional<T> findScreenOverlay(Class<T> cls);

    List<ScreenOverlay> overlays();

    void forEachReversed(Consumer<ScreenOverlay> consumer);

    boolean isOverlayHovered();

    boolean isActive(Function<ScreenOverlay, Boolean> function);

    default WidgetReference displayInOverlay(Widget widget) {
        return displayInOverlay(widget, widget);
    }

    default WidgetReference displayInOverlay(Object linkSource, Widget widget) {
        return displayInOverlay(linkSource.getClass(), widget);
    }

    default WidgetReference displayInOverlay(Class<?> linkSourceClass, Widget widget) {
        List<StyleSheet> stylesheets = new ArrayList<>();
        LinkMetaLoader linkMetaLoader = Laby.references().linkMetaLoader();
        Objects.requireNonNull(stylesheets);
        linkMetaLoader.loadMeta(linkSourceClass, (v1) -> {
            r2.add(v1);
        });
        return displayInOverlay(stylesheets, widget);
    }

    default WidgetReference displayInOverlay(List<StyleSheet> styles, Widget widget) {
        return displayInOverlay(null, styles, widget);
    }

    default WidgetReference displayInOverlay(LabyScreen sourceScreen, List<StyleSheet> styles, Widget widget) {
        WidgetScreenOverlay overlay = (WidgetScreenOverlay) screenOverlay(WidgetScreenOverlay.class);
        if (overlay != null) {
            return overlay.display(sourceScreen, styles, widget);
        }
        return null;
    }
}
