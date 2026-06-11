package net.labymod.core.client.gui.embed.content;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/content/DefaultFormEmbeddedContentBuilder.class */
public class DefaultFormEmbeddedContentBuilder implements FormEmbeddedContentBuilder {
    private Component title;
    private Component subTitle;
    private Icon icon;
    private boolean resubmittable;

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder
    public FormEmbeddedContentBuilder title(Component title) {
        this.title = title;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder
    public FormEmbeddedContentBuilder subTitle(Component subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder
    public FormEmbeddedContentBuilder icon(Icon icon) {
        this.icon = icon;
        return this;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder
    public FormEmbeddedContentBuilder resubmittable() {
        this.resubmittable = true;
        return this;
    }

    public Component title() {
        return this.title;
    }

    public Component subTitle() {
        return this.subTitle;
    }

    public Icon icon() {
        return this.icon;
    }

    public boolean isResubmittable() {
        return this.resubmittable;
    }

    @Override // net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder
    public FormEmbeddedContent build() {
        return new DefaultFormEmbeddedContent(this);
    }
}
