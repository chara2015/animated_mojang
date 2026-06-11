package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/GoatHornIdFix.class */
public class GoatHornIdFix extends ItemStackTagRemainderFix {
    private static final String[] INSTRUMENTS = {"minecraft:ponder_goat_horn", "minecraft:sing_goat_horn", "minecraft:seek_goat_horn", "minecraft:feel_goat_horn", "minecraft:admire_goat_horn", "minecraft:call_goat_horn", "minecraft:yearn_goat_horn", "minecraft:dream_goat_horn"};

    public GoatHornIdFix(Schema $$0) {
        super($$0, "GoatHornIdFix", $$02 -> {
            return $$02.equals("minecraft:goat_horn");
        });
    }

    @Override // net.minecraft.util.datafix.fixes.ItemStackTagRemainderFix
    protected <T> Dynamic<T> fixItemStackTag(Dynamic<T> $$0) {
        int $$1 = $$0.get("SoundVariant").asInt(0);
        String $$2 = INSTRUMENTS[($$1 < 0 || $$1 >= INSTRUMENTS.length) ? 0 : $$1];
        return $$0.remove("SoundVariant").set("instrument", $$0.createString($$2));
    }
}
