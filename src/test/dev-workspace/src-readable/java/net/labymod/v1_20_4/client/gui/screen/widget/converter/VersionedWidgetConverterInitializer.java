package net.labymod.v1_20_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(fhl.class, ffa.class, fen.class, fev.class, fef.class, ffm.class, fbs.class, fed.class, fep.class, fdr.class, fek.class, ffh.class, ffc.class, ffo.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) fcj.class, (Class<?>) exp.class));
        registry.register(new ButtonConverter(), exg.class, exn.class, exs.class, exw.class);
        registry.register(new SliderConverter(), exc.class, ewz.class, i.class);
        registry.register(new TextFieldConverter(), exp.class);
        registry.register(new TabLayoutConverter(), ezj.class);
        registry.register(new StringConverter(), eyn.class);
    }
}
