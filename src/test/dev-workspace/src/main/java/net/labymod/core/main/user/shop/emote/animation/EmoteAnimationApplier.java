package net.labymod.core.main.user.shop.emote.animation;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/animation/EmoteAnimationApplier.class */
public class EmoteAnimationApplier extends DefaultAnimationController.DefaultAnimationApplier {
    @Override // net.labymod.core.client.render.model.animation.DefaultAnimationController.DefaultAnimationApplier, net.labymod.api.client.render.model.animation.AnimationController.AnimationApplier
    public void applyPosition(Model model, ModelPart modelPart, FloatVector3 position, ModelPartAnimation animation) {
        if ((model instanceof HumanoidModel) && !((Boolean) animation.parent().getMetaDefault(EmoteAnimationMeta.BLOCK_SNEAKING, false)).booleanValue()) {
            modelPart.getAnimationTransformation().addTranslation(position);
        } else {
            super.applyPosition(model, modelPart, position, animation);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0044  */
    @Override // net.labymod.core.client.render.model.animation.DefaultAnimationController.DefaultAnimationApplier, net.labymod.api.client.render.model.animation.AnimationController.AnimationApplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void applyRotation(net.labymod.api.client.render.model.Model r7, net.labymod.api.client.render.model.ModelPart r8, net.labymod.api.util.math.vector.FloatVector3 r9, net.labymod.api.client.render.model.animation.ModelPartAnimation r10) {
        /*
            r6 = this;
            r0 = r7
            boolean r0 = r0 instanceof net.labymod.api.client.render.model.entity.HumanoidModel
            if (r0 == 0) goto L67
            r0 = r7
            net.labymod.api.client.render.model.entity.HumanoidModel r0 = (net.labymod.api.client.render.model.entity.HumanoidModel) r0
            r11 = r0
            r0 = r10
            net.labymod.api.client.render.model.animation.ModelAnimation r0 = r0.parent()
            net.labymod.api.client.render.model.animation.meta.AnimationMeta<java.lang.Boolean> r1 = net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta.BLOCK_SNEAKING
            r2 = 0
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            java.lang.Object r0 = r0.getMetaDefault(r1, r2)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L67
            r0 = r7
            boolean r0 = r0 instanceof net.labymod.api.client.render.model.entity.player.PlayerModel
            if (r0 == 0) goto L44
            r0 = r7
            net.labymod.api.client.render.model.entity.player.PlayerModel r0 = (net.labymod.api.client.render.model.entity.player.PlayerModel) r0
            r13 = r0
            r0 = r8
            r1 = r13
            net.labymod.api.client.render.model.ModelPart r1 = r1.getCloak()
            boolean r0 = java.util.Objects.equals(r0, r1)
            if (r0 == 0) goto L44
            r0 = 1
            goto L45
        L44:
            r0 = 0
        L45:
            r12 = r0
            r0 = r11
            net.labymod.api.client.render.model.ModelPart r0 = r0.getBody()
            r1 = r8
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L5c
            r0 = r12
            if (r0 == 0) goto L67
        L5c:
            r0 = r8
            net.labymod.api.util.math.Transformation r0 = r0.getAnimationTransformation()
            r1 = r9
            r0.addRotation(r1)
            return
        L67:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            super.applyRotation(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.main.user.shop.emote.animation.EmoteAnimationApplier.applyRotation(net.labymod.api.client.render.model.Model, net.labymod.api.client.render.model.ModelPart, net.labymod.api.util.math.vector.FloatVector3, net.labymod.api.client.render.model.animation.ModelPartAnimation):void");
    }
}
