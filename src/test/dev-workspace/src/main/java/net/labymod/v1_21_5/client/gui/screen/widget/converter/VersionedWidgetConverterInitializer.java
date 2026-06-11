package net.labymod.v1_21_5.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(gek.class, gbg.class, gat.class, gbb.class, gal.class, gbs.class, fym.class, gaj.class, gav.class, gaa.class, gaq.class, gbn.class, gbi.class, gbu.class, gbv.class, gbw.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) fzd.class, (Class<?>) fuh.class));
        registry.register(new ButtonConverter(), fty.class, fuf.class, fuk.class, fuo.class);
        registry.register(new SliderConverter(), ftt.class, ftq.class, i.class);
        registry.register(new TextFieldConverter(), fuh.class);
        registry.register(new TabLayoutConverter(), fwd.class);
        registry.register(new StringConverter(), fvf.class);
    }
}
