package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/SettingOpenActivityWidget.class */
@AutoWidget
@Deprecated
public class SettingOpenActivityWidget extends HorizontalListWidget {
    private final Icon icon;
    private final Component displayName;
    private final Runnable callback;

    public SettingOpenActivityWidget(Icon icon, Component displayName, Runnable callback) {
        this.icon = icon;
        this.displayName = displayName;
        this.callback = callback;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget statusIndicator = new DivWidget();
        statusIndicator.addId("status-indicator");
        addEntry(statusIndicator);
        if (this.icon != null) {
            addEntry(new IconWidget(this.icon));
        }
        addEntry(ComponentWidget.component(this.displayName));
        ButtonWidget advancedButton = ButtonWidget.icon(Textures.SpriteCommon.SETTINGS);
        advancedButton.addId("advanced-button");
        Runnable runnable = this.callback;
        Objects.requireNonNull(runnable);
        advancedButton.setPressable(runnable::run);
        addEntry(advancedButton).alignment().set(HorizontalAlignment.RIGHT);
    }
}
