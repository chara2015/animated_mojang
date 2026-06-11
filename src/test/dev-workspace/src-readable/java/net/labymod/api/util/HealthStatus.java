package net.labymod.api.util;

import net.labymod.api.client.entity.LivingEntity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/HealthStatus.class */
public interface HealthStatus {
    float getHealth();

    float getMaxHealth();

    float getAbsorptionHealth();

    static HealthStatus of(LivingEntity entity) {
        return new LivingEntityHealthStatus(entity);
    }

    static HealthStatus immutable(float health, float maxHealth) {
        return new Immutable(health, maxHealth);
    }

    static HealthStatus immutable(float health, float maxHealth, float absorptionHealth) {
        return new Immutable(health, maxHealth, absorptionHealth);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/HealthStatus$Immutable.class */
    public static class Immutable implements HealthStatus {
        private final float health;
        private final float maxHealth;
        private final float absorptionHealth;

        public Immutable(float health, float maxHealth) {
            this(health, maxHealth, 0.0f);
        }

        public Immutable(float health, float maxHealth, float absorptionHealth) {
            this.health = health;
            this.maxHealth = maxHealth;
            this.absorptionHealth = absorptionHealth;
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getHealth() {
            return this.health;
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getMaxHealth() {
            return this.maxHealth;
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getAbsorptionHealth() {
            return this.absorptionHealth;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/HealthStatus$LivingEntityHealthStatus.class */
    public static class LivingEntityHealthStatus implements HealthStatus {
        private final LivingEntity entity;

        public LivingEntityHealthStatus(LivingEntity entity) {
            this.entity = entity;
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getHealth() {
            return this.entity.getHealth();
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getMaxHealth() {
            return this.entity.getMaximalHealth();
        }

        @Override // net.labymod.api.util.HealthStatus
        public float getAbsorptionHealth() {
            return this.entity.getAbsorptionHealth();
        }
    }
}
