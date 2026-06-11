package net.labymod.core.test;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.notification.Notification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/ContextMenuTestActivity.class */
@AutoActivity
@Link("test/context-menu-test.lss")
public class ContextMenuTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ButtonWidget button1 = ButtonWidget.text("Click here");
        ButtonWidget button2 = ButtonWidget.text("Click here");
        fillContextMenu(button1.createContextMenu());
        fillContextMenu(button2.createContextMenu());
        Objects.requireNonNull(button1);
        button1.setPressable(button1::openContextMenu);
        Objects.requireNonNull(button2);
        button2.setPressable(button2::openContextMenu);
        ((Document) this.document).addChild(button1).addId("button-1");
        ((Document) this.document).addChild(button2).addId("button-2");
    }

    private void fillContextMenu(ContextMenu contextMenu) {
        contextMenu.addEntry(createNotificationEntry("Show notification and keep context menu open", false));
        contextMenu.addEntry(createNotificationEntry("Show notification and close context menu", true));
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Open sub menu")).icon(Textures.SpriteCommon.ARROW_RIGHT).subMenu(() -> {
            ContextMenu subMenu = new ContextMenu();
            fillContextMenu(subMenu);
            return subMenu;
        }).build());
    }

    private ContextMenuEntry createNotificationEntry(String text, boolean close) {
        return ContextMenuEntry.builder().text(Component.text(text)).icon(Textures.SpriteCommon.PEPE_SAD).clickHandler(entry -> {
            Laby.references().notificationController().push(Notification.builder().title(Component.text("Test notification")).text(Component.text("Test notification")).icon(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP).duration(5000L).build());
            return close;
        }).build();
    }
}
