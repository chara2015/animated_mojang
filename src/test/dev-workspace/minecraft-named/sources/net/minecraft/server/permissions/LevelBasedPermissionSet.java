package net.minecraft.server.permissions;

import net.minecraft.server.permissions.Permission;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/LevelBasedPermissionSet.class */
public interface LevelBasedPermissionSet extends PermissionSet {

    @Deprecated
    public static final LevelBasedPermissionSet ALL = create(PermissionLevel.ALL);
    public static final LevelBasedPermissionSet MODERATOR = create(PermissionLevel.MODERATORS);
    public static final LevelBasedPermissionSet GAMEMASTER = create(PermissionLevel.GAMEMASTERS);
    public static final LevelBasedPermissionSet ADMIN = create(PermissionLevel.ADMINS);
    public static final LevelBasedPermissionSet OWNER = create(PermissionLevel.OWNERS);

    PermissionLevel level();

    @Override // net.minecraft.server.permissions.PermissionSet
    default boolean hasPermission(Permission $$0) {
        if ($$0 instanceof Permission.HasCommandLevel) {
            Permission.HasCommandLevel $$1 = (Permission.HasCommandLevel) $$0;
            return level().isEqualOrHigherThan($$1.level());
        }
        if ($$0.equals(Permissions.COMMANDS_ENTITY_SELECTORS)) {
            return level().isEqualOrHigherThan(PermissionLevel.GAMEMASTERS);
        }
        return false;
    }

    @Override // net.minecraft.server.permissions.PermissionSet
    default PermissionSet union(PermissionSet $$0) {
        if ($$0 instanceof LevelBasedPermissionSet) {
            LevelBasedPermissionSet $$1 = (LevelBasedPermissionSet) $$0;
            if (level().isEqualOrHigherThan($$1.level())) {
                return $$1;
            }
            return this;
        }
        return super.union($$0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static LevelBasedPermissionSet forLevel(PermissionLevel $$0) throws MatchException {
        switch ($$0) {
            case ALL:
                return ALL;
            case MODERATORS:
                return MODERATOR;
            case GAMEMASTERS:
                return GAMEMASTER;
            case ADMINS:
                return ADMIN;
            case OWNERS:
                return OWNER;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private static LevelBasedPermissionSet create(final PermissionLevel $$0) {
        return new LevelBasedPermissionSet() { // from class: net.minecraft.server.permissions.LevelBasedPermissionSet.1
            @Override // net.minecraft.server.permissions.LevelBasedPermissionSet
            public PermissionLevel level() {
                return $$0;
            }

            public String toString() {
                return "permission level: " + $$0.name();
            }
        };
    }
}
