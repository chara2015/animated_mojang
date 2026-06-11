package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendEquation;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.platform.BlendFactor;
import com.mojang.blaze3d.platform.BlendOp;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.EnumSet;
import java.util.Optional;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.laby3d.api.rhi.Format;
import net.labymod.laby3d.api.rhi.buffer.BufferUsage;
import net.labymod.laby3d.api.rhi.buffer.MemoryDomain;
import net.labymod.laby3d.api.rhi.pipeline.BlendComponent;
import net.labymod.laby3d.api.rhi.pipeline.BlendState;
import net.labymod.laby3d.api.rhi.pipeline.ColorWriteMask;
import net.labymod.laby3d.api.rhi.pipeline.StepMode;
import net.labymod.laby3d.api.rhi.pipeline.VertexAttribute;
import net.labymod.laby3d.api.rhi.pipeline.VertexBufferLayout;
import net.labymod.laby3d.api.rhi.texture.TextureUsage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanConverters.class */
final class VulkanConverters {
    private VulkanConverters() {
    }

    static String sanitizeIdentifierPath(String name) {
        StringBuilder sb = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            boolean valid = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9') || c == '_' || c == '-' || c == '/');
            sb.append(valid ? Character.toLowerCase(c) : '_');
        }
        return sb.toString();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static int bufferUsage(EnumSet<BufferUsage> usage, MemoryDomain memory) throws MatchException {
        int i;
        int mc = 0;
        for (BufferUsage flag : usage) {
            int i2 = mc;
            switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[flag.ordinal()]) {
                case 1:
                    i = 32;
                    break;
                case 2:
                    i = 64;
                    break;
                case 3:
                    i = 128;
                    break;
                case 4:
                    i = 16;
                    break;
                case 5:
                    i = 8;
                    break;
                case 6:
                    throw new UnsupportedOperationException("STORAGE buffer usage not exposed by Mojang's 26.2 API");
                case 7:
                    throw new UnsupportedOperationException("INDIRECT buffer usage not exposed by Mojang's 26.2 API");
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            mc = i2 | i;
        }
        if (memory == MemoryDomain.CPU_TO_GPU) {
            mc |= 2;
        }
        return mc;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static int textureUsage(EnumSet<TextureUsage> usage) throws MatchException {
        int i;
        int mc = 0;
        for (TextureUsage flag : usage) {
            int i2 = mc;
            switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[flag.ordinal()]) {
                case 1:
                    i = 4;
                    break;
                case 2:
                    i = 8;
                    break;
                case 3:
                    i = 2;
                    break;
                case 4:
                    i = 1;
                    break;
                case 5:
                    throw new UnsupportedOperationException("STORAGE texture usage not exposed by Mojang's 26.2 API");
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            mc = i2 | i;
        }
        return mc;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static GpuFormat format(Format format) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$Format[format.ordinal()]) {
            case 1:
                return GpuFormat.R8_UNORM;
            case 2:
                return GpuFormat.RG8_UNORM;
            case 3:
                return GpuFormat.RGB8_UNORM;
            case 4:
                return GpuFormat.RGBA8_UNORM;
            case 5:
                return GpuFormat.R8_SNORM;
            case 6:
                return GpuFormat.RG8_SNORM;
            case 7:
                return GpuFormat.RGBA8_SNORM;
            case 8:
                return GpuFormat.R8_UINT;
            case 9:
                return GpuFormat.RG8_UINT;
            case 10:
                return GpuFormat.RGBA8_UINT;
            case 11:
                return GpuFormat.R8_SINT;
            case 12:
                return GpuFormat.RG8_SINT;
            case 13:
                return GpuFormat.RGBA8_SINT;
            case 14:
                return GpuFormat.R16_UNORM;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return GpuFormat.RG16_UNORM;
            case 16:
                return GpuFormat.RGBA16_UNORM;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return GpuFormat.R16_SNORM;
            case 18:
                return GpuFormat.RG16_SNORM;
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                return GpuFormat.RGBA16_SNORM;
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return GpuFormat.R16_UINT;
            case ImGuiFlags.StyleColors.Button /* 21 */:
                return GpuFormat.RG16_UINT;
            case 22:
                return GpuFormat.RGBA16_UINT;
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return GpuFormat.R16_SINT;
            case ImGuiFlags.StyleColors.Header /* 24 */:
                return GpuFormat.RG16_SINT;
            case ImGuiFlags.StyleColors.HeaderHovered /* 25 */:
                return GpuFormat.RGBA16_SINT;
            case 26:
                return GpuFormat.R16_FLOAT;
            case ImGuiFlags.StyleColors.Separator /* 27 */:
                return GpuFormat.RG16_FLOAT;
            case ImGuiFlags.StyleColors.SeparatorHovered /* 28 */:
                return GpuFormat.RGB16_FLOAT;
            case 29:
                return GpuFormat.RGBA16_FLOAT;
            case ImGuiFlags.StyleColors.ResizeGrip /* 30 */:
                return GpuFormat.R32_UINT;
            case ImGuiFlags.StyleColors.ResizeGripHovered /* 31 */:
                return GpuFormat.RG32_UINT;
            case 32:
                return GpuFormat.RGB32_UINT;
            case ImGuiFlags.StyleColors.Tab /* 33 */:
                return GpuFormat.RGBA32_UINT;
            case ImGuiFlags.StyleColors.TabHovered /* 34 */:
                return GpuFormat.R32_SINT;
            case ImGuiFlags.StyleColors.TabActive /* 35 */:
                return GpuFormat.RG32_SINT;
            case 36:
                return GpuFormat.RGB32_SINT;
            case ImGuiFlags.StyleColors.TabUnfocusedActive /* 37 */:
                return GpuFormat.RGBA32_SINT;
            case ImGuiFlags.StyleColors.DockingPreview /* 38 */:
                return GpuFormat.R32_FLOAT;
            case ImGuiFlags.StyleColors.DockingEmptyBg /* 39 */:
                return GpuFormat.RG32_FLOAT;
            case 40:
                return GpuFormat.RGB32_FLOAT;
            case 41:
                return GpuFormat.RGBA32_FLOAT;
            case ImGuiFlags.StyleColors.PlotHistogram /* 42 */:
                return GpuFormat.D16_UNORM;
            case ImGuiFlags.StyleColors.PlotHistogramHovered /* 43 */:
                return GpuFormat.D32_FLOAT;
            case 44:
                return GpuFormat.D24_UNORM_S8_UINT;
            case ImGuiFlags.StyleColors.TableBorderStrong /* 45 */:
                return GpuFormat.D32_FLOAT_S8_UINT;
            case ImGuiFlags.StyleColors.TableBorderLight /* 46 */:
                return GpuFormat.S8_UINT;
            case ImGuiFlags.StyleColors.TableRowBg /* 47 */:
            case 48:
                throw new UnsupportedOperationException("Format " + String.valueOf(format) + " has no equivalent in Mojang's 26.2 GpuFormat");
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static AddressMode addressMode(net.labymod.laby3d.api.rhi.texture.AddressMode mode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode[mode.ordinal()]) {
            case 1:
                return AddressMode.CLAMP_TO_EDGE;
            case 2:
                return AddressMode.REPEAT;
            case 3:
            case 4:
                throw new UnsupportedOperationException("AddressMode " + String.valueOf(mode) + " not supported by Mojang's 26.2 GpuSampler");
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static FilterMode filterMode(net.labymod.laby3d.api.rhi.texture.FilterMode mode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$texture$FilterMode[mode.ordinal()]) {
            case 1:
                return FilterMode.NEAREST;
            case 2:
                return FilterMode.LINEAR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static BlendFactor blendFactor(net.labymod.laby3d.api.rhi.pipeline.BlendFactor f) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[f.ordinal()]) {
            case 1:
                return BlendFactor.ZERO;
            case 2:
                return BlendFactor.ONE;
            case 3:
                return BlendFactor.SRC_COLOR;
            case 4:
                return BlendFactor.ONE_MINUS_SRC_COLOR;
            case 5:
                return BlendFactor.SRC_ALPHA;
            case 6:
                return BlendFactor.ONE_MINUS_SRC_ALPHA;
            case 7:
                return BlendFactor.DST_COLOR;
            case 8:
                return BlendFactor.ONE_MINUS_DST_COLOR;
            case 9:
                return BlendFactor.DST_ALPHA;
            case 10:
                return BlendFactor.ONE_MINUS_DST_ALPHA;
            case 11:
                return BlendFactor.CONSTANT_COLOR;
            case 12:
                return BlendFactor.ONE_MINUS_CONSTANT_COLOR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static BlendOp blendOp(net.labymod.laby3d.api.rhi.pipeline.BlendOp op) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[op.ordinal()]) {
            case 1:
                return BlendOp.ADD;
            case 2:
                return BlendOp.SUBTRACT;
            case 3:
                return BlendOp.REVERSE_SUBTRACT;
            case 4:
                return BlendOp.MIN;
            case 5:
                return BlendOp.MAX;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static CompareOp compareOp(net.labymod.laby3d.api.rhi.pipeline.CompareOp op) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[op.ordinal()]) {
            case 1:
                return CompareOp.NEVER_PASS;
            case 2:
                return CompareOp.LESS_THAN;
            case 3:
                return CompareOp.EQUAL;
            case 4:
                return CompareOp.LESS_THAN_OR_EQUAL;
            case 5:
                return CompareOp.GREATER_THAN;
            case 6:
                return CompareOp.NOT_EQUAL;
            case 7:
                return CompareOp.GREATER_THAN_OR_EQUAL;
            case 8:
                return CompareOp.ALWAYS_PASS;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static PrimitiveTopology primitiveTopology(net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology t) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[t.ordinal()]) {
            case 1:
                return PrimitiveTopology.POINTS;
            case 2:
                return PrimitiveTopology.LINES;
            case 3:
                return PrimitiveTopology.DEBUG_LINE_STRIP;
            case 4:
                return PrimitiveTopology.TRIANGLES;
            case 5:
                return PrimitiveTopology.TRIANGLE_STRIP;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static int writeMask(EnumSet<ColorWriteMask> mask) throws MatchException {
        int i;
        int mc = 0;
        for (ColorWriteMask flag : mask) {
            int i2 = mc;
            switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask[flag.ordinal()]) {
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i = 4;
                    break;
                case 4:
                    i = 8;
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            mc = i2 | i;
        }
        return mc;
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan.VulkanConverters$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanConverters$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$Format;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$texture$FilterMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask = new int[ColorWriteMask.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask[ColorWriteMask.RED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask[ColorWriteMask.GREEN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask[ColorWriteMask.BLUE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$ColorWriteMask[ColorWriteMask.ALPHA.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology = new int[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.POINT_LIST.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.LINE_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.LINE_STRIP.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.TRIANGLE_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$PrimitiveTopology[net.labymod.laby3d.api.rhi.pipeline.PrimitiveTopology.TRIANGLE_STRIP.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp = new int[net.labymod.laby3d.api.rhi.pipeline.CompareOp.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.NEVER.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.LESS.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.LESS_OR_EQUAL.ordinal()] = 4;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.GREATER.ordinal()] = 5;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.NOT_EQUAL.ordinal()] = 6;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.GREATER_OR_EQUAL.ordinal()] = 7;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CompareOp[net.labymod.laby3d.api.rhi.pipeline.CompareOp.ALWAYS.ordinal()] = 8;
            } catch (NoSuchFieldError e17) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp = new int[net.labymod.laby3d.api.rhi.pipeline.BlendOp.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[net.labymod.laby3d.api.rhi.pipeline.BlendOp.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[net.labymod.laby3d.api.rhi.pipeline.BlendOp.SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[net.labymod.laby3d.api.rhi.pipeline.BlendOp.REVERSE_SUBTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[net.labymod.laby3d.api.rhi.pipeline.BlendOp.MIN.ordinal()] = 4;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendOp[net.labymod.laby3d.api.rhi.pipeline.BlendOp.MAX.ordinal()] = 5;
            } catch (NoSuchFieldError e22) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor = new int[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ZERO.ordinal()] = 1;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE.ordinal()] = 2;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.SRC_COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE_MINUS_SRC_COLOR.ordinal()] = 4;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.SRC_ALPHA.ordinal()] = 5;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE_MINUS_SRC_ALPHA.ordinal()] = 6;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.DST_COLOR.ordinal()] = 7;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE_MINUS_DST_COLOR.ordinal()] = 8;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.DST_ALPHA.ordinal()] = 9;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE_MINUS_DST_ALPHA.ordinal()] = 10;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.CONSTANT_COLOR.ordinal()] = 11;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$BlendFactor[net.labymod.laby3d.api.rhi.pipeline.BlendFactor.ONE_MINUS_CONSTANT_COLOR.ordinal()] = 12;
            } catch (NoSuchFieldError e34) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$texture$FilterMode = new int[net.labymod.laby3d.api.rhi.texture.FilterMode.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$FilterMode[net.labymod.laby3d.api.rhi.texture.FilterMode.NEAREST.ordinal()] = 1;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$FilterMode[net.labymod.laby3d.api.rhi.texture.FilterMode.LINEAR.ordinal()] = 2;
            } catch (NoSuchFieldError e36) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode = new int[net.labymod.laby3d.api.rhi.texture.AddressMode.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode[net.labymod.laby3d.api.rhi.texture.AddressMode.CLAMP_TO_EDGE.ordinal()] = 1;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode[net.labymod.laby3d.api.rhi.texture.AddressMode.REPEAT.ordinal()] = 2;
            } catch (NoSuchFieldError e38) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode[net.labymod.laby3d.api.rhi.texture.AddressMode.MIRROR_REPEAT.ordinal()] = 3;
            } catch (NoSuchFieldError e39) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$AddressMode[net.labymod.laby3d.api.rhi.texture.AddressMode.CLAMP_TO_BORDER.ordinal()] = 4;
            } catch (NoSuchFieldError e40) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$Format = new int[Format.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8_UNORM.ordinal()] = 1;
            } catch (NoSuchFieldError e41) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8_UNORM.ordinal()] = 2;
            } catch (NoSuchFieldError e42) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8_UNORM.ordinal()] = 3;
            } catch (NoSuchFieldError e43) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8A8_UNORM.ordinal()] = 4;
            } catch (NoSuchFieldError e44) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8_SNORM.ordinal()] = 5;
            } catch (NoSuchFieldError e45) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8_SNORM.ordinal()] = 6;
            } catch (NoSuchFieldError e46) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8A8_SNORM.ordinal()] = 7;
            } catch (NoSuchFieldError e47) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8_UINT.ordinal()] = 8;
            } catch (NoSuchFieldError e48) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8_UINT.ordinal()] = 9;
            } catch (NoSuchFieldError e49) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8A8_UINT.ordinal()] = 10;
            } catch (NoSuchFieldError e50) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8_SINT.ordinal()] = 11;
            } catch (NoSuchFieldError e51) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8_SINT.ordinal()] = 12;
            } catch (NoSuchFieldError e52) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8A8_SINT.ordinal()] = 13;
            } catch (NoSuchFieldError e53) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16_UNORM.ordinal()] = 14;
            } catch (NoSuchFieldError e54) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16_UNORM.ordinal()] = 15;
            } catch (NoSuchFieldError e55) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16A16_UNORM.ordinal()] = 16;
            } catch (NoSuchFieldError e56) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16_SNORM.ordinal()] = 17;
            } catch (NoSuchFieldError e57) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16_SNORM.ordinal()] = 18;
            } catch (NoSuchFieldError e58) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16A16_SNORM.ordinal()] = 19;
            } catch (NoSuchFieldError e59) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16_UINT.ordinal()] = 20;
            } catch (NoSuchFieldError e60) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16_UINT.ordinal()] = 21;
            } catch (NoSuchFieldError e61) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16A16_UINT.ordinal()] = 22;
            } catch (NoSuchFieldError e62) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16_SINT.ordinal()] = 23;
            } catch (NoSuchFieldError e63) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16_SINT.ordinal()] = 24;
            } catch (NoSuchFieldError e64) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16A16_SINT.ordinal()] = 25;
            } catch (NoSuchFieldError e65) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16_FLOAT.ordinal()] = 26;
            } catch (NoSuchFieldError e66) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16_FLOAT.ordinal()] = 27;
            } catch (NoSuchFieldError e67) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16_FLOAT.ordinal()] = 28;
            } catch (NoSuchFieldError e68) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R16G16B16A16_FLOAT.ordinal()] = 29;
            } catch (NoSuchFieldError e69) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32_UINT.ordinal()] = 30;
            } catch (NoSuchFieldError e70) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32_UINT.ordinal()] = 31;
            } catch (NoSuchFieldError e71) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32_UINT.ordinal()] = 32;
            } catch (NoSuchFieldError e72) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32A32_UINT.ordinal()] = 33;
            } catch (NoSuchFieldError e73) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32_SINT.ordinal()] = 34;
            } catch (NoSuchFieldError e74) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32_SINT.ordinal()] = 35;
            } catch (NoSuchFieldError e75) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32_SINT.ordinal()] = 36;
            } catch (NoSuchFieldError e76) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32A32_SINT.ordinal()] = 37;
            } catch (NoSuchFieldError e77) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32_FLOAT.ordinal()] = 38;
            } catch (NoSuchFieldError e78) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32_FLOAT.ordinal()] = 39;
            } catch (NoSuchFieldError e79) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32_FLOAT.ordinal()] = 40;
            } catch (NoSuchFieldError e80) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R32G32B32A32_FLOAT.ordinal()] = 41;
            } catch (NoSuchFieldError e81) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.DEPTH16_UNORM.ordinal()] = 42;
            } catch (NoSuchFieldError e82) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.DEPTH32_FLOAT.ordinal()] = 43;
            } catch (NoSuchFieldError e83) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.DEPTH24_UNORM_STENCIL8_UINT.ordinal()] = 44;
            } catch (NoSuchFieldError e84) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.DEPTH32_FLOAT_STENCIL8_UINT.ordinal()] = 45;
            } catch (NoSuchFieldError e85) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.STENCIL8_UINT.ordinal()] = 46;
            } catch (NoSuchFieldError e86) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.R8G8B8A8_SRGB.ordinal()] = 47;
            } catch (NoSuchFieldError e87) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Format[Format.DEPTH24_UNORM.ordinal()] = 48;
            } catch (NoSuchFieldError e88) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage = new int[TextureUsage.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[TextureUsage.SAMPLED.ordinal()] = 1;
            } catch (NoSuchFieldError e89) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[TextureUsage.RENDER_TARGET.ordinal()] = 2;
            } catch (NoSuchFieldError e90) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[TextureUsage.COPY_SRC.ordinal()] = 3;
            } catch (NoSuchFieldError e91) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[TextureUsage.COPY_DST.ordinal()] = 4;
            } catch (NoSuchFieldError e92) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$texture$TextureUsage[TextureUsage.STORAGE.ordinal()] = 5;
            } catch (NoSuchFieldError e93) {
            }
            $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage = new int[BufferUsage.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.VERTEX.ordinal()] = 1;
            } catch (NoSuchFieldError e94) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.INDEX.ordinal()] = 2;
            } catch (NoSuchFieldError e95) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.UNIFORM.ordinal()] = 3;
            } catch (NoSuchFieldError e96) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.COPY_SRC.ordinal()] = 4;
            } catch (NoSuchFieldError e97) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.COPY_DST.ordinal()] = 5;
            } catch (NoSuchFieldError e98) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.STORAGE.ordinal()] = 6;
            } catch (NoSuchFieldError e99) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$buffer$BufferUsage[BufferUsage.INDIRECT.ordinal()] = 7;
            } catch (NoSuchFieldError e100) {
            }
        }
    }

    static ColorTargetState colorTargetState(net.labymod.laby3d.api.rhi.pipeline.ColorTargetState state) {
        Optional<BlendFunction> optionalEmpty;
        if (state.blend() != null) {
            optionalEmpty = Optional.of(blendFunction(state.blend()));
        } else {
            optionalEmpty = Optional.empty();
        }
        Optional<BlendFunction> blend = optionalEmpty;
        return new ColorTargetState(blend, format(state.format()), writeMask(state.writeMask()));
    }

    private static BlendFunction blendFunction(BlendState blend) {
        return new BlendFunction(blendEquation(blend.color()), blendEquation(blend.alpha()));
    }

    private static BlendEquation blendEquation(BlendComponent c) {
        return new BlendEquation(blendFactor(c.srcFactor()), blendFactor(c.dstFactor()), blendOp(c.operation()));
    }

    static DepthStencilState depthStencilState(net.labymod.laby3d.api.rhi.pipeline.DepthStencilState state) {
        return new DepthStencilState(compareOp(state.depthCompare()), state.depthWriteEnabled(), state.depthBiasSlopeScale(), state.depthBias());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static VertexFormat vertexFormat(VertexBufferLayout layout) throws MatchException {
        int iPixelSize;
        int stepRate = layout.stepMode() == StepMode.INSTANCE ? 1 : 0;
        VertexFormat.Builder builder = VertexFormat.builder(stepRate);
        VertexAttribute[] attrs = layout.attributes();
        int lastIdx = -1;
        long lastOff = -1;
        for (int i = 0; i < attrs.length; i++) {
            if (attrs[i].offset() > lastOff) {
                lastOff = attrs[i].offset();
                lastIdx = i;
            }
        }
        for (int i2 = 0; i2 < attrs.length; i2++) {
            VertexAttribute attr = attrs[i2];
            GpuFormat fmt = format(attr.format());
            if (i2 == lastIdx) {
                iPixelSize = (int) (layout.stride() - attr.offset());
            } else {
                iPixelSize = fmt.pixelSize();
            }
            int stride = iPixelSize;
            String name = attr.name() != null ? attr.name() : "_rhi_a" + attr.location();
            builder.addAttribute(name, (int) attr.offset(), stride, fmt, 1);
        }
        return builder.build();
    }
}
