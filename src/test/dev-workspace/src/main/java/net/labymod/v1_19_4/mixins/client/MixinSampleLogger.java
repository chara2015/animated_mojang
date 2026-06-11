package net.labymod.v1_19_4.mixins.client;

import net.labymod.api.client.MinecraftSampleLoggerAccessor;
import net.labymod.api.profiler.SampleLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/MixinSampleLogger.class */
@Mixin({aox.class})
public class MixinSampleLogger implements MinecraftSampleLoggerAccessor {

    @Unique
    private final SampleLogger labyMod$sampleLogger = new SampleLogger();

    @Inject(method = {"logFrameDuration"}, at = {@At("HEAD")})
    private void labyMod$addFrame(long sample, CallbackInfo ci) {
        this.labyMod$sampleLogger.log(sample);
    }

    @Override // net.labymod.api.client.MinecraftSampleLoggerAccessor
    public SampleLogger logger() {
        return this.labyMod$sampleLogger;
    }
}
