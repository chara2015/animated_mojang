package net.minecraft.client.model.object.bell;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/bell/BellModel.class */
public class BellModel extends Model<State> {
    private static final String BELL_BODY = "bell_body";
    private final ModelPart bellBody;

    public BellModel(ModelPart $$0) {
        super($$0, RenderTypes::entitySolid);
        this.bellBody = $$0.getChild(BELL_BODY);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(BELL_BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0f, -6.0f, -3.0f, 6.0f, 7.0f, 6.0f), PartPose.offset(8.0f, 12.0f, 8.0f));
        $$2.addOrReplaceChild("bell_base", CubeListBuilder.create().texOffs(0, 13).addBox(4.0f, 4.0f, 4.0f, 8.0f, 2.0f, 8.0f), PartPose.offset(-8.0f, -12.0f, -8.0f));
        return LayerDefinition.create($$0, 32, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(State $$0) {
        super.setupAnim($$0);
        float $$1 = 0.0f;
        float $$2 = 0.0f;
        if ($$0.shakeDirection != null) {
            float $$3 = Mth.sin($$0.ticks / 3.1415927f) / (4.0f + ($$0.ticks / 3.0f));
            switch ($$0.shakeDirection) {
                case NORTH:
                    $$1 = -$$3;
                    break;
                case SOUTH:
                    $$1 = $$3;
                    break;
                case EAST:
                    $$2 = -$$3;
                    break;
                case WEST:
                    $$2 = $$3;
                    break;
            }
        }
        this.bellBody.xRot = $$1;
        this.bellBody.zRot = $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/bell/BellModel$State.class */
    public static final class State extends Record {
        private final float ticks;
        private final Direction shakeDirection;

        public State(float $$0, Direction $$1) {
            this.ticks = $$0;
            this.shakeDirection = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, State.class), State.class, "ticks;shakeDirection", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->ticks:F", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->shakeDirection:Lnet/minecraft/core/Direction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, State.class), State.class, "ticks;shakeDirection", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->ticks:F", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->shakeDirection:Lnet/minecraft/core/Direction;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, State.class, Object.class), State.class, "ticks;shakeDirection", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->ticks:F", "FIELD:Lnet/minecraft/client/model/object/bell/BellModel$State;->shakeDirection:Lnet/minecraft/core/Direction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float ticks() {
            return this.ticks;
        }

        public Direction shakeDirection() {
            return this.shakeDirection;
        }
    }
}
