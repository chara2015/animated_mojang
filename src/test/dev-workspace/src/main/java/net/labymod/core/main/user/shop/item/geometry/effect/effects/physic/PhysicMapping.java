package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/PhysicMapping.class */
public enum PhysicMapping {
    X,
    Y,
    Z,
    F,
    G,
    S,
    N;

    public static PhysicMapping get(char character) {
        return character == 'x' ? X : character == 'y' ? Y : character == 'z' ? Z : character == 'f' ? F : character == 's' ? S : character == 'g' ? G : N;
    }
}
