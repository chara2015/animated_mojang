package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.DontObfuscate;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormatElement.class */
@DontObfuscate
public final class VertexFormatElement extends Record {
    private final int id;
    private final int index;
    private final Type type;
    private final Usage usage;
    private final int count;
    public static final int MAX_COUNT = 32;
    private static final VertexFormatElement[] BY_ID = new VertexFormatElement[32];
    private static final List<VertexFormatElement> ELEMENTS = new ArrayList(32);
    public static final VertexFormatElement POSITION = register(0, 0, Type.FLOAT, Usage.POSITION, 3);
    public static final VertexFormatElement COLOR = register(1, 0, Type.UBYTE, Usage.COLOR, 4);
    public static final VertexFormatElement UV0 = register(2, 0, Type.FLOAT, Usage.UV, 2);
    public static final VertexFormatElement UV = UV0;
    public static final VertexFormatElement UV1 = register(3, 1, Type.SHORT, Usage.UV, 2);
    public static final VertexFormatElement UV2 = register(4, 2, Type.SHORT, Usage.UV, 2);
    public static final VertexFormatElement NORMAL = register(5, 0, Type.BYTE, Usage.NORMAL, 3);
    public static final VertexFormatElement LINE_WIDTH = register(6, 0, Type.FLOAT, Usage.GENERIC, 1);

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VertexFormatElement.class), VertexFormatElement.class, "id;index;type;usage;count", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->id:I", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->index:I", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->type:Lcom/mojang/blaze3d/vertex/VertexFormatElement$Type;", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->usage:Lcom/mojang/blaze3d/vertex/VertexFormatElement$Usage;", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->count:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VertexFormatElement.class, Object.class), VertexFormatElement.class, "id;index;type;usage;count", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->id:I", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->index:I", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->type:Lcom/mojang/blaze3d/vertex/VertexFormatElement$Type;", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->usage:Lcom/mojang/blaze3d/vertex/VertexFormatElement$Usage;", "FIELD:Lcom/mojang/blaze3d/vertex/VertexFormatElement;->count:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int id() {
        return this.id;
    }

    public int index() {
        return this.index;
    }

    public Type type() {
        return this.type;
    }

    public Usage usage() {
        return this.usage;
    }

    public int count() {
        return this.count;
    }

    public static VertexFormatElement register(int $$0, int $$1, Type $$2, Usage $$3, int $$4) {
        VertexFormatElement $$5 = new VertexFormatElement($$0, $$1, $$2, $$3, $$4);
        if (BY_ID[$$0] != null) {
            throw new IllegalArgumentException("Duplicate element registration for: " + $$0);
        }
        BY_ID[$$0] = $$5;
        ELEMENTS.add($$5);
        return $$5;
    }

    public VertexFormatElement(int $$0, int $$1, Type $$2, Usage $$3, int $$4) {
        if ($$0 < 0 || $$0 >= BY_ID.length) {
            throw new IllegalArgumentException("Element ID must be in range [0; " + BY_ID.length + ")");
        }
        if (supportsUsage($$1, $$3)) {
            this.id = $$0;
            this.index = $$1;
            this.type = $$2;
            this.usage = $$3;
            this.count = $$4;
            return;
        }
        throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
    }

    private boolean supportsUsage(int $$0, Usage $$1) {
        return $$0 == 0 || $$1 == Usage.UV;
    }

    @Override // java.lang.Record
    public String toString() {
        return this.count + "," + String.valueOf(this.usage) + "," + String.valueOf(this.type) + " (" + this.id + ")";
    }

    public int mask() {
        return 1 << this.id;
    }

    public int byteSize() {
        return this.type.size() * this.count;
    }

    public static VertexFormatElement byId(int $$0) {
        return BY_ID[$$0];
    }

    public static Stream<VertexFormatElement> elementsFromMask(int $$0) {
        return ELEMENTS.stream().filter($$1 -> {
            return ($$0 & $$1.mask()) != 0;
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormatElement$Usage.class */
    @DontObfuscate
    public enum Usage {
        POSITION("Position"),
        NORMAL("Normal"),
        COLOR("Vertex Color"),
        UV("UV"),
        GENERIC("Generic");

        private final String name;

        Usage(String $$0) {
            this.name = $$0;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormatElement$Type.class */
    @DontObfuscate
    public enum Type {
        FLOAT(4, "Float"),
        UBYTE(1, "Unsigned Byte"),
        BYTE(1, "Byte"),
        USHORT(2, "Unsigned Short"),
        SHORT(2, "Short"),
        UINT(4, "Unsigned Int"),
        INT(4, "Int");

        private final int size;
        private final String name;

        Type(int $$0, String $$1) {
            this.size = $$0;
            this.name = $$1;
        }

        public int size() {
            return this.size;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }
}
