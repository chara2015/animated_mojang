package net.labymod.api.client.render.font;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/ComponentFormatter.class */
public interface ComponentFormatter {
    ComponentFormatter lineSpacing(float f);

    ComponentFormatter overflow(TextOverflowStrategy textOverflowStrategy);

    ComponentFormatter maxWidth(float f);

    ComponentFormatter alignment(HorizontalAlignment horizontalAlignment);

    ComponentFormatter disableCache();

    ComponentFormatter leadingSpaces(boolean z);

    ComponentFormatter maxLines(int i);

    ComponentFormatter maxLinesClipText(boolean z);

    ComponentFormatter useChatOptions(boolean z);

    RenderableComponent format(Component component);

    default ComponentFormatter cache(boolean cache) {
        if (!cache) {
            disableCache();
        }
        return this;
    }

    default ComponentFormatter keepLeadingSpaces() {
        return leadingSpaces(true);
    }

    default ComponentFormatter useChatOptions() {
        return useChatOptions(true);
    }
}
