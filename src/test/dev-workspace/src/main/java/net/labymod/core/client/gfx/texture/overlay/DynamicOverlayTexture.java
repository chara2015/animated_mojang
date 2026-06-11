package net.labymod.core.client.gfx.texture.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/texture/overlay/DynamicOverlayTexture.class */
public final class DynamicOverlayTexture {
    private static final DamageColor DEFAULT_COLOR = new DamageColor(1.0f, 0.0f, 0.0f, 0.3f);
    private final GameOverlayTexture texture;
    private boolean updateOverlayTexture;
    private final DamageColor color = new DamageColor(1.0f, 1.0f, 1.0f, 1.0f).setChangeListener(() -> {
        this.updateOverlayTexture = true;
    });
    private final DamageConfig damageConfig = Laby.labyAPI().config().ingame().damage();

    public DynamicOverlayTexture(GameOverlayTexture texture) {
        this.texture = texture;
    }

    public void setColorAndUpdate() {
        setColor();
        update();
    }

    public void setColor() {
        if (this.damageConfig.damageColored().get().booleanValue()) {
            ConfigProperty<Color> damageColorProperty = this.damageConfig.damageColor();
            Color damageColor = damageColorProperty.get();
            setColor(damageColor);
            return;
        }
        setDefaultColor();
    }

    public void setColor(Color color) {
        setColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public void setColor(float red, float green, float blue, float alpha) {
        this.color.set(red, green, blue, alpha);
        updateTexture();
    }

    public void setDefaultColor() {
        this.color.set(DEFAULT_COLOR);
        updateTexture();
    }

    public void update() {
        if (!this.updateOverlayTexture) {
            return;
        }
        this.updateOverlayTexture = false;
        this.texture.upload();
    }

    private void updateTexture() {
        GameImage image = this.texture.image();
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                if (y < 8) {
                    image.setARGB(x, y, this.color.getARGB());
                }
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/texture/overlay/DynamicOverlayTexture$DamageColor.class */
    static class DamageColor {
        private float red;
        private float green;
        private float blue;
        private float alpha;

        @Nullable
        private Runnable changeListener;

        public DamageColor(float red, float green, float blue, float alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public DamageColor setChangeListener(@Nullable Runnable runnable) {
            this.changeListener = runnable;
            return this;
        }

        public void set(DamageColor other) {
            set(other.red, other.green, other.blue, other.alpha);
        }

        public void set(float red, float green, float blue, float alpha) {
            if (this.red == red && this.green == green && this.blue == blue && this.alpha == alpha) {
                return;
            }
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
            onValueUpdate();
        }

        public int getARGB() {
            return ColorFormat.ARGB32.pack(this.red, this.green, this.blue, 1.0f - this.alpha);
        }

        private void onValueUpdate() {
            if (this.changeListener == null) {
                return;
            }
            this.changeListener.run();
        }
    }
}
