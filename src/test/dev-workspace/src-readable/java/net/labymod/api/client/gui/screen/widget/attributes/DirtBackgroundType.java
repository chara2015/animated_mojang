package net.labymod.api.client.gui.screen.widget.attributes;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/DirtBackgroundType.class */
public enum DirtBackgroundType {
    LIST(32.0f),
    MENU(64.0f);

    private final float brightness;

    DirtBackgroundType(float brightness) {
        this.brightness = brightness;
    }

    public float getBrightness() {
        return this.brightness;
    }
}
