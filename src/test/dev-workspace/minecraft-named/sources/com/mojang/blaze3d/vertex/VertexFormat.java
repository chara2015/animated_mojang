package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.GraphicsWorkarounds;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.CommonColors;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormat.class */
@DontObfuscate
public class VertexFormat {
    public static final int UNKNOWN_ELEMENT = -1;
    private final List<VertexFormatElement> elements;
    private final List<String> names;
    private final int vertexSize;
    private final int elementsMask;
    private final int[] offsetsByElement = new int[32];
    private GpuBuffer immediateDrawVertexBuffer;
    private GpuBuffer immediateDrawIndexBuffer;

    VertexFormat(List<VertexFormatElement> $$0, List<String> $$1, IntList $$2, int $$3) {
        this.elements = $$0;
        this.names = $$1;
        this.vertexSize = $$3;
        this.elementsMask = $$0.stream().mapToInt((v0) -> {
            return v0.mask();
        }).reduce(0, ($$02, $$12) -> {
            return $$02 | $$12;
        });
        for (int $$4 = 0; $$4 < this.offsetsByElement.length; $$4++) {
            VertexFormatElement $$5 = VertexFormatElement.byId($$4);
            int $$6 = $$5 != null ? $$0.indexOf($$5) : -1;
            this.offsetsByElement[$$4] = $$6 != -1 ? $$2.getInt($$6) : -1;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String toString() {
        return "VertexFormat" + String.valueOf(this.names);
    }

    public int getVertexSize() {
        return this.vertexSize;
    }

    public List<VertexFormatElement> getElements() {
        return this.elements;
    }

    public List<String> getElementAttributeNames() {
        return this.names;
    }

    public int[] getOffsetsByElement() {
        return this.offsetsByElement;
    }

    public int getOffset(VertexFormatElement $$0) {
        return this.offsetsByElement[$$0.id()];
    }

    public boolean contains(VertexFormatElement $$0) {
        return (this.elementsMask & $$0.mask()) != 0;
    }

    public int getElementsMask() {
        return this.elementsMask;
    }

    public String getElementName(VertexFormatElement $$0) {
        int $$1 = this.elements.indexOf($$0);
        if ($$1 == -1) {
            throw new IllegalArgumentException(String.valueOf($$0) + " is not contained in format");
        }
        return this.names.get($$1);
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof VertexFormat) {
            VertexFormat $$1 = (VertexFormat) $$0;
            if (this.elementsMask == $$1.elementsMask && this.vertexSize == $$1.vertexSize && this.names.equals($$1.names) && Arrays.equals(this.offsetsByElement, $$1.offsetsByElement)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.elementsMask * 31) + Arrays.hashCode(this.offsetsByElement);
    }

    private static GpuBuffer uploadToBuffer(GpuBuffer $$0, ByteBuffer $$1, @GpuBuffer.Usage int $$2, Supplier<String> $$3) {
        GpuDevice $$4 = RenderSystem.getDevice();
        if (GraphicsWorkarounds.get($$4).alwaysCreateFreshImmediateBuffer()) {
            if ($$0 != null) {
                $$0.close();
            }
            return $$4.createBuffer($$3, $$2, $$1);
        }
        if ($$0 == null) {
            $$0 = $$4.createBuffer($$3, $$2, $$1);
        } else {
            CommandEncoder $$5 = $$4.createCommandEncoder();
            if ($$0.size() < $$1.remaining()) {
                $$0.close();
                $$0 = $$4.createBuffer($$3, $$2, $$1);
            } else {
                $$5.writeToBuffer($$0.slice(), $$1);
            }
        }
        return $$0;
    }

    public GpuBuffer uploadImmediateVertexBuffer(ByteBuffer $$0) {
        this.immediateDrawVertexBuffer = uploadToBuffer(this.immediateDrawVertexBuffer, $$0, 40, () -> {
            return "Immediate vertex buffer for " + String.valueOf(this);
        });
        return this.immediateDrawVertexBuffer;
    }

    public GpuBuffer uploadImmediateIndexBuffer(ByteBuffer $$0) {
        this.immediateDrawIndexBuffer = uploadToBuffer(this.immediateDrawIndexBuffer, $$0, 72, () -> {
            return "Immediate index buffer for " + String.valueOf(this);
        });
        return this.immediateDrawIndexBuffer;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormat$Builder.class */
    @DontObfuscate
    public static class Builder {
        private final ImmutableMap.Builder<String, VertexFormatElement> elements = ImmutableMap.builder();
        private final IntList offsets = new IntArrayList();
        private int offset;

        Builder() {
        }

        public Builder add(String $$0, VertexFormatElement $$1) {
            this.elements.put($$0, $$1);
            this.offsets.add(this.offset);
            this.offset += $$1.byteSize();
            return this;
        }

        public Builder padding(int $$0) {
            this.offset += $$0;
            return this;
        }

        public VertexFormat build() {
            ImmutableMap<String, VertexFormatElement> $$0 = this.elements.buildOrThrow();
            ImmutableList<VertexFormatElement> $$1 = $$0.values().asList();
            ImmutableList<String> $$2 = $$0.keySet().asList();
            return new VertexFormat($$1, $$2, this.offsets, this.offset);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormat$IndexType.class */
    public enum IndexType {
        SHORT(2),
        INT(4);

        public final int bytes;

        IndexType(int $$0) {
            this.bytes = $$0;
        }

        public static IndexType least(int $$0) {
            if (($$0 & CommonColors.RED) != 0) {
                return INT;
            }
            return SHORT;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexFormat$Mode.class */
    public enum Mode {
        LINES(2, 2, false),
        DEBUG_LINES(2, 2, false),
        DEBUG_LINE_STRIP(2, 1, true),
        POINTS(1, 1, false),
        TRIANGLES(3, 3, false),
        TRIANGLE_STRIP(3, 1, true),
        TRIANGLE_FAN(3, 1, true),
        QUADS(4, 4, false);

        public final int primitiveLength;
        public final int primitiveStride;
        public final boolean connectedPrimitives;

        Mode(int $$0, int $$1, boolean $$2) {
            this.primitiveLength = $$0;
            this.primitiveStride = $$1;
            this.connectedPrimitives = $$2;
        }

        public int indexCount(int $$0) {
            int $$3;
            switch (this) {
                case LINES:
                case QUADS:
                    $$3 = ($$0 / 4) * 6;
                    break;
                case DEBUG_LINES:
                case DEBUG_LINE_STRIP:
                case POINTS:
                case TRIANGLES:
                case TRIANGLE_STRIP:
                case TRIANGLE_FAN:
                    $$3 = $$0;
                    break;
                default:
                    $$3 = 0;
                    break;
            }
            return $$3;
        }
    }
}
