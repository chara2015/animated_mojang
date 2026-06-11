package net.minecraft.world.level.storage;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.lang.runtime.SwitchBootstraps;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.ValueInput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput.class */
public class TagValueInput implements ValueInput {
    private final ProblemReporter problemReporter;
    private final ValueInputContextHelper context;
    private final CompoundTag input;

    private TagValueInput(ProblemReporter $$0, ValueInputContextHelper $$1, CompoundTag $$2) {
        this.problemReporter = $$0;
        this.context = $$1;
        this.input = $$2;
    }

    public static ValueInput create(ProblemReporter $$0, HolderLookup.Provider $$1, CompoundTag $$2) {
        return new TagValueInput($$0, new ValueInputContextHelper($$1, NbtOps.INSTANCE), $$2);
    }

    public static ValueInput.ValueInputList create(ProblemReporter $$0, HolderLookup.Provider $$1, List<CompoundTag> $$2) {
        return new CompoundListWrapper($$0, new ValueInputContextHelper($$1, NbtOps.INSTANCE), $$2);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.level.storage.ValueInput
    public <T> Optional<T> read(String $$0, Codec<T> $$1) throws MatchException {
        Tag $$2 = this.input.get($$0);
        if ($$2 == null) {
            return Optional.empty();
        }
        DataResult.Success<T> success = $$1.parse(this.context.ops(), $$2);
        Objects.requireNonNull(success);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(success, 0) /* invoke-custom */) {
            case 0:
                DataResult.Success<T> $$3 = success;
                return Optional.of($$3.value());
            case 1:
                DataResult.Error<T> $$4 = (DataResult.Error) success;
                this.problemReporter.report(new DecodeFromFieldFailedProblem($$0, $$2, $$4));
                return $$4.partialValue();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.level.storage.ValueInput
    public <T> Optional<T> read(MapCodec<T> $$0) throws MatchException {
        DynamicOps<Tag> $$1 = this.context.ops();
        DataResult.Success<T> successFlatMap = $$1.getMap(this.input).flatMap($$2 -> {
            return $$0.decode($$1, $$2);
        });
        Objects.requireNonNull(successFlatMap);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(successFlatMap, 0) /* invoke-custom */) {
            case 0:
                DataResult.Success<T> $$22 = successFlatMap;
                return Optional.of($$22.value());
            case 1:
                DataResult.Error<T> $$3 = (DataResult.Error) successFlatMap;
                this.problemReporter.report(new DecodeFromMapFailedProblem($$3));
                return $$3.partialValue();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private <T extends Tag> T getOptionalTypedTag(String str, TagType<T> tagType) {
        T t = (T) this.input.get(str);
        if (t == null) {
            return null;
        }
        TagType<?> type = t.getType();
        if (type != tagType) {
            this.problemReporter.report(new UnexpectedTypeProblem(str, tagType, type));
            return null;
        }
        return t;
    }

    private NumericTag getNumericTag(String $$0) {
        Tag $$1 = this.input.get($$0);
        if ($$1 == null) {
            return null;
        }
        if ($$1 instanceof NumericTag) {
            NumericTag $$2 = (NumericTag) $$1;
            return $$2;
        }
        this.problemReporter.report(new UnexpectedNonNumberProblem($$0, $$1.getType()));
        return null;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<ValueInput> child(String $$0) {
        CompoundTag $$1 = (CompoundTag) getOptionalTypedTag($$0, CompoundTag.TYPE);
        return $$1 != null ? Optional.of(wrapChild($$0, $$1)) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public ValueInput childOrEmpty(String $$0) {
        CompoundTag $$1 = (CompoundTag) getOptionalTypedTag($$0, CompoundTag.TYPE);
        return $$1 != null ? wrapChild($$0, $$1) : this.context.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<ValueInput.ValueInputList> childrenList(String $$0) {
        ListTag $$1 = (ListTag) getOptionalTypedTag($$0, ListTag.TYPE);
        return $$1 != null ? Optional.of(wrapList($$0, this.context, $$1)) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public ValueInput.ValueInputList childrenListOrEmpty(String $$0) {
        ListTag $$1 = (ListTag) getOptionalTypedTag($$0, ListTag.TYPE);
        return $$1 != null ? wrapList($$0, this.context, $$1) : this.context.emptyList();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public <T> Optional<ValueInput.TypedInputList<T>> list(String $$0, Codec<T> $$1) {
        ListTag $$2 = (ListTag) getOptionalTypedTag($$0, ListTag.TYPE);
        return $$2 != null ? Optional.of(wrapTypedList($$0, $$2, $$1)) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public <T> ValueInput.TypedInputList<T> listOrEmpty(String $$0, Codec<T> $$1) {
        ListTag $$2 = (ListTag) getOptionalTypedTag($$0, ListTag.TYPE);
        return $$2 != null ? wrapTypedList($$0, $$2, $$1) : this.context.emptyTypedList();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public boolean getBooleanOr(String $$0, boolean $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.byteValue() != 0 : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public byte getByteOr(String $$0, byte $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.byteValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public int getShortOr(String $$0, short $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.shortValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<Integer> getInt(String $$0) {
        NumericTag $$1 = getNumericTag($$0);
        return $$1 != null ? Optional.of(Integer.valueOf($$1.intValue())) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public int getIntOr(String $$0, int $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.intValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public long getLongOr(String $$0, long $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.longValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<Long> getLong(String $$0) {
        NumericTag $$1 = getNumericTag($$0);
        return $$1 != null ? Optional.of(Long.valueOf($$1.longValue())) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public float getFloatOr(String $$0, float $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.floatValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public double getDoubleOr(String $$0, double $$1) {
        NumericTag $$2 = getNumericTag($$0);
        return $$2 != null ? $$2.doubleValue() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<String> getString(String $$0) {
        StringTag $$1 = (StringTag) getOptionalTypedTag($$0, StringTag.TYPE);
        return $$1 != null ? Optional.of($$1.value()) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public String getStringOr(String $$0, String $$1) {
        StringTag $$2 = (StringTag) getOptionalTypedTag($$0, StringTag.TYPE);
        return $$2 != null ? $$2.value() : $$1;
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public Optional<int[]> getIntArray(String $$0) {
        IntArrayTag $$1 = (IntArrayTag) getOptionalTypedTag($$0, IntArrayTag.TYPE);
        return $$1 != null ? Optional.of($$1.getAsIntArray()) : Optional.empty();
    }

    @Override // net.minecraft.world.level.storage.ValueInput
    public HolderLookup.Provider lookup() {
        return this.context.lookup();
    }

    private ValueInput wrapChild(String $$0, CompoundTag $$1) {
        if ($$1.isEmpty()) {
            return this.context.empty();
        }
        return new TagValueInput(this.problemReporter.forChild(new ProblemReporter.FieldPathElement($$0)), this.context, $$1);
    }

    static ValueInput wrapChild(ProblemReporter $$0, ValueInputContextHelper $$1, CompoundTag $$2) {
        return $$2.isEmpty() ? $$1.empty() : new TagValueInput($$0, $$1, $$2);
    }

    private ValueInput.ValueInputList wrapList(String $$0, ValueInputContextHelper $$1, ListTag $$2) {
        return $$2.isEmpty() ? $$1.emptyList() : new ListWrapper(this.problemReporter, $$0, $$1, $$2);
    }

    private <T> ValueInput.TypedInputList<T> wrapTypedList(String $$0, ListTag $$1, Codec<T> $$2) {
        return $$1.isEmpty() ? this.context.emptyTypedList() : new TypedListWrapper(this.problemReporter, $$0, this.context, $$2, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$ListWrapper.class */
    static class ListWrapper implements ValueInput.ValueInputList {
        private final ProblemReporter problemReporter;
        private final String name;
        final ValueInputContextHelper context;
        private final ListTag list;

        ListWrapper(ProblemReporter $$0, String $$1, ValueInputContextHelper $$2, ListTag $$3) {
            this.problemReporter = $$0;
            this.name = $$1;
            this.context = $$2;
            this.list = $$3;
        }

        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        ProblemReporter reporterForChild(int $$0) {
            return this.problemReporter.forChild(new ProblemReporter.IndexedFieldPathElement(this.name, $$0));
        }

        void reportIndexUnwrapProblem(int $$0, Tag $$1) {
            this.problemReporter.report(new UnexpectedListElementTypeProblem(this.name, $$0, CompoundTag.TYPE, $$1.getType()));
        }

        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public Stream<ValueInput> stream() {
            return Streams.mapWithIndex(this.list.stream(), ($$0, $$1) -> {
                if ($$0 instanceof CompoundTag) {
                    CompoundTag $$2 = (CompoundTag) $$0;
                    return TagValueInput.wrapChild(reporterForChild((int) $$1), this.context, $$2);
                }
                reportIndexUnwrapProblem((int) $$1, $$0);
                return null;
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            });
        }

        @Override // java.lang.Iterable
        public Iterator<ValueInput> iterator() {
            final Iterator<Tag> $$0 = this.list.iterator();
            return new AbstractIterator<ValueInput>() { // from class: net.minecraft.world.level.storage.TagValueInput.ListWrapper.1
                private int index;

                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: renamed from: computeNext, reason: merged with bridge method [inline-methods] */
                public ValueInput m4206computeNext() {
                    while ($$0.hasNext()) {
                        Tag $$02 = (Tag) $$0.next();
                        int $$1 = this.index;
                        this.index = $$1 + 1;
                        if ($$02 instanceof CompoundTag) {
                            CompoundTag $$2 = (CompoundTag) $$02;
                            return TagValueInput.wrapChild(ListWrapper.this.reporterForChild($$1), ListWrapper.this.context, $$2);
                        }
                        ListWrapper.this.reportIndexUnwrapProblem($$1, $$02);
                    }
                    return (ValueInput) endOfData();
                }
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$TypedListWrapper.class */
    static class TypedListWrapper<T> implements ValueInput.TypedInputList<T> {
        private final ProblemReporter problemReporter;
        private final String name;
        final ValueInputContextHelper context;
        final Codec<T> codec;
        private final ListTag list;

        TypedListWrapper(ProblemReporter $$0, String $$1, ValueInputContextHelper $$2, Codec<T> $$3, ListTag $$4) {
            this.problemReporter = $$0;
            this.name = $$1;
            this.context = $$2;
            this.codec = $$3;
            this.list = $$4;
        }

        @Override // net.minecraft.world.level.storage.ValueInput.TypedInputList
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        void reportIndexUnwrapProblem(int $$0, Tag $$1, DataResult.Error<?> $$2) {
            this.problemReporter.report(new DecodeFromListFailedProblem(this.name, $$0, $$1, $$2));
        }

        @Override // net.minecraft.world.level.storage.ValueInput.TypedInputList
        public Stream<T> stream() {
            return Streams.mapWithIndex(this.list.stream(), ($$0, $$1) -> {
                DataResult.Success<T> success = this.codec.parse(this.context.ops(), $$0);
                Objects.requireNonNull(success);
                switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), DataResult.Success.class, DataResult.Error.class).dynamicInvoker().invoke(success, 0) /* invoke-custom */) {
                    case 0:
                        DataResult.Success<T> $$4 = success;
                        return $$4.value();
                    case 1:
                        DataResult.Error<?> error = (DataResult.Error) success;
                        reportIndexUnwrapProblem((int) $$1, $$0, error);
                        return error.partialValue().orElse(null);
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
            }).filter(Objects::nonNull);
        }

        @Override // java.lang.Iterable
        public Iterator<T> iterator() {
            final ListIterator<Tag> $$0 = this.list.listIterator();
            return new AbstractIterator<T>() { // from class: net.minecraft.world.level.storage.TagValueInput.TypedListWrapper.1
                /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                /* JADX WARN: Removed duplicated region for block: B:4:0x000c  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                protected T computeNext() throws java.lang.MatchException {
                    /*
                        r5 = this;
                    L0:
                        r0 = r5
                        java.util.ListIterator r0 = r5
                        boolean r0 = r0.hasNext()
                        if (r0 == 0) goto La2
                        r0 = r5
                        java.util.ListIterator r0 = r5
                        int r0 = r0.nextIndex()
                        r6 = r0
                        r0 = r5
                        java.util.ListIterator r0 = r5
                        java.lang.Object r0 = r0.next()
                        net.minecraft.nbt.Tag r0 = (net.minecraft.nbt.Tag) r0
                        r7 = r0
                        r0 = r5
                        net.minecraft.world.level.storage.TagValueInput$TypedListWrapper r0 = net.minecraft.world.level.storage.TagValueInput.TypedListWrapper.this
                        com.mojang.serialization.Codec<T> r0 = r0.codec
                        r1 = r5
                        net.minecraft.world.level.storage.TagValueInput$TypedListWrapper r1 = net.minecraft.world.level.storage.TagValueInput.TypedListWrapper.this
                        net.minecraft.world.level.storage.ValueInputContextHelper r1 = r1.context
                        com.mojang.serialization.DynamicOps r1 = r1.ops()
                        r2 = r7
                        com.mojang.serialization.DataResult r0 = r0.parse(r1, r2)
                        r1 = r0
                        java.lang.Object r1 = java.util.Objects.requireNonNull(r1)
                        r8 = r0
                        r0 = 0
                        r9 = r0
                        r0 = r8
                        r1 = r9
                        int r0 = call_site(
                            {METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/runtime/SwitchBootstraps;->typeSwitch(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}
                            {STRING: "typeSwitch"}
                            {METHOD_TYPE: (Ljava/lang/Object;, I)I}
                            {TYPE: Lcom/mojang/serialization/DataResult$Success;}
                            {TYPE: Lcom/mojang/serialization/DataResult$Error;}
                        ).invoke(r0, r1)
                        switch(r0) {
                            case 0: goto L6e;
                            case 1: goto L7a;
                            default: goto L64;
                        }
                    L64:
                        java.lang.MatchException r0 = new java.lang.MatchException
                        r1 = r0
                        r2 = 0
                        r3 = 0
                        r1.<init>(r2, r3)
                        throw r0
                    L6e:
                        r0 = r8
                        com.mojang.serialization.DataResult$Success r0 = (com.mojang.serialization.DataResult.Success) r0
                        r10 = r0
                        r0 = r10
                        java.lang.Object r0 = r0.value()
                        return r0
                    L7a:
                        r0 = r8
                        com.mojang.serialization.DataResult$Error r0 = (com.mojang.serialization.DataResult.Error) r0
                        r11 = r0
                        r0 = r5
                        net.minecraft.world.level.storage.TagValueInput$TypedListWrapper r0 = net.minecraft.world.level.storage.TagValueInput.TypedListWrapper.this
                        r1 = r6
                        r2 = r7
                        r3 = r11
                        r0.reportIndexUnwrapProblem(r1, r2, r3)
                        r0 = r11
                        java.util.Optional r0 = r0.partialValue()
                        boolean r0 = r0.isPresent()
                        if (r0 == 0) goto L9f
                        r0 = r11
                        java.util.Optional r0 = r0.partialValue()
                        java.lang.Object r0 = r0.get()
                        return r0
                    L9f:
                        goto L0
                    La2:
                        r0 = r5
                        java.lang.Object r0 = r0.endOfData()
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: net.minecraft.world.level.storage.TagValueInput.TypedListWrapper.AnonymousClass1.computeNext():java.lang.Object");
                }
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$CompoundListWrapper.class */
    static class CompoundListWrapper implements ValueInput.ValueInputList {
        private final ProblemReporter problemReporter;
        private final ValueInputContextHelper context;
        private final List<CompoundTag> list;

        public CompoundListWrapper(ProblemReporter $$0, ValueInputContextHelper $$1, List<CompoundTag> $$2) {
            this.problemReporter = $$0;
            this.context = $$1;
            this.list = $$2;
        }

        ValueInput wrapChild(int $$0, CompoundTag $$1) {
            return TagValueInput.wrapChild(this.problemReporter.forChild(new ProblemReporter.IndexedPathElement($$0)), this.context, $$1);
        }

        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public Stream<ValueInput> stream() {
            return Streams.mapWithIndex(this.list.stream(), ($$0, $$1) -> {
                return wrapChild((int) $$1, $$0);
            });
        }

        @Override // java.lang.Iterable
        public Iterator<ValueInput> iterator() {
            final ListIterator<CompoundTag> $$0 = this.list.listIterator();
            return new AbstractIterator<ValueInput>() { // from class: net.minecraft.world.level.storage.TagValueInput.CompoundListWrapper.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: renamed from: computeNext, reason: merged with bridge method [inline-methods] */
                public ValueInput m4205computeNext() {
                    if ($$0.hasNext()) {
                        int $$02 = $$0.nextIndex();
                        CompoundTag $$1 = (CompoundTag) $$0.next();
                        return CompoundListWrapper.this.wrapChild($$02, $$1);
                    }
                    return (ValueInput) endOfData();
                }
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem.class */
    public static final class DecodeFromFieldFailedProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final Tag tag;
        private final DataResult.Error<?> error;

        public DecodeFromFieldFailedProblem(String $$0, Tag $$1, DataResult.Error<?> $$2) {
            this.name = $$0;
            this.tag = $$1;
            this.error = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DecodeFromFieldFailedProblem.class), DecodeFromFieldFailedProblem.class, "name;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DecodeFromFieldFailedProblem.class), DecodeFromFieldFailedProblem.class, "name;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DecodeFromFieldFailedProblem.class, Object.class), DecodeFromFieldFailedProblem.class, "name;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromFieldFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public Tag tag() {
            return this.tag;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to decode value '" + String.valueOf(this.tag) + "' from field '" + this.name + "': " + this.error.message();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem.class */
    public static final class DecodeFromListFailedProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final int index;
        private final Tag tag;
        private final DataResult.Error<?> error;

        public DecodeFromListFailedProblem(String $$0, int $$1, Tag $$2, DataResult.Error<?> $$3) {
            this.name = $$0;
            this.index = $$1;
            this.tag = $$2;
            this.error = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DecodeFromListFailedProblem.class), DecodeFromListFailedProblem.class, "name;index;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DecodeFromListFailedProblem.class), DecodeFromListFailedProblem.class, "name;index;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DecodeFromListFailedProblem.class, Object.class), DecodeFromListFailedProblem.class, "name;index;tag;error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->tag:Lnet/minecraft/nbt/Tag;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromListFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public int index() {
            return this.index;
        }

        public Tag tag() {
            return this.tag;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to decode value '" + String.valueOf(this.tag) + "' from field '" + this.name + "' at index " + this.index + "': " + this.error.message();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$DecodeFromMapFailedProblem.class */
    public static final class DecodeFromMapFailedProblem extends Record implements ProblemReporter.Problem {
        private final DataResult.Error<?> error;

        public DecodeFromMapFailedProblem(DataResult.Error<?> $$0) {
            this.error = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DecodeFromMapFailedProblem.class), DecodeFromMapFailedProblem.class, "error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DecodeFromMapFailedProblem.class), DecodeFromMapFailedProblem.class, "error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DecodeFromMapFailedProblem.class, Object.class), DecodeFromMapFailedProblem.class, "error", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$DecodeFromMapFailedProblem;->error:Lcom/mojang/serialization/DataResult$Error;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DataResult.Error<?> error() {
            return this.error;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Failed to decode from map: " + this.error.message();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem.class */
    public static final class UnexpectedTypeProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final TagType<?> expected;
        private final TagType<?> actual;

        public UnexpectedTypeProblem(String $$0, TagType<?> $$1, TagType<?> $$2) {
            this.name = $$0;
            this.expected = $$1;
            this.actual = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UnexpectedTypeProblem.class), UnexpectedTypeProblem.class, "name;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UnexpectedTypeProblem.class), UnexpectedTypeProblem.class, "name;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UnexpectedTypeProblem.class, Object.class), UnexpectedTypeProblem.class, "name;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public TagType<?> expected() {
            return this.expected;
        }

        public TagType<?> actual() {
            return this.actual;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Expected field '" + this.name + "' to contain value of type " + this.expected.getName() + ", but got " + this.actual.getName();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem.class */
    public static final class UnexpectedNonNumberProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final TagType<?> actual;

        public UnexpectedNonNumberProblem(String $$0, TagType<?> $$1) {
            this.name = $$0;
            this.actual = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UnexpectedNonNumberProblem.class), UnexpectedNonNumberProblem.class, "name;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UnexpectedNonNumberProblem.class), UnexpectedNonNumberProblem.class, "name;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UnexpectedNonNumberProblem.class, Object.class), UnexpectedNonNumberProblem.class, "name;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedNonNumberProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public TagType<?> actual() {
            return this.actual;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Expected field '" + this.name + "' to contain number, but got " + this.actual.getName();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem.class */
    public static final class UnexpectedListElementTypeProblem extends Record implements ProblemReporter.Problem {
        private final String name;
        private final int index;
        private final TagType<?> expected;
        private final TagType<?> actual;

        public UnexpectedListElementTypeProblem(String $$0, int $$1, TagType<?> $$2, TagType<?> $$3) {
            this.name = $$0;
            this.index = $$1;
            this.expected = $$2;
            this.actual = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UnexpectedListElementTypeProblem.class), UnexpectedListElementTypeProblem.class, "name;index;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UnexpectedListElementTypeProblem.class), UnexpectedListElementTypeProblem.class, "name;index;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UnexpectedListElementTypeProblem.class, Object.class), UnexpectedListElementTypeProblem.class, "name;index;expected;actual", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->index:I", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->expected:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/world/level/storage/TagValueInput$UnexpectedListElementTypeProblem;->actual:Lnet/minecraft/nbt/TagType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public int index() {
            return this.index;
        }

        public TagType<?> expected() {
            return this.expected;
        }

        public TagType<?> actual() {
            return this.actual;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Expected list '" + this.name + "' to contain at index " + this.index + " value of type " + this.expected.getName() + ", but got " + this.actual.getName();
        }
    }
}
