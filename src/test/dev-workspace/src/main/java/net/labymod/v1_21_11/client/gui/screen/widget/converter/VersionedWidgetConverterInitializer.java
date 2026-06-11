package net.labymod.v1_21_11.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(gxq.class, gul.class, gty.class, gug.class, gtp.class, guy.class, gqy.class, gtn.class, gua.class, gsl.class, gtv.class, gus.class, gun.class, gva.class, gvb.class, gvc.class, gsd.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) gro.class, (Class<?>) gjn.class));
        registry.exclude(ExclusionStrategy.parent(gsu.class));
        registry.exclude(guu.class);
        registry.register(new ButtonConverter(), gje.class, gjl.class, gjq.class, gjv.class);
        registry.register(new SliderConverter(), giz.class, giw.class, i.class);
        registry.register(new TextFieldConverter(), gjn.class);
        registry.register(new TabLayoutConverter(), gmv.class);
        registry.register(new StringConverter(), gko.class);
    }
}
