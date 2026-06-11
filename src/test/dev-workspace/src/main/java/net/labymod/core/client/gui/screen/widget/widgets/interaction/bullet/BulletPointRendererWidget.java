package net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet;

import java.util.Collection;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.entity.player.interaction.autotext.AutoTextBulletPoint;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/bullet/BulletPointRendererWidget.class */
@AutoWidget
public class BulletPointRendererWidget extends AbstractCirclePointWidget<BulletPointWidget> {
    private static final ModifyReason UPDATE_LAYOUT = ModifyReason.of("bulletPointLayout");
    private final Collection<BulletPoint> bulletPoints;

    public BulletPointRendererWidget(InteractionAnimationController animationController, Collection<BulletPoint> bulletPoints) {
        super(animationController);
        this.bulletPoints = bulletPoints;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        BulletPointWidget.Side side;
        super.initialize(parent);
        for (BulletPoint bulletPoint : this.bulletPoints) {
            String namespace = this.labyAPI.getNamespace(bulletPoint);
            if (namespace.equals("labymod") && !(bulletPoint instanceof ServerBulletPoint) && !(bulletPoint instanceof AutoTextBulletPoint)) {
                side = BulletPointWidget.Side.RIGHT;
            } else {
                side = BulletPointWidget.Side.LEFT;
            }
            BulletPointWidget.Side side2 = side;
            BulletPointWidget bulletPointWidget = new BulletPointWidget(bulletPoint, side2);
            bulletPointWidget.addId("bullet-point");
            addChild(bulletPointWidget);
        }
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        updateLayoutOfSide(BulletPointWidget.Side.LEFT);
        updateLayoutOfSide(BulletPointWidget.Side.RIGHT);
        super.renderWidget(context);
    }

    private void updateLayoutOfSide(BulletPointWidget.Side side) {
        int amount = getBulletPointsAmountOnSide(side);
        if (amount == 0) {
            return;
        }
        float radius = getRadius();
        float step = 2.0943952f / amount;
        float rotation = ((step - 2.0943952f) / 2.0f) + this.animationController.getBulletRotationOffset(side);
        if (side == BulletPointWidget.Side.LEFT) {
            rotation = (float) (((double) rotation) + 3.141592653589793d);
        }
        float openProgress = this.animationController.getOpenProgress();
        float rotation2 = (float) (((double) rotation) + (((double) ((1.0f - openProgress) / 2.0f)) * 3.141592653589793d));
        Bounds rendererBounds = bounds();
        float centerX = rendererBounds.getCenterX();
        float centerY = rendererBounds.getCenterY();
        for (T widget : this.children) {
            if (widget.getSide() == side) {
                Bounds bounds = widget.bounds();
                float cos = MathHelper.cos(rotation2);
                float sin = MathHelper.sin(rotation2);
                float posX = centerX + (radius * cos);
                float posY = centerY + (radius * sin);
                float scale = openProgress + (widget.getHoverStrength() / 8.0f);
                bounds.setPosition(posX - (bounds.getWidth() / 2.0f), posY - (bounds.getHeight() / 2.0f), UPDATE_LAYOUT);
                widget.setCenteredScale(scale);
                widget.opacity().set(Float.valueOf(openProgress));
                widget.updateRotation(rotation2);
                rotation2 += step;
            }
        }
    }

    private int getBulletPointsAmountOnSide(BulletPointWidget.Side side) {
        int amount = 0;
        for (T widget : this.children) {
            if (widget.getSide() == side) {
                amount++;
            }
        }
        return amount;
    }
}
