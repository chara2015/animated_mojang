package net.labymod.core.client.render.draw.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/property/IntProperty.class */
@Deprecated
public final class IntProperty {
    private int value;
    private boolean set;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        this.set = true;
    }

    public boolean isSet() {
        return this.set;
    }

    public void reset() {
        this.value = 0;
        this.set = false;
    }
}
