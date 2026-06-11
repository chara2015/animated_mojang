package net.labymod.v1_8_9.mixins.client.resources;

import java.io.IOException;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/resources/MixinInputStreamLeakedResourceLogger.class */
@Mixin(targets = {"net.minecraft.client.resources.FallbackResourceManager$InputStreamLeakedResourceLogger"})
public abstract class MixinInputStreamLeakedResourceLogger extends InputStream {

    @Shadow
    @Final
    private InputStream a;

    @Shadow
    private boolean c;

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.c) {
            return 0;
        }
        return this.a.available();
    }
}
