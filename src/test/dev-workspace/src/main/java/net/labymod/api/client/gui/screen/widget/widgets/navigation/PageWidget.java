package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/PageWidget.class */
@AutoWidget
public class PageWidget extends AbstractWidget<ButtonWidget> {
    private final Component component;
    private final ScreenInstance screen;
    private final ButtonWidget button;

    private PageWidget(Component component, ScreenInstance screen) {
        this.component = component;
        this.screen = screen;
        this.button = ButtonWidget.component(this.component);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.button);
        setActive(isActive());
    }

    public ScreenInstance getScreen() {
        return this.screen;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float max = 0.0f;
        for (T child : this.children) {
            max = Math.max(child.bounds().getWidth(type), max);
        }
        return Math.max(max, getDefaultContentWidth(type));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float max = 0.0f;
        for (T child : this.children) {
            max = Math.max(child.bounds().getHeight(type), max);
        }
        return Math.max(max, getDefaultContentHeight(type));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void setActive(boolean active) {
        super.setActive(active);
        this.button.setEnabled(!active);
        for (ButtonWidget child : getGenericChildren()) {
            child.setActive(active);
            child.setEnabled(!active);
        }
    }

    public static PageWidget i18n(String key, ScreenInstance screen) {
        return component(Component.translatable(key, new Component[0]), screen);
    }

    public static PageWidget text(String string, ScreenInstance screen) {
        return component(Component.text(string), screen);
    }

    public static PageWidget component(Component component, ScreenInstance screen) {
        return new PageWidget(component, screen);
    }
}
