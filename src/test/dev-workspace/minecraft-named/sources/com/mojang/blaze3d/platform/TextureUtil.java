package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import net.minecraft.client.Screenshot;
import net.minecraft.util.ARGB;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/TextureUtil.class */
@DontObfuscate
public class TextureUtil {
    public static final int MIN_MIPMAP_LEVEL = 0;
    private static final int DEFAULT_IMAGE_BUFFER_SIZE = 8192;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int[][] DIRECTIONS = {new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}};

    public static ByteBuffer readResource(InputStream $$0) throws IOException {
        ReadableByteChannel $$1 = Channels.newChannel($$0);
        if ($$1 instanceof SeekableByteChannel) {
            SeekableByteChannel $$2 = (SeekableByteChannel) $$1;
            return readResource($$1, ((int) $$2.size()) + 1);
        }
        return readResource($$1, 8192);
    }

    private static ByteBuffer readResource(ReadableByteChannel $$0, int $$1) throws IOException {
        ByteBuffer $$2 = MemoryUtil.memAlloc($$1);
        while ($$0.read($$2) != -1) {
            try {
                if (!$$2.hasRemaining()) {
                    $$2 = MemoryUtil.memRealloc($$2, $$2.capacity() * 2);
                }
            } catch (IOException $$3) {
                MemoryUtil.memFree($$2);
                throw $$3;
            }
        }
        $$2.flip();
        return $$2;
    }

    public static void writeAsPNG(Path $$0, String $$1, GpuTexture $$2, int $$3, IntUnaryOperator $$4) {
        RenderSystem.assertOnRenderThread();
        long $$5 = 0;
        for (int $$6 = 0; $$6 <= $$3; $$6++) {
            $$5 += ((long) $$2.getFormat().pixelSize()) * ((long) $$2.getWidth($$6)) * ((long) $$2.getHeight($$6));
        }
        if ($$5 > 2147483647L) {
            throw new IllegalArgumentException("Exporting textures larger than 2GB is not supported");
        }
        GpuBuffer $$7 = RenderSystem.getDevice().createBuffer(() -> {
            return "Texture output buffer";
        }, 9, $$5);
        CommandEncoder $$8 = RenderSystem.getDevice().createCommandEncoder();
        Runnable $$9 = () -> {
            GpuBuffer.MappedView $$72 = $$8.mapBuffer($$7, true, false);
            int $$82 = 0;
            for (int $$92 = 0; $$92 <= $$3; $$92++) {
                try {
                    int $$10 = $$2.getWidth($$92);
                    int $$11 = $$2.getHeight($$92);
                    try {
                        NativeImage $$12 = new NativeImage($$10, $$11, false);
                        for (int $$13 = 0; $$13 < $$11; $$13++) {
                            for (int $$14 = 0; $$14 < $$10; $$14++) {
                                try {
                                    int $$15 = $$72.data().getInt($$82 + (($$14 + ($$13 * $$10)) * $$2.getFormat().pixelSize()));
                                    $$12.setPixelABGR($$14, $$13, $$4.applyAsInt($$15));
                                } catch (Throwable th) {
                                    try {
                                        $$12.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            }
                        }
                        Path $$16 = $$0.resolve($$1 + "_" + $$92 + ".png");
                        $$12.writeToFile($$16);
                        LOGGER.debug("Exported png to: {}", $$16.toAbsolutePath());
                        $$12.close();
                    } catch (IOException $$17) {
                        LOGGER.debug("Unable to write: ", $$17);
                    }
                    $$82 += $$2.getFormat().pixelSize() * $$10 * $$11;
                } catch (Throwable th3) {
                    if ($$72 != null) {
                        try {
                            $$72.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            }
            if ($$72 != null) {
                $$72.close();
            }
            $$7.close();
        };
        AtomicInteger $$10 = new AtomicInteger();
        int $$11 = 0;
        for (int $$12 = 0; $$12 <= $$3; $$12++) {
            $$8.copyTextureToBuffer($$2, $$7, $$11, () -> {
                if ($$10.getAndIncrement() == $$3) {
                    $$9.run();
                }
            }, $$12);
            $$11 += $$2.getFormat().pixelSize() * $$2.getWidth($$12) * $$2.getHeight($$12);
        }
    }

    public static Path getDebugTexturePath(Path $$0) {
        return $$0.resolve(Screenshot.SCREENSHOT_DIR).resolve("debug");
    }

    public static Path getDebugTexturePath() {
        return getDebugTexturePath(Path.of(".", new String[0]));
    }

    public static void solidify(NativeImage $$0) {
        int $$1 = $$0.getWidth();
        int $$2 = $$0.getHeight();
        int[] $$3 = new int[$$1 * $$2];
        int[] $$4 = new int[$$1 * $$2];
        Arrays.fill($$4, Integer.MAX_VALUE);
        IntArrayFIFOQueue $$5 = new IntArrayFIFOQueue();
        for (int $$6 = 0; $$6 < $$1; $$6++) {
            for (int $$7 = 0; $$7 < $$2; $$7++) {
                int $$8 = $$0.getPixel($$6, $$7);
                if (ARGB.alpha($$8) != 0) {
                    int $$9 = pack($$6, $$7, $$1);
                    $$4[$$9] = 0;
                    $$3[$$9] = $$8;
                    $$5.enqueue($$9);
                }
            }
        }
        while (!$$5.isEmpty()) {
            int $$10 = $$5.dequeueInt();
            int $$11 = x($$10, $$1);
            int $$12 = y($$10, $$1);
            for (int[] $$13 : DIRECTIONS) {
                int $$14 = $$11 + $$13[0];
                int $$15 = $$12 + $$13[1];
                int $$16 = pack($$14, $$15, $$1);
                if ($$14 >= 0 && $$15 >= 0 && $$14 < $$1 && $$15 < $$2 && $$4[$$16] > $$4[$$10] + 1) {
                    $$4[$$16] = $$4[$$10] + 1;
                    $$3[$$16] = $$3[$$10];
                    $$5.enqueue($$16);
                }
            }
        }
        for (int $$17 = 0; $$17 < $$1; $$17++) {
            for (int $$18 = 0; $$18 < $$2; $$18++) {
                int $$19 = $$0.getPixel($$17, $$18);
                if (ARGB.alpha($$19) == 0) {
                    $$0.setPixel($$17, $$18, ARGB.color(0, $$3[pack($$17, $$18, $$1)]));
                } else {
                    $$0.setPixel($$17, $$18, $$19);
                }
            }
        }
    }

    public static void fillEmptyAreasWithDarkColor(NativeImage $$0) {
        int $$1 = $$0.getWidth();
        int $$2 = $$0.getHeight();
        int $$3 = -1;
        int $$4 = Integer.MAX_VALUE;
        for (int $$5 = 0; $$5 < $$1; $$5++) {
            for (int $$6 = 0; $$6 < $$2; $$6++) {
                int $$7 = $$0.getPixel($$5, $$6);
                int $$8 = ARGB.alpha($$7);
                if ($$8 != 0) {
                    int $$9 = ARGB.red($$7);
                    int $$10 = ARGB.green($$7);
                    int $$11 = ARGB.blue($$7);
                    int $$12 = $$9 + $$10 + $$11;
                    if ($$12 < $$4) {
                        $$4 = $$12;
                        $$3 = $$7;
                    }
                }
            }
        }
        int $$13 = (3 * ARGB.red($$3)) / 4;
        int $$14 = (3 * ARGB.green($$3)) / 4;
        int $$15 = (3 * ARGB.blue($$3)) / 4;
        int $$16 = ARGB.color(0, $$13, $$14, $$15);
        for (int $$17 = 0; $$17 < $$1; $$17++) {
            for (int $$18 = 0; $$18 < $$2; $$18++) {
                int $$19 = $$0.getPixel($$17, $$18);
                if (ARGB.alpha($$19) == 0) {
                    $$0.setPixel($$17, $$18, $$16);
                }
            }
        }
    }

    private static int pack(int $$0, int $$1, int $$2) {
        return $$0 + ($$1 * $$2);
    }

    private static int x(int $$0, int $$1) {
        return $$0 % $$1;
    }

    private static int y(int $$0, int $$1) {
        return $$0 / $$1;
    }
}
