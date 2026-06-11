package net.labymod.api.client.gui.embed.content;

import java.util.Map;
import net.labymod.api.client.gui.embed.content.field.FormField;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/FormResult.class */
public interface FormResult {
    FormEmbeddedContent form();

    FormField<?> submitted();

    Map<String, Object> fieldValues();
}
