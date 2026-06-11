package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/NamespacedSchema.class */
public class NamespacedSchema extends Schema {
    public static final PrimitiveCodec<String> NAMESPACED_STRING_CODEC = new PrimitiveCodec<String>() { // from class: net.minecraft.util.datafix.schemas.NamespacedSchema.1
        public <T> DataResult<String> read(DynamicOps<T> $$0, T $$1) {
            return $$0.getStringValue($$1).map(NamespacedSchema::ensureNamespaced);
        }

        public <T> T write(DynamicOps<T> dynamicOps, String str) {
            return (T) dynamicOps.createString(str);
        }

        public String toString() {
            return "NamespacedString";
        }
    };
    private static final Type<String> NAMESPACED_STRING = new Const.PrimitiveType(NAMESPACED_STRING_CODEC);

    public NamespacedSchema(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public static String ensureNamespaced(String $$0) {
        Identifier $$1 = Identifier.tryParse($$0);
        if ($$1 != null) {
            return $$1.toString();
        }
        return $$0;
    }

    public static Type<String> namespacedString() {
        return NAMESPACED_STRING;
    }

    public Type<?> getChoiceType(DSL.TypeReference $$0, String $$1) {
        return super.getChoiceType($$0, ensureNamespaced($$1));
    }
}
