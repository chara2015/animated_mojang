package net.labymod.core.main.user.shop.cosmetic.texture;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.resources.texture.GameImage;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/TextureOpaqueAnalyzer.class */
public final class TextureOpaqueAnalyzer {
    private static final int ALPHA_THRESHOLD = 250;

    private TextureOpaqueAnalyzer() {
    }

    public static boolean isOpaqueInUVRegions(Model model, GameImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Set<Long> sampledPixels = new HashSet<>();
        collectUVPixels(model, width, height, sampledPixels);
        Iterator<Long> it = sampledPixels.iterator();
        while (it.hasNext()) {
            long packed = it.next().longValue();
            int x = (int) (packed >> 32);
            int y = (int) packed;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                int argb = image.getARGB(x, y);
                int alpha = (argb >> 24) & 255;
                if (alpha < ALPHA_THRESHOLD) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void collectUVPixels(Model model, int texWidth, int texHeight, Set<Long> pixels) {
        for (ModelPart part : model.getChildren().values()) {
            collectPartUVPixels(part, texWidth, texHeight, pixels);
        }
    }

    private static void collectPartUVPixels(ModelPart part, int texWidth, int texHeight, Set<Long> pixels) {
        for (ModelBox box : part.getBoxes()) {
            for (ModelBoxQuad quad : box.getQuads()) {
                float minU = Float.POSITIVE_INFINITY;
                float minV = Float.POSITIVE_INFINITY;
                float maxU = Float.NEGATIVE_INFINITY;
                float maxV = Float.NEGATIVE_INFINITY;
                for (ModelBoxVertex vertex : quad.getVertices()) {
                    Vector2f uv = vertex.getUV();
                    if (uv.x() < minU) {
                        minU = uv.x();
                    }
                    if (uv.x() > maxU) {
                        maxU = uv.x();
                    }
                    if (uv.y() < minV) {
                        minV = uv.y();
                    }
                    if (uv.y() > maxV) {
                        maxV = uv.y();
                    }
                }
                sampleUVRect(minU, minV, maxU, maxV, texWidth, texHeight, pixels);
            }
        }
        if (part instanceof EnhancedModelPart) {
            EnhancedModelPart enhanced = (EnhancedModelPart) part;
            for (Shape shape : enhanced.getShapes()) {
                for (ShapeQuad quad2 : shape.getQuads()) {
                    float minU2 = Float.POSITIVE_INFINITY;
                    float minV2 = Float.POSITIVE_INFINITY;
                    float maxU2 = Float.NEGATIVE_INFINITY;
                    float maxV2 = Float.NEGATIVE_INFINITY;
                    for (ShapeVertex vertex2 : quad2.getVertices()) {
                        Vector2f uv2 = vertex2.getUV();
                        if (uv2.x() < minU2) {
                            minU2 = uv2.x();
                        }
                        if (uv2.x() > maxU2) {
                            maxU2 = uv2.x();
                        }
                        if (uv2.y() < minV2) {
                            minV2 = uv2.y();
                        }
                        if (uv2.y() > maxV2) {
                            maxV2 = uv2.y();
                        }
                    }
                    sampleUVRect(minU2, minV2, maxU2, maxV2, texWidth, texHeight, pixels);
                }
            }
        }
        for (ModelPart child : part.getChildren().values()) {
            collectPartUVPixels(child, texWidth, texHeight, pixels);
        }
    }

    private static void sampleUVRect(float minU, float minV, float maxU, float maxV, int texWidth, int texHeight, Set<Long> pixels) {
        int x0 = (int) Math.floor(minU * texWidth);
        int y0 = (int) Math.floor(minV * texHeight);
        int x1 = (int) Math.ceil(maxU * texWidth);
        int y1 = (int) Math.ceil(maxV * texHeight);
        if (x1 <= x0) {
            x1 = x0 + 1;
        }
        if (y1 <= y0) {
            y1 = y0 + 1;
        }
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                pixels.add(Long.valueOf(pack(x, y)));
            }
        }
    }

    private static long pack(int x, int y) {
        return (((long) x) << 32) | (((long) y) & 4294967295L);
    }
}
