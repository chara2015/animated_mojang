package net.minecraft.world.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ItemBasedSteering.class */
public class ItemBasedSteering {
    private static final int MIN_BOOST_TIME = 140;
    private static final int MAX_BOOST_TIME = 700;
    private final SynchedEntityData entityData;
    private final EntityDataAccessor<Integer> boostTimeAccessor;
    private boolean boosting;
    private int boostTime;

    public ItemBasedSteering(SynchedEntityData $$0, EntityDataAccessor<Integer> $$1) {
        this.entityData = $$0;
        this.boostTimeAccessor = $$1;
    }

    public void onSynced() {
        this.boosting = true;
        this.boostTime = 0;
    }

    public boolean boost(RandomSource $$0) {
        if (this.boosting) {
            return false;
        }
        this.boosting = true;
        this.boostTime = 0;
        this.entityData.set(this.boostTimeAccessor, Integer.valueOf($$0.nextInt(841) + 140));
        return true;
    }

    public void tickBoost() {
        if (this.boosting) {
            int i = this.boostTime;
            this.boostTime = i + 1;
            if (i > boostTimeTotal()) {
                this.boosting = false;
            }
        }
    }

    public float boostFactor() {
        if (this.boosting) {
            return 1.0f + (1.15f * Mth.sin((this.boostTime / boostTimeTotal()) * 3.1415927f));
        }
        return 1.0f;
    }

    private int boostTimeTotal() {
        return ((Integer) this.entityData.get(this.boostTimeAccessor)).intValue();
    }
}
