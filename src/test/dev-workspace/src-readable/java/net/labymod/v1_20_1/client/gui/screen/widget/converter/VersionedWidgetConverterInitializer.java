package net.labymod.v1_20_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(eyu.class, ewo.class, ewb.class, ewj.class, evu.class, exa.class, eti.class, evs.class, ewd.class, evg.class, evz.class, ewv.class, ewq.class, exc.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) etz.class, (Class<?>) epr.class));
        registry.register(new ButtonConverter(), epi.class, epp.class, ept.class, epw.class);
        registry.register(new SliderConverter(), epd.class, epa.class, i.class);
        registry.register(new TextFieldConverter(), epr.class);
        registry.register(new TabLayoutConverter(), erb.class);
        registry.register(new StringConverter(), eqk.class);
    }
}
