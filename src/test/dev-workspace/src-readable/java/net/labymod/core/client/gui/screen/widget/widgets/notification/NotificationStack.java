package net.labymod.core.client.gui.screen.widget.widgets.notification;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/notification/NotificationStack.class */
@AutoWidget
public class NotificationStack extends VerticalListWidget<NotificationWidget> {
    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        float shiftY = 0.0f;
        for (T widget : this.children) {
            shiftY -= updateShift(widget, widget.notification(), shiftY);
        }
        setVisible(!this.children.isEmpty());
        super.render(context);
    }

    private float updateShift(NotificationWidget widget, Notification notification, float progressY) {
        float f;
        float timeAlive = notification.timeAlive();
        float duration = notification.duration();
        float progressIn = 1.0f - (timeAlive / 800.0f);
        float progressOut = 1.0f - ((duration - timeAlive) / 800.0f);
        if (timeAlive >= 0.0f && timeAlive <= 800.0f) {
            f = progressIn;
        } else if (timeAlive < duration - 800.0f || timeAlive > duration) {
            f = timeAlive > duration ? 1.0f : 0.0f;
        } else {
            f = progressOut;
        }
        float progressX = f;
        float x = widget.bounds().getWidth(BoundsType.OUTER) * MathHelper.sigmoid(progressX) * 2.0f;
        widget.translateX().set(Float.valueOf(x));
        widget.translateY().set(Float.valueOf(progressY));
        return progressX * widget.bounds().getHeight(BoundsType.OUTER);
    }

    public void push(Notification notification, boolean fadeIn) {
        NotificationWidget widget = new NotificationWidget(notification);
        if (fadeIn) {
            addChildAsync(widget);
        } else {
            addChild(widget);
        }
    }

    public void pop(Notification notification) {
        removeChildIf(NotificationWidget.class, notificationWidget -> {
            return notificationWidget.notification().equals(notification);
        });
    }

    public void update(Notification notification) {
        NotificationWidget widget = (NotificationWidget) CollectionHelper.get(this.children, n -> {
            return n.notification().equals(notification);
        });
        if (widget != null) {
            removeChild(widget);
            push(notification, true);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean canHover() {
        for (T child : this.children) {
            if (child.canHover()) {
                return true;
            }
        }
        return false;
    }
}
