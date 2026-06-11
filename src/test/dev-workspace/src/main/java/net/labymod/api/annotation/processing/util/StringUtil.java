package net.labymod.api.annotation.processing.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import net.labymod.api.util.javapoet.type.TypeFinder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/util/StringUtil.class */
public final class StringUtil {
    public static CharSequence capitalize(CharSequence sequence) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); index++) {
            char character = sequence.charAt(index);
            if (index == 0) {
                character = Character.toUpperCase(sequence.charAt(index));
            }
            builder.append(character);
        }
        return builder.toString();
    }

    public static String validateName(String sequence) {
        if (sequence == null) {
            sequence = "unknown";
        }
        return sequence.replace("-", "_");
    }

    public static String getSimpleName(TypeName name) {
        if (name instanceof ClassName) {
            return ((ClassName) name).simpleName();
        }
        if (name instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) name).rawType.simpleName();
        }
        return name.toString();
    }

    public static String getSimpleTypeName(String name) {
        return getSimpleName(TypeFinder.findTypeName(name));
    }
}
