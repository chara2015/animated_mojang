package net.labymod.api.client.render.model.entity.player;

import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/entity/player/PlayerModel.class */
public interface PlayerModel extends HumanoidModel {
    public static final String JACKET_NAME = "jacket";
    public static final String LEFT_PANTS_NAME = "left_pants";
    public static final String RIGHT_PANTS_NAME = "right_pants";
    public static final String LEFT_SLEEVE_NAME = "left_sleeve";
    public static final String RIGHT_SLEEVE_NAME = "right_sleeve";
    public static final String CLOAK_NAME = "cloak";

    ModelPart getJacket();

    ModelPart getCloak();

    ModelPart getLeftPants();

    ModelPart getRightPants();

    ModelPart getLeftSleeve();

    ModelPart getRightSleeve();

    default ModelPart getSleeve(boolean rightSide) {
        return rightSide ? getRightSleeve() : getLeftSleeve();
    }

    default ModelPart getPants(boolean rightSide) {
        return rightSide ? getRightPants() : getLeftPants();
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    default void copyToSecondLayer() {
        super.copyToSecondLayer();
        getJacket().copyFrom(getBody());
        getLeftPants().copyFrom(getLeftLeg());
        getRightPants().copyFrom(getRightLeg());
        getLeftSleeve().copyFrom(getLeftArm());
        getRightSleeve().copyFrom(getRightArm());
    }
}
