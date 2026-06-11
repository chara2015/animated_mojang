package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTilesGridWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/HudWidgetTilesGridWidgetLssPropertyResetter.class */
public class HudWidgetTilesGridWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof HudWidgetTilesGridWidget) && ((HudWidgetTilesGridWidget) widget).spaceBetweenEntries() != null) {
            ((HudWidgetTilesGridWidget) widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
