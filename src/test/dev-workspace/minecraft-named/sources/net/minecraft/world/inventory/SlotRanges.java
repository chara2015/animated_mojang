package net.minecraft.world.inventory;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/inventory/SlotRanges.class */
public class SlotRanges {
    private static final List<SlotRange> SLOTS = (List) Util.make(new ArrayList(), $$0 -> {
        addSingleSlot($$0, "contents", 0);
        addSlotRange($$0, "container.", 0, 54);
        addSlotRange($$0, "hotbar.", 0, 9);
        addSlotRange($$0, "inventory.", 9, 27);
        addSlotRange($$0, "enderchest.", 200, 27);
        addSlotRange($$0, "villager.", 300, 8);
        addSlotRange($$0, "horse.", 500, 15);
        int $$1 = EquipmentSlot.MAINHAND.getIndex(98);
        int $$2 = EquipmentSlot.OFFHAND.getIndex(98);
        addSingleSlot($$0, "weapon", $$1);
        addSingleSlot($$0, "weapon.mainhand", $$1);
        addSingleSlot($$0, "weapon.offhand", $$2);
        addSlots($$0, "weapon.*", $$1, $$2);
        int $$3 = EquipmentSlot.HEAD.getIndex(100);
        int $$4 = EquipmentSlot.CHEST.getIndex(100);
        int $$5 = EquipmentSlot.LEGS.getIndex(100);
        int $$6 = EquipmentSlot.FEET.getIndex(100);
        int $$7 = EquipmentSlot.BODY.getIndex(105);
        addSingleSlot($$0, "armor.head", $$3);
        addSingleSlot($$0, "armor.chest", $$4);
        addSingleSlot($$0, "armor.legs", $$5);
        addSingleSlot($$0, "armor.feet", $$6);
        addSingleSlot($$0, "armor.body", $$7);
        addSlots($$0, "armor.*", $$3, $$4, $$5, $$6, $$7);
        addSingleSlot($$0, "saddle", EquipmentSlot.SADDLE.getIndex(LivingEntity.SADDLE_OFFSET));
        addSingleSlot($$0, "horse.chest", 499);
        addSingleSlot($$0, "player.cursor", 499);
        addSlotRange($$0, "player.crafting.", 500, 4);
    });
    public static final Codec<SlotRange> CODEC = StringRepresentable.fromValues(() -> {
        return (SlotRange[]) SLOTS.toArray($$0 -> {
            return new SlotRange[$$0];
        });
    });
    private static final Function<String, SlotRange> NAME_LOOKUP = StringRepresentable.createNameLookup((SlotRange[]) SLOTS.toArray($$0 -> {
        return new SlotRange[$$0];
    }));

    private static SlotRange create(String $$0, int $$1) {
        return SlotRange.of($$0, IntLists.singleton($$1));
    }

    private static SlotRange create(String $$0, IntList $$1) {
        return SlotRange.of($$0, IntLists.unmodifiable($$1));
    }

    private static SlotRange create(String $$0, int... $$1) {
        return SlotRange.of($$0, IntList.of($$1));
    }

    private static void addSingleSlot(List<SlotRange> $$0, String $$1, int $$2) {
        $$0.add(create($$1, $$2));
    }

    private static void addSlotRange(List<SlotRange> $$0, String $$1, int $$2, int $$3) {
        IntArrayList intArrayList = new IntArrayList($$3);
        for (int $$5 = 0; $$5 < $$3; $$5++) {
            int $$6 = $$2 + $$5;
            $$0.add(create($$1 + $$5, $$6));
            intArrayList.add($$6);
        }
        $$0.add(create($$1 + "*", (IntList) intArrayList));
    }

    private static void addSlots(List<SlotRange> $$0, String $$1, int... $$2) {
        $$0.add(create($$1, $$2));
    }

    public static SlotRange nameToIds(String $$0) {
        return NAME_LOOKUP.apply($$0);
    }

    public static Stream<String> allNames() {
        return SLOTS.stream().map((v0) -> {
            return v0.getSerializedName();
        });
    }

    public static Stream<String> singleSlotNames() {
        return SLOTS.stream().filter($$0 -> {
            return $$0.size() == 1;
        }).map((v0) -> {
            return v0.getSerializedName();
        });
    }
}
