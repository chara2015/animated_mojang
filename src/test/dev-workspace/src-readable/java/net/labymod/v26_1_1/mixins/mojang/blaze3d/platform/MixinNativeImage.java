package net.labymod.v26_1_1.mixins.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
import org.lwjgl.stb.STBImage;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/mojang/blaze3d/platform/MixinNativeImage.class */
@Mixin({NativeImage.class})
@Implements({@Interface(iface = NativeImageAccessor.class, prefix = "accessor$")})
public abstract class MixinNativeImage implements NativeImageAccessor {

    @Shadow
    private long pixels;

    @Shadow
    protected abstract boolean writeToChannel(WritableByteChannel writableByteChannel) throws IOException;

    @Shadow
    public abstract long shadow$getPointer();

    @Override // net.labymod.core.client.accessor.resource.texture.NativeImageAccessor
    public void writeToStream(OutputStream outputStream) throws IOException {
        WritableByteChannel channel = Channels.newChannel(outputStream);
        if (!writeToChannel(channel)) {
            throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
        }
    }

    @Override // net.labymod.core.client.accessor.resource.texture.NativeImageAccessor
    public boolean isFreed() {
        return this.pixels == 0;
    }

    @Intrinsic
    public long accessor$getPointer() {
        return shadow$getPointer();
    }
}
