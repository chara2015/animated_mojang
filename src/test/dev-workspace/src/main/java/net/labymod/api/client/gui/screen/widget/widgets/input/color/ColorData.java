package net.labymod.api.client.gui.screen.widget.widgets.input.color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/ColorData.class */
public class ColorData {
    private Color color;
    private Color actualColor;
    private boolean alpha;
    private boolean chroma;
    private boolean removeAlpha;
    private boolean chromaSpeed;
    private boolean removeChromaSpeed;
    private int saturation = -1;
    private int brightness = -1;
    private final Map<String, Runnable> updateListeners = new HashMap();

    protected ColorData(Color color) {
        set(color);
    }

    public Color getColor() {
        return this.color;
    }

    public Color getActualColor() {
        return this.actualColor;
    }

    public void setAlpha(int alpha) {
        if (removeAlpha()) {
            return;
        }
        set(this.actualColor.withAlpha(alpha));
    }

    public void setHue(int hue) {
        set(this.actualColor.withHue(hue));
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setRgb(int rgb) {
        int alpha = 255;
        if (!removeAlpha() && this.actualColor.getAlpha() != 255) {
            alpha = this.actualColor.getAlpha();
        }
        Color color = Color.of(rgb, alpha);
        if (this.actualColor.isChroma()) {
            set(color, color.withChroma(this.actualColor.getChromaSpeed()));
        } else {
            set(color, color.withoutChroma(this.actualColor.getChromaSpeed()));
        }
    }

    public void applySB() {
        if (this.brightness == -1 && this.saturation == -1) {
            return;
        }
        int brightness = this.brightness;
        int saturation = this.saturation;
        int hue = this.color.getHue();
        if (brightness == -1) {
            brightness = this.color.getBrightness();
        }
        if (saturation == -1) {
            saturation = this.color.getSaturation();
        }
        Color color = Color.ofHSB(hue, saturation, brightness, this.actualColor.getAlpha());
        if (this.actualColor.isChroma()) {
            set(color, color.withChroma());
        } else {
            set(color);
        }
        this.brightness = -1;
        this.saturation = -1;
    }

    public void setChroma(boolean chroma) {
        set(chroma ? this.actualColor.withChroma() : this.actualColor.withoutChroma());
    }

    public void setChromaSpeed(float chromaSpeed) {
        set(this.actualColor.isChroma() ? this.actualColor.withChroma(chromaSpeed) : this.actualColor.withoutChroma(chromaSpeed));
    }

    public void setValue(int value) {
        if (this.color.get() == value) {
            return;
        }
        Color color = Color.of(value, this.actualColor.getAlpha());
        if (this.actualColor.isChroma()) {
            Color chroma = color.withChroma();
            set(color, chroma);
        } else {
            set(color);
        }
    }

    public void set(Color color) {
        set(color.withoutChroma(), color);
    }

    private void set(Color color, Color actualColor) {
        if (Objects.equals(this.actualColor, actualColor)) {
            return;
        }
        this.color = color;
        this.actualColor = actualColor;
        for (Runnable updateListener : this.updateListeners.values()) {
            updateListener.run();
        }
    }

    public boolean enabledAlpha() {
        return this.alpha;
    }

    public boolean enabledChroma() {
        return this.chroma;
    }

    public boolean enabledChromaSpeed() {
        return this.chromaSpeed;
    }

    public ColorData alphaEnabled(boolean alpha) {
        this.alpha = alpha;
        return this;
    }

    public ColorData chromaEnabled(boolean chroma) {
        this.chroma = chroma;
        return this;
    }

    public ColorData removeAlpha(boolean removeAlpha) {
        this.removeAlpha = removeAlpha;
        if (removeAlpha()) {
            set(this.actualColor.withoutAlpha());
        }
        return this;
    }

    public ColorData chromaSpeedEnabled(boolean chromaSpeed) {
        this.chromaSpeed = chromaSpeed;
        return this;
    }

    public ColorData removeChromaSpeed(boolean removeChromaSpeed) {
        this.removeChromaSpeed = removeChromaSpeed;
        if (removeChromaSpeed()) {
            set(this.actualColor.resetChromaSpeed());
        }
        return this;
    }

    public void addUpdateListener(Object instance, Runnable updateListener) {
        this.updateListeners.put(instance.getClass().getSimpleName(), updateListener);
    }

    private boolean removeAlpha() {
        return !this.alpha && this.removeAlpha;
    }

    private boolean removeChromaSpeed() {
        return !this.chromaSpeed && this.removeChromaSpeed;
    }
}
