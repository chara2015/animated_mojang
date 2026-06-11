package net.labymod.api.client.gui.embed.content;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/FormEmbeddedContent.class */
public interface FormEmbeddedContent extends EmbeddedContent {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/FormEmbeddedContent$SubmitListener.class */
    public interface SubmitListener {
        void submitted(FormField<?> formField);
    }

    Component title();

    Component subTitle();

    Icon icon();

    List<FormField<?>> fields();

    <T> FormField<T> getField(String str);

    FormFieldBuilder addField(String str);

    FormEmbeddedContent onSubmit(SubmitListener submitListener);

    void submit(FormField<?> formField);

    boolean wasSubmitted();
}
