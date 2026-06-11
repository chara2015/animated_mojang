package net.minecraft.world.entity.animal.golem;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel.class */
public final class CopperGolemOxidationLevel extends Record {
    private final SoundEvent spinHeadSound;
    private final SoundEvent hurtSound;
    private final SoundEvent deathSound;
    private final SoundEvent stepSound;
    private final Identifier texture;
    private final Identifier eyeTexture;

    public CopperGolemOxidationLevel(SoundEvent $$0, SoundEvent $$1, SoundEvent $$2, SoundEvent $$3, Identifier $$4, Identifier $$5) {
        this.spinHeadSound = $$0;
        this.hurtSound = $$1;
        this.deathSound = $$2;
        this.stepSound = $$3;
        this.texture = $$4;
        this.eyeTexture = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CopperGolemOxidationLevel.class), CopperGolemOxidationLevel.class, "spinHeadSound;hurtSound;deathSound;stepSound;texture;eyeTexture", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->spinHeadSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->hurtSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->deathSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->stepSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->eyeTexture:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CopperGolemOxidationLevel.class), CopperGolemOxidationLevel.class, "spinHeadSound;hurtSound;deathSound;stepSound;texture;eyeTexture", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->spinHeadSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->hurtSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->deathSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->stepSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->eyeTexture:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CopperGolemOxidationLevel.class, Object.class), CopperGolemOxidationLevel.class, "spinHeadSound;hurtSound;deathSound;stepSound;texture;eyeTexture", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->spinHeadSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->hurtSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->deathSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->stepSound:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->texture:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/animal/golem/CopperGolemOxidationLevel;->eyeTexture:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public SoundEvent spinHeadSound() {
        return this.spinHeadSound;
    }

    public SoundEvent hurtSound() {
        return this.hurtSound;
    }

    public SoundEvent deathSound() {
        return this.deathSound;
    }

    public SoundEvent stepSound() {
        return this.stepSound;
    }

    public Identifier texture() {
        return this.texture;
    }

    public Identifier eyeTexture() {
        return this.eyeTexture;
    }
}
