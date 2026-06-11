package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.gui.embed.content.field.FormButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/field/DefaultFormButton.class */
public class DefaultFormButton extends DefaultFormField<Void> implements FormButton {
    public DefaultFormButton(DefaultFormFieldBuilder builder) {
        super(builder);
    }

    @Override // net.labymod.core.client.gui.embed.content.field.DefaultFormField
    protected Widget createWidgetBase() {
        ButtonWidget widget = ButtonWidget.component(title());
        widget.setPressable(this::submit);
        return widget;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public boolean isValid(Void value) {
        return true;
    }
}
