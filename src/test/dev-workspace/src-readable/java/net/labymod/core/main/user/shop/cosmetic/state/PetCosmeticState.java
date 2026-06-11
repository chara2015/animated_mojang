package net.labymod.core.main.user.shop.cosmetic.state;

import net.labymod.core.main.user.shop.cosmetic.pet.PetDataStorage;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/PetCosmeticState.class */
public class PetCosmeticState extends CosmeticState {
    private final PetDataStorage petData;
    private final PetDataStorage screenContextPetData;
    private final PetDataStorage entityPetData;

    public PetCosmeticState(AnimationStorage animationStorage, PetDataStorage petData, PetDataStorage screenContextPetData, PetDataStorage entityPetData) {
        super(animationStorage);
        this.petData = petData;
        this.screenContextPetData = screenContextPetData;
        this.entityPetData = entityPetData;
    }

    public PetCosmeticState(PetCosmeticState other) {
        super(other);
        this.petData = PetDataStorage.copyOf(other.petData);
        this.screenContextPetData = PetDataStorage.copyOf(other.screenContextPetData);
        this.entityPetData = PetDataStorage.copyOf(other.entityPetData);
    }

    public PetDataStorage petData() {
        return this.petData;
    }

    public PetDataStorage screenContextPetData() {
        return this.screenContextPetData;
    }

    public PetDataStorage entityPetData() {
        return this.entityPetData;
    }
}
