package net.labymod.v1_8_9.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.core.client.render.state.entity.EntitySnapshotCreator;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/entity/MixinRenderTNTPrimed.class */
@Mixin({bkf.class})
public abstract class MixinRenderTNTPrimed extends biv<vj> {
    protected MixinRenderTNTPrimed(biu lvt_1_1_) {
        super(lvt_1_1_);
    }

    @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V"}, at = {@At("TAIL")})
    public void render(vj entity, double x, double y, double z, float lvt_8_1_, float distance, CallbackInfo callbackInfo) {
        if (b(entity)) {
            return;
        }
        double distanceSq = entity.h(this.b.c);
        if (distanceSq < distance * distance) {
            return;
        }
        bfl.E();
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        bfl.b(x, y + ((double) entity.K) + 0.5d, z);
        bfl.b(-this.b.e, 0.0f, 1.0f, 0.0f);
        bfl.b(labyMod$getPlayerViewX(), 1.0f, 0.0f, 0.0f);
        bfl.a(-0.02666667f, -0.02666667f, 0.02666667f);
        LabyAPI labyAPI = Laby.labyAPI();
        Laby.references().renderEnvironmentContext().setPackedLight(MinecraftUtil.getPackedLight(entity));
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        bfl.f();
        bfl.a(false);
        bfl.i();
        AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(Laby.labyAPI().minecraft().getPartialTicks());
        labyAPI.tagRegistry().render(VersionedStackProvider.DEFAULT_STACK, snapshot, 0.0f, TagType.MAIN_TAG);
        laby3D.restoreStates();
        bfl.F();
    }

    private float labyMod$getPlayerViewX() {
        if (Laby.labyAPI().minecraft().options().perspective() == Perspective.THIRD_PERSON_FRONT) {
            return -this.b.f;
        }
        return this.b.f;
    }
}
