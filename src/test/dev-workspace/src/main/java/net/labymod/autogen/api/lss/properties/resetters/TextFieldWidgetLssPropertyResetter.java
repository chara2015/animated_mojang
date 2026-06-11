package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/TextFieldWidgetLssPropertyResetter.class */
public class TextFieldWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof TextFieldWidget) {
            if (((TextFieldWidget) widget).textAlignmentX() != null) {
                ((TextFieldWidget) widget).textAlignmentX().reset();
            }
            if (((TextFieldWidget) widget).textAlignmentY() != null) {
                ((TextFieldWidget) widget).textAlignmentY().reset();
            }
            if (((TextFieldWidget) widget).placeHolderColor() != null) {
                ((TextFieldWidget) widget).placeHolderColor().reset();
            }
            if (((TextFieldWidget) widget).textColor() != null) {
                ((TextFieldWidget) widget).textColor().reset();
            }
            if (((TextFieldWidget) widget).markTextColor() != null) {
                ((TextFieldWidget) widget).markTextColor().reset();
            }
            if (((TextFieldWidget) widget).markColor() != null) {
                ((TextFieldWidget) widget).markColor().reset();
            }
            if (((TextFieldWidget) widget).cursorColor() != null) {
                ((TextFieldWidget) widget).cursorColor().reset();
            }
            if (((TextFieldWidget) widget).clearButton() != null) {
                ((TextFieldWidget) widget).clearButton().reset();
            }
            if (((TextFieldWidget) widget).submitButton() != null) {
                ((TextFieldWidget) widget).submitButton().reset();
            }
            if (((TextFieldWidget) widget).fontSize() != null) {
                ((TextFieldWidget) widget).fontSize().reset();
            }
            if (((TextFieldWidget) widget).textShadow() != null) {
                ((TextFieldWidget) widget).textShadow().reset();
            }
        }
        super.reset(widget);
    }
}
