package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import java.util.Iterator;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotContainerWidget.class */
@AutoWidget
public class ScreenshotContainerWidget extends AbstractWidget<ScreenshotSectionWidget> {
    private static final ModifyReason UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    private final ScreenshotTimelineWidget timelineWidget;

    public ScreenshotContainerWidget(ScreenshotTimelineWidget timelineWidget) {
        this.timelineWidget = timelineWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateLayout(true);
    }

    public void updateLayout(boolean purgeCache) {
        Rectangle timelineRectangle = bounds().rectangle(BoundsType.INNER);
        if (timelineRectangle.getWidth() == 0.0f) {
            return;
        }
        float x = timelineRectangle.getX();
        float y = timelineRectangle.getY() - this.timelineWidget.getScrollOffset();
        for (T widget : this.children) {
            widget.bounds().setPosition(x, y, UPDATE_LAYOUT_REASON);
            y += widget.getContentHeight(BoundsType.OUTER) + 5.0f;
        }
        Iterator it = this.children.iterator();
        while (it.hasNext()) {
            ((ScreenshotSectionWidget) it.next()).updateLayout(purgeCache);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        if (bounds().getWidth() == 0.0f) {
            return 0.0f;
        }
        float height = 0.0f;
        for (T widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + 5.0f;
        }
        return height;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public ScreenshotSectionWidget addChildInitialized(ScreenshotSectionWidget widget) {
        super.addChildInitialized(widget);
        updateLayout(true);
        return widget;
    }

    public ScreenshotSectionWidget getCurrentSectionWidget() {
        ScreenshotSectionWidget target = null;
        for (T widget : this.children) {
            target = widget;
            if (widget.bounds().getBottom() >= bounds().getTop()) {
                break;
            }
        }
        return target;
    }

    public float getOffsetOfSection(ScreenshotSection section) {
        float height = 0.0f;
        for (T widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + 5.0f;
            if (widget.section() == section) {
                break;
            }
        }
        return height;
    }

    public ScreenshotSectionWidget getSectionWidget(ScreenshotSection section) {
        for (T widget : this.children) {
            if (widget.section() == section) {
                return widget;
            }
        }
        return null;
    }

    public ScreenshotSectionWidget getSectionAtOffset(float offset) {
        float height = 0.0f;
        for (T widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + 5.0f;
            if (height >= offset) {
                return widget;
            }
        }
        if (this.children.isEmpty()) {
            return null;
        }
        return (ScreenshotSectionWidget) this.children.get(this.children.size() - 1);
    }

    public void purgeCache() {
        for (T widget : this.children) {
            widget.purgeCache();
        }
    }

    public float getOffsetOfCurrentSection() {
        ScreenshotSectionWidget widget = getCurrentSectionWidget();
        if (widget == null) {
            return 0.0f;
        }
        return getOffsetOfSection(widget.section());
    }

    public ScreenshotTileWidget getTileWidget(Screenshot screenshot) {
        ScreenshotSectionWidget sectionWidget;
        ScreenshotSection section = timelineWidget().holder().browser().getSectionOf(screenshot);
        if (section == null || (sectionWidget = getSectionWidget(section)) == null) {
            return null;
        }
        return sectionWidget.getTileWidget(screenshot);
    }

    public ScreenshotTimelineWidget timelineWidget() {
        return this.timelineWidget;
    }
}
