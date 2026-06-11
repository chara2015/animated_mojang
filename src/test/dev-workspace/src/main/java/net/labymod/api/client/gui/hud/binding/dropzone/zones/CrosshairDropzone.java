package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/CrosshairDropzone.class */
public class CrosshairDropzone extends HudWidgetDropzone {
    private static final float CROSSHAIR_OFFSET = 8.0f;
    private final boolean top;

    public CrosshairDropzone(boolean top) {
        super("crosshair_" + (top ? "top" : "bottom"));
        this.top = top;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - (hudWidgetSize.getScaledWidth() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        float f;
        float centerY = renderer.getArea().getCenterY();
        if (this.top) {
            f = -(CROSSHAIR_OFFSET + hudWidgetSize.getScaledHeight());
        } else {
            f = CROSSHAIR_OFFSET;
        }
        return centerY + f;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new CrosshairDropzone(this.top);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return this.top ? HudWidgetAnchor.CENTER_TOP : HudWidgetAnchor.CENTER_BOTTOM;
    }
}
