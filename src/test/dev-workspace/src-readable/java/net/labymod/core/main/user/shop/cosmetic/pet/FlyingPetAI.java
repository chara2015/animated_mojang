package net.labymod.core.main.user.shop.cosmetic.pet;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/FlyingPetAI.class */
public class FlyingPetAI extends AbstractShoulderPetAI {
    private static final long FAKE_IDLE_DELAY = 7000;
    private static final long FAKE_MOVE_DELAY = 1000;

    public FlyingPetAI(CosmeticDefinition definition) {
        super(definition);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected long getFakeIdleDelay() {
        return FAKE_IDLE_DELAY;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected long getFakeMoveDelay() {
        return 1000L;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected void onAbsoluteMovement(Stack stack, PetDataStorage storage, double x, double y, double z, double playerX, double playerY, double playerZ, float renderYaw) {
        if (!storage.isAttachedToOwner() && this.definition.details().canMove()) {
            stack.rotate(renderYaw, 0.0f, -1.0f, 0.0f);
            stack.scale(1.0659f, 1.0659f, 1.0659f);
            stack.translate(x - playerX, (-y) + playerY, (-z) + playerZ);
        }
    }
}
