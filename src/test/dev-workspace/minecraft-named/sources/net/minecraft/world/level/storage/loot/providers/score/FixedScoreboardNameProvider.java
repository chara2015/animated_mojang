package net.minecraft.world.level.storage.loot.providers.score;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.scores.ScoreHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider.class */
public final class FixedScoreboardNameProvider extends Record implements ScoreboardNameProvider {
    private final String name;
    public static final MapCodec<FixedScoreboardNameProvider> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
            return v0.name();
        })).apply($$0, FixedScoreboardNameProvider::new);
    });

    public FixedScoreboardNameProvider(String $$0) {
        this.name = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FixedScoreboardNameProvider.class), FixedScoreboardNameProvider.class, "name", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FixedScoreboardNameProvider.class), FixedScoreboardNameProvider.class, "name", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FixedScoreboardNameProvider.class, Object.class), FixedScoreboardNameProvider.class, "name", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public static ScoreboardNameProvider forName(String $$0) {
        return new FixedScoreboardNameProvider($$0);
    }

    @Override // net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider
    public LootScoreProviderType getType() {
        return ScoreboardNameProviders.FIXED;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider
    public ScoreHolder getScoreHolder(LootContext $$0) {
        return ScoreHolder.forNameOnly(this.name);
    }

    @Override // net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of();
    }
}
