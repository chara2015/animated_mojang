package net.labymod.v1_12_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(bkn.class, blb.class, bmx.class, bmp.class, bmh.class, bmn.class, bml.class, bmz.class, bnc.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) blb.class, (Class<?>) bje.class));
        registry.register(new ButtonConverter(), bja.class, bjn.class, bji.class, bjl.class, a.class);
        registry.register(new SliderConverter(), bjf.class, bjs.class, blo.class.getDeclaredClasses()[0]);
        registry.register(new TextFieldConverter(), bje.class);
    }
}
