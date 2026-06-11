package net.minecraft.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.nbt.SnbtOperations;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/TriState.class */
public enum TriState implements StringRepresentable {
    TRUE(SnbtOperations.BUILTIN_TRUE),
    FALSE(SnbtOperations.BUILTIN_FALSE),
    DEFAULT(GameTestEnvironments.DEFAULT);

    public static final Codec<TriState> CODEC = Codec.either(Codec.BOOL, StringRepresentable.fromEnum(TriState::values)).xmap($$0 -> {
        return (TriState) $$0.map((v0) -> {
            return from(v0);
        }, Function.identity());
    }, $$02 -> {
        switch ($$02) {
            case TRUE:
                return Either.left(true);
            case FALSE:
                return Either.left(false);
            case DEFAULT:
                return Either.right($$02);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    });
    private final String name;

    TriState(String $$0) {
        this.name = $$0;
    }

    public static TriState from(boolean $$0) {
        return $$0 ? TRUE : FALSE;
    }

    public boolean toBoolean(boolean $$0) {
        switch (this) {
            case TRUE:
                return true;
            case FALSE:
                return false;
            default:
                return $$0;
        }
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}
