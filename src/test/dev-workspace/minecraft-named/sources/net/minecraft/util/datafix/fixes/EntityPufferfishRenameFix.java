package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityPufferfishRenameFix.class */
public class EntityPufferfishRenameFix extends SimplestEntityRenameFix {
    public static final Map<String, String> RENAMED_IDS = ImmutableMap.builder().put("minecraft:puffer_fish_spawn_egg", "minecraft:pufferfish_spawn_egg").build();

    public EntityPufferfishRenameFix(Schema $$0, boolean $$1) {
        super("EntityPufferfishRenameFix", $$0, $$1);
    }

    @Override // net.minecraft.util.datafix.fixes.SimplestEntityRenameFix
    protected String rename(String $$0) {
        return Objects.equals("minecraft:puffer_fish", $$0) ? "minecraft:pufferfish" : $$0;
    }
}
