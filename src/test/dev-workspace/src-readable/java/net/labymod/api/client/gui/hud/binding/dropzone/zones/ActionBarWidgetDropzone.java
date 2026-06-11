package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/ActionBarWidgetDropzone.class */
public class ActionBarWidgetDropzone extends HudWidgetDropzone {
    public ActionBarWidgetDropzone() {
        super("action_bar");
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - (hudWidgetSize.getScaledWidth() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return (renderer.getArea().getHeight() - 72.0f) - 2.0f;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new ActionBarWidgetDropzone();
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_BOTTOM;
    }
}
