package net.labymod.v26_1_1.mixins.client.laby3d.textures;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/laby3d/textures/MixinGpuTextureView.class */
@Mixin({GpuTextureView.class})
@Implements({@Interface(iface = DeviceTextureView.class, prefix = "laby3d$")})
public abstract class MixinGpuTextureView implements DeviceTextureView {
    @Shadow
    public abstract GpuTexture shadow$texture();

    @Shadow
    public abstract int baseMipLevel();

    @Shadow
    public abstract boolean shadow$isClosed();

    @Shadow
    public abstract void shadow$close();

    @Shadow
    public abstract int shadow$mipLevels();

    @Intrinsic
    public DeviceTexture laby3d$texture() {
        return (DeviceTexture) CastUtil.requireInstanceOf(shadow$texture(), DeviceTexture.class);
    }

    public int startLevel() {
        return baseMipLevel();
    }

    @Intrinsic
    public int laby3d$mipLevels() {
        return shadow$mipLevels();
    }

    @Intrinsic
    public boolean laby3d$isClosed() {
        return shadow$isClosed();
    }

    @Intrinsic
    public void laby3d$close() {
        shadow$close();
    }
}
