package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/ListTag.class */
public final class ListTag extends AbstractList<Tag> implements CollectionTag {
    private static final String WRAPPER_MARKER = "";
    private static final int SELF_SIZE_IN_BYTES = 36;
    public static final TagType<ListTag> TYPE = new TagType.VariableSize<ListTag>() { // from class: net.minecraft.nbt.ListTag.1
        @Override // net.minecraft.nbt.TagType
        public ListTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.pushDepth();
            try {
                ListTag listTagLoadList = loadList($$0, $$1);
                $$1.popDepth();
                return listTagLoadList;
            } catch (Throwable th) {
                $$1.popDepth();
                throw th;
            }
        }

        private static ListTag loadList(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(36L);
            byte $$2 = $$0.readByte();
            int $$3 = readListCount($$0);
            if ($$2 == 0 && $$3 > 0) {
                throw new NbtFormatException("Missing type on ListTag");
            }
            $$1.accountBytes(4L, $$3);
            TagType<?> $$4 = TagTypes.getType($$2);
            ListTag $$5 = new ListTag(new ArrayList($$3));
            for (int $$6 = 0; $$6 < $$3; $$6++) {
                $$5.addAndUnwrap($$4.load($$0, $$1));
            }
            return $$5;
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            $$2.pushDepth();
            try {
                StreamTagVisitor.ValueResult list = parseList($$0, $$1, $$2);
                $$2.popDepth();
                return list;
            } catch (Throwable th) {
                $$2.popDepth();
                throw th;
            }
        }

        private static StreamTagVisitor.ValueResult parseList(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            $$2.accountBytes(36L);
            TagType<?> $$3 = TagTypes.getType($$0.readByte());
            int $$4 = readListCount($$0);
            switch (AnonymousClass2.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$ValueResult[$$1.visitList($$3, $$4).ordinal()]) {
                case 1:
                    return StreamTagVisitor.ValueResult.HALT;
                case 2:
                    $$3.skip($$0, $$4, $$2);
                    return $$1.visitContainerEnd();
                default:
                    $$2.accountBytes(4L, $$4);
                    int $$5 = 0;
                    while (true) {
                        if ($$5 < $$4) {
                            switch (AnonymousClass2.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$EntryResult[$$1.visitElement($$3, $$5).ordinal()]) {
                                case 1:
                                    return StreamTagVisitor.ValueResult.HALT;
                                case 2:
                                    $$3.skip($$0, $$2);
                                    break;
                                case 3:
                                    $$3.skip($$0, $$2);
                                    continue;
                                    $$5++;
                                    break;
                                default:
                                    switch (AnonymousClass2.$SwitchMap$net$minecraft$nbt$StreamTagVisitor$ValueResult[$$3.parse($$0, $$1, $$2).ordinal()]) {
                                        case 1:
                                            return StreamTagVisitor.ValueResult.HALT;
                                        case 2:
                                            break;
                                        default:
                                            $$5++;
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                    int $$6 = ($$4 - 1) - $$5;
                    if ($$6 > 0) {
                        $$3.skip($$0, $$6, $$2);
                    }
                    return $$1.visitContainerEnd();
            }
        }

        private static int readListCount(DataInput $$0) throws IOException {
            int $$1 = $$0.readInt();
            if ($$1 < 0) {
                throw new NbtFormatException("ListTag length cannot be negative: " + $$1);
            }
            return $$1;
        }

        @Override // net.minecraft.nbt.TagType
        public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.pushDepth();
            try {
                TagType<?> $$2 = TagTypes.getType($$0.readByte());
                int $$3 = $$0.readInt();
                $$2.skip($$0, $$3, $$1);
                $$1.popDepth();
            } catch (Throwable th) {
                $$1.popDepth();
                throw th;
            }
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "LIST";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_List";
        }
    };
    private final List<Tag> list;

    public ListTag() {
        this(new ArrayList());
    }

    ListTag(List<Tag> $$0) {
        this.list = $$0;
    }

    private static Tag tryUnwrap(CompoundTag $$0) {
        Tag $$1;
        if ($$0.size() == 1 && ($$1 = $$0.get("")) != null) {
            return $$1;
        }
        return $$0;
    }

    private static boolean isWrapper(CompoundTag $$0) {
        return $$0.size() == 1 && $$0.contains("");
    }

    private static Tag wrapIfNeeded(byte $$0, Tag $$1) {
        if ($$0 != 10) {
            return $$1;
        }
        if ($$1 instanceof CompoundTag) {
            CompoundTag $$2 = (CompoundTag) $$1;
            if (!isWrapper($$2)) {
                return $$2;
            }
        }
        return wrapElement($$1);
    }

    private static CompoundTag wrapElement(Tag $$0) {
        return new CompoundTag(Map.of("", $$0));
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        byte $$1 = identifyRawElementType();
        $$0.writeByte($$1);
        $$0.writeInt(this.list.size());
        for (Tag $$2 : this.list) {
            wrapIfNeeded($$1, $$2).write($$0);
        }
    }

    @VisibleForTesting
    byte identifyRawElementType() {
        byte $$0 = 0;
        for (Tag $$1 : this.list) {
            byte $$2 = $$1.getId();
            if ($$0 == 0) {
                $$0 = $$2;
            } else if ($$0 != $$2) {
                return (byte) 10;
            }
        }
        return $$0;
    }

    public void addAndUnwrap(Tag $$0) {
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$1 = (CompoundTag) $$0;
            add(tryUnwrap($$1));
        } else {
            add($$0);
        }
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        int $$0 = 36 + (4 * this.list.size());
        for (Tag $$1 : this.list) {
            $$0 += $$1.sizeInBytes();
        }
        return $$0;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 9;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<ListTag> getType() {
        return TYPE;
    }

    @Override // java.util.AbstractCollection, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitList(this);
        return $$0.build();
    }

    @Override // java.util.AbstractList, java.util.List
    public Tag remove(int $$0) {
        return this.list.remove($$0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, net.minecraft.nbt.CollectionTag
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Optional<CompoundTag> getCompound(int $$0) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof CompoundTag) {
            CompoundTag $$1 = (CompoundTag) nullable;
            return Optional.of($$1);
        }
        return Optional.empty();
    }

    public CompoundTag getCompoundOrEmpty(int $$0) {
        return getCompound($$0).orElseGet(CompoundTag::new);
    }

    public Optional<ListTag> getList(int $$0) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof ListTag) {
            ListTag $$1 = (ListTag) nullable;
            return Optional.of($$1);
        }
        return Optional.empty();
    }

    public ListTag getListOrEmpty(int $$0) {
        return getList($$0).orElseGet(ListTag::new);
    }

    public Optional<Short> getShort(int $$0) {
        return getOptional($$0).flatMap((v0) -> {
            return v0.asShort();
        });
    }

    public short getShortOr(int $$0, short $$1) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof NumericTag) {
            NumericTag $$2 = (NumericTag) nullable;
            return $$2.shortValue();
        }
        return $$1;
    }

    public Optional<Integer> getInt(int $$0) {
        return getOptional($$0).flatMap((v0) -> {
            return v0.asInt();
        });
    }

    public int getIntOr(int $$0, int $$1) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof NumericTag) {
            NumericTag $$2 = (NumericTag) nullable;
            return $$2.intValue();
        }
        return $$1;
    }

    public Optional<int[]> getIntArray(int $$0) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof IntArrayTag) {
            IntArrayTag $$1 = (IntArrayTag) nullable;
            return Optional.of($$1.getAsIntArray());
        }
        return Optional.empty();
    }

    public Optional<long[]> getLongArray(int $$0) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof LongArrayTag) {
            LongArrayTag $$1 = (LongArrayTag) nullable;
            return Optional.of($$1.getAsLongArray());
        }
        return Optional.empty();
    }

    public Optional<Double> getDouble(int $$0) {
        return getOptional($$0).flatMap((v0) -> {
            return v0.asDouble();
        });
    }

    public double getDoubleOr(int $$0, double $$1) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof NumericTag) {
            NumericTag $$2 = (NumericTag) nullable;
            return $$2.doubleValue();
        }
        return $$1;
    }

    public Optional<Float> getFloat(int $$0) {
        return getOptional($$0).flatMap((v0) -> {
            return v0.asFloat();
        });
    }

    public float getFloatOr(int $$0, float $$1) {
        Tag nullable = getNullable($$0);
        if (nullable instanceof NumericTag) {
            NumericTag $$2 = (NumericTag) nullable;
            return $$2.floatValue();
        }
        return $$1;
    }

    public Optional<String> getString(int $$0) {
        return getOptional($$0).flatMap((v0) -> {
            return v0.asString();
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public String getStringOr(int $$0, String $$1) throws MatchException {
        Tag $$2 = getNullable($$0);
        if ($$2 instanceof StringTag) {
            try {
                String $$3 = ((StringTag) $$2).value();
                return $$3;
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        return $$1;
    }

    private Tag getNullable(int $$0) {
        if ($$0 < 0 || $$0 >= this.list.size()) {
            return null;
        }
        return this.list.get($$0);
    }

    private Optional<Tag> getOptional(int $$0) {
        return Optional.ofNullable(getNullable($$0));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, net.minecraft.nbt.CollectionTag
    public int size() {
        return this.list.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public Tag get(int $$0) {
        return this.list.get($$0);
    }

    @Override // java.util.AbstractList, java.util.List
    public Tag set(int $$0, Tag $$1) {
        return this.list.set($$0, $$1);
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int $$0, Tag $$1) {
        this.list.add($$0, $$1);
    }

    @Override // net.minecraft.nbt.CollectionTag
    public boolean setTag(int $$0, Tag $$1) {
        this.list.set($$0, $$1);
        return true;
    }

    @Override // net.minecraft.nbt.CollectionTag
    public boolean addTag(int $$0, Tag $$1) {
        this.list.add($$0, $$1);
        return true;
    }

    @Override // net.minecraft.nbt.Tag
    public ListTag copy() {
        List<Tag> $$0 = new ArrayList<>(this.list.size());
        for (Tag $$1 : this.list) {
            $$0.add($$1.copy());
        }
        return new ListTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public Optional<ListTag> asList() {
        return Optional.of(this);
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        return ($$0 instanceof ListTag) && Objects.equals(this.list, ((ListTag) $$0).list);
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override // java.util.Collection, net.minecraft.nbt.CollectionTag
    public Stream<Tag> stream() {
        return super.stream();
    }

    public Stream<CompoundTag> compoundStream() {
        return stream().mapMulti(($$0, $$1) -> {
            if ($$0 instanceof CompoundTag) {
                CompoundTag $$2 = (CompoundTag) $$0;
                $$1.accept($$2);
            }
        });
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitList(this);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, net.minecraft.nbt.CollectionTag
    public void clear() {
        this.list.clear();
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        byte $$1 = identifyRawElementType();
        switch ($$0.visitList(TagTypes.getType($$1), this.list.size())) {
            case HALT:
                return StreamTagVisitor.ValueResult.HALT;
            case BREAK:
                return $$0.visitContainerEnd();
            default:
                for (int $$2 = 0; $$2 < this.list.size(); $$2++) {
                    wrapIfNeeded($$1, this.list.get($$2));
                    switch ($$0.visitElement($$3.getType(), $$2)) {
                        case HALT:
                            return StreamTagVisitor.ValueResult.HALT;
                        case BREAK:
                            return $$0.visitContainerEnd();
                        case SKIP:
                            break;
                        default:
                            switch ($$3.accept($$0)) {
                                case HALT:
                                    return StreamTagVisitor.ValueResult.HALT;
                                case BREAK:
                                    return $$0.visitContainerEnd();
                            }
                    }
                }
                return $$0.visitContainerEnd();
        }
    }
}
