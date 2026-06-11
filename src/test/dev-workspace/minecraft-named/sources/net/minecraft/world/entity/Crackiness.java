package net.minecraft.world.entity;

import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Crackiness.class */
public class Crackiness {
    public static final Crackiness GOLEM = new Crackiness(0.75f, 0.5f, 0.25f);
    public static final Crackiness WOLF_ARMOR = new Crackiness(0.95f, 0.69f, 0.32f);
    private final float fractionLow;
    private final float fractionMedium;
    private final float fractionHigh;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Crackiness$Level.class */
    public enum Level {
        NONE,
        LOW,
        MEDIUM,
        HIGH
    }

    private Crackiness(float $$0, float $$1, float $$2) {
        this.fractionLow = $$0;
        this.fractionMedium = $$1;
        this.fractionHigh = $$2;
    }

    public Level byFraction(float $$0) {
        if ($$0 < this.fractionHigh) {
            return Level.HIGH;
        }
        if ($$0 < this.fractionMedium) {
            return Level.MEDIUM;
        }
        if ($$0 < this.fractionLow) {
            return Level.LOW;
        }
        return Level.NONE;
    }

    public Level byDamage(ItemStack $$0) {
        if (!$$0.isDamageableItem()) {
            return Level.NONE;
        }
        return byDamage($$0.getDamageValue(), $$0.getMaxDamage());
    }

    public Level byDamage(int $$0, int $$1) {
        return byFraction(($$1 - $$0) / $$1);
    }
}
