package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.util.datafix.PackedBitStorage;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LeavesFix.class */
public class LeavesFix extends DataFix {
    private static final int NORTH_WEST_MASK = 128;
    private static final int WEST_MASK = 64;
    private static final int SOUTH_WEST_MASK = 32;
    private static final int SOUTH_MASK = 16;
    private static final int SOUTH_EAST_MASK = 8;
    private static final int EAST_MASK = 4;
    private static final int NORTH_EAST_MASK = 2;
    private static final int NORTH_MASK = 1;
    private static final int DECAY_DISTANCE = 7;
    private static final int SIZE_BITS = 12;
    private static final int SIZE = 4096;
    private static final int[][] DIRECTIONS = {new int[]{-1, 0, 0}, new int[]{1, 0, 0}, new int[]{0, -1, 0}, new int[]{0, 1, 0}, new int[]{0, 0, -1}, new int[]{0, 0, 1}};
    static final Object2IntMap<String> LEAVES = (Object2IntMap) DataFixUtils.make(new Object2IntOpenHashMap(), $$0 -> {
        $$0.put("minecraft:acacia_leaves", 0);
        $$0.put("minecraft:birch_leaves", 1);
        $$0.put("minecraft:dark_oak_leaves", 2);
        $$0.put("minecraft:jungle_leaves", 3);
        $$0.put("minecraft:oak_leaves", 4);
        $$0.put("minecraft:spruce_leaves", 5);
    });
    static final Set<String> LOGS = ImmutableSet.of("minecraft:acacia_bark", "minecraft:birch_bark", "minecraft:dark_oak_bark", "minecraft:jungle_bark", "minecraft:oak_bark", "minecraft:spruce_bark", new String[]{"minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log"});

    public LeavesFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.CHUNK);
        OpticFinder<?> $$1 = $$0.findField("Level");
        OpticFinder<?> $$2 = $$1.type().findField("Sections");
        List.ListType listTypeType = $$2.type();
        if (!(listTypeType instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type<?> $$4 = listTypeType.getElement();
        OpticFinder<?> $$5 = DSL.typeFinder($$4);
        return fixTypeEverywhereTyped("Leaves fix", $$0, $$3 -> {
            return $$3.updateTyped($$1, $$22 -> {
                int[] $$3 = {0};
                Typed<?> $$42 = $$22.updateTyped($$2, $$22 -> {
                    LeavesSection $$23;
                    Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap((Map) $$22.getAllTyped($$5).stream().map($$02 -> {
                        return new LeavesSection($$02, getInputSchema());
                    }).collect(Collectors.toMap((v0) -> {
                        return v0.getIndex();
                    }, $$03 -> {
                        return $$03;
                    })));
                    if (int2ObjectOpenHashMap.values().stream().allMatch((v0) -> {
                        return v0.isSkippable();
                    })) {
                        return $$22;
                    }
                    java.util.List<IntSet> $$43 = Lists.newArrayList();
                    for (int $$52 = 0; $$52 < 7; $$52++) {
                        $$43.add(new IntOpenHashSet());
                    }
                    ObjectIterator it = int2ObjectOpenHashMap.values().iterator();
                    while (it.hasNext()) {
                        LeavesSection $$6 = (LeavesSection) it.next();
                        if (!$$6.isSkippable()) {
                            for (int $$7 = 0; $$7 < 4096; $$7++) {
                                int $$8 = $$6.getBlock($$7);
                                if ($$6.isLog($$8)) {
                                    $$43.get(0).add(($$6.getIndex() << 12) | $$7);
                                } else if ($$6.isLeaf($$8)) {
                                    int $$9 = getX($$7);
                                    int $$10 = getZ($$7);
                                    $$3[0] = $$3[0] | getSideMask($$9 == 0, $$9 == 15, $$10 == 0, $$10 == 15);
                                }
                            }
                        }
                    }
                    for (int $$11 = 1; $$11 < 7; $$11++) {
                        IntSet $$12 = $$43.get($$11 - 1);
                        IntSet $$13 = $$43.get($$11);
                        IntIterator $$14 = $$12.iterator();
                        while ($$14.hasNext()) {
                            int $$15 = $$14.nextInt();
                            int $$16 = getX($$15);
                            int $$17 = getY($$15);
                            int $$18 = getZ($$15);
                            for (int[] $$19 : DIRECTIONS) {
                                int $$20 = $$16 + $$19[0];
                                int $$21 = $$17 + $$19[1];
                                int $$22 = $$18 + $$19[2];
                                if ($$20 >= 0 && $$20 <= 15 && $$22 >= 0 && $$22 <= 15 && $$21 >= 0 && $$21 <= 255 && ($$23 = (LeavesSection) int2ObjectOpenHashMap.get($$21 >> 4)) != null && !$$23.isSkippable()) {
                                    int $$24 = getIndex($$20, $$21 & 15, $$22);
                                    int $$25 = $$23.getBlock($$24);
                                    if ($$23.isLeaf($$25)) {
                                        int $$26 = $$23.getDistance($$25);
                                        if ($$26 > $$11) {
                                            $$23.setDistance($$24, $$25, $$11);
                                            $$13.add(getIndex($$20, $$21, $$22));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return $$22.updateTyped($$5, $$110 -> {
                        return ((LeavesSection) int2ObjectOpenHashMap.get(((Dynamic) $$110.get(DSL.remainderFinder())).get("Y").asInt(0))).write($$110);
                    });
                });
                if ($$3[0] != 0) {
                    $$42 = $$42.update(DSL.remainderFinder(), $$12 -> {
                        Dynamic<?> $$23 = (Dynamic) DataFixUtils.orElse($$12.get("UpgradeData").result(), $$12.emptyMap());
                        return $$12.set("UpgradeData", $$23.set("Sides", $$12.createByte((byte) ($$23.get("Sides").asByte((byte) 0) | $$3[0]))));
                    });
                }
                return $$42;
            });
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LeavesFix$Section.class */
    public static abstract class Section {
        protected static final String BLOCK_STATES_TAG = "BlockStates";
        protected static final String NAME_TAG = "Name";
        protected static final String PROPERTIES_TAG = "Properties";
        private final Type<Pair<String, Dynamic<?>>> blockStateType = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
        protected final OpticFinder<java.util.List<Pair<String, Dynamic<?>>>> paletteFinder = DSL.fieldFinder("Palette", DSL.list(this.blockStateType));
        protected final java.util.List<Dynamic<?>> palette;
        protected final int index;
        protected PackedBitStorage storage;

        protected abstract boolean skippable();

        public Section(Typed<?> $$0, Schema $$1) {
            if (!Objects.equals($$1.getType(References.BLOCK_STATE), this.blockStateType)) {
                throw new IllegalStateException("Block state type is not what was expected.");
            }
            Optional<java.util.List<Pair<String, Dynamic<?>>>> $$2 = $$0.getOptional(this.paletteFinder);
            this.palette = (java.util.List) $$2.map($$02 -> {
                return (java.util.List) $$02.stream().map((v0) -> {
                    return v0.getSecond();
                }).collect(Collectors.toList());
            }).orElse(ImmutableList.of());
            Dynamic<?> $$3 = (Dynamic) $$0.get(DSL.remainderFinder());
            this.index = $$3.get("Y").asInt(0);
            readStorage($$3);
        }

        protected void readStorage(Dynamic<?> $$0) {
            if (skippable()) {
                this.storage = null;
                return;
            }
            long[] $$1 = $$0.get(BLOCK_STATES_TAG).asLongStream().toArray();
            int $$2 = Math.max(4, DataFixUtils.ceillog2(this.palette.size()));
            this.storage = new PackedBitStorage($$2, 4096, $$1);
        }

        public Typed<?> write(Typed<?> $$0) {
            if (isSkippable()) {
                return $$0;
            }
            return $$0.update(DSL.remainderFinder(), $$02 -> {
                return $$02.set(BLOCK_STATES_TAG, $$02.createLongList(Arrays.stream(this.storage.getRaw())));
            }).set(this.paletteFinder, (java.util.List) this.palette.stream().map($$03 -> {
                return Pair.of(References.BLOCK_STATE.typeName(), $$03);
            }).collect(Collectors.toList()));
        }

        public boolean isSkippable() {
            return this.storage == null;
        }

        public int getBlock(int $$0) {
            return this.storage.get($$0);
        }

        protected int getStateId(String $$0, boolean $$1, int $$2) {
            return (LeavesFix.LEAVES.get($$0).intValue() << 5) | ($$1 ? 16 : 0) | $$2;
        }

        int getIndex() {
            return this.index;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LeavesFix$LeavesSection.class */
    public static final class LeavesSection extends Section {
        private static final String PERSISTENT = "persistent";
        private static final String DECAYABLE = "decayable";
        private static final String DISTANCE = "distance";
        private IntSet leaveIds;
        private IntSet logIds;
        private Int2IntMap stateToIdMap;

        public LeavesSection(Typed<?> $$0, Schema $$1) {
            super($$0, $$1);
        }

        @Override // net.minecraft.util.datafix.fixes.LeavesFix.Section
        protected boolean skippable() {
            this.leaveIds = new IntOpenHashSet();
            this.logIds = new IntOpenHashSet();
            this.stateToIdMap = new Int2IntOpenHashMap();
            for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
                Dynamic<?> $$1 = this.palette.get($$0);
                String $$2 = $$1.get(StateHolder.NAME_TAG).asString("");
                if (LeavesFix.LEAVES.containsKey($$2)) {
                    boolean $$3 = Objects.equals($$1.get(StateHolder.PROPERTIES_TAG).get(DECAYABLE).asString(""), SnbtOperations.BUILTIN_FALSE);
                    this.leaveIds.add($$0);
                    this.stateToIdMap.put(getStateId($$2, $$3, 7), $$0);
                    this.palette.set($$0, makeLeafTag($$1, $$2, $$3, 7));
                }
                if (LeavesFix.LOGS.contains($$2)) {
                    this.logIds.add($$0);
                }
            }
            return this.leaveIds.isEmpty() && this.logIds.isEmpty();
        }

        private Dynamic<?> makeLeafTag(Dynamic<?> $$0, String $$1, boolean $$2, int $$3) {
            Dynamic<?> $$4 = $$0.emptyMap();
            Dynamic<?> $$42 = $$4.set(PERSISTENT, $$4.createString($$2 ? SnbtOperations.BUILTIN_TRUE : SnbtOperations.BUILTIN_FALSE));
            Dynamic<?> $$5 = $$0.emptyMap().set(StateHolder.PROPERTIES_TAG, $$42.set(DISTANCE, $$42.createString(Integer.toString($$3))));
            return $$5.set(StateHolder.NAME_TAG, $$5.createString($$1));
        }

        public boolean isLog(int $$0) {
            return this.logIds.contains($$0);
        }

        public boolean isLeaf(int $$0) {
            return this.leaveIds.contains($$0);
        }

        int getDistance(int $$0) {
            if (isLog($$0)) {
                return 0;
            }
            return Integer.parseInt(this.palette.get($$0).get(StateHolder.PROPERTIES_TAG).get(DISTANCE).asString(""));
        }

        void setDistance(int $$0, int $$1, int $$2) {
            Dynamic<?> $$3 = this.palette.get($$1);
            String $$4 = $$3.get(StateHolder.NAME_TAG).asString("");
            boolean $$5 = Objects.equals($$3.get(StateHolder.PROPERTIES_TAG).get(PERSISTENT).asString(""), SnbtOperations.BUILTIN_TRUE);
            int $$6 = getStateId($$4, $$5, $$2);
            if (!this.stateToIdMap.containsKey($$6)) {
                int $$7 = this.palette.size();
                this.leaveIds.add($$7);
                this.stateToIdMap.put($$6, $$7);
                this.palette.add(makeLeafTag($$3, $$4, $$5, $$2));
            }
            int $$8 = this.stateToIdMap.get($$6);
            if ((1 << this.storage.getBits()) <= $$8) {
                PackedBitStorage $$9 = new PackedBitStorage(this.storage.getBits() + 1, 4096);
                for (int $$10 = 0; $$10 < 4096; $$10++) {
                    $$9.set($$10, this.storage.get($$10));
                }
                this.storage = $$9;
            }
            this.storage.set($$0, $$8);
        }
    }

    public static int getIndex(int $$0, int $$1, int $$2) {
        return ($$1 << 8) | ($$2 << 4) | $$0;
    }

    private int getX(int $$0) {
        return $$0 & 15;
    }

    private int getY(int $$0) {
        return ($$0 >> 8) & 255;
    }

    private int getZ(int $$0) {
        return ($$0 >> 4) & 15;
    }

    public static int getSideMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
        int $$4 = 0;
        if ($$2) {
            if ($$1) {
                $$4 = 0 | 2;
            } else if ($$0) {
                $$4 = 0 | 128;
            } else {
                $$4 = 0 | 1;
            }
        } else if ($$3) {
            if ($$0) {
                $$4 = 0 | 32;
            } else if ($$1) {
                $$4 = 0 | 8;
            } else {
                $$4 = 0 | 16;
            }
        } else if ($$1) {
            $$4 = 0 | 4;
        } else if ($$0) {
            $$4 = 0 | 64;
        }
        return $$4;
    }
}
