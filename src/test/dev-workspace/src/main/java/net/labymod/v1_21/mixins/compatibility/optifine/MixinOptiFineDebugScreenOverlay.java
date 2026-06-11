package net.labymod.v1_21.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/compatibility/optifine/MixinOptiFineDebugScreenOverlay.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({fiu.class})
public abstract class MixinOptiFineDebugScreenOverlay {

    @Shadow
    private exa i;

    @Shadow
    @Final
    private fgo f;

    @Shadow
    private exa j;

    @Shadow
    @Final
    private fkd w;

    @Shadow
    @Final
    private fkf x;

    @Shadow
    @Final
    private fkc z;

    @Shadow
    @Final
    private fke y;

    @Shadow
    private boolean p;

    @Shadow
    private boolean q;

    @Shadow
    protected abstract void b(fhz fhzVar);

    @Shadow
    protected abstract void c(fhz fhzVar);

    @Overwrite
    public void a(fhz graphicsIn) {
        this.f.aH().a("debug");
        bsr entity = this.f.an();
        this.i = entity.a(20.0d, 0.0f, false);
        this.j = entity.a(20.0d, 0.0f, true);
        graphicsIn.a(() -> {
            b(graphicsIn);
            c(graphicsIn);
            if (this.p) {
                int width = graphicsIn.a();
                int halfWidth = width / 2;
                this.w.a(graphicsIn, 0, this.w.a(halfWidth));
                if (this.f.V() != null) {
                    int tpsChartWidth = this.x.a(halfWidth);
                    this.x.a(graphicsIn, width - tpsChartWidth, tpsChartWidth);
                }
            }
            if (this.q) {
                int width2 = graphicsIn.a();
                int halfWidth2 = width2 / 2;
                if (!this.f.T()) {
                    this.z.a(graphicsIn, 0, this.z.a(halfWidth2));
                }
                int pingChartWidth = this.y.a(halfWidth2);
                this.y.a(graphicsIn, width2 - pingChartWidth, pingChartWidth);
            }
        });
        this.f.aH().c();
    }
}
