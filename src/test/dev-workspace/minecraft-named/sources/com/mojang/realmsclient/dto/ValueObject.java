package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/ValueObject.class */
public abstract class ValueObject {
    public String toString() {
        StringBuilder $$0 = new StringBuilder("{");
        for (Field $$1 : getClass().getFields()) {
            if (!isStatic($$1)) {
                try {
                    $$0.append(getName($$1)).append("=").append($$1.get(this)).append(" ");
                } catch (IllegalAccessException e) {
                }
            }
        }
        $$0.deleteCharAt($$0.length() - 1);
        $$0.append('}');
        return $$0.toString();
    }

    private static String getName(Field $$0) {
        SerializedName $$1 = $$0.getAnnotation(SerializedName.class);
        return $$1 != null ? $$1.value() : $$0.getName();
    }

    private static boolean isStatic(Field $$0) {
        return Modifier.isStatic($$0.getModifiers());
    }
}
