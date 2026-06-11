package net.minecraft.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.world.entity.player.Input;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/InputPredicate.class */
public final class InputPredicate extends Record {
    private final Optional<Boolean> forward;
    private final Optional<Boolean> backward;
    private final Optional<Boolean> left;
    private final Optional<Boolean> right;
    private final Optional<Boolean> jump;
    private final Optional<Boolean> sneak;
    private final Optional<Boolean> sprint;
    public static final Codec<InputPredicate> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.BOOL.optionalFieldOf("forward").forGetter((v0) -> {
            return v0.forward();
        }), Codec.BOOL.optionalFieldOf("backward").forGetter((v0) -> {
            return v0.backward();
        }), Codec.BOOL.optionalFieldOf("left").forGetter((v0) -> {
            return v0.left();
        }), Codec.BOOL.optionalFieldOf("right").forGetter((v0) -> {
            return v0.right();
        }), Codec.BOOL.optionalFieldOf("jump").forGetter((v0) -> {
            return v0.jump();
        }), Codec.BOOL.optionalFieldOf("sneak").forGetter((v0) -> {
            return v0.sneak();
        }), Codec.BOOL.optionalFieldOf("sprint").forGetter((v0) -> {
            return v0.sprint();
        })).apply($$0, InputPredicate::new);
    });

    public InputPredicate(Optional<Boolean> $$0, Optional<Boolean> $$1, Optional<Boolean> $$2, Optional<Boolean> $$3, Optional<Boolean> $$4, Optional<Boolean> $$5, Optional<Boolean> $$6) {
        this.forward = $$0;
        this.backward = $$1;
        this.left = $$2;
        this.right = $$3;
        this.jump = $$4;
        this.sneak = $$5;
        this.sprint = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, InputPredicate.class), InputPredicate.class, "forward;backward;left;right;jump;sneak;sprint", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->forward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->backward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->jump:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sneak:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sprint:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, InputPredicate.class), InputPredicate.class, "forward;backward;left;right;jump;sneak;sprint", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->forward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->backward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->jump:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sneak:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sprint:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, InputPredicate.class, Object.class), InputPredicate.class, "forward;backward;left;right;jump;sneak;sprint", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->forward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->backward:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->left:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->right:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->jump:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sneak:Ljava/util/Optional;", "FIELD:Lnet/minecraft/advancements/criterion/InputPredicate;->sprint:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Boolean> forward() {
        return this.forward;
    }

    public Optional<Boolean> backward() {
        return this.backward;
    }

    public Optional<Boolean> left() {
        return this.left;
    }

    public Optional<Boolean> right() {
        return this.right;
    }

    public Optional<Boolean> jump() {
        return this.jump;
    }

    public Optional<Boolean> sneak() {
        return this.sneak;
    }

    public Optional<Boolean> sprint() {
        return this.sprint;
    }

    public boolean matches(Input $$0) {
        return matches(this.forward, $$0.forward()) && matches(this.backward, $$0.backward()) && matches(this.left, $$0.left()) && matches(this.right, $$0.right()) && matches(this.jump, $$0.jump()) && matches(this.sneak, $$0.shift()) && matches(this.sprint, $$0.sprint());
    }

    private boolean matches(Optional<Boolean> $$0, boolean $$1) {
        return ((Boolean) $$0.map($$12 -> {
            return Boolean.valueOf($$12.booleanValue() == $$1);
        }).orElse(true)).booleanValue();
    }
}
