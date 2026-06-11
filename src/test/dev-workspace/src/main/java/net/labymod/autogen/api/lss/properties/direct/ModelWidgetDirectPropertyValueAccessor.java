package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ModelWidgetBaseScalePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ModelWidgetModelColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ModelWidgetModelOffsetPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ModelWidgetSubmissionStrategyPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.ModelWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/ModelWidgetDirectPropertyValueAccessor.class */
public class ModelWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> modelColor = new ModelWidgetModelColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> baseScale = new ModelWidgetBaseScalePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> modelOffset = new ModelWidgetModelOffsetPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> submissionStrategy = new ModelWidgetSubmissionStrategyPropertyValueAccessor();
    LssPropertyResetter ModelWidgetResetter = new ModelWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "modelColor":
                return this.modelColor;
            case "baseScale":
                return this.baseScale;
            case "modelOffset":
                return this.modelOffset;
            case "submissionStrategy":
                return this.submissionStrategy;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "modelColor":
                return true;
            case "baseScale":
                return true;
            case "modelOffset":
                return true;
            case "submissionStrategy":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.ModelWidgetResetter;
    }
}
