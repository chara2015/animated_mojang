package net.labymod.core.client.gui.background.title;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.title.TitleScreenLogoInitializeEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic.AnimatedMinecraftTextWidget;
import net.labymod.core.event.labymod.gui.title.TitleScreenLogoPreRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/title/TitleScreenLogoListener.class */
public class TitleScreenLogoListener {
    private final DynamicBackgroundController controller;

    public TitleScreenLogoListener(DynamicBackgroundController controller) {
        this.controller = controller;
    }

    @Subscribe
    public void onTitleScreenLogoInitialize(TitleScreenLogoInitializeEvent event) {
        if (!DynamicBackgroundController.isEnabled()) {
            return;
        }
        AnimatedMinecraftTextWidget widget = new AnimatedMinecraftTextWidget();
        widget.addId("animated-minecraft");
        event.setMinecraftWidget(widget);
    }

    @Subscribe
    public void onTitleScreenLogoInitialize(TitleScreenLogoPreRenderEvent event) {
        if (!DynamicBackgroundController.isEnabled()) {
            return;
        }
        float timePassed = this.controller.getTimePassed();
        LogoWidgetAccessor logo = event.logo();
        SplashWidgetAccessor splashWidget = logo.getSplashWidget();
        if (splashWidget != null) {
            splashWidget.setScale(curve(timePassed - 2900.0f, 500.0f));
        }
        Widget editionWidget = logo.getEditionWidget();
        if (editionWidget != null) {
            editionWidget.setScale(curve(timePassed - 2700.0f, 500.0f));
        }
    }

    private float curve(float timePassed, float speed) {
        float scale = MathHelper.clamp(timePassed / speed, 0.0f, 1.0f);
        return (float) CubicBezier.BOUNCE.curve(scale);
    }
}
