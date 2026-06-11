package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/skin/LastUsedSkinsContainerWidget.class */
@AutoWidget
public class LastUsedSkinsContainerWidget extends VerticalListWidget<Widget> {
    private final SkinActivity skinActivity;
    private TextureResult lastUsedSkins;

    public LastUsedSkinsContainerWidget(SkinActivity skinActivity) {
        this.skinActivity = skinActivity;
        this.lazy = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (!hasValidTextures()) {
            return;
        }
        addChild(ComponentWidget.text("Last used skins").addId("last-used-skins-title"));
        WrappedListWidget<SkinPreviewWidget> skinsContainer = new WrappedListWidget<>();
        skinsContainer.addId("skins-container");
        List<Skin> textures = this.lastUsedSkins.getTextures();
        for (Skin texture : textures) {
            skinsContainer.addChild(new SkinPreviewWidget(this.skinActivity, texture));
        }
        addChild(skinsContainer);
    }

    public void setTextureResult(TextureResult textureResult) {
        this.lastUsedSkins = textureResult;
        visible().set(Boolean.valueOf(hasValidTextures()));
        reInitialize();
    }

    public boolean hasValidTextures() {
        List<Skin> textures;
        return (this.lastUsedSkins == null || (textures = this.lastUsedSkins.getTextures()) == null || textures.isEmpty()) ? false : true;
    }
}
