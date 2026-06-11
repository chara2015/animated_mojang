package net.labymod.core.client.chat;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/ConfirmLinkActivity.class */
@Link("activity/confirm.lss")
@AutoActivity
public class ConfirmLinkActivity extends ConfirmActivity {
    private static final TextColor WARNING_COLOR = TextColor.color(255, 204, 204);
    private final Consumer<LinkType> responseConsumer;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/ConfirmLinkActivity$LinkType.class */
    public enum LinkType {
        OPEN,
        COPY,
        NONE
    }

    public ConfirmLinkActivity(String link, Consumer<LinkType> responseConsumer) {
        super(Component.translatable("chat.link.confirm", new Component[0]).append(Component.newline()).append(Component.newline()).append(Component.text(link)).append(Component.newline()).append(Component.newline()).append(Component.translatable("chat.link.warning", WARNING_COLOR)), Laby.labyAPI().minecraft().getTranslation("gui.yes"), Laby.labyAPI().minecraft().getTranslation("gui.no"), (Consumer<Boolean>) allow -> {
            responseConsumer.accept((allow == null || !allow.booleanValue()) ? LinkType.NONE : LinkType.OPEN);
        });
        this.responseConsumer = responseConsumer;
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity, net.labymod.api.client.gui.screen.activity.types.TitledActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.buttonRow.addEntry(ButtonWidget.text(Laby.labyAPI().minecraft().getTranslation("chat.copy"), () -> {
            this.responseConsumer.accept(LinkType.COPY);
            displayPreviousScreen();
        }));
    }
}
