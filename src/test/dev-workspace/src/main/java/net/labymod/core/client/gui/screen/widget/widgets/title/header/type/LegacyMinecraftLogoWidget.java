package net.labymod.core.client.gui.screen.widget.widgets.title.header.type;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/type/LegacyMinecraftLogoWidget.class */
@AutoWidget
public class LegacyMinecraftLogoWidget extends MinecraftLogoWidget {
    private final MinecraftTextures textures;

    public LegacyMinecraftLogoWidget() {
        super("legacy");
        this.textures = this.labyAPI.minecraft().textures();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget
    protected Widget createMinecraftWidget() {
        DivWidget legacyContainer = new DivWidget();
        IconWidget logoLeftWidget = new IconWidget(Icon.sprite(this.textures.minecraftLogoTexture(), 0, 0, 155, 44, 256, 256));
        logoLeftWidget.addId("mine");
        legacyContainer.addChild(logoLeftWidget);
        IconWidget logoRightWidget = new IconWidget(Icon.sprite(this.textures.minecraftLogoTexture(), 0, 45, 155, 44, 256, 256));
        logoRightWidget.addId("craft");
        legacyContainer.addChild(logoRightWidget);
        return legacyContainer;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget
    protected Widget createEditionWidget() {
        ResourceLocation resource = this.textures.minecraftEditionTexture();
        if (resource == null) {
            return null;
        }
        return new IconWidget(Icon.sprite(resource, 0, 0, 98, 14, 128, 16));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 310.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 51.0f;
    }
}
