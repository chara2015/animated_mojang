package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/FilteredBooksFix.class */
public class FilteredBooksFix extends ItemStackTagFix {
    public FilteredBooksFix(Schema $$0) {
        super($$0, "Remove filtered text from books", $$02 -> {
            return $$02.equals("minecraft:writable_book") || $$02.equals("minecraft:written_book");
        });
    }

    @Override // net.minecraft.util.datafix.fixes.ItemStackTagFix
    protected Typed<?> fixItemStackTag(Typed<?> $$0) {
        return Util.writeAndReadTypedOrThrow($$0, $$0.getType(), $$02 -> {
            return $$02.remove("filtered_title").remove("filtered_pages");
        });
    }
}
