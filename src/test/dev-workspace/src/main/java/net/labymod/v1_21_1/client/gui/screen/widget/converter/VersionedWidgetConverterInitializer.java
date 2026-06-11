package net.labymod.v1_21_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(fsw.class, fpt.class, fpg.class, fpo.class, foy.class, fqf.class, fmz.class, fow.class, fpi.class, fon.class, fpd.class, fqa.class, fpv.class, fqh.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) fnq.class, (Class<?>) fiv.class));
        registry.register(new ButtonConverter(), fim.class, fit.class, fiy.class, fjc.class);
        registry.register(new SliderConverter(), fii.class, fif.class, i.class);
        registry.register(new TextFieldConverter(), fiv.class);
        registry.register(new TabLayoutConverter(), fkq.class);
        registry.register(new StringConverter(), fjt.class);
    }
}
