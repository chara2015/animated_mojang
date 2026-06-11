package net.labymod.api.client.world.block.properties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/properties/ComparisonOperator.class */
public enum ComparisonOperator {
    COMPARE,
    SUBTRACT;

    private static final ComparisonOperator[] VALUES = values();

    public static ComparisonOperator[] getValues() {
        return VALUES;
    }
}
