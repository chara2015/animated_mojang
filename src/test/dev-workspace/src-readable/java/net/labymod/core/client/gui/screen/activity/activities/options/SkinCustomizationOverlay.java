package net.labymod.core.client.gui.screen.activity.activities.options;

import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.screen.activity.activities.NavigationActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/options/SkinCustomizationOverlay.class */
@Link("activity/player/skin-customization.lss")
@AutoActivity
public class SkinCustomizationOverlay extends AbstractLayerActivity {
    public SkinCustomizationOverlay(ScreenInstance parentScreen) {
        super(parentScreen);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ButtonWidget refreshButton = createRefreshButton();
        refreshButton.addId("labymod-refresh-button");
        ((Document) this.document).addChild(refreshButton);
    }

    public static ButtonWidget createRefreshButton() {
        ButtonWidget widget = ButtonWidget.component(Component.translatable("labymod.activity.customization.refresh", new Component[0]));
        if (Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof NavigationActivity) {
            widget.addId("in-navigation");
        } else {
            widget.addId("standalone");
        }
        widget.setActionListener(() -> {
            widget.setEnabled(false);
            Laby.labyAPI().refresh();
            Task.builder(() -> {
                widget.setEnabled(true);
            }).delay(2L, TimeUnit.SECONDS).build().executeOnRenderThread();
        });
        return widget;
    }
}
