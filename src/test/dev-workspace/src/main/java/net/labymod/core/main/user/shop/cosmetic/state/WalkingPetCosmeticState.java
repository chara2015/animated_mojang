package net.labymod.core.main.user.shop.cosmetic.state;

import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/WalkingPetCosmeticState.class */
public class WalkingPetCosmeticState extends CosmeticState {
    private PetBehavior behavior;
    private boolean firstTeleported;

    @Nullable
    private Player owner;

    public WalkingPetCosmeticState(AnimationStorage animationStorage, PetBehavior behavior) {
        super(animationStorage);
        this.behavior = behavior;
    }

    public WalkingPetCosmeticState(WalkingPetCosmeticState other) {
        super(other);
        this.behavior = other.behavior;
        this.firstTeleported = other.firstTeleported;
        this.owner = other.owner;
    }

    public PetBehavior behavior() {
        return this.behavior;
    }

    public void setBehavior(PetBehavior behavior) {
        this.behavior = behavior;
    }

    public boolean isFirstTeleported() {
        return this.firstTeleported;
    }

    public void setFirstTeleported(boolean firstTeleported) {
        this.firstTeleported = firstTeleported;
    }

    @Nullable
    public Player owner() {
        return this.owner;
    }

    public void setOwner(@Nullable Player owner) {
        this.owner = owner;
    }
}
