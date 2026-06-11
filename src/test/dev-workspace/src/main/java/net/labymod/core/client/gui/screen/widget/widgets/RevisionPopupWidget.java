package net.labymod.core.client.gui.screen.widget.widgets;

import java.util.ArrayList;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/RevisionPopupWidget.class */
@AutoWidget
@Link("activity/revision-popup.lss")
public class RevisionPopupWidget extends SimpleWidget {
    private final Icon banner;
    private WidgetReference overlayReference;
    private FlexibleContentWidget containerWidget;

    public RevisionPopupWidget(Icon banner) {
        this.banner = banner;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.containerWidget = new FlexibleContentWidget();
        this.containerWidget.addId("container");
        IconWidget banner = new IconWidget(this.banner);
        banner.addId("banner");
        this.containerWidget.addContent(banner);
        HorizontalListWidget footer = new HorizontalListWidget();
        footer.addId("footer");
        ButtonWidget button = ButtonWidget.component(Component.translatable("labymod.ui.button.close", new Component[0]));
        button.addId("ok");
        button.setPressable(this::close);
        footer.addEntry(button);
        this.containerWidget.addContent(footer);
        addChild(this.containerWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (!this.containerWidget.bounds().isInRectangle(mouse)) {
            close();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ENTER || key == Key.ESCAPE) {
            close();
            return true;
        }
        return super.keyPressed(key, type);
    }

    public void displayInOverlay() {
        this.overlayReference = displayInOverlay(new ArrayList(), this);
        if (this.overlayReference == null) {
            throw new IllegalStateException("Widget overlay not initialized yet");
        }
        this.overlayReference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        this.overlayReference.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
    }

    public void close() {
        if (this.overlayReference != null && this.overlayReference.isAlive()) {
            this.overlayReference.remove();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        close();
        LabyMod.getInstance().getLabyConfig().save();
    }
}
