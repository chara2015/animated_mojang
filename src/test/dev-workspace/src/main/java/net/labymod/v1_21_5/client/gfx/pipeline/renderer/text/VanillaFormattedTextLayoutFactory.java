package net.labymod.v1_21_5.client.gfx.pipeline.renderer.text;

import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayoutFactory.class */
@Singleton
@Implements(FormattedTextLayout.Factory.class)
public class VanillaFormattedTextLayoutFactory implements FormattedTextLayout.Factory {
    private final ComponentMapper mapper;

    public VanillaFormattedTextLayoutFactory(ComponentMapper mapper) {
        this.mapper = mapper;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout.Factory
    public FormattedTextLayout create(String text, Style style) {
        return new VanillaFormattedTextLayout(text, (yd) style);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout.Factory
    public FormattedTextLayout create(Component component) {
        return new VanillaFormattedTextLayout((xl) this.mapper.toMinecraftComponent(component));
    }
}
