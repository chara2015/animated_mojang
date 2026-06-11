package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.core.flint.FlintUrls;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/DirectionHudWidgetDropzone.class */
public class DirectionHudWidgetDropzone extends HudWidgetDropzone {
    public DirectionHudWidgetDropzone() {
        super(FlintUrls.QUERY_DIRECTION_PARAM);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - (hudWidgetSize.getScaledWidth() / 2.0f);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        HudWidget<?> hudWidget = renderer.getRelevantHudWidgetForDropzone(NamedHudWidgetDropzones.BOSS_BAR);
        float offset = 0.0f;
        if (hudWidget != null) {
            offset = 0.0f + renderer.getWidget(hudWidget).scaledBounds().getHeight();
        }
        return renderer.getArea().getTop() + 2.0f + offset;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new DirectionHudWidgetDropzone();
    }
}
