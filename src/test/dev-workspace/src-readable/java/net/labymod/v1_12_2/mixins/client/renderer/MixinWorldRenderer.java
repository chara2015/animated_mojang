package net.labymod.v1_12_2.mixins.client.renderer;

import java.nio.ByteBuffer;
import net.labymod.v1_12_2.client.renderer.WorldRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinWorldRenderer.class */
@Mixin({buk.class})
public abstract class MixinWorldRenderer implements WorldRendererAccessor {

    @Shadow
    private boolean o;

    @Shadow
    private ByteBuffer b;

    @Shadow
    private cea n;

    @Shadow
    private int f;

    @Shadow
    private int h;

    @Shadow
    protected abstract void k();

    @Override // net.labymod.v1_12_2.client.renderer.WorldRendererAccessor
    public ByteBuffer getBuffer() {
        return this.b;
    }

    @Override // net.labymod.v1_12_2.client.renderer.WorldRendererAccessor
    public int getNextElementPosition() {
        return (this.f * this.n.g()) + this.n.d(this.h);
    }

    @Override // net.labymod.v1_12_2.client.renderer.WorldRendererAccessor
    public boolean building() {
        return this.o;
    }

    @Override // net.labymod.v1_12_2.client.renderer.WorldRendererAccessor
    public void updateVertexFormatIndex() {
        k();
    }
}
