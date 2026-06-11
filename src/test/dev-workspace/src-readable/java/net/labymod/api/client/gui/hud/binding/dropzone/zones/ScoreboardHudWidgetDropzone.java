package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import java.util.Locale;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/ScoreboardHudWidgetDropzone.class */
public class ScoreboardHudWidgetDropzone extends HudWidgetDropzone {
    private final boolean rightBound;

    /* JADX WARN: Illegal instructions before constructor call */
    public ScoreboardHudWidgetDropzone(boolean rightBound) {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[1];
        objArr[0] = rightBound ? "right" : "left";
        super(String.format(locale, "scoreboard_%s", objArr));
        this.rightBound = rightBound;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        Rectangle area = renderer.getArea();
        if (this.rightBound) {
            return area.getRight() - hudWidgetSize.getScaledWidth();
        }
        return area.getLeft();
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return (renderer.getArea().getCenterY() - 9.0f) - (hudWidgetSize.getScaledHeight() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return this.rightBound ? HudWidgetAnchor.RIGHT_TOP : HudWidgetAnchor.LEFT_TOP;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new ScoreboardHudWidgetDropzone(this.rightBound);
    }
}
