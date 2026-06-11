package net.minecraft.client.model;

import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/EntityModel.class */
public abstract class EntityModel<T extends EntityRenderState> extends Model<T> {
    public static final float MODEL_Y_OFFSET = -1.501f;

    protected EntityModel(ModelPart $$0) {
        this($$0, RenderTypes::entityCutoutNoCull);
    }

    protected EntityModel(ModelPart $$0, Function<Identifier, RenderType> $$1) {
        super($$0, $$1);
    }
}
