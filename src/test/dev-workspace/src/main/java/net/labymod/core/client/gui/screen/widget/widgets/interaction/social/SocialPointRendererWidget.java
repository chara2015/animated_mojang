package net.labymod.core.client.gui.screen.widget.widgets.interaction.social;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/social/SocialPointRendererWidget.class */
@AutoWidget
public class SocialPointRendererWidget extends AbstractCirclePointWidget<SocialPointWidget> {
    private static final ModifyReason UPDATE_LAYOUT = ModifyReason.of("socialBarLayout");

    public SocialPointRendererWidget(InteractionAnimationController animationController, Player player) {
        super(animationController);
        LabyNetController controller = Laby.references().labyNetController();
        controller.loadSocials(player.getUniqueId(), socials -> {
            if (!socials.isPresent()) {
                return;
            }
            List<SocialMediaEntry> entries = (List) socials.get();
            if (entries.isEmpty()) {
                return;
            }
            ThreadSafe.executeOnRenderThread(() -> {
                initializeEntries(entries);
            });
        });
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        updateLayout();
        super.renderWidget(context);
    }

    private void initializeEntries(List<SocialMediaEntry> entries) {
        ArrayList<SocialPointWidget> arrayList = new ArrayList();
        for (SocialMediaEntry entry : entries) {
            SocialPointWidget iconWidget = new SocialPointWidget(entry);
            iconWidget.addId("social-icon");
            arrayList.add(iconWidget);
        }
        if (this.initialized) {
            addChildrenInitialized(arrayList, true);
            return;
        }
        for (SocialPointWidget child : arrayList) {
            addChild(child, false);
        }
        sortChildren();
    }

    private void updateLayout() {
        int amount = this.children.size();
        if (amount == 0) {
            return;
        }
        float transitionProgress = this.animationController.getSocialTransitionProgress();
        float linearFilter = (1.0f + 6.0f) - (transitionProgress * 6.0f);
        Bounds rendererBounds = bounds();
        float centerX = rendererBounds.getCenterX();
        float centerY = rendererBounds.getCenterY();
        float radius = rendererBounds.getHeight() / 2.0f;
        float range = ((float) ((((double) amount) * 3.141592653589793d) / 10.0d)) / linearFilter;
        float rotation = ((float) (((double) ((-range) / 2.0f)) + 1.5707963267948966d)) + (amount == 1 ? range / 2.0f : 0.0f);
        float step = amount == 1 ? 0.0f : range / (amount - 1);
        int index = 0;
        for (T widget : this.children) {
            Bounds bounds = widget.bounds();
            float cos = MathHelper.cos(rotation);
            float sin = MathHelper.sin(rotation);
            float posX = centerX + (radius * cos * linearFilter);
            float posY = centerY + (radius * sin);
            float animationProgress = getBounceFadeInProgress(widget, index);
            float scale = animationProgress + (widget.getHoverStrength() / 2.0f);
            bounds.setPosition(posX - (bounds.getWidth() / 2.0f), (posY - (bounds.getHeight() / 2.0f)) + 10.0f, UPDATE_LAYOUT);
            widget.setCenteredScale(scale);
            rotation += step;
            index++;
        }
    }

    private float getBounceFadeInProgress(SocialPointWidget widget, int index) {
        int amount = this.children.size();
        long timePassed = TimeUtil.getMillis() - widget.getLastInitialTime();
        return (float) CubicBezier.BOUNCE.curve(MathHelper.clamp((timePassed - (((long) (amount - index)) * 30)) / 300.0f, 0.0f, 1.0f));
    }
}
