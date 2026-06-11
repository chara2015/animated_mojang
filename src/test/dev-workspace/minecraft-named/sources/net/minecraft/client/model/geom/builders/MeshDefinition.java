package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import java.util.function.UnaryOperator;
import net.minecraft.client.model.geom.PartPose;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/builders/MeshDefinition.class */
public class MeshDefinition {
    private final PartDefinition root;

    public MeshDefinition() {
        this(new PartDefinition(ImmutableList.of(), PartPose.ZERO));
    }

    private MeshDefinition(PartDefinition $$0) {
        this.root = $$0;
    }

    public PartDefinition getRoot() {
        return this.root;
    }

    public MeshDefinition transformed(UnaryOperator<PartPose> $$0) {
        return new MeshDefinition(this.root.transformed($$0));
    }

    public MeshDefinition apply(MeshTransformer $$0) {
        return $$0.apply(this);
    }
}
