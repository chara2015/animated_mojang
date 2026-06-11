package net.labymod.core.client.entity.player;

import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.model.BoneGroup;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.metadata.Metadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/DummyPlayerModel.class */
public class DummyPlayerModel implements PlayerModel {
    private final Model model;

    public DummyPlayerModel(Model model) {
        this.model = model;
    }

    @Override // net.labymod.api.client.render.model.Model
    public void addPart(String name, ModelPart part) {
    }

    @Override // net.labymod.api.client.render.model.Model
    public boolean isInvalidPart(String name) {
        return this.model.isInvalidPart(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public ModelPart getPart(String name) {
        return this.model.getPart(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public Map<String, ModelPart> getParts() {
        return this.model.getParts();
    }

    @Override // net.labymod.api.client.render.model.Model
    public List<BoneGroup> getBoneGroups() {
        return this.model.getBoneGroups();
    }

    @Override // net.labymod.api.client.render.model.Model
    public void setBoneGroups(List<BoneGroup> groups) {
        this.model.setBoneGroups(groups);
    }

    @Override // net.labymod.api.client.render.model.Model
    public Metadata metadata() {
        return this.model.metadata();
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        this.model.addChild(name, child);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        return this.model.getChild(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        return this.model.getChildren();
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHead() {
        return this.model.getPart(HumanoidModel.HEAD_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHat() {
        return this.model.getPart(HumanoidModel.HAT_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getBody() {
        return this.model.getPart("chest");
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftLeg() {
        return this.model.getPart(HumanoidModel.LEFT_LEG_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightLeg() {
        return this.model.getPart(HumanoidModel.RIGHT_LEG_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftArm() {
        return this.model.getPart(HumanoidModel.LEFT_ARM_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightArm() {
        return this.model.getPart(HumanoidModel.RIGHT_ARM_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel, net.labymod.api.client.render.model.entity.HumanoidModel
    public void copyToSecondLayer() {
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.model.getPart(PlayerModel.JACKET_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return this.model.getPart(PlayerModel.CLOAK_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.model.getPart(PlayerModel.LEFT_PANTS_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.model.getPart(PlayerModel.RIGHT_PANTS_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.model.getPart(PlayerModel.LEFT_SLEEVE_NAME);
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.model.getPart(PlayerModel.RIGHT_SLEEVE_NAME);
    }
}
