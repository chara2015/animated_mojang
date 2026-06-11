package net.labymod.core.client.gui.screen.activity.activities.ingame.interaction;

import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/interaction/InteractionAnimationController.class */
public interface InteractionAnimationController {
    float getOpenProgress();

    float getSocialTransitionProgress();

    float getBulletRotationOffset(BulletPointWidget.Side side);
}
