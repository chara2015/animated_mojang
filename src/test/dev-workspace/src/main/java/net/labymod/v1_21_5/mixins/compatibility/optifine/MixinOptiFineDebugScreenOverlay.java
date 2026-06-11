package net.labymod.v1_21_5.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/compatibility/optifine/MixinOptiFineDebugScreenOverlay.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({fug.class})
public abstract class MixinOptiFineDebugScreenOverlay {

    @Shadow
    private fga m;

    @Shadow
    @Final
    private fqq h;

    @Shadow
    private fga n;

    @Shadow
    @Final
    private fvp A;

    @Shadow
    @Final
    private fvs B;

    @Shadow
    @Final
    private fvo D;

    @Shadow
    @Final
    private fvq C;

    @Shadow
    private boolean t;

    @Shadow
    private boolean u;

    @Shadow
    @Final
    private fvr E;

    @Shadow
    @Final
    private bpn w;

    @Shadow
    protected abstract void b(ftk ftkVar);

    @Shadow
    protected abstract void c(ftk ftkVar);

    @Overwrite
    public void a(ftk graphics) {
        brm filler = brl.a();
        filler.a("debug");
        bxe cameraEntity = this.h.ao();
        this.m = cameraEntity.a(20.0d, 0.0f, false);
        this.n = cameraEntity.a(20.0d, 0.0f, true);
        b(graphics);
        c(graphics);
        this.E.a(10);
        if (this.t) {
            int $$3 = graphics.a();
            int $$4 = $$3 / 2;
            this.A.a(graphics, 0, this.A.a($$4));
            if (this.w.d() > 0) {
                int $$5 = this.B.a($$4);
                this.B.a(graphics, $$3 - $$5, $$5);
            }
            this.E.a(this.B.a());
        }
        if (this.u) {
            int $$6 = graphics.a();
            int $$7 = $$6 / 2;
            if (!this.h.T()) {
                this.D.a(graphics, 0, this.D.a($$7));
            }
            int $$8 = this.C.a($$7);
            this.C.a(graphics, $$6 - $$8, $$8);
            this.E.a(this.C.a());
        }
        brr $$9 = filler.d("profilerPie");
        try {
            this.E.a(graphics);
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
