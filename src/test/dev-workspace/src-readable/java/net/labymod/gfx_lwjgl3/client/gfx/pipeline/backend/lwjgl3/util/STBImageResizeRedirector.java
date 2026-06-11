package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/STBImageResizeRedirector.class */
public final class STBImageResizeRedirector {
    private STBImageResizeRedirector() {
    }

    public static int nstbir_resize_uint8(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        return (int) STBImageResize.nstbir_resize_uint8_linear(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels);
    }

    public static int nstbir_resize_uint8_generic(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space, long alloc_context) {
        long result = STBImageResize.nstbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, space, mapEdgeMode(edge_wrap_mode), filter);
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize_float(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        long result = STBImageResize.nstbir_resize_float_linear(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels);
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize_uint8_srgb(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags) {
        long result = STBImageResize.nstbir_resize_uint8_srgb(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, mapPixelLayout(num_channels, alpha_channel, flags));
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize_uint8_srgb_edgemode(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode) {
        long result = STBImageResize.nstbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, mapPixelLayout(num_channels, alpha_channel, flags), 1, mapEdgeMode(edge_wrap_mode), 0);
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize_uint16_generic(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space, long alloc_context) {
        long result = STBImageResize.nstbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, mapPixelLayout(num_channels, alpha_channel, flags), 3, mapEdgeMode(edge_wrap_mode), filter);
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize_float_generic(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space, long alloc_context) {
        long result = STBImageResize.nstbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, mapPixelLayout(num_channels, alpha_channel, flags), 4, mapEdgeMode(edge_wrap_mode), filter);
        return result != 0 ? 1 : 0;
    }

    public static int nstbir_resize(long input_pixels, int input_w, int input_h, int input_stride_in_bytes, long output_pixels, int output_w, int output_h, int output_stride_in_bytes, int datatype, int num_channels, int alpha_channel, int flags, int edge_mode_horizontal, int edge_mode_vertical, int filter_horizontal, int filter_vertical, int space, long alloc_context) {
        long result = STBImageResize.nstbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, mapPixelLayout(num_channels, alpha_channel, flags), mapDataType(datatype, space), mapEdgeMode(edge_mode_horizontal), filter_horizontal);
        return result != 0 ? 1 : 0;
    }

    public static boolean stbir_resize_uint8(ByteBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ByteBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        return nstbir_resize_uint8(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels) != 0;
    }

    public static boolean stbir_resize_float(FloatBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, FloatBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        return nstbir_resize_float(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels) != 0;
    }

    public static boolean stbir_resize_uint8_srgb(ByteBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ByteBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags) {
        return nstbir_resize_uint8_srgb(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags) != 0;
    }

    public static boolean stbir_resize_uint8_srgb_edgemode(ByteBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ByteBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode) {
        return nstbir_resize_uint8_srgb_edgemode(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode) != 0;
    }

    public static boolean stbir_resize_uint8_generic(ByteBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ByteBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space) {
        return nstbir_resize_uint8_generic(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, 0L) != 0;
    }

    public static boolean stbir_resize_uint16_generic(ShortBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ShortBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space) {
        return nstbir_resize_uint16_generic(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, 0L) != 0;
    }

    public static boolean stbir_resize_float_generic(FloatBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, FloatBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space) {
        return nstbir_resize_float_generic(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, 0L) != 0;
    }

    public static boolean stbir_resize(ByteBuffer input_pixels, int input_w, int input_h, int input_stride_in_bytes, ByteBuffer output_pixels, int output_w, int output_h, int output_stride_in_bytes, int datatype, int num_channels, int alpha_channel, int flags, int edge_mode_horizontal, int edge_mode_vertical, int filter_horizontal, int filter_vertical, int space) {
        return nstbir_resize(MemoryUtil.memAddress(input_pixels), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(output_pixels), output_w, output_h, output_stride_in_bytes, datatype, num_channels, alpha_channel, flags, edge_mode_horizontal, edge_mode_vertical, filter_horizontal, filter_vertical, space, 0L) != 0;
    }

    public static boolean stbir_resize_float(float[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, float[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        FloatBuffer inputBuf = MemoryUtil.memAllocFloat(input_pixels.length);
        FloatBuffer outputBuf = MemoryUtil.memAllocFloat(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_float(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels);
            outputBuf.get(output_pixels);
            return result != 0;
        } finally {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
        }
    }

    public static boolean stbir_resize_uint16_generic(short[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, short[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space) {
        ShortBuffer inputBuf = MemoryUtil.memAllocShort(input_pixels.length);
        ShortBuffer outputBuf = MemoryUtil.memAllocShort(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_uint16_generic(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, 0L);
            outputBuf.get(output_pixels);
            return result != 0;
        } finally {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
        }
    }

    public static boolean stbir_resize_float_generic(float[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, float[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space) {
        FloatBuffer inputBuf = MemoryUtil.memAllocFloat(input_pixels.length);
        FloatBuffer outputBuf = MemoryUtil.memAllocFloat(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_float_generic(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, 0L);
            outputBuf.get(output_pixels);
            return result != 0;
        } finally {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
        }
    }

    public static int nstbir_resize_float(float[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, float[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels) {
        FloatBuffer inputBuf = MemoryUtil.memAllocFloat(input_pixels.length);
        FloatBuffer outputBuf = MemoryUtil.memAllocFloat(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_float(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels);
            outputBuf.get(output_pixels);
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            return result;
        } catch (Throwable th) {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            throw th;
        }
    }

    public static int nstbir_resize_uint16_generic(short[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, short[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space, long alloc_context) {
        ShortBuffer inputBuf = MemoryUtil.memAllocShort(input_pixels.length);
        ShortBuffer outputBuf = MemoryUtil.memAllocShort(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_uint16_generic(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, alloc_context);
            outputBuf.get(output_pixels);
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            return result;
        } catch (Throwable th) {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            throw th;
        }
    }

    public static int nstbir_resize_float_generic(float[] input_pixels, int input_w, int input_h, int input_stride_in_bytes, float[] output_pixels, int output_w, int output_h, int output_stride_in_bytes, int num_channels, int alpha_channel, int flags, int edge_wrap_mode, int filter, int space, long alloc_context) {
        FloatBuffer inputBuf = MemoryUtil.memAllocFloat(input_pixels.length);
        FloatBuffer outputBuf = MemoryUtil.memAllocFloat(output_pixels.length);
        try {
            inputBuf.put(input_pixels).flip();
            int result = nstbir_resize_float_generic(MemoryUtil.memAddress(inputBuf), input_w, input_h, input_stride_in_bytes, MemoryUtil.memAddress(outputBuf), output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, alloc_context);
            outputBuf.get(output_pixels);
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            return result;
        } catch (Throwable th) {
            MemoryUtil.memFree(outputBuf);
            MemoryUtil.memFree(inputBuf);
            throw th;
        }
    }

    private static int mapPixelLayout(int numChannels, int alphaChannel, int flags) {
        return numChannels;
    }

    private static int mapEdgeMode(int oldEdge) {
        return oldEdge - 1;
    }

    private static int mapDataType(int oldDataType, int colorspace) {
        if (oldDataType == 0 && colorspace == 1) {
            return 1;
        }
        switch (oldDataType) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
            default:
                return 0;
            case 3:
                return 4;
        }
    }
}
