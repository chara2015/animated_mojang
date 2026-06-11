package net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.serverapi.core.model.supplement.InputPrompt;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/serverapi/InputPromptActivity.class */
@AutoActivity
@Link("activity/overlay/input-prompt.lss")
public class InputPromptActivity extends Activity {
    private final Consumer<String> onSubmit;
    private final Component title;
    private final Component placeholder;
    private final int maxLength;
    private String value;

    public InputPromptActivity(Component title, Component placeholder, String defaultValue, int maxLength, Consumer<String> onSubmit) {
        this.value = "";
        this.title = title;
        this.placeholder = placeholder;
        this.onSubmit = onSubmit;
        this.maxLength = maxLength;
        if (defaultValue != null) {
            this.value = defaultValue;
        }
    }

    public InputPromptActivity(InputPrompt prompt, Consumer<String> onSubmit) {
        this(Laby.references().labyModProtocolService().mapComponent(prompt.title()), Laby.references().labyModProtocolService().mapComponent(prompt.getPlaceholder()), prompt.getDefaultValue(), prompt.getMaxLength(), onSubmit);
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
        TextFieldWidget textFieldWidget = new TextFieldWidget();
        textFieldWidget.addId("input");
        textFieldWidget.setText(this.value, true);
        textFieldWidget.maximalLength(this.maxLength == 0 ? 128 : this.maxLength);
        textFieldWidget.updateListener(text -> {
            this.value = text;
        });
        if (this.placeholder != null) {
            textFieldWidget.placeholder(this.placeholder);
        }
        container.addChild(textFieldWidget);
        DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        ButtonWidget confirmButton = ButtonWidget.component(Component.text("Submit"));
        confirmButton.addId("confirm");
        confirmButton.setPressable(() -> {
            this.onSubmit.accept(textFieldWidget.getText());
            displayPreviousScreen();
        });
        buttonWrapper.addChild(confirmButton);
        container.addChild(buttonWrapper);
        wrapper.addChild(container);
        ((Document) this.document).addChild(wrapper);
    }
}
