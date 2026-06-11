package net.labymod.api.client.gui.embed.content.field;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/field/FormFieldBuilder.class */
public interface FormFieldBuilder {
    FormFieldBuilder title(Component component);

    FormFieldBuilder description(Component component);

    FormFieldBuilder required(boolean z);

    FormFieldBuilder submit(boolean z);

    FormButton makeButton();
}
