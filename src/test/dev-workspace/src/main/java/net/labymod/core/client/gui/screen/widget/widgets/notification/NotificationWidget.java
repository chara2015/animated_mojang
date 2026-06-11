package net.labymod.core.client.gui.screen.widget.widgets.notification;

import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/notification/NotificationWidget.class */
@AutoWidget
public class NotificationWidget extends SimpleWidget {
    private final Notification notification;
    private int swipeStartPosition = -1;
    private long swipeTimePassed = 0;
    private float swipeVelocity = 0.0f;
    private float swipeDistance = 0.0f;

    public NotificationWidget(Notification notification) {
        this.notification = notification;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Notification";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("container");
        Icon icon = this.notification.icon();
        if (icon != null) {
            IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("icon");
            Icon secondaryIcon = this.notification.secondaryIcon();
            if (secondaryIcon != null) {
                IconWidget secondaryIconWidget = new IconWidget(secondaryIcon);
                secondaryIconWidget.addId("secondary-icon");
                iconWidget.addChild(secondaryIconWidget);
            }
            flexibleContentWidget.addContent(iconWidget);
        }
        FlexibleContentWidget rows = new FlexibleContentWidget();
        rows.addId("rows");
        Component title = this.notification.title();
        if (title != null) {
            if (this.notification.getIndex() > 0) {
                title = title.append(Component.text(" (" + (this.notification.getIndex() + 1) + ")", NamedTextColor.YELLOW));
            }
            ComponentWidget titleWidget = ComponentWidget.component(title);
            titleWidget.addId("title");
            rows.addContent(titleWidget);
        }
        Component text = this.notification.text();
        if (text != null) {
            ComponentWidget textWidget = ComponentWidget.component(text);
            textWidget.addId("text");
            rows.addContent(textWidget);
        }
        Icon thumbnail = this.notification.thumbnail();
        if (thumbnail != null) {
            IconWidget thumbnailWidget = new IconWidget(thumbnail);
            thumbnailWidget.addId("thumbnail");
            rows.addContent(thumbnailWidget);
        }
        FlexibleContentWidget buttons = new FlexibleContentWidget();
        buttons.addId("buttons");
        for (Notification.NotificationButton button : this.notification.buttons()) {
            SimpleButtonWidget buttonWidget = new SimpleButtonWidget(button.text());
            buttonWidget.addId("button");
            if (button.hoverText() != null) {
                buttonWidget.setHoverComponent(button.hoverText());
            }
            buttonWidget.setPressable(() -> {
                button.action().run();
                this.notification.remove();
            });
            buttons.addContent(buttonWidget);
        }
        rows.addContent(buttons);
        flexibleContentWidget.addFlexibleContent(rows);
        IconWidget closeIcon = new IconWidget(Textures.SpriteCommon.SMALL_X);
        closeIcon.addId("close-button");
        Notification notification = this.notification;
        Objects.requireNonNull(notification);
        closeIcon.setPressable(notification::remove);
        flexibleContentWidget.addContent(closeIcon);
        addChild(flexibleContentWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        double offset;
        if (isHovered()) {
            this.notification.holdProgress();
        } else {
            this.notification.resumeProgress();
        }
        if (this.notification.isFadingOut()) {
            this.swipeTimePassed = (long) (this.swipeTimePassed + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
            offset = Math.min(this.swipeDistance + ((this.swipeVelocity * this.swipeTimePassed) / 300.0f), bounds().getWidth());
        } else {
            offset = Math.max(this.swipeDistance, 0.0f);
        }
        Stack stack = context.stack();
        stack.push();
        stack.translate((float) offset, 0.0f, 0.0f);
        super.render(context);
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.notification.isFadingOut()) {
            return false;
        }
        this.swipeStartPosition = mouse.getX();
        this.swipeVelocity = 0.0f;
        super.mouseClicked(mouse, mouseButton);
        return true;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (this.notification.isFadingOut()) {
            return false;
        }
        if (isDragging()) {
            this.swipeDistance = mouse.getX() - this.swipeStartPosition;
            this.swipeVelocity = (float) Math.max(this.swipeVelocity, deltaX);
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (this.swipeDistance > 0.0f && this.notification.isAlive() && !this.notification.isFadingOut()) {
            this.swipeTimePassed = 0L;
            if (this.swipeDistance > 10.0f) {
                this.notification.remove();
                return true;
            }
            this.swipeDistance = 0.0f;
            return true;
        }
        if (isHovered() && !this.notification.isFading()) {
            return this.notification.click();
        }
        return super.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean canHover() {
        return !this.notification.isFadingOut();
    }

    public Notification notification() {
        return this.notification;
    }
}
