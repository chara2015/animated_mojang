package net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.client.worldsharing.Worldsharing;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/tabs/WhitelistActivity.class */
@AutoActivity
public class WhitelistActivity extends AbstractPlayerActivity {
    private final Worldsharing worldsharing;

    public WhitelistActivity(Worldsharing worldsharing) {
        this.worldsharing = worldsharing;
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.AbstractPlayerActivity
    protected void buildHeader(HorizontalListWidget header) {
        TextFieldWidget playerInput = (TextFieldWidget) new TextFieldWidget().addId("player-input");
        playerInput.placeholder(Component.translatable("labymod.ui.textfield.username", new Component[0]));
        playerInput.maximalLength(16);
        ButtonWidget addButton = ButtonWidget.icon(Textures.SpriteCommon.ADD);
        addButton.addId("add-button");
        addButton.setActionListener(() -> {
            String playerName = playerInput.getText();
            if (playerName.length() > 1 && playerName.length() <= 16) {
                addButton.setEnabled(false);
                playerInput.setText("");
                this.worldsharing.netEventHandler().setWhitelist(playerName, true, () -> {
                    addButton.setEnabled(true);
                    fillList(this.list, true);
                });
            }
        });
        playerInput.submitHandler(name -> {
            if (name.length() > 1 && name.length() <= 16) {
                playerInput.setText("");
                addButton.setEnabled(false);
                this.worldsharing.netEventHandler().setWhitelist(name, true, () -> {
                    addButton.setEnabled(true);
                    fillList(this.list, true);
                });
            }
        });
        header.addEntry(playerInput);
        header.addEntry(addButton);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.AbstractPlayerActivity
    protected void fillList(VerticalListWidget<Widget> list, boolean initialized) {
        if (list == null) {
            return;
        }
        list.getChildren().clear();
        for (String player : this.worldsharing.netEventHandler().getWhitelist()) {
            if (initialized) {
                list.addChildInitialized(createWhitelistEntry(player));
            } else {
                list.addChild(createWhitelistEntry(player));
            }
        }
    }

    private HorizontalListWidget createWhitelistEntry(String playerName) {
        HorizontalListWidget entry = (HorizontalListWidget) new HorizontalListWidget().addId("entry");
        ButtonWidget removeButton = ButtonWidget.icon(Textures.SpriteCommon.MEDIUM_X_WITH_SHADOW);
        removeButton.addId("remove");
        removeButton.setActionListener(() -> {
            this.worldsharing.netEventHandler().setWhitelist(playerName, false, () -> {
                fillList(this.list, true);
            });
        });
        entry.addEntry(new IconWidget(Icon.head(playerName)));
        ComponentWidget nameWidget = ComponentWidget.text(playerName);
        nameWidget.addId("name");
        entry.addEntry(nameWidget);
        entry.addEntry(removeButton);
        return entry;
    }
}
