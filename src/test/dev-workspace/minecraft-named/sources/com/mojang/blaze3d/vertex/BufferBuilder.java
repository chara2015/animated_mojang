package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/BufferBuilder.class */
public class BufferBuilder implements VertexConsumer {
    private static final int MAX_VERTEX_COUNT = 16777215;
    private static final long NOT_BUILDING = -1;
    private static final long UNKNOWN_ELEMENT = -1;
    private static final boolean IS_LITTLE_ENDIAN;
    private final ByteBufferBuilder buffer;
    private int vertices;
    private final VertexFormat format;
    private final VertexFormat.Mode mode;
    private final boolean fastFormat;
    private final boolean fullFormat;
    private final int vertexSize;
    private final int initialElementsToFill;
    private final int[] offsetsByElement;
    private int elementsToFill;
    private long vertexPointer = -1;
    private boolean building = true;

    static {
        IS_LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    }

    public BufferBuilder(ByteBufferBuilder $$0, VertexFormat.Mode $$1, VertexFormat $$2) {
        if (!$$2.contains(VertexFormatElement.POSITION)) {
            throw new IllegalArgumentException("Cannot build mesh with no position element");
        }
        this.buffer = $$0;
        this.mode = $$1;
        this.format = $$2;
        this.vertexSize = $$2.getVertexSize();
        this.initialElementsToFill = $$2.getElementsMask() & (VertexFormatElement.POSITION.mask() ^ (-1));
        this.offsetsByElement = $$2.getOffsetsByElement();
        boolean $$3 = $$2 == DefaultVertexFormat.NEW_ENTITY;
        boolean $$4 = $$2 == DefaultVertexFormat.BLOCK;
        this.fastFormat = $$3 || $$4;
        this.fullFormat = $$3;
    }

    public MeshData build() {
        ensureBuilding();
        endLastVertex();
        MeshData $$0 = storeMesh();
        this.building = false;
        this.vertexPointer = -1L;
        return $$0;
    }

    public MeshData buildOrThrow() {
        MeshData $$0 = build();
        if ($$0 == null) {
            throw new IllegalStateException("BufferBuilder was empty");
        }
        return $$0;
    }

    private void ensureBuilding() {
        if (!this.building) {
            throw new IllegalStateException("Not building!");
        }
    }

    private MeshData storeMesh() {
        ByteBufferBuilder.Result $$0;
        if (this.vertices == 0 || ($$0 = this.buffer.build()) == null) {
            return null;
        }
        int $$1 = this.mode.indexCount(this.vertices);
        VertexFormat.IndexType $$2 = VertexFormat.IndexType.least(this.vertices);
        return new MeshData($$0, new MeshData.DrawState(this.format, this.vertices, $$1, this.mode, $$2));
    }

    private long beginVertex() {
        ensureBuilding();
        endLastVertex();
        if (this.vertices >= MAX_VERTEX_COUNT) {
            throw new IllegalStateException("Trying to write too many vertices (>16777215) into BufferBuilder");
        }
        this.vertices++;
        long $$0 = this.buffer.reserve(this.vertexSize);
        this.vertexPointer = $$0;
        return $$0;
    }

    private long beginElement(VertexFormatElement $$0) {
        int $$1 = this.elementsToFill;
        int $$2 = $$1 & ($$0.mask() ^ (-1));
        if ($$2 == $$1) {
            return -1L;
        }
        this.elementsToFill = $$2;
        long $$3 = this.vertexPointer;
        if ($$3 == -1) {
            throw new IllegalArgumentException("Not currently building vertex");
        }
        return $$3 + ((long) this.offsetsByElement[$$0.id()]);
    }

    private void endLastVertex() {
        if (this.vertices == 0) {
            return;
        }
        if (this.elementsToFill != 0) {
            Stream<VertexFormatElement> streamElementsFromMask = VertexFormatElement.elementsFromMask(this.elementsToFill);
            VertexFormat vertexFormat = this.format;
            Objects.requireNonNull(vertexFormat);
            String $$0 = (String) streamElementsFromMask.map(vertexFormat::getElementName).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT));
            throw new IllegalStateException("Missing elements in vertex: " + $$0);
        }
        if (this.mode == VertexFormat.Mode.LINES) {
            long $$1 = this.buffer.reserve(this.vertexSize);
            MemoryUtil.memCopy($$1 - ((long) this.vertexSize), $$1, this.vertexSize);
            this.vertices++;
        }
    }

    private static void putRgba(long $$0, int $$1) {
        int $$2 = ARGB.toABGR($$1);
        MemoryUtil.memPutInt($$0, IS_LITTLE_ENDIAN ? $$2 : Integer.reverseBytes($$2));
    }

    private static void putPackedUv(long $$0, int $$1) {
        if (IS_LITTLE_ENDIAN) {
            MemoryUtil.memPutInt($$0, $$1);
        } else {
            MemoryUtil.memPutShort($$0, (short) ($$1 & 65535));
            MemoryUtil.memPutShort($$0 + 2, (short) (($$1 >> 16) & 65535));
        }
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer addVertex(float $$0, float $$1, float $$2) {
        long $$3 = beginVertex() + ((long) this.offsetsByElement[VertexFormatElement.POSITION.id()]);
        this.elementsToFill = this.initialElementsToFill;
        MemoryUtil.memPutFloat($$3, $$0);
        MemoryUtil.memPutFloat($$3 + 4, $$1);
        MemoryUtil.memPutFloat($$3 + 8, $$2);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setColor(int $$0, int $$1, int $$2, int $$3) {
        long $$4 = beginElement(VertexFormatElement.COLOR);
        if ($$4 != -1) {
            MemoryUtil.memPutByte($$4, (byte) $$0);
            MemoryUtil.memPutByte($$4 + 1, (byte) $$1);
            MemoryUtil.memPutByte($$4 + 2, (byte) $$2);
            MemoryUtil.memPutByte($$4 + 3, (byte) $$3);
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setColor(int $$0) {
        long $$1 = beginElement(VertexFormatElement.COLOR);
        if ($$1 != -1) {
            putRgba($$1, $$0);
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv(float $$0, float $$1) {
        long $$2 = beginElement(VertexFormatElement.UV0);
        if ($$2 != -1) {
            MemoryUtil.memPutFloat($$2, $$0);
            MemoryUtil.memPutFloat($$2 + 4, $$1);
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv1(int $$0, int $$1) {
        return uvShort((short) $$0, (short) $$1, VertexFormatElement.UV1);
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setOverlay(int $$0) {
        long $$1 = beginElement(VertexFormatElement.UV1);
        if ($$1 != -1) {
            putPackedUv($$1, $$0);
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv2(int $$0, int $$1) {
        return uvShort((short) $$0, (short) $$1, VertexFormatElement.UV2);
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setLight(int $$0) {
        long $$1 = beginElement(VertexFormatElement.UV2);
        if ($$1 != -1) {
            putPackedUv($$1, $$0);
        }
        return this;
    }

    private VertexConsumer uvShort(short $$0, short $$1, VertexFormatElement $$2) {
        long $$3 = beginElement($$2);
        if ($$3 != -1) {
            MemoryUtil.memPutShort($$3, $$0);
            MemoryUtil.memPutShort($$3 + 2, $$1);
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setNormal(float $$0, float $$1, float $$2) {
        long $$3 = beginElement(VertexFormatElement.NORMAL);
        if ($$3 != -1) {
            MemoryUtil.memPutByte($$3, normalIntValue($$0));
            MemoryUtil.memPutByte($$3 + 1, normalIntValue($$1));
            MemoryUtil.memPutByte($$3 + 2, normalIntValue($$2));
        }
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setLineWidth(float $$0) {
        long $$1 = beginElement(VertexFormatElement.LINE_WIDTH);
        if ($$1 != -1) {
            MemoryUtil.memPutFloat($$1, $$0);
        }
        return this;
    }

    private static byte normalIntValue(float $$0) {
        return (byte) (((int) (Mth.clamp($$0, -1.0f, 1.0f) * 127.0f)) & 255);
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public void addVertex(float $$0, float $$1, float $$2, int $$3, float $$4, float $$5, int $$6, int $$7, float $$8, float $$9, float $$10) {
        long $$13;
        if (this.fastFormat) {
            long $$11 = beginVertex();
            MemoryUtil.memPutFloat($$11 + 0, $$0);
            MemoryUtil.memPutFloat($$11 + 4, $$1);
            MemoryUtil.memPutFloat($$11 + 8, $$2);
            putRgba($$11 + 12, $$3);
            MemoryUtil.memPutFloat($$11 + 16, $$4);
            MemoryUtil.memPutFloat($$11 + 20, $$5);
            if (this.fullFormat) {
                putPackedUv($$11 + 24, $$6);
                $$13 = $$11 + 28;
            } else {
                $$13 = $$11 + 24;
            }
            putPackedUv($$13 + 0, $$7);
            MemoryUtil.memPutByte($$13 + 4, normalIntValue($$8));
            MemoryUtil.memPutByte($$13 + 5, normalIntValue($$9));
            MemoryUtil.memPutByte($$13 + 6, normalIntValue($$10));
            return;
        }
        super.addVertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10);
    }
}
