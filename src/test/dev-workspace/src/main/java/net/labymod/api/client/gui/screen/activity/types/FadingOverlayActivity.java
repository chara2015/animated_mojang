package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/FadingOverlayActivity.class */
public abstract class FadingOverlayActivity extends AbstractLayerActivity {
    protected boolean fading;
    protected long fadeInStart;
    protected boolean fadeParent;

    public FadingOverlayActivity(ScreenInstance parentScreen) {
        this(parentScreen, !PlatformEnvironment.isAncientOpenGL());
    }

    public FadingOverlayActivity(ScreenInstance parentScreen, boolean fading) {
        super(parentScreen);
        this.fadeParent = true;
        this.fading = fading;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        long running = this.labyAPI.minecraft().getRunningMillis();
        if (this.fadeInStart == 0 && this.fading) {
            this.fadeInStart = running;
        }
        float timePassed = this.fading ? (running - this.fadeInStart) / 1000.0f : 2.0f;
        float opacity = MathHelper.clamp(timePassed - 1.0f, 0.0f, 1.0f);
        if (PlatformEnvironment.isAncientOpenGL()) {
            opacity = 1.0f;
        }
        this.labyAPI.renderPipeline().multiplyAlpha(opacity, () -> {
            super.renderParent(context);
            renderCustom(context);
        });
        ((Document) this.document).opacity().set(Float.valueOf(opacity));
        super.renderSuper(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity
    protected void renderParent(ScreenContext environment) {
        if (this.fadeParent) {
            super.renderParent(environment);
        } else {
            this.labyAPI.renderPipeline().setAlpha(1.0f, () -> {
                super.renderParent(environment);
            });
        }
    }

    protected void renderCustom(ScreenContext environment) {
    }
}
