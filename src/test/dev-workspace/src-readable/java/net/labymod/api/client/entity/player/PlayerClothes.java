package net.labymod.api.client.entity.player;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/PlayerClothes.class */
public enum PlayerClothes {
    CAPE(0, "cape"),
    JACKET(1, PlayerModel.JACKET_NAME),
    LEFT_SLEEVE(2, PlayerModel.LEFT_SLEEVE_NAME),
    RIGHT_SLEEVE(3, PlayerModel.RIGHT_SLEEVE_NAME),
    LEFT_PANTS_LEG(4, "left_pants_leg"),
    RIGHT_PANTS_LEG(5, "right_pants_leg"),
    HAT(6, HumanoidModel.HAT_NAME);

    private static final PlayerClothes[] CLOTHES = values();

    @Deprecated
    private final int bit;

    @Deprecated
    private final int mask;

    @Deprecated
    private final String id;

    @Deprecated
    private final Component name;

    PlayerClothes(int bit, String id) {
        this.bit = bit;
        this.mask = 1 << bit;
        this.id = id;
        this.name = Component.translatable("options.modelPart." + id, new Component[0]);
    }

    @Deprecated
    public int getBit() {
        return this.bit;
    }

    @Deprecated
    public int getMask() {
        return this.mask;
    }

    @Deprecated
    public String getId() {
        return this.id;
    }

    @Deprecated
    public Component partName() {
        return this.name;
    }
}
