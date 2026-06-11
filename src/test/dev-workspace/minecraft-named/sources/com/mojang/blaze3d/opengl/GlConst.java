package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlConst.class */
@DontObfuscate
public class GlConst {
    public static final int GL_READ_FRAMEBUFFER = 36008;
    public static final int GL_DRAW_FRAMEBUFFER = 36009;
    public static final int GL_TRUE = 1;
    public static final int GL_FALSE = 0;
    public static final int GL_NONE = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_POINTS = 0;
    public static final int GL_WRITE_ONLY = 35001;
    public static final int GL_READ_ONLY = 35000;
    public static final int GL_READ_WRITE = 35002;
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_EQUAL = 514;
    public static final int GL_LEQUAL = 515;
    public static final int GL_LESS = 513;
    public static final int GL_GREATER = 516;
    public static final int GL_GEQUAL = 518;
    public static final int GL_ALWAYS = 519;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_NEAREST = 9728;
    public static final int GL_LINEAR = 9729;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static final int GL_CLAMP_TO_EDGE = 33071;
    public static final int GL_REPEAT = 10497;
    public static final int GL_FRONT = 1028;
    public static final int GL_FRONT_AND_BACK = 1032;
    public static final int GL_LINE = 6913;
    public static final int GL_FILL = 6914;
    public static final int GL_BYTE = 5120;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_SHORT = 5122;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_INT = 5124;
    public static final int GL_UNSIGNED_INT = 5125;
    public static final int GL_FLOAT = 5126;
    public static final int GL_ZERO = 0;
    public static final int GL_ONE = 1;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_DST_COLOR = 774;
    public static final int GL_ONE_MINUS_DST_COLOR = 775;
    public static final int GL_REPLACE = 7681;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_RGBA8 = 32856;
    public static final int GL_PROXY_TEXTURE_2D = 32868;
    public static final int GL_RGBA = 6408;
    public static final int GL_TEXTURE_WIDTH = 4096;
    public static final int GL_BGR = 32992;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX = 32776;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static final int GL_DEPTH_COMPONENT24 = 33190;
    public static final int GL_STATIC_DRAW = 35044;
    public static final int GL_DYNAMIC_DRAW = 35048;
    public static final int GL_STREAM_DRAW = 35040;
    public static final int GL_STATIC_READ = 35045;
    public static final int GL_DYNAMIC_READ = 35049;
    public static final int GL_STREAM_READ = 35041;
    public static final int GL_STATIC_COPY = 35046;
    public static final int GL_DYNAMIC_COPY = 35050;
    public static final int GL_STREAM_COPY = 35042;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_WAIT_FAILED = 37149;
    public static final int GL_UNPACK_SWAP_BYTES = 3312;
    public static final int GL_UNPACK_LSB_FIRST = 3313;
    public static final int GL_UNPACK_ROW_LENGTH = 3314;
    public static final int GL_UNPACK_SKIP_ROWS = 3315;
    public static final int GL_UNPACK_SKIP_PIXELS = 3316;
    public static final int GL_UNPACK_ALIGNMENT = 3317;
    public static final int GL_PACK_ALIGNMENT = 3333;
    public static final int GL_PACK_ROW_LENGTH = 3330;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int[] CUBEMAP_TARGETS = {34069, 34070, 34071, 34072, 34073, 34074};
    public static final int GL_DEPTH_COMPONENT = 6402;
    public static final int GL_DEPTH_COMPONENT32 = 33191;
    public static final int GL_FRAMEBUFFER = 36160;
    public static final int GL_RENDERBUFFER = 36161;
    public static final int GL_COLOR_ATTACHMENT0 = 36064;
    public static final int GL_DEPTH_ATTACHMENT = 36096;
    public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static final int GL_LINK_STATUS = 35714;
    public static final int GL_COMPILE_STATUS = 35713;
    public static final int GL_VERTEX_SHADER = 35633;
    public static final int GL_FRAGMENT_SHADER = 35632;
    public static final int GL_TEXTURE0 = 33984;
    public static final int GL_TEXTURE1 = 33985;
    public static final int GL_TEXTURE2 = 33986;
    public static final int GL_DEPTH_TEXTURE_MODE = 34891;
    public static final int GL_TEXTURE_COMPARE_MODE = 34892;
    public static final int GL_ARRAY_BUFFER = 34962;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static final int GL_PIXEL_PACK_BUFFER = 35051;
    public static final int GL_COPY_READ_BUFFER = 36662;
    public static final int GL_COPY_WRITE_BUFFER = 36663;
    public static final int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static final int GL_UNIFORM_BUFFER = 35345;
    public static final int GL_ALPHA_BIAS = 3357;
    public static final int GL_RGB = 6407;
    public static final int GL_RG = 33319;
    public static final int GL_R8 = 33321;
    public static final int GL_RED = 6403;
    public static final int GL_OUT_OF_MEMORY = 1285;

    public static int toGl(DepthTestFunction $$0) {
        switch ($$0) {
            case NO_DEPTH_TEST:
                return GL_ALWAYS;
            case EQUAL_DEPTH_TEST:
                return GL_EQUAL;
            case LESS_DEPTH_TEST:
                return GL_LESS;
            case GREATER_DEPTH_TEST:
                return GL_GREATER;
            default:
                return GL_LEQUAL;
        }
    }

    public static int toGl(PolygonMode $$0) {
        switch ($$0) {
            case WIREFRAME:
                return GL_LINE;
            default:
                return GL_FILL;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(DestFactor $$0) throws MatchException {
        switch ($$0) {
            case CONSTANT_ALPHA:
                return 32771;
            case CONSTANT_COLOR:
                return 32769;
            case DST_ALPHA:
                return GL_DST_ALPHA;
            case DST_COLOR:
                return 774;
            case ONE:
                return 1;
            case ONE_MINUS_CONSTANT_ALPHA:
                return 32772;
            case ONE_MINUS_CONSTANT_COLOR:
                return 32770;
            case ONE_MINUS_DST_ALPHA:
                return GL_ONE_MINUS_DST_ALPHA;
            case ONE_MINUS_DST_COLOR:
                return GL_ONE_MINUS_DST_COLOR;
            case ONE_MINUS_SRC_ALPHA:
                return GL_ONE_MINUS_SRC_ALPHA;
            case ONE_MINUS_SRC_COLOR:
                return GL_ONE_MINUS_SRC_COLOR;
            case SRC_ALPHA:
                return GL_SRC_ALPHA;
            case SRC_COLOR:
                return GL_SRC_COLOR;
            case ZERO:
                return 0;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(SourceFactor $$0) throws MatchException {
        switch ($$0) {
            case CONSTANT_ALPHA:
                return 32771;
            case CONSTANT_COLOR:
                return 32769;
            case DST_ALPHA:
                return GL_DST_ALPHA;
            case DST_COLOR:
                return 774;
            case ONE:
                return 1;
            case ONE_MINUS_CONSTANT_ALPHA:
                return 32772;
            case ONE_MINUS_CONSTANT_COLOR:
                return 32770;
            case ONE_MINUS_DST_ALPHA:
                return GL_ONE_MINUS_DST_ALPHA;
            case ONE_MINUS_DST_COLOR:
                return GL_ONE_MINUS_DST_COLOR;
            case ONE_MINUS_SRC_ALPHA:
                return GL_ONE_MINUS_SRC_ALPHA;
            case ONE_MINUS_SRC_COLOR:
                return GL_ONE_MINUS_SRC_COLOR;
            case SRC_ALPHA:
                return GL_SRC_ALPHA;
            case SRC_ALPHA_SATURATE:
                return 776;
            case SRC_COLOR:
                return GL_SRC_COLOR;
            case ZERO:
                return 0;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(VertexFormat.Mode $$0) throws MatchException {
        switch ($$0) {
            case LINES:
                return 4;
            case DEBUG_LINES:
                return 1;
            case DEBUG_LINE_STRIP:
                return 3;
            case POINTS:
                return 0;
            case TRIANGLES:
                return 4;
            case TRIANGLE_STRIP:
                return 5;
            case TRIANGLE_FAN:
                return 6;
            case QUADS:
                return 4;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(VertexFormat.IndexType $$0) throws MatchException {
        switch ($$0) {
            case SHORT:
                return GL_UNSIGNED_SHORT;
            case INT:
                return GL_UNSIGNED_INT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(NativeImage.Format $$0) throws MatchException {
        switch ($$0) {
            case RGBA:
                return GL_RGBA;
            case RGB:
                return GL_RGB;
            case LUMINANCE_ALPHA:
                return GL_RG;
            case LUMINANCE:
                return GL_RED;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(AddressMode $$0) throws MatchException {
        switch ($$0) {
            case REPEAT:
                return GL_REPEAT;
            case CLAMP_TO_EDGE:
                return GL_CLAMP_TO_EDGE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(VertexFormatElement.Type $$0) throws MatchException {
        switch ($$0) {
            case FLOAT:
                return GL_FLOAT;
            case UBYTE:
                return GL_UNSIGNED_BYTE;
            case BYTE:
                return GL_BYTE;
            case USHORT:
                return GL_UNSIGNED_SHORT;
            case SHORT:
                return GL_SHORT;
            case UINT:
                return GL_UNSIGNED_INT;
            case INT:
                return GL_INT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGlInternalId(TextureFormat $$0) throws MatchException {
        switch ($$0) {
            case RGBA8:
                return GL_RGBA8;
            case RED8:
                return GL_R8;
            case RED8I:
                return 33329;
            case DEPTH32:
                return GL_DEPTH_COMPONENT32;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGlExternalId(TextureFormat $$0) throws MatchException {
        switch ($$0) {
            case RGBA8:
                return GL_RGBA;
            case RED8:
                return GL_RED;
            case RED8I:
                return GL_RED;
            case DEPTH32:
                return GL_DEPTH_COMPONENT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGlType(TextureFormat $$0) throws MatchException {
        switch ($$0) {
            case RGBA8:
                return GL_UNSIGNED_BYTE;
            case RED8:
                return GL_UNSIGNED_BYTE;
            case RED8I:
                return GL_UNSIGNED_BYTE;
            case DEPTH32:
                return GL_FLOAT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static int toGl(ShaderType $$0) throws MatchException {
        switch ($$0) {
            case VERTEX:
                return GL_VERTEX_SHADER;
            case FRAGMENT:
                return GL_FRAGMENT_SHADER;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static int bufferUsageToGlFlag(@GpuBuffer.Usage int $$0) {
        int $$1 = 0;
        if (($$0 & 1) != 0) {
            $$1 = 0 | 65;
        }
        if (($$0 & 2) != 0) {
            $$1 |= 66;
        }
        if (($$0 & 8) != 0) {
            $$1 |= 256;
        }
        if (($$0 & 4) != 0) {
            $$1 |= 512;
        }
        return $$1;
    }

    public static int bufferUsageToGlEnum(@GpuBuffer.Usage int $$0) {
        boolean $$1 = ($$0 & 4) != 0;
        if (($$0 & 2) != 0) {
            if ($$1) {
                return GL_STREAM_DRAW;
            }
            return GL_STATIC_DRAW;
        }
        if (($$0 & 1) != 0) {
            if ($$1) {
                return GL_STREAM_READ;
            }
            return GL_STATIC_READ;
        }
        return GL_STATIC_DRAW;
    }
}
