package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockPropertyRenameAndFix.class */
public class BlockPropertyRenameAndFix extends AbstractBlockPropertyFix {
    private final String blockId;
    private final String oldPropertyName;
    private final String newPropertyName;
    private final UnaryOperator<String> valueFixer;

    public BlockPropertyRenameAndFix(Schema $$0, String $$1, String $$2, String $$3, String $$4, UnaryOperator<String> $$5) {
        super($$0, $$1);
        this.blockId = $$2;
        this.oldPropertyName = $$3;
        this.newPropertyName = $$4;
        this.valueFixer = $$5;
    }

    @Override // net.minecraft.util.datafix.fixes.AbstractBlockPropertyFix
    protected boolean shouldFix(String $$0) {
        return $$0.equals(this.blockId);
    }

    @Override // net.minecraft.util.datafix.fixes.AbstractBlockPropertyFix
    protected <T> Dynamic<T> fixProperties(String $$0, Dynamic<T> $$1) {
        return $$1.renameAndFixField(this.oldPropertyName, this.newPropertyName, $$02 -> {
            return $$02.createString((String) this.valueFixer.apply($$02.asString("")));
        });
    }
}
