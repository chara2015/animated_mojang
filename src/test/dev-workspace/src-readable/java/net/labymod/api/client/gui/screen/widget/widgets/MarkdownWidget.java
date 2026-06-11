package net.labymod.api.client.gui.screen.widget.widgets;

import java.util.List;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.util.markdown.MarkdownComponent;
import net.labymod.api.util.markdown.MarkdownDocument;
import net.labymod.api.util.markdown.MarkdownEmptyLineComponent;
import net.labymod.api.util.markdown.MarkdownHeaderComponent;
import net.labymod.api.util.markdown.MarkdownRawComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/MarkdownWidget.class */
@AutoWidget
@Link("widget/markdown.lss")
public class MarkdownWidget extends VerticalListWidget<Widget> {
    private final MarkdownDocument document;

    public MarkdownWidget(MarkdownDocument document) {
        this.document = document;
        addId("markdown-container");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        WrappedListWidget<Widget> wrappedListWidget = null;
        List<MarkdownComponent> components = this.document.getComponents();
        int i = 0;
        while (i < components.size()) {
            MarkdownComponent component = components.get(i);
            if (!(component instanceof MarkdownRawComponent) && wrappedListWidget != null) {
                addChild(wrappedListWidget);
                wrappedListWidget = null;
            }
            MarkdownComponent previous = i == 0 ? null : components.get(i - 1);
            if (component instanceof MarkdownHeaderComponent) {
                MarkdownHeaderComponent header = (MarkdownHeaderComponent) component;
                ComponentWidget widget = ComponentWidget.component(Component.text(header.getText()).decorate(TextDecoration.BOLD));
                widget.addId("markdown-header", "markdown-header-" + header.getLevel());
                if (previous instanceof MarkdownRawComponent) {
                    widget.addId("markdown-header-offset");
                }
                addChild(widget);
            } else if (component instanceof MarkdownEmptyLineComponent) {
                DivWidget widget2 = new DivWidget();
                widget2.addId("markdown-empty-line");
                addChild(widget2);
            } else if (component instanceof MarkdownRawComponent) {
                MarkdownRawComponent raw = (MarkdownRawComponent) component;
                AbstractWidget<Widget> container = wrappedListWidget == null ? this : wrappedListWidget;
                if (wrappedListWidget == null && i + 1 < components.size() && (components.get(i + 1) instanceof MarkdownRawComponent)) {
                    wrappedListWidget = new WrappedListWidget<>();
                    wrappedListWidget.addId("markdown-raw-container");
                    container = wrappedListWidget;
                }
                TextComponent text = Component.text(raw.getText());
                if (raw.hasFormatting(MarkdownRawComponent.Formatting.ITALIC)) {
                    text = text.decorate(TextDecoration.ITALIC);
                }
                ComponentWidget widget3 = ComponentWidget.component(text);
                widget3.addId("markdown-text");
                for (MarkdownRawComponent.Formatting formatting : raw.getFormatting()) {
                    widget3.addId("markdown-formatting-" + formatting.name().toLowerCase(Locale.ROOT));
                }
                if (previous instanceof MarkdownHeaderComponent) {
                    if (wrappedListWidget != null) {
                        wrappedListWidget.addId("markdown-raw-offset");
                    } else {
                        widget3.addId("markdown-raw-offset");
                    }
                }
                container.addChild(widget3);
            } else {
                throw new UnsupportedOperationException("Unsupported markdown component: " + component.getClass().getSimpleName());
            }
            i++;
        }
        if (wrappedListWidget != null) {
            addChild(wrappedListWidget);
        }
    }
}
