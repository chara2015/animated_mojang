package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonRecommendationPacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/AddonRecommendationItemWidgetLssPropertyResetter.class */
public class AddonRecommendationItemWidgetLssPropertyResetter extends StoreItemWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.StoreItemWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof AddonRecommendationPacketHandler.AddonRecommendationItemWidget) {
        }
        super.reset(widget);
    }
}
