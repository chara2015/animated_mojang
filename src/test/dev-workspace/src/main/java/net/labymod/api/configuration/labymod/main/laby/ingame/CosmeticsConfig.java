package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.user.shop.RenderMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/CosmeticsConfig.class */
public interface CosmeticsConfig extends ConfigAccessor {
    ConfigProperty<Boolean> renderCosmetics();

    ConfigProperty<Boolean> showCosmeticsInFirstPerson();

    ConfigProperty<RenderMode> renderMode();

    ConfigProperty<CloakPriority> cloakPriority();

    ConfigProperty<Boolean> hideCosmeticsInDistance();

    ConfigProperty<Integer> hideAfterBlocks();

    ConfigProperty<Integer> maxCosmeticsPerPlayer();

    ConfigProperty<Boolean> onlyFriends();

    ConfigProperty<Boolean> walkingPets();

    ConfigProperty<Boolean> showWalkingPetNametags();
}
