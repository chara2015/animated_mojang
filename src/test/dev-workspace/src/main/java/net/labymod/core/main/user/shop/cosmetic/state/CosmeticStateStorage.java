package net.labymod.core.main.user.shop.cosmetic.state;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.pet.PetDataStorage;
import net.labymod.core.main.user.shop.item.ItemUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/CosmeticStateStorage.class */
public class CosmeticStateStorage {
    private final Int2ObjectOpenHashMap<CosmeticState> states = new Int2ObjectOpenHashMap<>();

    public void prepare(DefaultGameUser user, GameUserData userData) {
        for (GameUserItem entry : userData.getItems()) {
            CosmeticDefinition definition = entry.definition();
            if (definition != null) {
                int identifier = definition.id();
                if (!this.states.containsKey(identifier)) {
                    this.states.put(identifier, CosmeticStateFactory.create(definition, definition.cosmeticType()));
                }
            }
        }
    }

    @Nullable
    public CosmeticState getState(int cosmeticId) {
        return (CosmeticState) this.states.get(cosmeticId);
    }

    @Nullable
    public <T extends CosmeticState> T getState(int cosmeticId, Class<T> type) {
        CosmeticState state = (CosmeticState) this.states.get(cosmeticId);
        if (type.isInstance(state)) {
            return type.cast(state);
        }
        return null;
    }

    @Nullable
    public PetDataStorage getPetDataStorage(int cosmeticId) {
        PetCosmeticState petState = (PetCosmeticState) getState(cosmeticId, PetCosmeticState.class);
        if (petState == null) {
            return null;
        }
        if (ItemUtil.isSkipFlyingPets()) {
            return petState.entityPetData();
        }
        if (Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().isScreenContext()) {
            return petState.screenContextPetData();
        }
        return petState.petData();
    }

    public void clear() {
        ObjectIterator it = this.states.values().iterator();
        while (it.hasNext()) {
            CosmeticState state = (CosmeticState) it.next();
            state.close();
        }
        this.states.clear();
    }
}
