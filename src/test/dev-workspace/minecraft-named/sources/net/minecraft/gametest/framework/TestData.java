package net.minecraft.gametest.framework;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Rotation;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/TestData.class */
public final class TestData<EnvironmentType> extends Record {
    private final EnvironmentType environment;
    private final Identifier structure;
    private final int maxTicks;
    private final int setupTicks;
    private final boolean required;
    private final Rotation rotation;
    private final boolean manualOnly;
    private final int maxAttempts;
    private final int requiredSuccesses;
    private final boolean skyAccess;
    public static final MapCodec<TestData<Holder<TestEnvironmentDefinition>>> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(TestEnvironmentDefinition.CODEC.fieldOf("environment").forGetter((v0) -> {
            return v0.environment();
        }), Identifier.CODEC.fieldOf("structure").forGetter((v0) -> {
            return v0.structure();
        }), ExtraCodecs.POSITIVE_INT.fieldOf("max_ticks").forGetter((v0) -> {
            return v0.maxTicks();
        }), ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("setup_ticks", 0).forGetter((v0) -> {
            return v0.setupTicks();
        }), Codec.BOOL.optionalFieldOf("required", true).forGetter((v0) -> {
            return v0.required();
        }), Rotation.CODEC.optionalFieldOf("rotation", Rotation.NONE).forGetter((v0) -> {
            return v0.rotation();
        }), Codec.BOOL.optionalFieldOf("manual_only", false).forGetter((v0) -> {
            return v0.manualOnly();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_attempts", 1).forGetter((v0) -> {
            return v0.maxAttempts();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("required_successes", 1).forGetter((v0) -> {
            return v0.requiredSuccesses();
        }), Codec.BOOL.optionalFieldOf("sky_access", false).forGetter((v0) -> {
            return v0.skyAccess();
        })).apply($$0, (v1, v2, v3, v4, v5, v6, v7, v8, v9, v10) -> {
            return new TestData(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
        });
    });

    public TestData(EnvironmentType $$0, Identifier $$1, int $$2, int $$3, boolean $$4, Rotation $$5, boolean $$6, int $$7, int $$8, boolean $$9) {
        this.environment = $$0;
        this.structure = $$1;
        this.maxTicks = $$2;
        this.setupTicks = $$3;
        this.required = $$4;
        this.rotation = $$5;
        this.manualOnly = $$6;
        this.maxAttempts = $$7;
        this.requiredSuccesses = $$8;
        this.skyAccess = $$9;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TestData.class), TestData.class, "environment;structure;maxTicks;setupTicks;required;rotation;manualOnly;maxAttempts;requiredSuccesses;skyAccess", "FIELD:Lnet/minecraft/gametest/framework/TestData;->environment:Ljava/lang/Object;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->structure:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->setupTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->required:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->rotation:Lnet/minecraft/world/level/block/Rotation;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->manualOnly:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxAttempts:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->requiredSuccesses:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->skyAccess:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TestData.class), TestData.class, "environment;structure;maxTicks;setupTicks;required;rotation;manualOnly;maxAttempts;requiredSuccesses;skyAccess", "FIELD:Lnet/minecraft/gametest/framework/TestData;->environment:Ljava/lang/Object;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->structure:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->setupTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->required:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->rotation:Lnet/minecraft/world/level/block/Rotation;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->manualOnly:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxAttempts:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->requiredSuccesses:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->skyAccess:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TestData.class, Object.class), TestData.class, "environment;structure;maxTicks;setupTicks;required;rotation;manualOnly;maxAttempts;requiredSuccesses;skyAccess", "FIELD:Lnet/minecraft/gametest/framework/TestData;->environment:Ljava/lang/Object;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->structure:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->setupTicks:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->required:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->rotation:Lnet/minecraft/world/level/block/Rotation;", "FIELD:Lnet/minecraft/gametest/framework/TestData;->manualOnly:Z", "FIELD:Lnet/minecraft/gametest/framework/TestData;->maxAttempts:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->requiredSuccesses:I", "FIELD:Lnet/minecraft/gametest/framework/TestData;->skyAccess:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public EnvironmentType environment() {
        return this.environment;
    }

    public Identifier structure() {
        return this.structure;
    }

    public int maxTicks() {
        return this.maxTicks;
    }

    public int setupTicks() {
        return this.setupTicks;
    }

    public boolean required() {
        return this.required;
    }

    public Rotation rotation() {
        return this.rotation;
    }

    public boolean manualOnly() {
        return this.manualOnly;
    }

    public int maxAttempts() {
        return this.maxAttempts;
    }

    public int requiredSuccesses() {
        return this.requiredSuccesses;
    }

    public boolean skyAccess() {
        return this.skyAccess;
    }

    public TestData(EnvironmentType $$0, Identifier $$1, int $$2, int $$3, boolean $$4, Rotation $$5) {
        this($$0, $$1, $$2, $$3, $$4, $$5, false, 1, 1, false);
    }

    public TestData(EnvironmentType $$0, Identifier $$1, int $$2, int $$3, boolean $$4) {
        this($$0, $$1, $$2, $$3, $$4, Rotation.NONE);
    }

    public <T> TestData<T> map(Function<EnvironmentType, T> $$0) {
        return new TestData<>($$0.apply(this.environment), this.structure, this.maxTicks, this.setupTicks, this.required, this.rotation, this.manualOnly, this.maxAttempts, this.requiredSuccesses, this.skyAccess);
    }
}
