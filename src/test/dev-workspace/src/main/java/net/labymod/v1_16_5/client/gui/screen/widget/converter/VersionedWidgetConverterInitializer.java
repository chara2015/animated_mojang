package net.labymod.v1_16_5.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(dsc.class, dql.class, dqa.class, dqh.class, dpt.class, dqx.class, dnq.class, dpr.class, dqc.class, dpi.class, dpy.class, dqs.class, dqn.class, dqz.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) doe.class, (Class<?>) dlq.class));
        registry.register(new ButtonConverter(), dlj.class, dlr.class, dlt.class, dlw.class);
        registry.register(new SliderConverter(), dlg.class, dlz.class, dme.class);
        registry.register(new TextFieldConverter(), dlq.class);
    }
}
