package net.minecraft.client.model.geom;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.geom.builders.LayerDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/EntityModelSet.class */
public class EntityModelSet {
    public static final EntityModelSet EMPTY = new EntityModelSet(Map.of());
    private final Map<ModelLayerLocation, LayerDefinition> roots;

    public EntityModelSet(Map<ModelLayerLocation, LayerDefinition> $$0) {
        this.roots = $$0;
    }

    public ModelPart bakeLayer(ModelLayerLocation $$0) {
        LayerDefinition $$1 = this.roots.get($$0);
        if ($$1 == null) {
            throw new IllegalArgumentException("No model for layer " + String.valueOf($$0));
        }
        return $$1.bakeRoot();
    }

    public static EntityModelSet vanilla() {
        return new EntityModelSet(ImmutableMap.copyOf(LayerDefinitions.createRoots()));
    }
}
