package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Crypt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ValueOutput;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtUtils.class */
public final class NbtUtils {
    public static final String SNBT_DATA_TAG = "data";
    private static final char PROPERTIES_START = '{';
    private static final char PROPERTIES_END = '}';
    private static final char KEY_VALUE_SEPARATOR = ':';
    private static final int INDENT = 2;
    private static final int NOT_FOUND = -1;
    private static final Comparator<ListTag> YXZ_LISTTAG_INT_COMPARATOR = Comparator.comparingInt($$0 -> {
        return $$0.getIntOr(1, 0);
    }).thenComparingInt($$02 -> {
        return $$02.getIntOr(0, 0);
    }).thenComparingInt($$03 -> {
        return $$03.getIntOr(2, 0);
    });
    private static final Comparator<ListTag> YXZ_LISTTAG_DOUBLE_COMPARATOR = Comparator.comparingDouble($$0 -> {
        return $$0.getDoubleOr(1, Density.SURFACE);
    }).thenComparingDouble($$02 -> {
        return $$02.getDoubleOr(0, Density.SURFACE);
    }).thenComparingDouble($$03 -> {
        return $$03.getDoubleOr(2, Density.SURFACE);
    });
    private static final Codec<ResourceKey<Block>> BLOCK_NAME_CODEC = ResourceKey.codec(Registries.BLOCK);
    private static final String ELEMENT_SEPARATOR = ",";
    private static final Splitter COMMA_SPLITTER = Splitter.on(ELEMENT_SEPARATOR);
    private static final Splitter COLON_SPLITTER = Splitter.on(':').limit(2);
    private static final Logger LOGGER = LogUtils.getLogger();

    private NbtUtils() {
    }

    @VisibleForTesting
    public static boolean compareNbt(Tag $$0, Tag $$1, boolean $$2) {
        if ($$0 == $$1 || $$0 == null) {
            return true;
        }
        if ($$1 == null || !$$0.getClass().equals($$1.getClass())) {
            return false;
        }
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$3 = (CompoundTag) $$0;
            CompoundTag $$4 = (CompoundTag) $$1;
            if ($$4.size() < $$3.size()) {
                return false;
            }
            for (Map.Entry<String, Tag> $$5 : $$3.entrySet()) {
                Tag $$6 = $$5.getValue();
                if (!compareNbt($$6, $$4.get($$5.getKey()), $$2)) {
                    return false;
                }
            }
            return true;
        }
        if ($$0 instanceof ListTag) {
            ListTag $$7 = (ListTag) $$0;
            if ($$2) {
                ListTag $$8 = (ListTag) $$1;
                if ($$7.isEmpty()) {
                    return $$8.isEmpty();
                }
                if ($$8.size() < $$7.size()) {
                    return false;
                }
                for (Tag $$9 : $$7) {
                    boolean $$10 = false;
                    Iterator<Tag> it = $$8.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Tag $$11 = it.next();
                        if (compareNbt($$9, $$11, $$2)) {
                            $$10 = true;
                            break;
                        }
                    }
                    if (!$$10) {
                        return false;
                    }
                }
                return true;
            }
        }
        return $$0.equals($$1);
    }

    public static BlockState readBlockState(HolderGetter<Block> $$0, CompoundTag $$1) {
        Optional optional = $$1.read(StateHolder.NAME_TAG, BLOCK_NAME_CODEC);
        Objects.requireNonNull($$0);
        Optional<? extends Holder<Block>> $$2 = optional.flatMap($$0::get);
        if ($$2.isEmpty()) {
            return Blocks.AIR.defaultBlockState();
        }
        Block $$3 = (Block) ((Holder) $$2.get()).value();
        BlockState $$4 = $$3.defaultBlockState();
        Optional<CompoundTag> $$5 = $$1.getCompound(StateHolder.PROPERTIES_TAG);
        if ($$5.isPresent()) {
            StateDefinition<Block, BlockState> $$6 = $$3.getStateDefinition();
            for (String $$7 : $$5.get().keySet()) {
                Property<?> $$8 = $$6.getProperty($$7);
                if ($$8 != null) {
                    $$4 = (BlockState) setValueHelper($$4, $$8, $$7, $$5.get(), $$1);
                }
            }
        }
        return $$4;
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S $$0, Property<T> $$1, String $$2, CompoundTag $$3, CompoundTag $$4) {
        Optional<String> string = $$3.getString($$2);
        Objects.requireNonNull($$1);
        Optional<U> optionalFlatMap = string.flatMap($$1::getValue);
        if (optionalFlatMap.isPresent()) {
            return (S) $$0.setValue($$1, (Comparable) optionalFlatMap.get());
        }
        LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", new Object[]{$$2, $$3.get($$2), $$4});
        return $$0;
    }

    public static CompoundTag writeBlockState(BlockState $$0) {
        CompoundTag $$1 = new CompoundTag();
        $$1.putString(StateHolder.NAME_TAG, BuiltInRegistries.BLOCK.getKey($$0.getBlock()).toString());
        Map<Property<?>, Comparable<?>> $$2 = $$0.getValues();
        if (!$$2.isEmpty()) {
            CompoundTag $$3 = new CompoundTag();
            for (Map.Entry<Property<?>, Comparable<?>> $$4 : $$2.entrySet()) {
                Property<?> $$5 = $$4.getKey();
                $$3.putString($$5.getName(), getName($$5, $$4.getValue()));
            }
            $$1.put(StateHolder.PROPERTIES_TAG, $$3);
        }
        return $$1;
    }

    public static CompoundTag writeFluidState(FluidState $$0) {
        CompoundTag $$1 = new CompoundTag();
        $$1.putString(StateHolder.NAME_TAG, BuiltInRegistries.FLUID.getKey($$0.getType()).toString());
        Map<Property<?>, Comparable<?>> $$2 = $$0.getValues();
        if (!$$2.isEmpty()) {
            CompoundTag $$3 = new CompoundTag();
            for (Map.Entry<Property<?>, Comparable<?>> $$4 : $$2.entrySet()) {
                Property<?> $$5 = $$4.getKey();
                $$3.putString($$5.getName(), getName($$5, $$4.getValue()));
            }
            $$1.put(StateHolder.PROPERTIES_TAG, $$3);
        }
        return $$1;
    }

    private static <T extends Comparable<T>> String getName(Property<T> $$0, Comparable<?> $$1) {
        return $$0.getName($$1);
    }

    public static String prettyPrint(Tag $$0) {
        return prettyPrint($$0, false);
    }

    public static String prettyPrint(Tag $$0, boolean $$1) {
        return prettyPrint(new StringBuilder(), $$0, 0, $$1).toString();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static StringBuilder prettyPrint(StringBuilder $$0, Tag $$1, int $$2, boolean $$3) throws MatchException {
        Objects.requireNonNull($$1);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), PrimitiveTag.class, EndTag.class, ByteArrayTag.class, ListTag.class, IntArrayTag.class, CompoundTag.class, LongArrayTag.class).dynamicInvoker().invoke($$1, 0) /* invoke-custom */) {
            case 0:
                PrimitiveTag $$4 = (PrimitiveTag) $$1;
                return $$0.append($$4);
            case 1:
                return $$0;
            case 2:
                ByteArrayTag $$6 = (ByteArrayTag) $$1;
                byte[] $$7 = $$6.getAsByteArray();
                int $$8 = $$7.length;
                indent($$2, $$0).append("byte[").append($$8).append("] {\n");
                if ($$3) {
                    indent($$2 + 1, $$0);
                    for (int $$9 = 0; $$9 < $$7.length; $$9++) {
                        if ($$9 != 0) {
                            $$0.append(',');
                        }
                        if ($$9 % 16 == 0 && $$9 / 16 > 0) {
                            $$0.append('\n');
                            if ($$9 < $$7.length) {
                                indent($$2 + 1, $$0);
                            }
                        } else if ($$9 != 0) {
                            $$0.append(' ');
                        }
                        $$0.append(String.format(Locale.ROOT, "0x%02X", Integer.valueOf($$7[$$9] & 255)));
                    }
                } else {
                    indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true");
                }
                $$0.append('\n');
                indent($$2, $$0).append('}');
                return $$0;
            case 3:
                ListTag $$10 = (ListTag) $$1;
                int $$11 = $$10.size();
                indent($$2, $$0).append("list").append("[").append($$11).append("] [");
                if ($$11 != 0) {
                    $$0.append('\n');
                }
                for (int $$12 = 0; $$12 < $$11; $$12++) {
                    if ($$12 != 0) {
                        $$0.append(",\n");
                    }
                    indent($$2 + 1, $$0);
                    prettyPrint($$0, $$10.get($$12), $$2 + 1, $$3);
                }
                if ($$11 != 0) {
                    $$0.append('\n');
                }
                indent($$2, $$0).append(']');
                return $$0;
            case 4:
                IntArrayTag $$13 = (IntArrayTag) $$1;
                int[] $$14 = $$13.getAsIntArray();
                int $$15 = 0;
                for (int $$16 : $$14) {
                    $$15 = Math.max($$15, String.format(Locale.ROOT, "%X", Integer.valueOf($$16)).length());
                }
                int $$17 = $$14.length;
                indent($$2, $$0).append("int[").append($$17).append("] {\n");
                if ($$3) {
                    indent($$2 + 1, $$0);
                    for (int $$18 = 0; $$18 < $$14.length; $$18++) {
                        if ($$18 != 0) {
                            $$0.append(',');
                        }
                        if ($$18 % 16 == 0 && $$18 / 16 > 0) {
                            $$0.append('\n');
                            if ($$18 < $$14.length) {
                                indent($$2 + 1, $$0);
                            }
                        } else if ($$18 != 0) {
                            $$0.append(' ');
                        }
                        $$0.append(String.format(Locale.ROOT, "0x%0" + $$15 + "X", Integer.valueOf($$14[$$18])));
                    }
                } else {
                    indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true");
                }
                $$0.append('\n');
                indent($$2, $$0).append('}');
                return $$0;
            case 5:
                CompoundTag $$19 = (CompoundTag) $$1;
                List<String> $$20 = Lists.newArrayList($$19.keySet());
                Collections.sort($$20);
                indent($$2, $$0).append('{');
                if ($$0.length() - $$0.lastIndexOf(Crypt.MIME_LINE_SEPARATOR) > 2 * ($$2 + 1)) {
                    $$0.append('\n');
                    indent($$2 + 1, $$0);
                }
                int $$21 = $$20.stream().mapToInt((v0) -> {
                    return v0.length();
                }).max().orElse(0);
                String $$22 = Strings.repeat(" ", $$21);
                for (int $$23 = 0; $$23 < $$20.size(); $$23++) {
                    if ($$23 != 0) {
                        $$0.append(",\n");
                    }
                    String $$24 = $$20.get($$23);
                    indent($$2 + 1, $$0).append('\"').append($$24).append('\"').append((CharSequence) $$22, 0, $$22.length() - $$24.length()).append(": ");
                    prettyPrint($$0, $$19.get($$24), $$2 + 1, $$3);
                }
                if (!$$20.isEmpty()) {
                    $$0.append('\n');
                }
                indent($$2, $$0).append('}');
                return $$0;
            case 6:
                LongArrayTag $$25 = (LongArrayTag) $$1;
                long[] $$26 = $$25.getAsLongArray();
                long $$27 = 0;
                for (long $$28 : $$26) {
                    $$27 = Math.max($$27, String.format(Locale.ROOT, "%X", Long.valueOf($$28)).length());
                }
                long $$29 = $$26.length;
                indent($$2, $$0).append("long[").append($$29).append("] {\n");
                if ($$3) {
                    indent($$2 + 1, $$0);
                    for (int $$30 = 0; $$30 < $$26.length; $$30++) {
                        if ($$30 != 0) {
                            $$0.append(',');
                        }
                        if ($$30 % 16 == 0 && $$30 / 16 > 0) {
                            $$0.append('\n');
                            if ($$30 < $$26.length) {
                                indent($$2 + 1, $$0);
                            }
                        } else if ($$30 != 0) {
                            $$0.append(' ');
                        }
                        $$0.append(String.format(Locale.ROOT, "0x%0" + $$27 + "X", Long.valueOf($$26[$$30])));
                    }
                } else {
                    indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true");
                }
                $$0.append('\n');
                indent($$2, $$0).append('}');
                return $$0;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private static StringBuilder indent(int $$0, StringBuilder $$1) {
        int $$2 = $$1.lastIndexOf(Crypt.MIME_LINE_SEPARATOR) + 1;
        int $$3 = $$1.length() - $$2;
        for (int $$4 = 0; $$4 < (2 * $$0) - $$3; $$4++) {
            $$1.append(' ');
        }
        return $$1;
    }

    public static Component toPrettyComponent(Tag $$0) {
        return new TextComponentTagVisitor("").visit($$0);
    }

    public static String structureToSnbt(CompoundTag $$0) {
        return new SnbtPrinterTagVisitor().visit(packStructureTemplate($$0));
    }

    public static CompoundTag snbtToStructure(String $$0) throws CommandSyntaxException {
        return unpackStructureTemplate(TagParser.parseCompoundFully($$0));
    }

    @VisibleForTesting
    static CompoundTag packStructureTemplate(CompoundTag $$0) {
        ListTag $$3;
        Optional<ListTag> $$1 = $$0.getList(StructureTemplate.PALETTE_LIST_TAG);
        if ($$1.isPresent()) {
            $$3 = $$1.get().getListOrEmpty(0);
        } else {
            $$3 = $$0.getListOrEmpty(StructureTemplate.PALETTE_TAG);
        }
        ListTag $$4 = (ListTag) $$3.compoundStream().map(NbtUtils::packBlockState).map(StringTag::valueOf).collect(Collectors.toCollection(ListTag::new));
        $$0.put(StructureTemplate.PALETTE_TAG, $$4);
        if ($$1.isPresent()) {
            ListTag $$5 = new ListTag();
            $$1.get().stream().flatMap($$02 -> {
                return $$02.asList().stream();
            }).forEach($$2 -> {
                CompoundTag $$32 = new CompoundTag();
                for (int $$42 = 0; $$42 < $$2.size(); $$42++) {
                    $$32.putString($$4.getString($$42).orElseThrow(), packBlockState($$2.getCompound($$42).orElseThrow()));
                }
                $$5.add($$32);
            });
            $$0.put(StructureTemplate.PALETTE_LIST_TAG, $$5);
        }
        Optional<ListTag> $$6 = $$0.getList(StructureTemplate.ENTITIES_TAG);
        if ($$6.isPresent()) {
            ListTag $$7 = (ListTag) $$6.get().compoundStream().sorted(Comparator.comparing($$03 -> {
                return $$03.getList("pos");
            }, Comparators.emptiesLast(YXZ_LISTTAG_DOUBLE_COMPARATOR))).collect(Collectors.toCollection(ListTag::new));
            $$0.put(StructureTemplate.ENTITIES_TAG, $$7);
        }
        ListTag $$8 = (ListTag) $$0.getList(StructureTemplate.BLOCKS_TAG).stream().flatMap((v0) -> {
            return v0.compoundStream();
        }).sorted(Comparator.comparing($$04 -> {
            return $$04.getList("pos");
        }, Comparators.emptiesLast(YXZ_LISTTAG_INT_COMPARATOR))).peek($$12 -> {
            $$12.putString(StructureTemplate.BLOCK_TAG_STATE, $$4.getString($$12.getIntOr(StructureTemplate.BLOCK_TAG_STATE, 0)).orElseThrow());
        }).collect(Collectors.toCollection(ListTag::new));
        $$0.put("data", $$8);
        $$0.remove(StructureTemplate.BLOCKS_TAG);
        return $$0;
    }

    @VisibleForTesting
    static CompoundTag unpackStructureTemplate(CompoundTag $$0) {
        ListTag $$1 = $$0.getListOrEmpty(StructureTemplate.PALETTE_TAG);
        Map<String, Tag> $$2 = (Map) $$1.stream().flatMap($$02 -> {
            return $$02.asString().stream();
        }).collect(ImmutableMap.toImmutableMap(Function.identity(), NbtUtils::unpackBlockState));
        Optional<ListTag> $$3 = $$0.getList(StructureTemplate.PALETTE_LIST_TAG);
        if ($$3.isPresent()) {
            $$0.put(StructureTemplate.PALETTE_LIST_TAG, (Tag) $$3.get().compoundStream().map($$12 -> {
                return (ListTag) $$2.keySet().stream().map($$12 -> {
                    return $$12.getString($$12).orElseThrow();
                }).map(NbtUtils::unpackBlockState).collect(Collectors.toCollection(ListTag::new));
            }).collect(Collectors.toCollection(ListTag::new)));
            $$0.remove(StructureTemplate.PALETTE_TAG);
        } else {
            $$0.put(StructureTemplate.PALETTE_TAG, (Tag) $$2.values().stream().collect(Collectors.toCollection(ListTag::new)));
        }
        Optional<ListTag> $$4 = $$0.getList("data");
        if ($$4.isPresent()) {
            Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
            object2IntOpenHashMap.defaultReturnValue(-1);
            for (int $$6 = 0; $$6 < $$1.size(); $$6++) {
                object2IntOpenHashMap.put($$1.getString($$6).orElseThrow(), $$6);
            }
            ListTag $$7 = $$4.get();
            for (int $$8 = 0; $$8 < $$7.size(); $$8++) {
                CompoundTag $$9 = $$7.getCompound($$8).orElseThrow();
                String $$10 = $$9.getString(StructureTemplate.BLOCK_TAG_STATE).orElseThrow();
                int $$11 = object2IntOpenHashMap.getInt($$10);
                if ($$11 == -1) {
                    throw new IllegalStateException("Entry " + $$10 + " missing from palette");
                }
                $$9.putInt(StructureTemplate.BLOCK_TAG_STATE, $$11);
            }
            $$0.put(StructureTemplate.BLOCKS_TAG, $$7);
            $$0.remove("data");
        }
        return $$0;
    }

    @VisibleForTesting
    static String packBlockState(CompoundTag $$0) {
        StringBuilder $$1 = new StringBuilder($$0.getString(StateHolder.NAME_TAG).orElseThrow());
        $$0.getCompound(StateHolder.PROPERTIES_TAG).ifPresent($$12 -> {
            String $$2 = (String) $$12.entrySet().stream().sorted(Map.Entry.comparingByKey()).map($$02 -> {
                return ((String) $$02.getKey()) + ":" + ((Tag) $$02.getValue()).asString().orElseThrow();
            }).collect(Collectors.joining(ELEMENT_SEPARATOR));
            $$1.append('{').append($$2).append('}');
        });
        return $$1.toString();
    }

    @VisibleForTesting
    static CompoundTag unpackBlockState(String $$0) {
        String $$6;
        CompoundTag $$1 = new CompoundTag();
        int $$2 = $$0.indexOf(PROPERTIES_START);
        if ($$2 >= 0) {
            $$6 = $$0.substring(0, $$2);
            CompoundTag $$4 = new CompoundTag();
            if ($$2 + 2 <= $$0.length()) {
                String $$5 = $$0.substring($$2 + 1, $$0.indexOf(PROPERTIES_END, $$2));
                COMMA_SPLITTER.split($$5).forEach($$22 -> {
                    List<String> $$3 = COLON_SPLITTER.splitToList($$22);
                    if ($$3.size() == 2) {
                        $$4.putString($$3.get(0), $$3.get(1));
                    } else {
                        LOGGER.error("Something went wrong parsing: '{}' -- incorrect gamedata!", $$0);
                    }
                });
                $$1.put(StateHolder.PROPERTIES_TAG, $$4);
            }
        } else {
            $$6 = $$0;
        }
        $$1.putString(StateHolder.NAME_TAG, $$6);
        return $$1;
    }

    public static CompoundTag addCurrentDataVersion(CompoundTag $$0) {
        int $$1 = SharedConstants.getCurrentVersion().dataVersion().version();
        return addDataVersion($$0, $$1);
    }

    public static CompoundTag addDataVersion(CompoundTag $$0, int $$1) {
        $$0.putInt(SharedConstants.DATA_VERSION_TAG, $$1);
        return $$0;
    }

    public static Dynamic<Tag> addCurrentDataVersion(Dynamic<Tag> $$0) {
        int $$1 = SharedConstants.getCurrentVersion().dataVersion().version();
        return addDataVersion($$0, $$1);
    }

    public static Dynamic<Tag> addDataVersion(Dynamic<Tag> $$0, int $$1) {
        return $$0.set(SharedConstants.DATA_VERSION_TAG, $$0.createInt($$1));
    }

    public static void addCurrentDataVersion(ValueOutput $$0) {
        int $$1 = SharedConstants.getCurrentVersion().dataVersion().version();
        addDataVersion($$0, $$1);
    }

    public static void addDataVersion(ValueOutput $$0, int $$1) {
        $$0.putInt(SharedConstants.DATA_VERSION_TAG, $$1);
    }

    public static int getDataVersion(CompoundTag $$0) {
        return getDataVersion($$0, -1);
    }

    public static int getDataVersion(CompoundTag $$0, int $$1) {
        return $$0.getIntOr(SharedConstants.DATA_VERSION_TAG, $$1);
    }

    public static int getDataVersion(Dynamic<?> $$0, int $$1) {
        return $$0.get(SharedConstants.DATA_VERSION_TAG).asInt($$1);
    }
}
