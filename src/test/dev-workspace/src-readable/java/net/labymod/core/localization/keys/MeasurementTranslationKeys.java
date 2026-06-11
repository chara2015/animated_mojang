package net.labymod.core.localization.keys;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/keys/MeasurementTranslationKeys.class */
public final class MeasurementTranslationKeys {
    private static final TranslatableComponent HINT_CONTROLS = Component.translatable("labymod.measurement.hint.controls", new Component[0]);

    public static Component getHintDistance(Component arg0) {
        return Component.translatable("labymod.measurement.hint.distance", arg0);
    }

    public static Component getHintControls() {
        return HINT_CONTROLS;
    }
}
