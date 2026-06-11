package net.labymod.core.client.gui.embed.content;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.embed.content.EmbeddedContent;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/DefaultEmbeddedContent.class */
public abstract class DefaultEmbeddedContent implements EmbeddedContent {
    protected abstract Widget createWidgetBase();

    @Override // net.labymod.api.client.gui.embed.content.EmbeddedContent
    public final Widget createWidget() {
        Widget widget = createWidgetBase();
        LinkMetaList metaList = Laby.references().linkMetaLoader().getMeta(getClass());
        if (metaList != null) {
            for (LinkReference link : metaList.getLinks()) {
                StyleSheet styleSheet = link.loadStyleSheet();
                if (styleSheet != null) {
                    widget.applyStyleSheet(styleSheet);
                }
            }
        }
        return widget;
    }
}
