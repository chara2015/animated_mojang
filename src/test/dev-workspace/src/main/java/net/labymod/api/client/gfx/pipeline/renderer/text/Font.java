package net.labymod.api.client.gfx.pipeline.renderer.text;

import java.util.Objects;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/Font.class */
public final class Font {
    private final ResourceLocation id;

    Font(ResourceLocation id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Font font = (Font) o;
        return Objects.equals(this.id, font.id);
    }

    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    public String toString() {
        return this.id.toString();
    }
}
