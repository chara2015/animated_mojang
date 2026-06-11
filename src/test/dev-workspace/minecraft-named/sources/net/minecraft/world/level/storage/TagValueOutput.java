package net.minecraft.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.lang.runtime.SwitchBootstraps;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.ValueOutput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput.class */
public class TagValueOutput implements ValueOutput {
    private final ProblemReporter problemReporter;
    private final DynamicOps<Tag> ops;
    private final CompoundTag output;

    TagValueOutput(ProblemReporter $$0, DynamicOps<Tag> $$1, CompoundTag $$2) {
        this.problemReporter = $$0;
        this.ops = $$1;
        this.output = $$2;
    }

    public static TagValueOutput createWithContext(ProblemReporter $$0, HolderLookup.Provider $$1) {
        return new TagValueOutput($$0, $$1.createSerializationContext(NbtOps.INSTANCE), new CompoundTag());
    }

    public static TagValueOutput createWithoutContext(ProblemReporter $$0) {
        return new TagValueOutput($$0, NbtOps.INSTANCE, new CompoundTag());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.level.storage.ValueOutput
    public <T> void store(String $$0, Codec<T> $$1, T $$2) throws MatchException {
        DataResult.Success<Tag> successEncodeStart = $$1.encodeStart(this.ops, $$2);
        Objects.requireNonNull(successEncodeStart);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(successEncodeStart, 0) /* invoke-custom */) {
            case 0:
                DataResult.Success<Tag> $$3 = successEncodeStart;
                this.output.put($$0, (Tag) $$3.value());
                return;
            case 1:
                DataResult.Error<Tag> $$4 = (DataResult.Error) successEncodeStart;
                this.problemReporter.report(new EncodeToFieldFailedProblem($$0, $$2, $$4));
                $$4.partialValue().ifPresent($$12 -> {
                    this.output.put($$0, $$12);
                });
                return;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.level.storage.ValueOutput
    public <T> void storeNullable(String $$0, Codec<T> $$1, T $$2) throws MatchException {
        if ($$2 != null) {
            store($$0, $$1, $$2);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.level.storage.ValueOutput
    public <T> void store(MapCodec<T> $$0, T $$1) throws MatchException {
        DataResult.Success<Tag> successEncodeStart = $$0.encoder().encodeStart(this.ops, $$1);
        Objects.requireNonNull(successEncodeStart);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(successEncodeStart, 0) /* invoke-custom */) {
            case 0:
                DataResult.Success<Tag> $$2 = successEncodeStart;
                this.output.merge((CompoundTag) $$2.value());
                return;
            case 1:
                DataResult.Error<Tag> $$3 = (DataResult.Error) successEncodeStart;
                this.problemReporter.report(new EncodeToMapFailedProblem($$1, $$3));
                $$3.partialValue().ifPresent($$02 -> {
                    this.output.merge((CompoundTag) $$02);
                });
                return;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putBoolean(String $$0, boolean $$1) {
        this.output.putBoolean($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putByte(String $$0, byte $$1) {
        this.output.putByte($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putShort(String $$0, short $$1) {
        this.output.putShort($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putInt(String $$0, int $$1) {
        this.output.putInt($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putLong(String $$0, long $$1) {
        this.output.putLong($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putFloat(String $$0, float $$1) {
        this.output.putFloat($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putDouble(String $$0, double $$1) {
        this.output.putDouble($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putString(String $$0, String $$1) {
        this.output.putString($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void putIntArray(String $$0, int[] $$1) {
        this.output.putIntArray($$0, $$1);
    }

    private ProblemReporter reporterForChild(String $$0) {
        return this.problemReporter.forChild(new ProblemReporter.FieldPathElement($$0));
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public ValueOutput child(String $$0) {
        CompoundTag $$1 = new CompoundTag();
        this.output.put($$0, $$1);
        return new TagValueOutput(reporterForChild($$0), this.ops, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public ValueOutput.ValueOutputList childrenList(String $$0) {
        ListTag $$1 = new ListTag();
        this.output.put($$0, $$1);
        return new ListWrapper($$0, this.problemReporter, this.ops, $$1);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public <T> ValueOutput.TypedOutputList<T> list(String $$0, Codec<T> $$1) {
        ListTag $$2 = new ListTag();
        this.output.put($$0, $$2);
        return new TypedListWrapper(this.problemReporter, $$0, this.ops, $$1, $$2);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public void discard(String $$0) {
        this.output.remove($$0);
    }

    @Override // net.minecraft.world.level.storage.ValueOutput
    public boolean isEmpty() {
        return this.output.isEmpty();
    }

    public CompoundTag buildResult() {
        return this.output;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput$ListWrapper.class */
    static class ListWrapper implements ValueOutput.ValueOutputList {
        private final String fieldName;
        private final ProblemReporter problemReporter;
        private final DynamicOps<Tag> ops;
        private final ListTag output;

        ListWrapper(String $$0, ProblemReporter $$1, DynamicOps<Tag> $$2, ListTag $$3) {
            this.fieldName = $$0;
            this.problemReporter = $$1;
            this.ops = $$2;
            this.output = $$3;
        }

        @Override // net.minecraft.world.level.storage.ValueOutput.ValueOutputList
        public ValueOutput addChild() {
            int $$0 = this.output.size();
            CompoundTag $$1 = new CompoundTag();
            this.output.add($$1);
            return new TagValueOutput(this.problemReporter.forChild(new ProblemReporter.IndexedFieldPathElement(this.fieldName, $$0)), this.ops, $$1);
        }

        @Override // net.minecraft.world.level.storage.ValueOutput.ValueOutputList
        public void discardLast() {
            this.output.removeLast();
        }

        @Override // net.minecraft.world.level.storage.ValueOutput.ValueOutputList
        public boolean isEmpty() {
            return this.output.isEmpty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput$TypedListWrapper.class */
    static class TypedListWrapper<T> implements ValueOutput.TypedOutputList<T> {
        private final ProblemReporter problemReporter;
        private final String name;
        private final DynamicOps<Tag> ops;
        private final Codec<T> codec;
        private final ListTag output;

        TypedListWrapper(ProblemReporter $$0, String $$1, DynamicOps<Tag> $$2, Codec<T> $$3, ListTag $$4) {
            this.problemReporter = $$0;
            this.name = $$1;
            this.ops = $$2;
            this.codec = $$3;
            this.output = $$4;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        @Override // net.minecraft.world.level.storage.ValueOutput.TypedOutputList
        public void add(T $$0) throws MatchException {
            DataResult.Success<Tag> successEncodeStart = this.codec.encodeStart(this.ops, $$0);
            Objects.requireNonNull(successEncodeStart);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(successEncodeStart, 0) /* invoke-custom */) {
                case 0:
                    DataResult.Success<Tag> $$1 = successEncodeStart;
                    this.output.add((Tag) $$1.value());
                    return;
                case 1:
                    DataResult.Error<Tag> $$2 = (DataResult.Error) successEncodeStart;
                    this.problemReporter.report(new EncodeToListFailedProblem(this.name, $$0, $$2));
                    Optional optionalPartialValue = $$2.partialValue();
                    ListTag listTag = this.output;
                    Objects.requireNonNull(listTag);
                    optionalPartialValue.ifPresent((v1) -> {
                        r1.add(v1);
                    });
                    return;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        @Override // net.minecraft.world.level.storage.ValueOutput.TypedOutputList
        public boolean isEmpty() {
            return this.output.isEmpty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem.class */
    public static final class EncodeToFieldFailedProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final Object value;
        private final DataResult.Error<?> error;

        public EncodeToFieldFailedProblem(String $$0, Object $$1, DataResult.Error<?> $$2) {
            this.name = $$0;
            this.value = $$1;
            this.error = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EncodeToFieldFailedProblem.class), EncodeToFieldFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EncodeToFieldFailedProblem.class), EncodeToFieldFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EncodeToFieldFailedProblem.class, Object.class), EncodeToFieldFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public Object value() {
            return this.value;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to encode value '" + String.valueOf(this.value) + "' to field '" + this.name + "': " + this.error.message();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem.class */
    public static final class EncodeToListFailedProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final Object value;
        private final DataResult.Error<?> error;

        public EncodeToListFailedProblem(String $$0, Object $$1, DataResult.Error<?> $$2) {
            this.name = $$0;
            this.value = $$1;
            this.error = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EncodeToListFailedProblem.class), EncodeToListFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EncodeToListFailedProblem.class), EncodeToListFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EncodeToListFailedProblem.class, Object.class), EncodeToListFailedProblem.class, "name;value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public Object value() {
            return this.value;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to append value '" + String.valueOf(this.value) + "' to list '" + this.name + "': " + this.error.message();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem.class */
    public static final class EncodeToMapFailedProblem extends Record implements ProblemReporter.Problem {
        private final Object value;
        private final DataResult.Error<?> error;

        public EncodeToMapFailedProblem(Object $$0, DataResult.Error<?> $$1) {
            this.value = $$0;
            this.error = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EncodeToMapFailedProblem.class), EncodeToMapFailedProblem.class, "value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EncodeToMapFailedProblem.class), EncodeToMapFailedProblem.class, "value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EncodeToMapFailedProblem.class, Object.class), EncodeToMapFailedProblem.class, "value;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/level/storage/TagValueOutput$EncodeToMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Object value() {
            return this.value;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to merge value '" + String.valueOf(this.value) + "' to an object: " + this.error.message();
        }
    }
}
