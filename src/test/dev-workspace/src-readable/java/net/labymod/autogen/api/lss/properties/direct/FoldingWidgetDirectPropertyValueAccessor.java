package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.FoldingWidgetContentOffsetPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.FoldingWidgetPreviewExpandPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.FoldingWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/FoldingWidgetDirectPropertyValueAccessor.class */
public class FoldingWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> contentOffset = new FoldingWidgetContentOffsetPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> previewExpand = new FoldingWidgetPreviewExpandPropertyValueAccessor();
    LssPropertyResetter FoldingWidgetResetter = new FoldingWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "contentOffset":
                return this.contentOffset;
            case "previewExpand":
                return this.previewExpand;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "contentOffset":
                return true;
            case "previewExpand":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.FoldingWidgetResetter;
    }
}
