package net.labymod.api.client.render.font;

import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/FontSize.class */
public class FontSize implements SizedFont {
    private final PredefinedFontSize predefined;
    private final float customValue;

    private FontSize(PredefinedFontSize predefined, float customValue) {
        this.predefined = predefined;
        this.customValue = customValue;
    }

    @NotNull
    public static FontSize predefined(@NotNull PredefinedFontSize predefined) {
        return predefined.fontSize();
    }

    @NotNull
    public static FontSize custom(float fontSize) {
        return new FontSize(null, fontSize);
    }

    @Nullable
    public static FontSize nextLowerPredefined(float fontSize) {
        PredefinedFontSize nearest = null;
        float nearestValue = -1.0f;
        PredefinedFontSize[] values = PredefinedFontSize.VALUES;
        for (int i = values.length - 1; i >= 0; i--) {
            PredefinedFontSize predefined = values[i];
            float predefinedValue = predefined.fontSize().getSize();
            if (predefinedValue <= fontSize && (nearestValue == -1.0f || nearestValue < predefinedValue)) {
                nearest = predefined;
                nearestValue = predefinedValue;
            }
        }
        if (nearest != null) {
            return nearest.fontSize();
        }
        return null;
    }

    @Nullable
    public PredefinedFontSize getPredefined() {
        return this.predefined;
    }

    @Override // net.labymod.api.client.render.font.SizedFont
    public float getSize() {
        if (this.predefined != null) {
            float guiScale = Laby.labyAPI().minecraft().minecraftWindow().getScale();
            if (guiScale == 0.0f) {
                guiScale = 1.0f;
            }
            return this.predefined.value((int) guiScale);
        }
        return this.customValue;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/FontSize$PredefinedFontSize.class */
    public enum PredefinedFontSize {
        SMALL(0.75f, 0.5f, 0.67f, 0.75f, 0.6f, 0.67f),
        MEDIUM(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f),
        LARGE(2.0f, 1.5f, 1.67f, 1.5f, 1.67f, 1.5f);

        private static final PredefinedFontSize[] VALUES = values();
        private final FontSize fontSize = new FontSize(this, -1.0f);
        private final float[] values;

        PredefinedFontSize(float... values) {
            this.values = values;
        }

        @NotNull
        public FontSize fontSize() {
            return this.fontSize;
        }

        public float value(int guiScale) {
            if (guiScale > 0 && this.values.length > guiScale - 1) {
                return this.values[guiScale - 1];
            }
            if (this.values.length > 0) {
                return this.values[this.values.length - 1];
            }
            return 1.0f;
        }

        @Nullable
        public static PredefinedFontSize of(String value) {
            if (value == null) {
                return null;
            }
            for (PredefinedFontSize predefinedFontSize : VALUES) {
                if (value.equals(predefinedFontSize.name())) {
                    return predefinedFontSize;
                }
            }
            return null;
        }
    }
}
