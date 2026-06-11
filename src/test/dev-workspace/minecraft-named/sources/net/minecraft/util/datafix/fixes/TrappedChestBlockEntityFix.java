package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.LeavesFix;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/TrappedChestBlockEntityFix.class */
public class TrappedChestBlockEntityFix extends DataFix {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int SIZE = 4096;
    private static final short SIZE_BITS = 12;

    public TrappedChestBlockEntityFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
        Type<?> $$1 = $$0.findFieldType("Level");
        List.ListType<?> listTypeFindFieldType = $$1.findFieldType("TileEntities");
        if (!(listTypeFindFieldType instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType<?> $$3 = listTypeFindFieldType;
        OpticFinder<? extends java.util.List<?>> $$4 = DSL.fieldFinder("TileEntities", $$3);
        Type<?> $$5 = getInputSchema().getType(References.CHUNK);
        OpticFinder<?> $$6 = $$5.findField("Level");
        OpticFinder<?> $$7 = $$6.type().findField("Sections");
        List.ListType listTypeType = $$7.type();
        if (!(listTypeType instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type<?> $$9 = listTypeType.getElement();
        OpticFinder<?> $$10 = DSL.typeFinder($$9);
        return TypeRewriteRule.seq(new AddNewChoices(getOutputSchema(), "AddTrappedChestFix", References.BLOCK_ENTITY).makeRule(), fixTypeEverywhereTyped("Trapped Chest fix", $$5, $$42 -> {
            return $$42.updateTyped($$6, $$32 -> {
                Optional<? extends Typed<?>> $$42 = $$32.getOptionalTyped($$7);
                if ($$42.isEmpty()) {
                    return $$32;
                }
                java.util.List<? extends Typed<?>> $$52 = ((Typed) $$42.get()).getAllTyped($$10);
                IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
                for (Typed<?> $$72 : $$52) {
                    TrappedChestSection $$8 = new TrappedChestSection($$72, getInputSchema());
                    if (!$$8.isSkippable()) {
                        for (int $$92 = 0; $$92 < 4096; $$92++) {
                            int $$102 = $$8.getBlock($$92);
                            if ($$8.isTrappedChest($$102)) {
                                intOpenHashSet.add(($$8.getIndex() << 12) | $$92);
                            }
                        }
                    }
                }
                Dynamic<?> $$11 = (Dynamic) $$32.get(DSL.remainderFinder());
                int $$12 = $$11.get(SerializableChunkData.X_POS_TAG).asInt(0);
                int $$13 = $$11.get(SerializableChunkData.Z_POS_TAG).asInt(0);
                TaggedChoice.TaggedChoiceType<String> $$14 = getInputSchema().findChoiceType(References.BLOCK_ENTITY);
                return $$32.updateTyped($$4, $$43 -> {
                    return $$43.updateTyped($$14.finder(), $$43 -> {
                        Dynamic<?> $$53 = (Dynamic) $$43.getOrCreate(DSL.remainderFinder());
                        int $$62 = $$53.get("x").asInt(0) - ($$12 << 4);
                        int $$73 = $$53.get("y").asInt(0);
                        int $$82 = $$53.get("z").asInt(0) - ($$13 << 4);
                        if (intOpenHashSet.contains(LeavesFix.getIndex($$62, $$73, $$82))) {
                            return $$43.update($$14.finder(), $$02 -> {
                                return $$02.mapFirst($$02 -> {
                                    if (!Objects.equals($$02, "minecraft:chest")) {
                                        LOGGER.warn("Block Entity was expected to be a chest");
                                        return "minecraft:trapped_chest";
                                    }
                                    return "minecraft:trapped_chest";
                                });
                            });
                        }
                        return $$43;
                    });
                });
            });
        }));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/TrappedChestBlockEntityFix$TrappedChestSection.class */
    public static final class TrappedChestSection extends LeavesFix.Section {
        private IntSet chestIds;

        public TrappedChestSection(Typed<?> $$0, Schema $$1) {
            super($$0, $$1);
        }

        @Override // net.minecraft.util.datafix.fixes.LeavesFix.Section
        protected boolean skippable() {
            this.chestIds = new IntOpenHashSet();
            for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
                Dynamic<?> $$1 = this.palette.get($$0);
                String $$2 = $$1.get(StateHolder.NAME_TAG).asString("");
                if (Objects.equals($$2, "minecraft:trapped_chest")) {
                    this.chestIds.add($$0);
                }
            }
            return this.chestIds.isEmpty();
        }

        public boolean isTrappedChest(int $$0) {
            return this.chestIds.contains($$0);
        }
    }
}
