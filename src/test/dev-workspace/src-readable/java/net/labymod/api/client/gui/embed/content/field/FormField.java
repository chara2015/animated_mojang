package net.labymod.api.client.gui.embed.content.field;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/field/FormField.class */
public interface FormField<T> {
    String id();

    Component title();

    Component description();

    boolean isRequired();

    boolean isSubmit();

    T getValue();

    void setValue(T t);

    boolean isValid(T t);

    void onSubmit(FormEmbeddedContent.SubmitListener submitListener);

    Widget createWidget();
}
