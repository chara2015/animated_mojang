package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import java.util.Locale;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.service.Identifiable;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/chat/ChatButtonWidget.class */
@AutoWidget
public class ChatButtonWidget extends AbstractWidget<Widget> implements Identifiable {
    private final Supplier<ChatInputTabActivity<?>> supplier;
    private final String id;
    private final Widget widget;
    private final Component defaultHoverComponent;
    private Component currentHoverComponent;

    @Nullable
    private String permissionId;
    private boolean allowed = true;

    @Nullable
    private ConfigProperty<Boolean> enabledProperty;

    private ChatButtonWidget(String id, Widget widget, Supplier<ChatInputTabActivity<?>> supplier) {
        this.supplier = supplier;
        this.widget = widget;
        this.id = id;
        this.defaultHoverComponent = Component.translatable(String.format(Locale.ROOT, "labymod.chatInput.tab.%s.name", this.id), new Component[0]);
        this.currentHoverComponent = this.defaultHoverComponent;
        addId("chat-button");
        addId(id);
        widget.addId("chat-button-entry");
    }

    public static ChatButtonWidget icon(String id, Icon icon, Supplier<ChatInputTabActivity<?>> supplier) {
        return new ChatButtonWidget(id, new IconWidget(icon), supplier);
    }

    public static ChatButtonWidget component(String id, Component component, Supplier<ChatInputTabActivity<?>> supplier) {
        return new ChatButtonWidget(id, ComponentWidget.component(component), supplier);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.widget);
        setHoverComponent(this.currentHoverComponent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        if (!isVisible() || opacity().get().floatValue() <= 0.0f || !isHoverComponentRendered()) {
            return;
        }
        ReasonableMutableRectangle rectangle = bounds().rectangle(BoundsType.OUTER);
        Laby.references().tooltipService().renderFixedTooltip(context, Point.fixed((int) rectangle.getCenterX(), (int) rectangle.getY()), getHoverComponent(), true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverComponent(Component component) {
        super.setHoverComponent(component);
        this.currentHoverComponent = component;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getUniqueId() {
        return this.id;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.id;
    }

    public ScreenInstance createScreen() {
        return this.supplier.get();
    }

    public boolean isEnabled() {
        return this.allowed;
    }

    public ChatButtonWidget setEnabled(boolean enabled) {
        this.allowed = enabled;
        if (enabled) {
            setHoverComponent(this.defaultHoverComponent);
        } else {
            setHoverComponent(Component.translatable("labymod.chatInput.permission.description", NamedTextColor.RED, this.defaultHoverComponent.copy()));
        }
        return this;
    }

    public ChatButtonWidget setPermissionId(@Nullable String permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    @Nullable
    public String getPermissionId() {
        return this.permissionId;
    }

    public ConfigProperty<Boolean> getProperty() {
        return this.enabledProperty;
    }

    public ChatButtonWidget property(ConfigProperty<Boolean> enabledProperty) {
        this.enabledProperty = enabledProperty;
        return this;
    }
}
