package net.labymod.core.client.mojang.model;

import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.model.MojangModelService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/mojang/model/DefaultMojangModelService.class */
@Singleton
@Implements(MojangModelService.class)
public class DefaultMojangModelService implements MojangModelService {
    @Override // net.labymod.api.mojang.model.MojangModelService
    public Model getPlayerModel(MinecraftServices.SkinVariant variant) {
        ResourceLocation resourceLocation;
        if (variant == MinecraftServices.SkinVariant.SLIM) {
            resourceLocation = Constants.Resources.MODEL_ALEX;
        } else {
            resourceLocation = Constants.Resources.MODEL_STEVE;
        }
        ResourceLocation modelResource = resourceLocation;
        return createModel(modelResource);
    }

    @Override // net.labymod.api.mojang.model.MojangModelService
    public Model createModel(ResourceLocation modelResource) {
        MinecraftServices.SkinVariant skinVariant;
        ModelService modelService = Laby.labyAPI().renderPipeline().modelService();
        Model model = modelService.loadBlockBenchModel(modelResource);
        if (model != null) {
            Metadata metadata = model.metadata();
            if (modelResource == Constants.Resources.MODEL_ALEX) {
                skinVariant = MinecraftServices.SkinVariant.SLIM;
            } else {
                skinVariant = MinecraftServices.SkinVariant.CLASSIC;
            }
            metadata.set("variant", skinVariant);
        }
        return model;
    }
}
