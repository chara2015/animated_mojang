package net.labymod.api.client.gui.screen.widget.converter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/MinecraftWidgetType.class */
public enum MinecraftWidgetType {
    BUTTON("Button"),
    SLIDER("Slider"),
    TEXT_FIELD("TextField"),
    TAB_NAVIGATION("TabLayout"),
    STRING("String");

    private final String name;

    MinecraftWidgetType(String name) {
        this.name = name;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }
}
