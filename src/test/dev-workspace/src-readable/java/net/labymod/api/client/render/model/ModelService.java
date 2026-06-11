package net.labymod.api.client.render.model;

import java.io.InputStream;
import java.util.Collection;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelService.class */
@Referenceable
public interface ModelService {
    Model createEmptyModel();

    ModelPart createEmptyPart();

    @Nullable
    Model loadBlockBenchModel(String str);

    @Nullable
    Model loadBlockBenchModel(InputStream inputStream);

    @Nullable
    Model loadBlockBenchModel(ResourceLocation resourceLocation);

    Collection<ModelAnimation> loadBlockBenchAnimations(String str, Collection<AnimationMeta<?>> collection);

    AnimationController createAnimationController();

    void generateBoneIds(Model model);
}
