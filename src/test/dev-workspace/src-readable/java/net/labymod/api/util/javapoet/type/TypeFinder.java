package net.labymod.api.util.javapoet.type;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.WildcardTypeName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/javapoet/type/TypeFinder.class */
public final class TypeFinder {
    private static final char OPEN_ANGLE_BRACKET = '<';
    private static final char CLOSING_ANGLE_BRACKET = '>';
    private static final char CLOSING_SQUARE_BRACKET = ']';
    private static final char COMMA = ',';
    private static final char QUESTION_MARK = '?';
    private static final String EXTENDS = "extends";
    private static final String SUPER = "super";

    public static TypeName findTypeName(String type) {
        if (type.contains("$")) {
            type = type.replace("$", ".");
        }
        try {
            String trimmedType = type.trim();
            if (trimmedType.endsWith(String.valueOf(']'))) {
                String typeWithoutArray = trimmedType.substring(0, trimmedType.length() - 2);
                return ArrayTypeName.of(findTypeName(typeWithoutArray));
            }
            return findType(trimmedType);
        } catch (StringIndexOutOfBoundsException exception) {
            throw new IllegalArgumentException(type, exception);
        }
    }

    private static TypeName findType(String type) {
        TypeName name = PrimitiveType.getName(type);
        return name == null ? findObjectType(type) : name;
    }

    private static TypeName findObjectType(String type) {
        if (type.startsWith(String.valueOf('?'))) {
            return findWildcardType(type);
        }
        if (type.contains(String.valueOf('<'))) {
            return findGenericType(type);
        }
        return ClassName.bestGuess(type);
    }

    private static TypeName findWildcardType(String type) {
        int indexOfExtends = type.indexOf(EXTENDS);
        int indexOfSuper = type.indexOf(SUPER);
        if (firstBeforeSecond(indexOfExtends, indexOfSuper)) {
            return findExtendingType(type);
        }
        if (firstBeforeSecond(indexOfSuper, indexOfExtends)) {
            return findSuperType(type);
        }
        return WildcardTypeName.subtypeOf(TypeName.get(Object.class));
    }

    private static TypeName findExtendingType(String type) {
        return WildcardTypeName.subtypeOf(findTypeName(type.substring(type.indexOf(EXTENDS) + EXTENDS.length())));
    }

    private static TypeName findSuperType(String type) {
        return WildcardTypeName.supertypeOf(findTypeName(type.substring(type.indexOf(SUPER) + SUPER.length())));
    }

    private static TypeName findGenericType(String type) {
        String className = type.substring(0, type.indexOf(OPEN_ANGLE_BRACKET)).trim();
        String genericContent = type.substring(type.indexOf(OPEN_ANGLE_BRACKET) + 1, type.lastIndexOf(62));
        ClassName name = ClassName.bestGuess(className);
        return ParameterizedTypeName.get(name, findGenericTypeArguments(genericContent));
    }

    private static TypeName[] findGenericTypeArguments(String content) {
        List<TypeName> types = new ArrayList<>();
        String strSubstring = content;
        while (true) {
            String parse = strSubstring;
            if (!parse.isEmpty()) {
                int indexComma = parse.indexOf(44);
                int indexAngle = parse.indexOf(OPEN_ANGLE_BRACKET);
                if (firstBeforeSecond(indexComma, indexAngle)) {
                    types.add(findTypeName(parse.substring(0, indexComma)));
                    strSubstring = parse.substring(indexComma + 1);
                } else if (firstBeforeSecond(indexAngle, indexComma)) {
                    int endIndex = endIndexOfGenericType(parse);
                    types.add(findTypeName(parse.substring(0, endIndex)));
                    strSubstring = parse.substring(Math.min(endIndex + 1, parse.length()));
                } else {
                    types.add(findTypeName(parse));
                    strSubstring = "";
                }
            } else {
                return (TypeName[]) types.toArray(new TypeName[0]);
            }
        }
    }

    private static boolean firstBeforeSecond(int first, int second) {
        return first(first, second) || only(first, second);
    }

    private static boolean first(int first, int second) {
        return exists(first, second) && first < second;
    }

    private static boolean only(int first, int second) {
        return exists(first) && !firstBeforeSecond(second, first);
    }

    private static boolean exists(int first, int second) {
        return exists(first) && exists(second);
    }

    private static boolean exists(int index) {
        return index >= 0;
    }

    private static int endIndexOfGenericType(String content) {
        int countOpenAngles = 0;
        int countCloseAngles = 0;
        int contentLength = content.length();
        int endIndex = contentLength;
        int index = 0;
        while (true) {
            if (index >= contentLength) {
                break;
            }
            if (content.charAt(index) == OPEN_ANGLE_BRACKET) {
                countOpenAngles++;
            }
            if (content.charAt(index) == '>') {
                countCloseAngles++;
                if (countOpenAngles == countCloseAngles) {
                    endIndex = index + 1;
                    break;
                }
            }
            index++;
        }
        return endIndex;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/javapoet/type/TypeFinder$PrimitiveType.class */
    enum PrimitiveType {
        BOOLEAN(TypeName.BOOLEAN),
        BYTE(TypeName.BYTE),
        SHORT(TypeName.SHORT),
        LONG(TypeName.LONG),
        CHAR(TypeName.CHAR),
        FLOAT(TypeName.FLOAT),
        DOUBLE(TypeName.DOUBLE),
        INT(TypeName.INT);

        private static final Map<String, PrimitiveType> TYPES = new HashMap();
        private final String name = name().toLowerCase(Locale.ENGLISH);
        private final TypeName typeName;

        static {
            for (PrimitiveType value : values()) {
                TYPES.put(value.name, value);
            }
        }

        PrimitiveType(TypeName typeName) {
            this.typeName = typeName;
        }

        @Nullable
        public static TypeName getName(String type) {
            PrimitiveType primitiveType = TYPES.getOrDefault(type, null);
            if (primitiveType == null) {
                return null;
            }
            return primitiveType.typeName;
        }
    }
}
