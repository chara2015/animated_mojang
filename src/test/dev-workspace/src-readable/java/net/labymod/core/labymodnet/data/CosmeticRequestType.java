package net.labymod.core.labymodnet.data;

import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/CosmeticRequestType.class */
public enum CosmeticRequestType {
    SWITCH,
    MULTI;

    private static final CosmeticRequestType[] VALUES = values();
    private final String lowercaseName = StringUtil.toLowercase(name());

    public static CosmeticRequestType[] getValues() {
        return VALUES;
    }

    CosmeticRequestType() {
    }

    public static CosmeticRequestType get(String name) {
        for (CosmeticRequestType value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + CosmeticRequestType.class.getCanonicalName() + "." + name);
    }

    public static CosmeticRequestType getOrDefault(String name, CosmeticRequestType defaultValue) {
        for (CosmeticRequestType value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.lowercaseName;
    }
}
