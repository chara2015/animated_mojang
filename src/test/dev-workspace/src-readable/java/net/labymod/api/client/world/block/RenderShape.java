package net.labymod.api.client.world.block;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/RenderShape.class */
public enum RenderShape {
    INVISIBLE,
    ENTITY_BLOCK_ANIMATED,
    MODEL;

    private static final RenderShape[] VALUES = values();

    public static RenderShape[] getValues() {
        return VALUES;
    }

    public static RenderShape get(String name) {
        for (RenderShape value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + RenderShape.class.getCanonicalName() + "." + name);
    }

    public static RenderShape getOrDefault(String name, RenderShape defaultValue) {
        for (RenderShape value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}
