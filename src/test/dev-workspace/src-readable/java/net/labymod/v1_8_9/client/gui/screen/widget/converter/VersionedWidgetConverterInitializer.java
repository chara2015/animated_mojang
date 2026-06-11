package net.labymod.v1_8_9.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(awv.class, azc.class, ayu.class, aym.class, aye.class, ayq.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) axk.class, (Class<?>) avw.class));
        registry.register(new ButtonConverter(), avs.class, awe.class, avz.class, awc.class, a.class);
        registry.register(new SliderConverter(), avx.class, awj.class, axz.class.getDeclaredClasses()[0]);
        registry.register(new TextFieldConverter(), avw.class);
    }
}
