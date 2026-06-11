package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import java.util.List;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AttributeIdPrefixFix.class */
public class AttributeIdPrefixFix extends AttributesRenameFix {
    private static final List<String> PREFIXES = List.of("generic.", "horse.", "player.", "zombie.");

    public AttributeIdPrefixFix(Schema $$0) {
        super($$0, "AttributeIdPrefixFix", AttributeIdPrefixFix::replaceId);
    }

    private static String replaceId(String $$0) {
        String $$1 = NamespacedSchema.ensureNamespaced($$0);
        for (String $$2 : PREFIXES) {
            String $$3 = NamespacedSchema.ensureNamespaced($$2);
            if ($$1.startsWith($$3)) {
                return "minecraft:" + $$1.substring($$3.length());
            }
        }
        return $$0;
    }
}
