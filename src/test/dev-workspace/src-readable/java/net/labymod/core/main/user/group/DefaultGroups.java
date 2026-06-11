package net.labymod.core.main.user.group;

import net.labymod.api.user.group.Group;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/group/DefaultGroups.class */
public final class DefaultGroups {
    public static final Group DEFAULT_GROUP = create(0, "default", "Default", "FFFFFF");
    public static final Group LEGACY_GROUP = create(-1, "legacy", "Legacy", "FFFFFF");

    private static Group create(int id, String name, String displayName, String colorHex) {
        Group group = new Group(id, name, displayName, colorHex, 'f', "", "", false);
        group.initialize();
        return group;
    }
}
