package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityMinecartIdentifiersFix.class */
public class EntityMinecartIdentifiersFix extends EntityRenameFix {
    public EntityMinecartIdentifiersFix(Schema $$0) {
        super("EntityMinecartIdentifiersFix", $$0, true);
    }

    @Override // net.minecraft.util.datafix.fixes.EntityRenameFix
    protected Pair<String, Typed<?>> fix(String $$0, Typed<?> $$1) {
        String str;
        if (!$$0.equals("Minecart")) {
            return Pair.of($$0, $$1);
        }
        int $$2 = ((Dynamic) $$1.getOrCreate(DSL.remainderFinder())).get("Type").asInt(0);
        switch ($$2) {
            case 1:
                str = "MinecartChest";
                break;
            case 2:
                str = "MinecartFurnace";
                break;
            default:
                str = "MinecartRideable";
                break;
        }
        String $$3 = str;
        Type<?> $$4 = (Type) getOutputSchema().findChoiceType(References.ENTITY).types().get($$3);
        return Pair.of($$3, Util.writeAndReadTypedOrThrow($$1, $$4, $$02 -> {
            return $$02.remove("Type");
        }));
    }
}
