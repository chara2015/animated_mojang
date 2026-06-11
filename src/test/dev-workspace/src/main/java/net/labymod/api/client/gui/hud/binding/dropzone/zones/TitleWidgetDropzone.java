package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/TitleWidgetDropzone.class */
public class TitleWidgetDropzone extends HudWidgetDropzone {
    private static final float TITLE_OFFSET_X = 0.0f;
    private static final float TITLE_OFFSET_Y = -8.0f;

    public TitleWidgetDropzone() {
        super("title");
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        float x = renderer.getArea().getCenterX() - (hudWidgetSize.getScaledWidth() / 2.0f);
        return x + 0.0f;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        float y = renderer.getArea().getCenterY() - (hudWidgetSize.getScaledHeight() / 2.0f);
        return y + TITLE_OFFSET_Y;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new TitleWidgetDropzone();
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }
}
