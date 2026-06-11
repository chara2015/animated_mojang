package net.labymod.api.client.gui.screen.widget.attributes;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/FilterType.class */
public enum FilterType {
    BLUR("blur", Float.TYPE);

    private final String name;
    private final Class<?>[] types;

    FilterType(String name, Class... clsArr) {
        this.name = name;
        this.types = clsArr;
    }

    public String getName() {
        return this.name;
    }

    public Class<?>[] getTypes() {
        return this.types;
    }
}
