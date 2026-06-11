package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(exh.class, eva.class, eun.class, euv.class, eug.class, evn.class, erw.class, eue.class, eup.class, ets.class, eul.class, evi.class, evc.class, evp.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) esm.class, (Class<?>) eol.class));
        registry.register(new ButtonConverter(), eoc.class, eoj.class, eom.class, eop.class);
        registry.register(new SliderConverter(), enx.class, enu.class, i.class);
        registry.register(new TextFieldConverter(), eol.class);
        registry.register(new TabLayoutConverter(), ept.class);
        registry.register(new StringConverter(), epc.class);
    }
}
