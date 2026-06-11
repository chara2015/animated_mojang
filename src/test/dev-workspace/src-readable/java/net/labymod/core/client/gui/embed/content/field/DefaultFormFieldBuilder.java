package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.field.FormButton;
import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;
import net.labymod.core.client.gui.embed.content.DefaultFormEmbeddedContent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/field/DefaultFormFieldBuilder.class */
public class DefaultFormFieldBuilder implements FormFieldBuilder {
    private final DefaultFormEmbeddedContent form;
    private final String id;
    private Component title;
    private Component description;
    private boolean required;
    private boolean submit;

    public DefaultFormFieldBuilder(DefaultFormEmbeddedContent form, String id) {
        this.form = form;
        this.id = id;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormFieldBuilder
    public FormFieldBuilder title(Component title) {
        this.title = title;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormFieldBuilder
    public FormFieldBuilder description(Component description) {
        this.description = description;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormFieldBuilder
    public FormFieldBuilder required(boolean required) {
        this.required = required;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormFieldBuilder
    public FormFieldBuilder submit(boolean submit) {
        this.submit = submit;
        return this;
    }

    public FormEmbeddedContent form() {
        return this.form;
    }

    public String id() {
        return this.id;
    }

    public Component title() {
        return this.title;
    }

    public Component description() {
        return this.description;
    }

    public boolean required() {
        return this.required;
    }

    public boolean submit() {
        return this.submit;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormFieldBuilder
    public FormButton makeButton() {
        return (FormButton) addField(new DefaultFormButton(this));
    }

    private <T extends FormField<?>> T addField(T t) {
        this.form.addField(t);
        return t;
    }
}
