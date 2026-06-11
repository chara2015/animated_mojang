package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/advancements/AdvancementSubProvider.class */
public interface AdvancementSubProvider {
    void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer);

    static AdvancementHolder createPlaceholder(String $$0) {
        return Advancement.Builder.advancement().build(Identifier.parse($$0));
    }
}
