package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/MeshRecorderState.class */
public final class MeshRecorderState extends Record implements RecorderState {
    private final GeometryData geometryData;
    private final Material material;

    @Nullable
    private final ScissorArea scissorArea;

    @Nullable
    private final ClipShape clipShape;
    private final RenderTarget destination;
    private final Consumer<DrawCommandContext> commandConsumer;

    public MeshRecorderState(GeometryData geometryData, Material material, @Nullable ScissorArea scissorArea, @Nullable ClipShape clipShape, RenderTarget destination, Consumer<DrawCommandContext> commandConsumer) {
        this.geometryData = geometryData;
        this.material = material;
        this.scissorArea = scissorArea;
        this.clipShape = clipShape;
        this.destination = destination;
        this.commandConsumer = commandConsumer;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MeshRecorderState.class), MeshRecorderState.class, "geometryData;material;scissorArea;clipShape;destination;commandConsumer", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->geometryData:Lnet/labymod/laby3d/api/mesh/GeometryData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->material:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->scissorArea:Lnet/labymod/api/client/gui/screen/util/scissor/ScissorArea;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->clipShape:Lnet/labymod/api/client/gui/screen/state/ClipShape;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->destination:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->commandConsumer:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MeshRecorderState.class), MeshRecorderState.class, "geometryData;material;scissorArea;clipShape;destination;commandConsumer", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->geometryData:Lnet/labymod/laby3d/api/mesh/GeometryData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->material:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->scissorArea:Lnet/labymod/api/client/gui/screen/util/scissor/ScissorArea;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->clipShape:Lnet/labymod/api/client/gui/screen/state/ClipShape;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->destination:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->commandConsumer:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MeshRecorderState.class, Object.class), MeshRecorderState.class, "geometryData;material;scissorArea;clipShape;destination;commandConsumer", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->geometryData:Lnet/labymod/laby3d/api/mesh/GeometryData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->material:Lnet/labymod/api/laby3d/pipeline/material/Material;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->scissorArea:Lnet/labymod/api/client/gui/screen/util/scissor/ScissorArea;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->clipShape:Lnet/labymod/api/client/gui/screen/state/ClipShape;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->destination:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;->commandConsumer:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public GeometryData geometryData() {
        return this.geometryData;
    }

    public Material material() {
        return this.material;
    }

    @Nullable
    public ScissorArea scissorArea() {
        return this.scissorArea;
    }

    @Nullable
    public ClipShape clipShape() {
        return this.clipShape;
    }

    public RenderTarget destination() {
        return this.destination;
    }

    public Consumer<DrawCommandContext> commandConsumer() {
        return this.commandConsumer;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecorderState, java.lang.AutoCloseable
    public void close() {
        this.geometryData.close();
    }

    public void consumeCommand(DrawCommandContext command) {
        this.commandConsumer.accept(command);
    }
}
