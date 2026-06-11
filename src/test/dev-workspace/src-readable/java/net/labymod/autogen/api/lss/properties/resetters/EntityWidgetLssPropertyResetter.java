package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity.EntityWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/EntityWidgetLssPropertyResetter.class */
public class EntityWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof EntityWidget) {
            if (((EntityWidget) widget).entityRotationX() != null) {
                ((EntityWidget) widget).entityRotationX().reset();
            }
            if (((EntityWidget) widget).entityRotationY() != null) {
                ((EntityWidget) widget).entityRotationY().reset();
            }
            if (((EntityWidget) widget).entityRotationZ() != null) {
                ((EntityWidget) widget).entityRotationZ().reset();
            }
            if (((EntityWidget) widget).entityScale() != null) {
                ((EntityWidget) widget).entityScale().reset();
            }
        }
        super.reset(widget);
    }
}
