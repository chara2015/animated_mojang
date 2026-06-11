package net.labymod.core.main.user.shop.cosmetic.pet;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ShoulderPetAI.class */
public class ShoulderPetAI extends AbstractShoulderPetAI {
    private static final long FAKE_IDLE_DELAY = 0;
    private static final long FAKE_MOVE_DELAY = 0;

    public ShoulderPetAI(CosmeticDefinition definition) {
        super(definition);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected boolean shouldBounce(PetDataStorage storage) {
        return true;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected boolean isAlwaysAttachedToArm() {
        return true;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected boolean shouldTriggerOnSwing() {
        return false;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected long getFakeIdleDelay() {
        return 0L;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected long getFakeMoveDelay() {
        return 0L;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.AbstractShoulderPetAI
    protected void onAbsoluteMovement(Stack stack, PetDataStorage storage, double x, double y, double z, double playerX, double playerY, double playerZ, float renderYaw) {
    }
}
