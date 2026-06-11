package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetBoxSizingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetPriorityLayerPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetRendererPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/WrappedWidgetDirectPropertyValueAccessor.class */
public class WrappedWidgetDirectPropertyValueAccessor extends StyledWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> priorityLayer = new WrappedWidgetPriorityLayerPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignmentX = new WrappedWidgetAlignmentXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignmentY = new WrappedWidgetAlignmentYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> renderer = new WrappedWidgetRendererPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> boxSizing = new WrappedWidgetBoxSizingPropertyValueAccessor();
    LssPropertyResetter WrappedWidgetResetter = new WrappedWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "priorityLayer":
                return this.priorityLayer;
            case "alignmentX":
                return this.alignmentX;
            case "alignmentY":
                return this.alignmentY;
            case "renderer":
                return this.renderer;
            case "boxSizing":
                return this.boxSizing;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "priorityLayer":
                return true;
            case "alignmentX":
                return true;
            case "alignmentY":
                return true;
            case "renderer":
                return true;
            case "boxSizing":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.WrappedWidgetResetter;
    }
}
