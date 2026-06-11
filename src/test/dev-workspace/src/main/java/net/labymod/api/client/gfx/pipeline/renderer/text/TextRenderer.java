package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/TextRenderer.class */
@Referenceable
public interface TextRenderer extends FontRenderer {
    Font getCurrentFont();

    void setCurrentFont(Font font);

    FontRenderer getCurrent();
}
