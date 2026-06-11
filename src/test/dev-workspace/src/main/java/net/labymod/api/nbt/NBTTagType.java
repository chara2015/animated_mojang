package net.labymod.api.nbt;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/NBTTagType.class */
public enum NBTTagType {
    BYTE((byte) 1),
    SHORT((byte) 2),
    INT((byte) 3),
    LONG((byte) 4),
    FLOAT((byte) 5),
    DOUBLE((byte) 6),
    BYTE_ARRAY((byte) 7),
    STRING((byte) 8),
    LIST((byte) 9),
    COMPOUND((byte) 10),
    INT_ARRAY((byte) 11),
    LONG_ARRAY((byte) 12),
    ANY_NUMERIC((byte) 99);

    private static final NBTTagType[] TYPES = new NBTTagType[127];
    private final byte id;

    static {
        for (NBTTagType type : values()) {
            TYPES[type.id] = type;
        }
    }

    NBTTagType(byte id) {
        this.id = id;
    }

    public static NBTTagType getById(byte id) {
        if (id < 0 || id >= TYPES.length) {
            return null;
        }
        return TYPES[id];
    }

    public byte getId() {
        return this.id;
    }
}
