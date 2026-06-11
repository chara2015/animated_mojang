package net.labymod.core.main.user.shop.cosmetic.state;

import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import net.labymod.core.main.user.shop.cosmetic.pet.PetDataStorage;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.DefaultPetBehavior;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/CosmeticStateFactory.class */
public final class CosmeticStateFactory {
    private CosmeticStateFactory() {
    }

    public static CosmeticState create(CosmeticDefinition definition, CosmeticType type) {
        AnimationStorage animationStorage = AnimationStorage.create();
        if (definition.id() == 36) {
            return new EyelidsCosmeticState(animationStorage);
        }
        switch (type) {
            case FLYING_PET:
            case SHOULDER_PET:
                return new PetCosmeticState(animationStorage, PetDataStorage.create(), PetDataStorage.create(), PetDataStorage.create());
            case WALKING_PET:
                float movementSpeed = definition.details().getPetMovementSpeed();
                WalkingPetCosmeticState walkingState = new WalkingPetCosmeticState(animationStorage, null);
                Supplier supplier = () -> {
                    if (definition.itemModel() != null) {
                        return definition.itemModel().getModel();
                    }
                    return null;
                };
                Objects.requireNonNull(walkingState);
                PetBehavior behavior = new DefaultPetBehavior(supplier, movementSpeed, walkingState::owner);
                walkingState.setBehavior(behavior);
                return walkingState;
            default:
                return new CosmeticState(animationStorage);
        }
    }

    public static CosmeticState copy(CosmeticState existing) {
        if (existing instanceof EyelidsCosmeticState) {
            EyelidsCosmeticState eyelidsState = (EyelidsCosmeticState) existing;
            return new EyelidsCosmeticState(eyelidsState);
        }
        if (existing instanceof WalkingPetCosmeticState) {
            WalkingPetCosmeticState walkingPetState = (WalkingPetCosmeticState) existing;
            return new WalkingPetCosmeticState(walkingPetState);
        }
        if (existing instanceof PetCosmeticState) {
            PetCosmeticState petState = (PetCosmeticState) existing;
            return new PetCosmeticState(petState);
        }
        return new CosmeticState(existing);
    }
}
