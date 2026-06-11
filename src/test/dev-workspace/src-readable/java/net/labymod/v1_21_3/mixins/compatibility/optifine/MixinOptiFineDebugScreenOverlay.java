package net.labymod.v1_21_3.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/compatibility/optifine/MixinOptiFineDebugScreenOverlay.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({fon.class})
public abstract class MixinOptiFineDebugScreenOverlay {

    @Shadow
    private fbw i;

    @Shadow
    @Final
    private fmg f;

    @Shadow
    private fbw j;

    @Shadow
    @Final
    private fpx w;

    @Shadow
    @Final
    private fqa x;

    @Shadow
    @Final
    private fpw z;

    @Shadow
    @Final
    private fpy y;

    @Shadow
    private boolean p;

    @Shadow
    private boolean q;

    @Shadow
    @Final
    private fpz A;

    @Shadow
    @Final
    private bod s;

    @Shadow
    protected abstract void b(fns fnsVar);

    @Shadow
    protected abstract void c(fns fnsVar);

    @Overwrite
    public void a(fns graphics) {
        bpt filler = bps.a();
        filler.a("debug");
        bvk cameraEntity = this.f.ao();
        this.i = cameraEntity.a(20.0d, 0.0f, false);
        this.j = cameraEntity.a(20.0d, 0.0f, true);
        b(graphics);
        c(graphics);
        this.A.a(10);
        if (this.p) {
            int $$3 = graphics.a();
            int $$4 = $$3 / 2;
            this.w.a(graphics, 0, this.w.a($$4));
            if (this.s.d() > 0) {
                int $$5 = this.x.a($$4);
                this.x.a(graphics, $$3 - $$5, $$5);
            }
            this.A.a(this.x.a());
        }
        if (this.q) {
            int $$6 = graphics.a();
            int $$7 = $$6 / 2;
            if (!this.f.T()) {
                this.z.a(graphics, 0, this.z.a($$7));
            }
            int $$8 = this.y.a($$7);
            this.y.a(graphics, $$6 - $$8, $$8);
            this.A.a(this.y.a());
        }
        bpy $$9 = filler.d("profilerPie");
        try {
            this.A.a(graphics);
            if ($$9 != null) {
                $$9.close();
            }
            filler.c();
        } catch (Throwable th) {
            if ($$9 != null) {
                try {
                    $$9.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
