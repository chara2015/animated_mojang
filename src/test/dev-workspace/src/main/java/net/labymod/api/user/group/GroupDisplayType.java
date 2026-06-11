package net.labymod.api.user.group;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/group/GroupDisplayType.class */
public enum GroupDisplayType {
    NONE,
    ABOVE_HEAD,
    BESIDE_NAME;

    private static final GroupDisplayType[] VALUES = values();

    public static GroupDisplayType getDisplayType(String name) {
        for (GroupDisplayType value : VALUES) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
