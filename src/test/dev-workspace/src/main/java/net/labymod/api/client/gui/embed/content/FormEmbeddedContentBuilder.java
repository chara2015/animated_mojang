package net.labymod.api.client.gui.embed.content;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/FormEmbeddedContentBuilder.class */
public interface FormEmbeddedContentBuilder {
    FormEmbeddedContentBuilder title(Component component);

    FormEmbeddedContentBuilder subTitle(Component component);

    FormEmbeddedContentBuilder icon(Icon icon);

    FormEmbeddedContentBuilder resubmittable();

    FormEmbeddedContent build();
}
