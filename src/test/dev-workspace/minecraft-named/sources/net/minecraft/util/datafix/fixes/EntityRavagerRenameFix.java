package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityRavagerRenameFix.class */
public class EntityRavagerRenameFix extends SimplestEntityRenameFix {
    public static final Map<String, String> RENAMED_IDS = ImmutableMap.builder().put("minecraft:illager_beast_spawn_egg", "minecraft:ravager_spawn_egg").build();

    public EntityRavagerRenameFix(Schema $$0, boolean $$1) {
        super("EntityRavagerRenameFix", $$0, $$1);
    }

    @Override // net.minecraft.util.datafix.fixes.SimplestEntityRenameFix
    protected String rename(String $$0) {
        return Objects.equals("minecraft:illager_beast", $$0) ? "minecraft:ravager" : $$0;
    }
}
