package net.labymod.api.client.gui.screen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenName.class */
public final class ScreenName {
    private final Type type;
    private final Class<?> identifier;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenName$Type.class */
    public enum Type {
        MINECRAFT,
        FABRIC,
        FORGE,
        OPTIFINE,
        UNKNOWN
    }

    private ScreenName(Type type, Class<?> identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    public static ScreenName typed(Type type, Class<?> identifier) {
        return new ScreenName(type, identifier);
    }

    public static ScreenName minecraft(Class<?> identifier) {
        return typed(Type.MINECRAFT, identifier);
    }

    public static ScreenName fabric(Class<?> identifier) {
        return typed(Type.FABRIC, identifier);
    }

    public static ScreenName forge(Class<?> identifier) {
        return typed(Type.FORGE, identifier);
    }

    public static ScreenName optifine(Class<?> identifier) {
        return typed(Type.OPTIFINE, identifier);
    }

    public static ScreenName unknown(Class<?> identifier) {
        return typed(Type.UNKNOWN, identifier);
    }

    public Type getType() {
        return this.type;
    }

    public Class<?> getIdentifier() {
        return this.identifier;
    }
}
