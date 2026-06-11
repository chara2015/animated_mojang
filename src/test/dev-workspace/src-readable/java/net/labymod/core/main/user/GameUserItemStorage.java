package net.labymod.core.main.user;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.laby.lib.cosmetics.AttachmentPoint;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import net.labymod.core.main.user.shop.cosmetic.pet.PetDataStorage;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.ItemUtil;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/GameUserItemStorage.class */
public class GameUserItemStorage {
    private final Int2ObjectMap<AnimationStorage> animationStorages = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<PetDataStorage> petDataStorages = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<PetDataStorage> screenContextPetDataStorages = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<PetDataStorage> minecraftEntityPetDataStorages = new Int2ObjectOpenHashMap();
    private int maxPets;

    public void prepare(DefaultGameUser user, @NotNull GameUserData userData) {
        int iPut;
        this.maxPets = 0;
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        user.removeTag(GameUser.HIDE_CAPE, GameUser.HIDE_SHIELD, GameUser.CUSTOM_ITEM);
        for (GameUserItem entry : userData.getItems()) {
            CosmeticDefinition definition = entry.definition();
            definition.id();
            CosmeticDetails itemDetails = definition.details();
            AttachmentPoint attachmentPoint = itemDetails.getAttachmentPoint();
            if (object2IntOpenHashMap.containsKey(attachmentPoint)) {
                iPut = object2IntOpenHashMap.getInt(attachmentPoint);
            } else {
                iPut = object2IntOpenHashMap.put(attachmentPoint, 0);
            }
            int level = iPut;
            definition.setPriorityLevel(level);
            object2IntOpenHashMap.put(attachmentPoint, level + 1);
            if (itemDetails.isHideCape()) {
                user.setTag(GameUser.HIDE_CAPE);
            }
            if (definition.cosmeticType() == CosmeticType.WALKING_PET) {
                this.maxPets++;
            }
        }
    }

    public AnimationStorage getAnimationStorage(int identifier) {
        return (AnimationStorage) this.animationStorages.get(identifier);
    }

    public PetDataStorage getPetDataStorage(int identifier) {
        if (ItemUtil.isSkipFlyingPets()) {
            return (PetDataStorage) this.minecraftEntityPetDataStorages.get(identifier);
        }
        if (Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().isScreenContext()) {
            return (PetDataStorage) this.screenContextPetDataStorages.get(identifier);
        }
        return (PetDataStorage) this.petDataStorages.get(identifier);
    }

    public int getMaxPets() {
        return this.maxPets;
    }

    public void clear() {
        this.animationStorages.clear();
        this.petDataStorages.clear();
        this.screenContextPetDataStorages.clear();
    }
}
