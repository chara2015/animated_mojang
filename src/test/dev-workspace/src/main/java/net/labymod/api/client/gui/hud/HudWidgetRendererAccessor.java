package net.labymod.api.client.gui.hud;

import java.util.Collection;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/HudWidgetRendererAccessor.class */
public interface HudWidgetRendererAccessor {
    void onVisibilityChanged(HudWidget<?> hudWidget);

    void onSizeChanged(HudWidget<?> hudWidget);

    Rectangle getArea();

    boolean isEditor();

    boolean isDebugEnabled();

    boolean canUpdateHudWidget(HudWidget<?> hudWidget);

    HudWidget<?> getHudWidgetInDropzone(String str);

    Collection<HudWidget<?>> getHudWidgetsInArea(Rectangle rectangle);

    @NotNull
    HudWidgetWidget getWidget(@NotNull HudWidget<?> hudWidget);

    default boolean shouldIgnoreAlignment() {
        return false;
    }

    default void openSettings(HudWidget<?> hudWidget) {
        throw new UnsupportedOperationException("Not implemented");
    }

    default HudWidget<?> getDraggingHudWidget() {
        return null;
    }

    default HudWidget<?> getHudWidgetInDropzone(HudWidgetDropzone dropzone) {
        return getHudWidgetInDropzone(dropzone.getId());
    }

    default boolean isDropzoneInUse(String dropzoneId) {
        return getHudWidgetInDropzone(dropzoneId) != null;
    }

    default boolean isDropzoneInUse(HudWidgetDropzone dropzone) {
        return isDropzoneInUse(dropzone.getId());
    }

    default HudWidget<?> getRelevantHudWidgetForDropzone(HudWidgetDropzone zone) {
        HudWidget<?> dragging;
        HudWidget<?> hudWidget = getHudWidgetInDropzone(zone);
        if (hudWidget != null) {
            return hudWidget;
        }
        if (!shouldIgnoreAlignment() && (dragging = getDraggingHudWidget()) != null && dragging.fitsInDropzone(zone)) {
            return dragging;
        }
        return null;
    }
}
