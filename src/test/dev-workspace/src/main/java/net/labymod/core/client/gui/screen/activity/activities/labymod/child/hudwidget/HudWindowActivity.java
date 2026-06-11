package net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/hudwidget/HudWindowActivity.class */
public abstract class HudWindowActivity extends Activity {
    private static final int ANIMATION_DURATION_TICKS = 4;
    protected final HudWidgetWindowWidget window;
    private int trashOpacity = 0;
    private int prevTrashOpacity = 0;

    public abstract boolean canDropHudWidget();

    public HudWindowActivity(HudWidgetWindowWidget window) {
        this.window = window;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        Icon icon;
        float opacity = MathHelper.lerp(this.trashOpacity, this.prevTrashOpacity) / 4.0f;
        ((Document) this.document).opacity().set(Float.valueOf(1.0f - opacity));
        super.render(context);
        if (opacity > 0.0f) {
            float size = bounds().getWidth() / 4.0f;
            if (this.trashOpacity > 3) {
                icon = Textures.SpriteWidgetEditor.TRASH_FRAME_2;
            } else if (this.trashOpacity < 2) {
                icon = Textures.SpriteWidgetEditor.TRASH_FRAME_0;
            } else {
                icon = Textures.SpriteWidgetEditor.TRASH_FRAME_1;
            }
            Icon icon2 = icon;
            this.labyAPI.renderPipeline().setAlpha(opacity, () -> {
                context.canvas().submitIcon(icon2, bounds().getCenterX() - (size / 2.0f), bounds().getCenterY() - (size / 2.0f), size, size);
            });
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        this.prevTrashOpacity = this.trashOpacity;
        if (isTrashVisible()) {
            this.trashOpacity++;
            if (this.trashOpacity > 4) {
                this.trashOpacity = 4;
                return;
            }
            return;
        }
        this.trashOpacity--;
        if (this.trashOpacity < 0) {
            this.trashOpacity = 0;
        }
    }

    public boolean isTrashVisible() {
        if (!canDropHudWidget()) {
            return false;
        }
        return this.window.isHudWidgetOnTrashArea();
    }
}
