package net.labymod.v1_21_10.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(grw.class, gos.class, gof.class, gon.class, gnw.class, gpe.class, glg.class, gnu.class, goh.class, gmt.class, goc.class, goz.class, gou.class, gpg.class, gph.class, gpi.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) glw.class, (Class<?>) gdy.class));
        registry.exclude(ExclusionStrategy.parent(gnc.class));
        registry.register(new ButtonConverter(), gdp.class, gdw.class, geb.class, geg.class);
        registry.register(new SliderConverter(), gdk.class, gdh.class, i.class);
        registry.register(new TextFieldConverter(), gdy.class);
        registry.register(new TabLayoutConverter(), ghf.class);
        registry.register(new StringConverter(), gey.class);
    }
}
