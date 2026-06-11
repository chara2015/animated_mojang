package net.labymod.v1_21_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(fze.class, fwc.class, fvp.class, fvx.class, fvh.class, fwo.class, fti.class, fvf.class, fvr.class, fuw.class, fvm.class, fwj.class, fwe.class, fwq.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) ftz.class, (Class<?>) fpd.class));
        registry.register(new ButtonConverter(), fou.class, fpb.class, fpg.class, fpk.class);
        registry.register(new SliderConverter(), foo.class, fol.class, i.class);
        registry.register(new TextFieldConverter(), fpd.class);
        registry.register(new TabLayoutConverter(), fqz.class);
        registry.register(new StringConverter(), fqb.class);
    }
}
