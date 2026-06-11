package net.minecraft.client.model.animal.feline;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.entity.state.CatRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/feline/CatModel.class */
public class CatModel extends FelineModel<CatRenderState> {
    public static final MeshTransformer CAT_TRANSFORMER = MeshTransformer.scaling(0.8f);

    public CatModel(ModelPart $$0) {
        super($$0);
    }
}
