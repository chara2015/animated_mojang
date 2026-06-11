package net.labymod.api.client.gui.hud.binding.dropzone;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.HudWidgetBinding;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/HudWidgetDropzone.class */
public abstract class HudWidgetDropzone extends HudWidgetBinding {
    private static final int ANIMATION_DURATION_TICKS = 8;
    private int opacity;
    private int prevOpacity;

    public abstract float getX(HudWidgetRendererAccessor hudWidgetRendererAccessor, HudSize hudSize);

    public abstract float getY(HudWidgetRendererAccessor hudWidgetRendererAccessor, HudSize hudSize);

    public abstract HudWidgetDropzone copy();

    public abstract HudWidgetAnchor getAnchor();

    public HudWidgetDropzone(@NotNull String id) {
        super(id);
    }

    public void render(ScreenContext context, HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        if (this.opacity <= 0) {
            return;
        }
        float opacity = MathHelper.lerp(this.opacity / 8.0f, this.prevOpacity / 8.0f);
        float x = getX(renderer, hudWidgetSize);
        float y = getY(renderer, hudWidgetSize);
        context.canvas().submitRelativeRect(x, y, hudWidgetSize.getScaledWidth(), hudWidgetSize.getScaledHeight(), ColorFormat.ARGB32.pack(255, 255, 255, (int) (opacity * 90.0f)));
    }

    public void tick(boolean visible, boolean isOverlapping) {
        this.prevOpacity = this.opacity;
        if (visible) {
            if (isOverlapping) {
                if (this.opacity < 8) {
                    this.opacity++;
                    return;
                }
                return;
            } else {
                if (this.opacity > 4) {
                    this.opacity--;
                }
                if (this.opacity < 4) {
                    this.opacity++;
                    return;
                }
                return;
            }
        }
        if (this.opacity > 0) {
            this.opacity--;
        }
    }

    public boolean isOverlapping(HudWidgetRendererAccessor renderer, HudWidgetWidget widget) {
        Bounds bounds = widget.bounds();
        return isOverlapping(renderer, widget.size(), bounds.getX(), bounds.getY());
    }

    public boolean isInside(HudWidgetRendererAccessor renderer, HudWidgetWidget widget) {
        Bounds bounds = widget.bounds();
        return isInside(renderer, widget.size(), bounds.getX(), bounds.getY());
    }

    public boolean isOverlapping(HudWidgetRendererAccessor renderer, HudSize size, float hudWidgetX, float hudWidgetY) {
        float x = getX(renderer, size);
        float y = getY(renderer, size);
        return hudWidgetX + size.getScaledWidth() >= x && hudWidgetX <= x + size.getScaledWidth() && hudWidgetY + size.getScaledHeight() >= y && hudWidgetY <= y + size.getScaledHeight();
    }

    public boolean isInside(HudWidgetRendererAccessor renderer, HudSize size, float hudWidgetX, float hudWidgetY) {
        float x = getX(renderer, size);
        float y = getY(renderer, size);
        float centerX = hudWidgetX + (size.getScaledWidth() / 2.0f);
        float centerY = hudWidgetY + (size.getScaledHeight() / 2.0f);
        return centerX >= x && centerX <= x + size.getScaledWidth() && centerY >= y && centerY <= y + size.getScaledHeight();
    }
}
