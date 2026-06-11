package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/interaction/AbstractBulletPoint.class */
public abstract class AbstractBulletPoint implements BulletPoint {
    private final Component title;
    private final Icon icon;

    protected AbstractBulletPoint(Component title) {
        this(title, null);
    }

    protected AbstractBulletPoint(Component title, Icon icon) {
        this.title = title;
        this.icon = icon;
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public Component getTitle() {
        return this.title;
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public Icon getIcon() {
        return this.icon;
    }
}
