package net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet;

import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/bullet/BulletPointWidget.class */
@AutoWidget
public class BulletPointWidget extends AbstractPointWidget {
    private static final ModifyReason UPDATE_ROTATION = ModifyReason.of("bulletPointRotation");
    private final BulletPoint bulletPoint;
    private final Side side;
    private IconWidget bulletIconWidget;
    private HorizontalListWidget entryContainer;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/bullet/BulletPointWidget$Side.class */
    public enum Side {
        LEFT,
        RIGHT
    }

    public BulletPointWidget(BulletPoint bulletPoint, Side side) {
        this.bulletPoint = bulletPoint;
        this.side = side;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.bulletIconWidget = new IconWidget(Textures.SpriteCommon.BULLET_POINT);
        this.bulletIconWidget.addId("bullet-icon");
        addChild(this.bulletIconWidget);
        this.entryContainer = new HorizontalListWidget();
        this.entryContainer.addId("entry-container");
        ComponentWidget titleWidget = ComponentWidget.component(this.bulletPoint.getTitle());
        titleWidget.addId("title");
        this.entryContainer.addEntry(titleWidget);
        Icon icon = this.bulletPoint.getIcon();
        if (icon != null) {
            IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("icon");
            this.entryContainer.addEntry(iconWidget);
        }
        addChild(this.entryContainer);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        int brightness = 50 + ((int) (getHoverStrength() * 200.0f));
        this.bulletIconWidget.color().set(Integer.valueOf(ColorFormat.ARGB32.pack(brightness, brightness, brightness)));
        super.render(context);
    }

    public void updateRotation(float rotation) {
        Bounds originBounds = bounds();
        float originX = originBounds.getCenterX();
        float originY = originBounds.getCenterY();
        double radius = originBounds.getWidth() / 3.0f;
        float originX2 = (float) (((double) originX) + (Math.cos(rotation) * radius));
        float originY2 = (float) (((double) originY) + (Math.sin(rotation) * radius));
        boolean isLeft = this.side == Side.LEFT;
        Bounds bounds = this.entryContainer.bounds();
        bounds.setPosition(originX2 - (isLeft ? bounds.getWidth() : 0.0f), originY2 - (bounds.getHeight() / 2.0f), UPDATE_ROTATION);
    }

    public Side getSide() {
        return this.side;
    }

    public BulletPoint bulletPoint() {
        return this.bulletPoint;
    }
}
