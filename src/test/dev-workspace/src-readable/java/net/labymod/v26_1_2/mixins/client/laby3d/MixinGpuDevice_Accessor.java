package net.labymod.v26_1_2.mixins.client.laby3d;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.GpuDeviceBackend;
import net.labymod.v26_1_2.laby3d.accessor.GpuDeviceAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/laby3d/MixinGpuDevice_Accessor.class */
@Mixin({GpuDevice.class})
public class MixinGpuDevice_Accessor implements GpuDeviceAccessor {

    @Shadow
    @Final
    private GpuDeviceBackend backend;

    @Override // net.labymod.v26_1_2.laby3d.accessor.GpuDeviceAccessor
    public GpuDeviceBackend labyMod$getBackend() {
        return this.backend;
    }
}
