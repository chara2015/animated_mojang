package net.labymod.core.client.gui.embed.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.client.gui.embed.content.field.DefaultFormFieldBuilder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/DefaultFormEmbeddedContent.class */
@Link("widget/form.lss")
public class DefaultFormEmbeddedContent extends DefaultEmbeddedContent implements FormEmbeddedContent {
    private final Component title;
    private final Component subTitle;
    private final Icon icon;
    private final boolean resubmittable;
    private final List<FormField<?>> fields = new ArrayList();
    private final List<FormEmbeddedContent.SubmitListener> submitListeners = new ArrayList();
    private boolean submitted;

    public DefaultFormEmbeddedContent(DefaultFormEmbeddedContentBuilder builder) {
        this.title = builder.title();
        this.subTitle = builder.subTitle();
        this.icon = builder.icon();
        this.resubmittable = builder.isResubmittable();
    }

    @Override // net.labymod.core.client.gui.embed.content.DefaultEmbeddedContent
    protected Widget createWidgetBase() {
        FlexibleContentWidget flexibleContentWidget = (FlexibleContentWidget) new FlexibleContentWidget().addId("content-wrapper");
        if (icon() != null) {
            flexibleContentWidget.addContent(new IconWidget(icon())).addId("form-icon");
        }
        FlexibleContentWidget form = (FlexibleContentWidget) new FlexibleContentWidget().addId("form");
        flexibleContentWidget.addFlexibleContent(form);
        if (title() != null) {
            form.addContent(ComponentWidget.component(title())).addId("title");
        }
        if (subTitle() != null) {
            form.addContent(ComponentWidget.component(subTitle())).addId("sub-title");
        }
        HorizontalListWidget fields = (HorizontalListWidget) new HorizontalListWidget().addId("fields");
        form.addContent(fields);
        for (FormField<?> field : fields()) {
            fields.addEntry(field.createWidget()).addId("field");
        }
        return flexibleContentWidget;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public Component title() {
        return this.title;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public Component subTitle() {
        return this.subTitle;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public Icon icon() {
        return this.icon;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public List<FormField<?>> fields() {
        return this.fields;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public <T> FormField<T> getField(String id) {
        Iterator<FormField<?>> it = this.fields.iterator();
        while (it.hasNext()) {
            FormField<T> formField = (FormField) it.next();
            if (formField.id().equals(id)) {
                return formField;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public FormFieldBuilder addField(String id) {
        if (getField(id) != null) {
            throw new IllegalArgumentException("Field already present: " + id);
        }
        return new DefaultFormFieldBuilder(this, id);
    }

    public void addField(FormField<?> field) {
        this.fields.add(field);
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public FormEmbeddedContent onSubmit(FormEmbeddedContent.SubmitListener listener) {
        this.submitListeners.add(listener);
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public void submit(FormField<?> submitted) {
        if (!this.resubmittable && this.submitted) {
            return;
        }
        for (FormField<?> formField : this.fields) {
            if (formField.isRequired() && !formField.isValid(formField.getValue())) {
                return;
            }
        }
        this.submitted = true;
        for (FormEmbeddedContent.SubmitListener listener : this.submitListeners) {
            listener.submitted(submitted);
        }
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContent
    public boolean wasSubmitted() {
        return this.submitted;
    }
}
