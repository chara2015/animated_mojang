package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation.class */
public interface ListOperation {
    public static final MapCodec<ListOperation> UNLIMITED_CODEC = codec(Integer.MAX_VALUE);

    Type mode();

    <T> List<T> apply(List<T> list, List<T> list2, int i);

    static MapCodec<ListOperation> codec(int $$0) {
        return Type.CODEC.dispatchMap("mode", (v0) -> {
            return v0.mode();
        }, $$02 -> {
            return $$02.mapCodec;
        }).validate($$1 -> {
            int $$3;
            if ($$1 instanceof ReplaceSection) {
                ReplaceSection $$2 = (ReplaceSection) $$1;
                if ($$2.size().isPresent() && ($$3 = $$2.size().get().intValue()) > $$0) {
                    return DataResult.error(() -> {
                        return "Size value too large: " + $$3 + ", max size is " + $$0;
                    });
                }
            }
            return DataResult.success($$1);
        });
    }

    default <T> List<T> apply(List<T> $$0, List<T> $$1) {
        return apply($$0, $$1, Integer.MAX_VALUE);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$Type.class */
    public enum Type implements StringRepresentable {
        REPLACE_ALL("replace_all", ReplaceAll.MAP_CODEC),
        REPLACE_SECTION("replace_section", ReplaceSection.MAP_CODEC),
        INSERT("insert", Insert.MAP_CODEC),
        APPEND("append", Append.MAP_CODEC);

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private final String id;
        final MapCodec<? extends ListOperation> mapCodec;

        Type(String $$0, MapCodec mapCodec) {
            this.id = $$0;
            this.mapCodec = mapCodec;
        }

        public MapCodec<? extends ListOperation> mapCodec() {
            return this.mapCodec;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.id;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceAll.class */
    public static class ReplaceAll implements ListOperation {
        public static final ReplaceAll INSTANCE = new ReplaceAll();
        public static final MapCodec<ReplaceAll> MAP_CODEC = MapCodec.unit(() -> {
            return INSTANCE;
        });

        private ReplaceAll() {
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public Type mode() {
            return Type.REPLACE_ALL;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public <T> List<T> apply(List<T> $$0, List<T> $$1, int $$2) {
            return $$1;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection.class */
    public static final class ReplaceSection extends Record implements ListOperation {
        private final int offset;
        private final Optional<Integer> size;
        private static final Logger LOGGER = LogUtils.getLogger();
        public static final MapCodec<ReplaceSection> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("offset", 0).forGetter((v0) -> {
                return v0.offset();
            }), ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf(StructureTemplate.SIZE_TAG).forGetter((v0) -> {
                return v0.size();
            })).apply($$0, (v1, v2) -> {
                return new ReplaceSection(v1, v2);
            });
        });

        public ReplaceSection(int $$0, Optional<Integer> $$1) {
            this.offset = $$0;
            this.size = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ReplaceSection.class), ReplaceSection.class, "offset;size", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->offset:I", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ReplaceSection.class), ReplaceSection.class, "offset;size", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->offset:I", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ReplaceSection.class, Object.class), ReplaceSection.class, "offset;size", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->offset:I", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$ReplaceSection;->size:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int offset() {
            return this.offset;
        }

        public Optional<Integer> size() {
            return this.size;
        }

        public ReplaceSection(int $$0) {
            this($$0, Optional.empty());
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public Type mode() {
            return Type.REPLACE_SECTION;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public <T> List<T> apply(List<T> $$0, List<T> $$1, int $$2) {
            int $$3 = $$0.size();
            if (this.offset > $$3) {
                LOGGER.error("Cannot replace when offset is out of bounds");
                return $$0;
            }
            ImmutableList.Builder<T> $$4 = ImmutableList.builder();
            $$4.addAll($$0.subList(0, this.offset));
            $$4.addAll($$1);
            int $$5 = this.offset + this.size.orElse(Integer.valueOf($$1.size())).intValue();
            if ($$5 < $$3) {
                $$4.addAll($$0.subList($$5, $$3));
            }
            ImmutableList immutableListBuild = $$4.build();
            if (immutableListBuild.size() > $$2) {
                LOGGER.error("Contents overflow in section replacement");
                return $$0;
            }
            return immutableListBuild;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$Insert.class */
    public static final class Insert extends Record implements ListOperation {
        private final int offset;
        private static final Logger LOGGER = LogUtils.getLogger();
        public static final MapCodec<Insert> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("offset", 0).forGetter((v0) -> {
                return v0.offset();
            })).apply($$0, (v1) -> {
                return new Insert(v1);
            });
        });

        public Insert(int $$0) {
            this.offset = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Insert.class), Insert.class, "offset", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$Insert;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Insert.class), Insert.class, "offset", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$Insert;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Insert.class, Object.class), Insert.class, "offset", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$Insert;->offset:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int offset() {
            return this.offset;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public Type mode() {
            return Type.INSERT;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public <T> List<T> apply(List<T> $$0, List<T> $$1, int $$2) {
            int $$3 = $$0.size();
            if (this.offset > $$3) {
                LOGGER.error("Cannot insert when offset is out of bounds");
                return $$0;
            }
            if ($$3 + $$1.size() > $$2) {
                LOGGER.error("Contents overflow in section insertion");
                return $$0;
            }
            ImmutableList.Builder<T> $$4 = ImmutableList.builder();
            $$4.addAll($$0.subList(0, this.offset));
            $$4.addAll($$1);
            $$4.addAll($$0.subList(this.offset, $$3));
            return $$4.build();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$Append.class */
    public static class Append implements ListOperation {
        private static final Logger LOGGER = LogUtils.getLogger();
        public static final Append INSTANCE = new Append();
        public static final MapCodec<Append> MAP_CODEC = MapCodec.unit(() -> {
            return INSTANCE;
        });

        private Append() {
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public Type mode() {
            return Type.APPEND;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.ListOperation
        public <T> List<T> apply(List<T> $$0, List<T> $$1, int $$2) {
            if ($$0.size() + $$1.size() > $$2) {
                LOGGER.error("Contents overflow in section append");
                return $$0;
            }
            return Stream.concat($$0.stream(), $$1.stream()).toList();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone.class */
    public static final class StandAlone<T> extends Record {
        private final List<T> value;
        private final ListOperation operation;

        public StandAlone(List<T> $$0, ListOperation $$1) {
            this.value = $$0;
            this.operation = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StandAlone.class), StandAlone.class, "value;operation", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->value:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->operation:Lnet/minecraft/world/level/storage/loot/functions/ListOperation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StandAlone.class), StandAlone.class, "value;operation", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->value:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->operation:Lnet/minecraft/world/level/storage/loot/functions/ListOperation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StandAlone.class, Object.class), StandAlone.class, "value;operation", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->value:Ljava/util/List;", "FIELD:Lnet/minecraft/world/level/storage/loot/functions/ListOperation$StandAlone;->operation:Lnet/minecraft/world/level/storage/loot/functions/ListOperation;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<T> value() {
            return this.value;
        }

        public ListOperation operation() {
            return this.operation;
        }

        public static <T> Codec<StandAlone<T>> codec(Codec<T> $$0, int $$1) {
            return RecordCodecBuilder.create($$2 -> {
                return $$2.group($$0.sizeLimitedListOf($$1).fieldOf("values").forGetter($$02 -> {
                    return $$02.value;
                }), ListOperation.codec($$1).forGetter($$03 -> {
                    return $$03.operation;
                })).apply($$2, StandAlone::new);
            });
        }

        public List<T> apply(List<T> $$0) {
            return this.operation.apply($$0, this.value);
        }
    }
}
