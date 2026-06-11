package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryAlignmentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryFlexPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryIgnoreHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryIgnoreWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntrySkipFillPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.HorizontalListEntryLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/HorizontalListEntryDirectPropertyValueAccessor.class */
public class HorizontalListEntryDirectPropertyValueAccessor extends WrappedWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> flex = new HorizontalListEntryFlexPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignment = new HorizontalListEntryAlignmentPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> ignoreWidth = new HorizontalListEntryIgnoreWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> ignoreHeight = new HorizontalListEntryIgnoreHeightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> skipFill = new HorizontalListEntrySkipFillPropertyValueAccessor();
    LssPropertyResetter HorizontalListEntryResetter = new HorizontalListEntryLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "flex":
                return this.flex;
            case "alignment":
                return this.alignment;
            case "ignoreWidth":
                return this.ignoreWidth;
            case "ignoreHeight":
                return this.ignoreHeight;
            case "skipFill":
                return this.skipFill;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "flex":
                return true;
            case "alignment":
                return true;
            case "ignoreWidth":
                return true;
            case "ignoreHeight":
                return true;
            case "skipFill":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.HorizontalListEntryResetter;
    }
}
