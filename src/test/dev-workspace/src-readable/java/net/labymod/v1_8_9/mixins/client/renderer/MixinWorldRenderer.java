package net.labymod.v1_8_9.mixins.client.renderer;

import java.nio.ByteBuffer;
import net.labymod.v1_8_9.client.renderer.WorldRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinWorldRenderer.class */
@Mixin({bfd.class})
public abstract class MixinWorldRenderer implements WorldRendererAccessor {

    @Shadow
    private boolean n;

    @Shadow
    private ByteBuffer a;

    @Shadow
    private bmu m;

    @Shadow
    private int e;

    @Shadow
    private int g;

    @Shadow
    protected abstract void k();

    @Override // net.labymod.v1_8_9.client.renderer.WorldRendererAccessor
    public ByteBuffer getBuffer() {
        return this.a;
    }

    @Override // net.labymod.v1_8_9.client.renderer.WorldRendererAccessor
    public int getNextElementPosition() {
        return (this.e * this.m.g()) + this.m.d(this.g);
    }

    @Override // net.labymod.v1_8_9.client.renderer.WorldRendererAccessor
    public boolean building() {
        return this.n;
    }

    @Override // net.labymod.v1_8_9.client.renderer.WorldRendererAccessor
    public void updateVertexFormatIndex() {
        k();
    }
}
