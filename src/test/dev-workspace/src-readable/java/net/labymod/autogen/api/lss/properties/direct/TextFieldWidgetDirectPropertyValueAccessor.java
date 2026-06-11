package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetClearButtonPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetCursorColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetFontSizePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetMarkColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetMarkTextColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetPlaceHolderColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetSubmitButtonPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextShadowPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.TextFieldWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/TextFieldWidgetDirectPropertyValueAccessor.class */
public class TextFieldWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> textAlignmentX = new TextFieldWidgetTextAlignmentXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> textAlignmentY = new TextFieldWidgetTextAlignmentYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> placeHolderColor = new TextFieldWidgetPlaceHolderColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> textColor = new TextFieldWidgetTextColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> markTextColor = new TextFieldWidgetMarkTextColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> markColor = new TextFieldWidgetMarkColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> cursorColor = new TextFieldWidgetCursorColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> clearButton = new TextFieldWidgetClearButtonPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> submitButton = new TextFieldWidgetSubmitButtonPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> fontSize = new TextFieldWidgetFontSizePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> textShadow = new TextFieldWidgetTextShadowPropertyValueAccessor();
    LssPropertyResetter TextFieldWidgetResetter = new TextFieldWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "textAlignmentX":
                return this.textAlignmentX;
            case "textAlignmentY":
                return this.textAlignmentY;
            case "placeHolderColor":
                return this.placeHolderColor;
            case "textColor":
                return this.textColor;
            case "markTextColor":
                return this.markTextColor;
            case "markColor":
                return this.markColor;
            case "cursorColor":
                return this.cursorColor;
            case "clearButton":
                return this.clearButton;
            case "submitButton":
                return this.submitButton;
            case "fontSize":
                return this.fontSize;
            case "textShadow":
                return this.textShadow;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "textAlignmentX":
                return true;
            case "textAlignmentY":
                return true;
            case "placeHolderColor":
                return true;
            case "textColor":
                return true;
            case "markTextColor":
                return true;
            case "markColor":
                return true;
            case "cursorColor":
                return true;
            case "clearButton":
                return true;
            case "submitButton":
                return true;
            case "fontSize":
                return true;
            case "textShadow":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.TextFieldWidgetResetter;
    }
}
