package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/field/DefaultFormField.class */
public abstract class DefaultFormField<T> implements FormField<T> {
    private final FormEmbeddedContent form;
    private final String id;
    private final Component title;
    private final Component description;
    private final boolean required;
    private boolean submit;
    private T value;

    protected abstract Widget createWidgetBase();

    protected DefaultFormField(DefaultFormFieldBuilder builder) {
        this.form = builder.form();
        this.id = builder.id();
        this.title = builder.title();
        this.description = builder.description();
        this.required = builder.required();
        this.submit = builder.submit();
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public String id() {
        return this.id;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public Component title() {
        return this.title;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public Component description() {
        return this.description;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public boolean isRequired() {
        return this.required;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public boolean isSubmit() {
        return this.submit;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public T getValue() {
        return this.value;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public void setValue(T value) {
        this.value = value;
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public void onSubmit(FormEmbeddedContent.SubmitListener listener) {
        this.submit = true;
        this.form.onSubmit(submitted -> {
            if (submitted == this) {
                listener.submitted(submitted);
            }
        });
    }

    protected void submit() {
        if (!this.submit) {
            return;
        }
        this.form.submit(this);
    }

    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    public final Widget createWidget() {
        Widget widget = createWidgetBase();
        widget.addId(this.id);
        if (this.description != null) {
            widget.setHoverComponent(this.description);
        }
        return widget;
    }
}
