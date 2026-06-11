package net.labymod.core.main.user.shop.item;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/ItemUtil.class */
@Deprecated(forRemoval = true, since = "4.1.0")
public final class ItemUtil {
    private static boolean skipFlyingPets;

    public static void skipFlyingPets() {
        skipFlyingPets = true;
    }

    public static boolean isSkipFlyingPets() {
        return skipFlyingPets;
    }

    public static void resetSkipFlyingPets() {
        skipFlyingPets = false;
    }
}
