package net.labymod.v1_21_3.client.gfx.pipeline.blaze3d;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gfx/pipeline/blaze3d/PositionTextureColorVertexConsumerProvider.class */
public class PositionTextureColorVertexConsumerProvider {
    private static boolean initialized;
    private static boolean isVertexBufferWriterPresent = false;

    public static fgw create() {
        check();
        if (isVertexBufferWriterPresent) {
            return SodiumPositionTextureColorVertexConsumer.create();
        }
        return PositionTextureColorVertexConsumer.create();
    }

    private static void check() {
        if (initialized) {
            return;
        }
        initialized = true;
        try {
            Class.forName("net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter");
            isVertexBufferWriterPresent = true;
        } catch (ClassNotFoundException e) {
            isVertexBufferWriterPresent = false;
        }
    }
}
