package net.labymod.v1_21_8.mixins.mojang.blaze3d.platform;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/mojang/blaze3d/platform/MixinNativeImage.class */
@Mixin({fnf.class})
@Implements({@Interface(iface = NativeImageAccessor.class, prefix = "accessor$")})
public abstract class MixinNativeImage implements NativeImageAccessor {

    @Shadow
    private long h;

    @Shadow
    protected abstract boolean a(WritableByteChannel writableByteChannel) throws IOException;

    @Shadow
    public abstract long shadow$h();

    @Override // net.labymod.core.client.accessor.resource.texture.NativeImageAccessor
    public void writeToStream(OutputStream outputStream) throws IOException {
        WritableByteChannel channel = Channels.newChannel(outputStream);
        if (!a(channel)) {
            throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
        }
    }

    @Override // net.labymod.core.client.accessor.resource.texture.NativeImageAccessor
    public boolean isFreed() {
        return this.h == 0;
    }

    @Intrinsic
    public long accessor$getPointer() {
        return shadow$h();
    }
}
