package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/DangerWarnerHudWidgetDropzone.class */
public class DangerWarnerHudWidgetDropzone extends HudWidgetDropzone {
    public DangerWarnerHudWidgetDropzone() {
        super("danger_warner");
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        Rectangle area = renderer.getArea();
        return area.getCenterX() - (hudWidgetSize.getScaledWidth() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        Rectangle area = renderer.getArea();
        return area.getCenterY() - (hudWidgetSize.getScaledHeight() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new DangerWarnerHudWidgetDropzone();
    }
}
