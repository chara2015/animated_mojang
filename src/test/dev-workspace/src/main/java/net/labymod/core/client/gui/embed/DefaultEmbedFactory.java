package net.labymod.core.client.gui.embed;

import javax.inject.Singleton;
import net.labymod.api.client.gui.embed.EmbedFactory;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.embed.content.DefaultFormEmbeddedContentBuilder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/embed/DefaultEmbedFactory.class */
@Singleton
@Implements(EmbedFactory.class)
public class DefaultEmbedFactory implements EmbedFactory {
    @Override // net.labymod.api.client.gui.embed.EmbedFactory
    public FormEmbeddedContentBuilder form() {
        return new DefaultFormEmbeddedContentBuilder();
    }
}
