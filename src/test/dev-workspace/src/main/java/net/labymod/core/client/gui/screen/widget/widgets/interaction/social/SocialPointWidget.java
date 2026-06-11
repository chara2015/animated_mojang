package net.labymod.core.client.gui.screen.widget.widgets.interaction.social;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/social/SocialPointWidget.class */
@AutoWidget
public class SocialPointWidget extends AbstractPointWidget {
    private final SocialMediaEntry entry;
    private final Icon icon;

    public SocialPointWidget(SocialMediaEntry entry) {
        this.entry = entry;
        this.icon = Textures.SpriteBrands.byName(entry.getService());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(new IconWidget(this.icon));
    }

    public SocialMediaEntry entry() {
        return this.entry;
    }
}
