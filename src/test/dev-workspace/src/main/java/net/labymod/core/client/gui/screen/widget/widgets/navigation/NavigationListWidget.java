package net.labymod.core.client.gui.screen.widget.widgets.navigation;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.NavigationElementWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.collection.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/navigation/NavigationListWidget.class */
@AutoWidget
@Deprecated
public class NavigationListWidget extends HorizontalListWidget {
    private static final ModifyReason LIST_POSITION = ModifyReason.of("listPosition");
    private final NavigationWrapper wrapper;
    private HorizontalListEntry overflowWidget;
    private ButtonWidget triggerWidget;
    private final LssProperty<HorizontalAlignment> priorityAlignment = new LssProperty<>(null);
    private final LssProperty<Float> maxPadding = new LssProperty<>(Float.valueOf(0.0f));
    private final List<AbstractNavigationElement<?>> overflowElements = Lists.newArrayList();

    public NavigationListWidget(NavigationWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.triggerWidget = ButtonWidget.component(Component.text("..."));
        setContextMenu();
        ButtonWidget buttonWidget = this.triggerWidget;
        ButtonWidget buttonWidget2 = this.triggerWidget;
        Objects.requireNonNull(buttonWidget2);
        buttonWidget.setPressable(buttonWidget2::openContextMenu);
        this.overflowWidget = addEntry(this.triggerWidget);
        this.overflowWidget.addId("overflow-widget");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        setContextMenu();
        for (T child : this.children) {
            Widget widget = child.childWidget();
            if (widget instanceof NavigationElementWidget) {
                NavigationElementWidget elementWidget = (NavigationElementWidget) widget;
                NavigationElement<?> element = elementWidget.getElement();
                if ((elementWidget.getWidget() instanceof ButtonWidget) && (element instanceof ScreenNavigationElement)) {
                    ScreenNavigationElement screenNavigationElement = (ScreenNavigationElement) element;
                    if (this.wrapper.isActive(screenNavigationElement) && this.overflowElements.contains(screenNavigationElement)) {
                        this.triggerWidget.updateIcon(((ButtonWidget) elementWidget.getWidget()).icon().get());
                        return;
                    }
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget
    public HorizontalListEntry addEntry(Widget widget) {
        return super.addEntry(widget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        HorizontalAlignment priorityAlignment = this.priorityAlignment.get();
        if (priorityAlignment == null) {
            if (this.overflowWidget != null) {
                this.overflowWidget.setVisible(false);
            }
            super.updateContentBounds();
            return;
        }
        if (this.overflowWidget != null) {
            this.overflowWidget.alignment().set(priorityAlignment == HorizontalAlignment.RIGHT ? HorizontalAlignment.LEFT : HorizontalAlignment.CENTER);
        }
        updateContentBounds(priorityAlignment);
        for (HorizontalAlignment alignment : HorizontalAlignment.VALUES) {
            if (alignment != priorityAlignment) {
                updateContentBounds(alignment);
            }
        }
        super.updateContentBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        context.canvas().nextLayer();
        super.render(context);
    }

    private void setContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        for (AbstractNavigationElement<?> element : this.overflowElements) {
            ContextMenuEntry.Builder builder = ContextMenuEntry.builder();
            builder.icon(element.getIcon());
            builder.text(element.getDisplayName());
            if (element instanceof ScreenNavigationElement) {
                ScreenNavigationElement screenNavigationElement = (ScreenNavigationElement) element;
                builder.disabled(() -> {
                    return this.wrapper.isActive(screenNavigationElement);
                });
                builder.clickHandler(entry -> {
                    this.wrapper.displayScreen(screenNavigationElement);
                    return true;
                });
            }
            contextMenu.addEntry(builder.build());
        }
        this.triggerWidget.setContextMenu(contextMenu);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget
    protected void updateContentBounds(HorizontalAlignment alignment) {
        HorizontalAlignment priorityAlignment = this.priorityAlignment.get();
        if (priorityAlignment == null || alignment == HorizontalAlignment.NONE || alignment == HorizontalAlignment.RIGHT) {
            super.updateContentBounds(alignment);
            return;
        }
        if (priorityAlignment == HorizontalAlignment.CENTER && alignment == HorizontalAlignment.CENTER) {
            priorityCenter();
        } else if (priorityAlignment == HorizontalAlignment.RIGHT && alignment == HorizontalAlignment.LEFT) {
            priorityRight();
        } else {
            super.updateContentBounds(alignment);
        }
    }

    private void priorityCenter() {
        float padding;
        float spaceLeft = spaceLeft().get().floatValue();
        Bounds bounds = bounds();
        float width = (bounds.getWidth(BoundsType.INNER) - spaceLeft) - spaceRight().get().floatValue();
        float height = bounds.getHeight(BoundsType.INNER);
        int children = 0;
        float minWidth = 0.0f;
        Iterator it = this.children.iterator();
        while (it.hasNext()) {
            Widget widget = getWidgetFromEntry((HorizontalListEntry) it.next(), HorizontalAlignment.CENTER);
            if (widget != null) {
                children++;
                float childWidth = widget.getContentWidth(BoundsType.INNER);
                if (minWidth < childWidth) {
                    minWidth = childWidth;
                }
            }
        }
        float minWidth2 = minWidth == 0.0f ? width / children : minWidth;
        if (minWidth2 == 0.0f) {
            return;
        }
        float maxPadding = this.maxPadding.get().floatValue();
        float spaceBetweenEntries = spaceBetweenEntries().get().intValue();
        float f = 0.0f;
        while (true) {
            padding = f;
            if (padding >= maxPadding || (minWidth2 + spaceBetweenEntries + (padding * 2.0f)) * children >= width) {
                break;
            } else {
                f = padding + 1.0f;
            }
        }
        float preTotalWidth = 0.0f;
        for (int i = 0; i < children; i++) {
            preTotalWidth += minWidth2 + spaceBetweenEntries + (padding * 2.0f);
        }
        float totalWidth = 0.0f;
        float entryWidth = minWidth2 + (padding * 2.0f);
        float x = bounds.getX() + spaceLeft + ((width - preTotalWidth) / 2.0f);
        float y = bounds.getY();
        for (T entry : this.children) {
            if (getWidgetFromEntry(entry, HorizontalAlignment.CENTER) != null) {
                float entryY = y;
                Bounds childBounds = entry.bounds();
                switch (entry.alignmentY().get()) {
                    case CENTER:
                        entryY += (height / 2.0f) - (childBounds.getHeight(BoundsType.OUTER) / 2.0f);
                        break;
                    case BOTTOM:
                        entryY += height - childBounds.getHeight(BoundsType.OUTER);
                        break;
                }
                float entryX = x + totalWidth;
                childBounds.setMiddleLeft(useFloatingPointPosition().get().booleanValue() ? entryX : (int) entryX, LIST_POSITION);
                childBounds.setMiddleTop(useFloatingPointPosition().get().booleanValue() ? entryY : (int) entryY, LIST_POSITION);
                childBounds.setMiddleWidth(entryWidth, LIST_POSITION);
                childBounds.setMiddleHeight(height, LIST_POSITION);
                entry.updateBounds();
                totalWidth += entryWidth + spaceBetweenEntries;
            }
        }
    }

    private AbstractWidget<?> getWidgetFromEntry(HorizontalListEntry entry, HorizontalAlignment alignment) {
        Widget widget = entry.childWidget();
        if ((widget instanceof AbstractWidget) && entry != this.overflowWidget) {
            if ((!((AbstractWidget) widget).visible().get().booleanValue() && !widget.hasId("overflown")) || entry.ignoreWidth().get().booleanValue() || entry.alignment().get() != alignment) {
                return null;
            }
            return (AbstractWidget) widget;
        }
        return null;
    }

    private void priorityRight() {
        Bounds bounds = bounds();
        float used = getTotalWidth(HorizontalAlignment.RIGHT, spaceBetweenEntries().get().intValue());
        float spaceLeft = spaceLeft().get().floatValue();
        float width = ((bounds.getWidth(BoundsType.INNER) - spaceLeft) - spaceRight().get().floatValue()) - used;
        float height = bounds.getHeight(BoundsType.INNER);
        float x = bounds.getX(BoundsType.INNER) + spaceLeft;
        float y = bounds.getY(BoundsType.INNER);
        float spaceBetweenEntries = spaceBetweenEntries().get().intValue();
        float totalWidth = 0.0f;
        float overflowWidth = (this.overflowWidget == null || this.overflowElements.isEmpty()) ? 0.0f : this.overflowWidget.bounds().getWidth(BoundsType.OUTER) + spaceBetweenEntries;
        boolean overflow = false;
        this.overflowElements.clear();
        for (T entry : this.children) {
            AbstractWidget<?> widget = getWidgetFromEntry(entry, HorizontalAlignment.LEFT);
            if (widget != null) {
                float entryWidth = entry.bounds().getWidth(BoundsType.OUTER);
                if (totalWidth + entryWidth + spaceBetweenEntries + overflowWidth > width || overflow) {
                    overflow = true;
                    widget.opacity().set(Float.valueOf(0.0f));
                    if (widget instanceof NavigationElementWidget) {
                        NavigationElementWidget element = (NavigationElementWidget) widget;
                        NavigationElement<?> navigationElement = element.getElement();
                        if (navigationElement instanceof AbstractNavigationElement) {
                            this.overflowElements.add((AbstractNavigationElement) navigationElement);
                        }
                    }
                } else {
                    float entryY = y;
                    switch (entry.alignmentY().get()) {
                        case CENTER:
                            entryY += (height / 2.0f) - (entry.bounds().getHeight(BoundsType.OUTER) / 2.0f);
                            break;
                        case BOTTOM:
                            entryY += height - entry.bounds().getHeight(BoundsType.OUTER);
                            break;
                    }
                    float entryX = x + totalWidth;
                    entry.bounds().setOuterPosition(useFloatingPointPosition().get().booleanValue() ? entryX : (int) entryX, useFloatingPointPosition().get().booleanValue() ? entryY : (int) entryY, LIST_POSITION);
                    totalWidth += entryWidth + spaceBetweenEntries;
                    widget.opacity().set(Float.valueOf(1.0f));
                }
            }
        }
        float totalWidth2 = totalWidth - spaceBetweenEntries;
        if (this.overflowWidget != null) {
            if (overflow) {
                this.overflowWidget.setVisible(true);
                float entryY2 = y;
                switch (this.overflowWidget.alignmentY().get()) {
                    case CENTER:
                        entryY2 += (height / 2.0f) - (this.overflowWidget.bounds().getHeight(BoundsType.OUTER) / 2.0f);
                        break;
                    case BOTTOM:
                        entryY2 += height - this.overflowWidget.bounds().getHeight(BoundsType.OUTER);
                        break;
                }
                float entryX2 = x + totalWidth2 + spaceBetweenEntries;
                this.overflowWidget.bounds().setOuterPosition(useFloatingPointPosition().get().booleanValue() ? entryX2 : (int) entryX2, useFloatingPointPosition().get().booleanValue() ? entryY2 : (int) entryY2, LIST_POSITION);
                return;
            }
            this.overflowWidget.setVisible(false);
        }
    }

    public LssProperty<HorizontalAlignment> priorityAlignment() {
        return this.priorityAlignment;
    }

    public LssProperty<Float> maxPadding() {
        return this.maxPadding;
    }
}
