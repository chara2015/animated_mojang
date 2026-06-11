package net.labymod.core.client.gui.screen.widget.widgets.spray;

import net.laby.lib.sprays.Spray;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.spray.model.SprayAssetProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/spray/SpraySegmentWidget.class */
public class SpraySegmentWidget extends WheelWidget.Segment {
    private final Spray spray;
    private final boolean canSpray;

    public SpraySegmentWidget(Spray pack, boolean canSpray) {
        this.spray = pack;
        this.canSpray = canSpray;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean hasSpray = this.spray != null;
        if (!hasSpray) {
            return;
        }
        boolean canNotSpray = !this.canSpray;
        IconWidget iconWidget = new IconWidget(createSprayIcon(canNotSpray));
        iconWidget.addId("icon");
        if (canNotSpray) {
            iconWidget.addId("red");
        }
        super.addChild(iconWidget);
        ComponentWidget packNameWidget = ComponentWidget.component(Component.text(this.spray.name()));
        packNameWidget.addId("name");
        super.addChild(packNameWidget);
    }

    private Icon createSprayIcon(boolean canNotSpray) {
        if (canNotSpray) {
            return Textures.SpriteCommon.WHITE_X;
        }
        CompletableResourceLocation sprayTexture = LabyMod.references().sprayAssetProvider().getTexture(this.spray, SprayAssetProvider.TextureType.DIFFUSE);
        return Icon.completable(sprayTexture);
    }

    public Spray getSpray() {
        return this.spray;
    }
}
