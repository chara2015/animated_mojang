package net.labymod.v1_21_10.mixins.client.renderer.entity;

import net.labymod.api.client.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/entity/MixinEntityRenderDispatcher.class */
@Mixin({hnw.class})
public abstract class MixinEntityRenderDispatcher implements EntityRenderDispatcher {
    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public boolean isRenderDebugHitBoxes() {
        fzz minecraft = fzz.W();
        return minecraft.m.b(ggh.w) && !minecraft.aF();
    }

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public void setRenderDebugHitBoxes(boolean renderDebugHitBoxes) {
        ggk ggkVar;
        ggj ggjVar = fzz.W().m;
        amj amjVar = ggh.w;
        if (renderDebugHitBoxes) {
            ggkVar = ggk.a;
        } else {
            ggkVar = ggk.c;
        }
        ggjVar.a(amjVar, ggkVar);
    }
}
