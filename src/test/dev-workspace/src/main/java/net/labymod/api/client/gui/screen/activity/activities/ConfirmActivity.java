package net.labymod.api.client.gui.screen.activity.activities;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.TitledActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/ConfirmActivity.class */
@Link("activity/confirm.lss")
@AutoActivity
public class ConfirmActivity extends TitledActivity {
    private final Component confirmText;
    private final Component cancelText;
    private final Consumer<Boolean> responseConsumer;
    private boolean fired;
    protected HorizontalListWidget buttonRow;

    public ConfirmActivity(Component title, Consumer<Boolean> responseConsumer) {
        this(title, Component.translatable("gui.yes", new Component[0]), Component.translatable("gui.no", new Component[0]), responseConsumer);
    }

    @ApiStatus.ScheduledForRemoval
    @Deprecated
    public ConfirmActivity(Component title, String confirmText, String cancelText, Consumer<Boolean> responseConsumer) {
        this(title, Component.text(confirmText), Component.text(cancelText), responseConsumer);
    }

    private ConfirmActivity(Component title, Component confirmText, Component cancelText, Consumer<Boolean> responseConsumer) {
        super(title);
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.responseConsumer = responseConsumer;
    }

    public static void confirm(Component title, Consumer<Boolean> responseConsumer) {
        ConfirmActivity activity = new ConfirmActivity(title, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }

    @ApiStatus.ScheduledForRemoval
    @Deprecated
    public static void confirm(Component title, String confirmText, String cancelText, Consumer<Boolean> responseConsumer) {
        ConfirmActivity activity = new ConfirmActivity(title, confirmText, cancelText, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }

    public static void confirm(Component title, Component confirmText, Component cancelText, Consumer<Boolean> responseConsumer) {
        ConfirmActivity activity = new ConfirmActivity(title, confirmText, cancelText, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.TitledActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.buttonRow = new HorizontalListWidget();
        this.buttonRow.addId("button-row");
        this.buttonRow.addEntry(ButtonWidget.component(this.confirmText, () -> {
            clicked(true);
        }));
        this.buttonRow.addEntry(ButtonWidget.component(this.cancelText, () -> {
            clicked(false);
        }));
        ((Document) this.document).addChild(this.buttonRow);
    }

    private void clicked(boolean accepted) {
        accept(Boolean.valueOf(accepted));
        super.displayPreviousScreen();
    }

    private void accept(Boolean response) {
        if (!this.fired) {
            this.fired = true;
            this.responseConsumer.accept(response);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        accept(null);
    }
}
