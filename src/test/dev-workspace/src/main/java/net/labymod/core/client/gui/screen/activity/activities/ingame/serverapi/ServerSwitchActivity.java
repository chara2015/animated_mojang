package net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.serverapi.core.model.supplement.ServerSwitchPrompt;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/serverapi/ServerSwitchActivity.class */
@AutoActivity
@Link("activity/overlay/server-switch.lss")
public class ServerSwitchActivity extends Activity {
    private final Component title;
    private final String address;
    private final boolean preview;
    private final ServerData currentServerData;
    private final BooleanConsumer acceptConsumer;

    public ServerSwitchActivity(Component title, String address, boolean preview, ServerData currentServerData, BooleanConsumer acceptConsumer) {
        this.title = title;
        this.address = address;
        this.preview = preview;
        this.currentServerData = currentServerData;
        this.acceptConsumer = acceptConsumer;
    }

    public ServerSwitchActivity(ServerSwitchPrompt prompt, ServerData currentServerData, BooleanConsumer acceptConsumer) {
        this(Laby.references().labyModProtocolService().mapComponent(prompt.title()), prompt.getAddress(), prompt.isShowPreview(), currentServerData, acceptConsumer);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget wrapper = new DivWidget();
        wrapper.addId("wrapper");
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        container.addId("container");
        ComponentWidget titleWidget = ComponentWidget.component(this.title);
        titleWidget.addId("title");
        container.addChild(titleWidget);
        ComponentWidget warningWidget = ComponentWidget.i18n("labymod.activity.serverSwitch.warning", this.address);
        warningWidget.addId("warning");
        container.addChild(warningWidget);
        CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
        HorizontalListWidget buttonWrapper = new HorizontalListWidget();
        buttonWrapper.addId("button-wrapper");
        ButtonWidget stayButton = ButtonWidget.i18n("labymod.activity.serverSwitch.deny");
        stayButton.setPressable(() -> {
            submit(checkBoxWidget, false);
        });
        buttonWrapper.addEntry(stayButton);
        ButtonWidget connectButton = ButtonWidget.i18n("labymod.activity.serverSwitch.accept");
        connectButton.setPressable(() -> {
            submit(checkBoxWidget, true);
        });
        buttonWrapper.addEntry(connectButton);
        container.addChild(buttonWrapper);
        HorizontalListWidget checkBoxWrapper = new HorizontalListWidget();
        checkBoxWrapper.addId("checkbox-wrapper");
        checkBoxWrapper.addEntry(checkBoxWidget);
        ComponentWidget checkBoxLabel = ComponentWidget.i18n("labymod.activity.serverSwitch.remember", this.currentServerData.address().toString(), this.address);
        Objects.requireNonNull(checkBoxWidget);
        checkBoxLabel.setPressable(checkBoxWidget::onPress);
        checkBoxWrapper.addEntry(checkBoxLabel);
        container.addChild(checkBoxWrapper);
        wrapper.addChild(container);
        ((Document) this.document).addChild(wrapper);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ESCAPE) {
            submit(null, false);
            return true;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        return true;
    }

    private void submit(CheckBoxWidget rememberCheckBox, boolean connect) {
        if (rememberCheckBox != null && rememberCheckBox.state() == CheckBoxWidget.State.CHECKED) {
            Map<String, Boolean> serverSwitchChoices = LabyConfigProvider.INSTANCE.get().other().serverSwitchChoices().get();
            serverSwitchChoices.put(this.currentServerData.actualAddress().toString() + "#" + this.address, Boolean.valueOf(connect));
        }
        this.acceptConsumer.accept(connect);
        displayPreviousScreen();
        if (!connect) {
            return;
        }
        this.labyAPI.serverController().joinServer(ConnectableServerData.builder().address(this.address).build());
    }
}
