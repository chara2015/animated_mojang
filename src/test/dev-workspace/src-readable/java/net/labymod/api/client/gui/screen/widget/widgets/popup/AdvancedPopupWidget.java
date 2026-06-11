package net.labymod.api.client.gui.screen.widget.widgets.popup;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/AdvancedPopupWidget.class */
@AutoWidget
@Link(value = "activity/popup.lss", priority = -127)
public class AdvancedPopupWidget extends AbstractWidget<Widget> {
    private final AdvancedPopup popup;

    protected AdvancedPopupWidget(@NotNull AdvancedPopup popup) {
        Objects.requireNonNull(popup, "popup");
        this.popup = popup;
        this.popup.setup(this);
    }

    @NotNull
    public static AdvancedPopupWidget of(@NotNull AdvancedPopup popup) {
        return new AdvancedPopupWidget(popup);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        addId("popup-wrapper");
        super.initialize(parent);
        Widget widgetInitialize = this.popup.initialize();
        if (widgetInitialize == null) {
            return;
        }
        widgetInitialize.addId("popup-container");
        if (this.children.contains(widgetInitialize)) {
            return;
        }
        DivWidget divWidget = new DivWidget();
        divWidget.addId("popup-container-wrapper");
        divWidget.addChild(widgetInitialize);
        this.children.add(divWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        this.popup.tick();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        super.postInitialize();
        Laby.references().linkMetaLoader().loadMeta(this.popup.getClass(), styleSheet -> {
            if (this.styleSheets.contains(styleSheet)) {
                return;
            }
            applyStyleSheet(styleSheet);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        for (T child : this.children) {
            if (child.bounds().isInRectangle(BoundsType.OUTER, mouse)) {
                return false;
            }
        }
        this.popup.close();
        return true;
    }

    @NotNull
    public final AdvancedPopup popup() {
        return this.popup;
    }
}
