package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.minecraft.network.chat.FormattedText;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayoutFactory.class */
@Singleton
@Implements(FormattedTextLayout.Factory.class)
public class VanillaFormattedTextLayoutFactory implements FormattedTextLayout.Factory {
    private final ComponentMapper mapper;

    public VanillaFormattedTextLayoutFactory(ComponentMapper mapper) {
        this.mapper = mapper;
    }

    public FormattedTextLayout create(String text, Style style) {
        return new VanillaFormattedTextLayout(text, (net.minecraft.network.chat.Style) CastUtil.cast(style));
    }

    public FormattedTextLayout create(Component component) {
        return new VanillaFormattedTextLayout((FormattedText) this.mapper.toMinecraftComponent(component));
    }
}
