package net.labymod.v1_21_8.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(gke.class, ghb.class, ggo.class, ggw.class, ggf.class, ghn.class, gdp.class, ggd.class, ggq.class, gfd.class, ggl.class, ghi.class, ghd.class, ghp.class, ghq.class, ghr.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) geg.class, (Class<?>) fxx.class));
        registry.exclude(ExclusionStrategy.parent(gfl.class));
        registry.register(new ButtonConverter(), fxo.class, fxv.class, fya.class, fyf.class);
        registry.register(new SliderConverter(), fxj.class, fxg.class, i.class);
        registry.register(new TextFieldConverter(), fxx.class);
        registry.register(new TabLayoutConverter(), fzw.class);
        registry.register(new StringConverter(), fyx.class);
    }
}
