package net.labymod.v1_21_3.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(fyq.class, fvo.class, fvb.class, fvj.class, fut.class, fwa.class, fsu.class, fur.class, fvd.class, fui.class, fuy.class, fvv.class, fvq.class, fwc.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) ftl.class, (Class<?>) foo.class));
        registry.register(new ButtonConverter(), fof.class, fom.class, fos.class, fow.class);
        registry.register(new SliderConverter(), fob.class, fny.class, i.class);
        registry.register(new TextFieldConverter(), foo.class);
        registry.register(new TabLayoutConverter(), fql.class);
        registry.register(new StringConverter(), fpn.class);
    }
}
