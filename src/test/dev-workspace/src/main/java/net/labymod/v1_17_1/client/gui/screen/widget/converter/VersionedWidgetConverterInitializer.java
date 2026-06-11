package net.labymod.v1_17_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(eec.class, ecj.class, eby.class, ecf.class, ebr.class, ecv.class, dzn.class, ebp.class, eca.class, ebf.class, ebw.class, ecq.class, ecl.class, ecx.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) eab.class, (Class<?>) dxi.class));
        registry.register(new ButtonConverter(), dxa.class, dxg.class, dxj.class, dxl.class);
        registry.register(new SliderConverter(), dwx.class, dxq.class, dxu.class);
        registry.register(new TextFieldConverter(), dxi.class);
    }
}
