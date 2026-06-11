package net.labymod.core.client.render.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Singleton;
import net.laby.lib.bedrock.animation.AnimationFile;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.labymod.api.client.render.model.BoneGroup;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.core.main.user.shop.item.geometry.BedrockModelLoader;
import net.labymod.core.main.user.shop.item.geometry.GeometryLoader;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/DefaultModelService.class */
@Singleton
@Implements(ModelService.class)
public class DefaultModelService implements ModelService {
    private static final Logging LOGGER = Logging.create((Class<?>) ModelService.class);
    private int boneId;

    @Override // net.labymod.api.client.render.model.ModelService
    public Model createEmptyModel() {
        return new DefaultModel();
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public ModelPart createEmptyPart() {
        return new DefaultModelPart();
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public Model loadBlockBenchModel(String json) {
        try {
            return BedrockModelLoader.create(new GeometryLoader(json).getGeometry(), true).getModel();
        } catch (Exception exception) {
            LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public Model loadBlockBenchModel(InputStream inputStream) {
        try {
            return BedrockModelLoader.create(new GeometryLoader(inputStream).getGeometry(), true).getModel();
        } catch (Exception exception) {
            LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public Model loadBlockBenchModel(ResourceLocation location) {
        try {
            return loadBlockBenchModel(location.openStream());
        } catch (Exception exception) {
            LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }

    public Model loadBlockBenchModel(GeometryFile geometryFile) {
        try {
            return BedrockModelLoader.create(geometryFile, true).getModel();
        } catch (Exception exception) {
            LOGGER.error("Block bench model could not be loaded.", exception);
            return null;
        }
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public Collection<ModelAnimation> loadBlockBenchAnimations(String json, Collection<AnimationMeta<?>> supportedMeta) {
        return new AnimationLoader(json).load(supportedMeta).getAnimations();
    }

    public Collection<ModelAnimation> loadBlockBenchAnimations(AnimationFile animationFile, Collection<AnimationMeta<?>> supportedMeta) {
        return AnimationLoader.fromAnimationFile(animationFile, supportedMeta).getAnimations();
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public AnimationController createAnimationController() {
        return new DefaultAnimationController();
    }

    @Override // net.labymod.api.client.render.model.ModelService
    public void generateBoneIds(Model model) {
        List<BoneGroup> groups = new ArrayList<>();
        List<ModelPart> currentRoots = new ArrayList<>();
        int currentGroupBones = 0;
        for (ModelPart root : model.getChildren().values()) {
            int subtreeSize = countSubtreeSize(root);
            if (subtreeSize > 240) {
                throw new IllegalStateException("Bone subtree '" + root.getDebugName() + "' has " + subtreeSize + " bones, exceeds the maximum limit of 240 bones per group.");
            }
            if (currentGroupBones + subtreeSize > 240) {
                groups.add(new BoneGroup(currentRoots));
                currentRoots = new ArrayList<>();
                currentGroupBones = 0;
            }
            this.boneId = currentGroupBones;
            assignIds(root);
            currentRoots.add(root);
            currentGroupBones += subtreeSize;
        }
        if (!currentRoots.isEmpty()) {
            groups.add(new BoneGroup(currentRoots));
        }
        this.boneId = 0;
        model.setBoneGroups(groups);
    }

    private int countSubtreeSize(ModelPart part) {
        int count = 1;
        for (ModelPart child : part.getChildren().values()) {
            count += countSubtreeSize(child);
        }
        return count;
    }

    private void assignIds(ModelPart part) {
        int i = this.boneId;
        this.boneId = i + 1;
        part.setId(i);
        for (ModelPart child : part.getChildren().values()) {
            assignIds(child);
        }
    }
}
