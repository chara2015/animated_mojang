package net.labymod.api.mojang.model;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/model/MojangModelService.class */
@Referenceable
public interface MojangModelService {
    Model getPlayerModel(MinecraftServices.SkinVariant skinVariant);

    Model createModel(ResourceLocation resourceLocation);
}
