package net.labymod.core.client.gui.screen.widget.widgets.title.header.type;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/type/ModernMinecraftLogoWidget.class */
@AutoWidget
public class ModernMinecraftLogoWidget extends MinecraftLogoWidget {
    private final MinecraftTextures textures;

    public ModernMinecraftLogoWidget() {
        super("modern");
        this.textures = this.labyAPI.minecraft().textures();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget
    protected Widget createMinecraftWidget() {
        return new IconWidget(Icon.sprite(this.textures.minecraftLogoTexture(), 0, 0, 256, 44, 256, 64));
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget
    protected Widget createEditionWidget() {
        return new IconWidget(Icon.sprite(this.textures.minecraftEditionTexture(), 0, 0, 128, 14, 128, 16));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 256.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 51.0f;
    }
}
