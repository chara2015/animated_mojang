package net.labymod.api.client.gui.screen.state.recorder;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.VanillaMeshUtil;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.DeviceBuffer;
import net.labymod.laby3d.api.buffers.MemoryAccess;
import net.labymod.laby3d.api.mesh.BufferResource;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.mesh.SharedMeshIndexBuffer;
import net.labymod.laby3d.api.pipeline.IndexType;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.vertex.VertexDescription;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/MeshRecorder.class */
@ApiStatus.Internal
public final class MeshRecorder {
    private static final Consumer<DrawCommandContext> NO_OP_CONSUMER = context -> {
    };
    private final Laby3D laby3D;
    private final List<RecorderState> recorderStates = new ArrayList();
    private final List<RenderStep> renderSteps = new ArrayList();
    private final List<RecordedState<?>> recordedStates = new ArrayList();
    private final Object2IntMap<VertexDescription> vertexBufferSizes = new Object2IntOpenHashMap();
    private int requiredIndices;
    private RenderTarget previousTarget;
    private int currentRangeStart;

    public MeshRecorder(Laby3D laby3D) {
        this.laby3D = laby3D;
    }

    public void prepareRecordedStates(VertexBufferManager bufferManager) {
        captureLastRenderStep();
        bufferManager.ensureCapacity(this.vertexBufferSizes);
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        ObjectIterator it = this.vertexBufferSizes.object2IntEntrySet().iterator();
        while (it.hasNext()) {
            Object2IntMap.Entry<VertexDescription> entry = (Object2IntMap.Entry) it.next();
            object2IntOpenHashMap.put((VertexDescription) entry.getKey(), bufferManager.getAccumulatedOffset((VertexDescription) entry.getKey()));
        }
        Object2IntOpenHashMap object2IntOpenHashMap2 = new Object2IntOpenHashMap();
        RenderDevice renderDevice = this.laby3D.renderDevice();
        for (RecorderState recorderState : this.recorderStates) {
            if (recorderState instanceof MeshRecorderState) {
                MeshRecorderState meshRecorderState = (MeshRecorderState) recorderState;
                recordMesh(renderDevice, bufferManager, object2IntOpenHashMap, object2IntOpenHashMap2, meshRecorderState);
            } else if (recorderState instanceof ClearRecorderState) {
                ClearRecorderState clearRecorderState = (ClearRecorderState) recorderState;
                this.recordedStates.add(new RecordedClearState(clearRecorderState));
            } else if (recorderState instanceof DebugGroupRecorderState) {
                DebugGroupRecorderState debugGroupRecorderState = (DebugGroupRecorderState) recorderState;
                this.recordedStates.add(new RecordedDebugGroupRecorderState(debugGroupRecorderState));
            } else if (recorderState instanceof GenericTaskRecorderState) {
                GenericTaskRecorderState genericTaskRecorderState = (GenericTaskRecorderState) recorderState;
                this.recordedStates.add(new RecordedGenericTaskState(genericTaskRecorderState));
            } else if (recorderState instanceof CommandTaskRecorderState) {
                CommandTaskRecorderState commandTaskRecorderState = (CommandTaskRecorderState) recorderState;
                this.recordedStates.add(new RecordedCommandTaskState(commandTaskRecorderState));
            }
        }
        ObjectIterator it2 = object2IntOpenHashMap.object2IntEntrySet().iterator();
        while (it2.hasNext()) {
            Object2IntMap.Entry<VertexDescription> entry2 = (Object2IntMap.Entry) it2.next();
            bufferManager.setAccumulatedOffset((VertexDescription) entry2.getKey(), entry2.getIntValue());
        }
    }

    public List<RenderStep> renderSteps() {
        return this.renderSteps;
    }

    public List<RecordedState<?>> recordedStates() {
        return this.recordedStates;
    }

    public boolean isEmpty() {
        return this.recorderStates.isEmpty();
    }

    public void invalidate() {
        this.recorderStates.forEach((v0) -> {
            v0.close();
        });
        this.recorderStates.clear();
        this.recordedStates.clear();
        this.renderSteps.clear();
        this.vertexBufferSizes.clear();
        this.requiredIndices = 0;
        this.previousTarget = null;
    }

    public void record(@NotNull BufferBuilder bufferBuilder, @NotNull Material material, @Nullable ScissorArea scissorArea, @Nullable ClipShape clipShape, RenderTarget destination) {
        record(bufferBuilder, material, scissorArea, clipShape, destination, NO_OP_CONSUMER);
    }

    public void record(@NotNull BufferBuilder bufferBuilder, @NotNull Material material, @Nullable ScissorArea scissorArea, @Nullable ClipShape clipShape, RenderTarget destination, @NotNull Consumer<DrawCommandContext> contextConsumer) {
        GeometryData geometryData = bufferBuilder.build();
        if (geometryData == null) {
            return;
        }
        MeshRecorderState command = new MeshRecorderState(geometryData, material, scissorArea, clipShape, destination, VanillaMeshUtil.applyVanillaWorkaround(this.laby3D, material.renderState(), contextConsumer));
        recordCommand(command);
        calculateVertexBufferSize(command);
        if (this.previousTarget == null) {
            this.previousTarget = destination;
            this.currentRangeStart = 0;
        } else if (this.previousTarget != destination) {
            int end = this.recorderStates.size() - 1;
            this.renderSteps.add(new RenderStep(this.previousTarget, this.currentRangeStart, end));
            this.currentRangeStart = end;
            this.previousTarget = destination;
        }
    }

    public void recordCommand(RecorderState command) {
        this.recorderStates.add(command);
    }

    private void calculateVertexBufferSize(MeshRecorderState command) {
        GeometryData.Details details = command.geometryData().details();
        VertexDescription description = details.description();
        if (!this.vertexBufferSizes.containsKey(description)) {
            this.vertexBufferSizes.put(description, 0);
        }
        int totalVertexBufferSize = this.vertexBufferSizes.getInt(description) + (description.size() * details.vertexCount());
        this.vertexBufferSizes.put(description, totalVertexBufferSize);
        int indexCount = details.indexCount();
        if (this.requiredIndices < indexCount) {
            this.requiredIndices = indexCount;
        }
    }

    private void captureLastRenderStep() {
        if (this.previousTarget == null) {
            return;
        }
        this.renderSteps.add(new RenderStep(this.previousTarget, this.currentRangeStart, this.recorderStates.size()));
    }

    private void recordMesh(RenderDevice renderDevice, VertexBufferManager bufferManager, Object2IntMap<VertexDescription> offsets, Object2IntMap<VertexDescription> indexOffsets, MeshRecorderState meshState) {
        int baseVertex;
        GeometryData geometryData = meshState.geometryData();
        GeometryData.Details details = geometryData.details();
        VertexDescription description = details.description();
        if (!offsets.containsKey(description)) {
            offsets.put(description, bufferManager.getAccumulatedOffset(description));
        }
        ByteBuffer buffer = geometryData.vertexBuffer();
        int startPosition = offsets.getInt(description);
        int remaining = buffer.remaining();
        DeviceBuffer vertexBuffer = bufferManager.acquireBuffer(description);
        DeviceBuffer.Slice mappedSlice = vertexBuffer.slice(startPosition, remaining);
        DeviceBuffer.MappedView mappedView = renderDevice.mapBuffer(mappedSlice, MemoryAccess.CPU_TO_GPU);
        try {
            MemoryUtil.memCopy(buffer, mappedView.data());
            if (mappedView != null) {
                mappedView.close();
            }
            SharedMeshIndexBuffer sharedMeshBuffer = renderDevice.getSharedMeshBuffer(details.mode());
            DeviceBuffer indexBuffer = sharedMeshBuffer.getBuffer(this.requiredIndices);
            IndexType indexType = sharedMeshBuffer.getType();
            if (renderDevice.isCompatible(8)) {
                baseVertex = startPosition / description.size();
            } else {
                baseVertex = 0;
                int newIndexOffset = indexOffsets.getOrDefault(description, 0) + details.indexCount();
                indexOffsets.put(description, newIndexOffset);
            }
            offsets.put(description, startPosition + remaining);
            this.recordedStates.add(new RecordedMeshState(vertexBuffer, meshState, Mesh.create(() -> {
                return meshState.material().id().toString();
            }, new BufferResource(vertexBuffer, indexBuffer, indexType, true), 0, indexOffsets.getInt(description), details.vertexCount(), details.indexCount(), baseVertex), startPosition));
            geometryData.close();
        } catch (Throwable th) {
            if (mappedView != null) {
                try {
                    mappedView.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep.class */
    public static final class RenderStep extends Record {
        private final RenderTarget target;
        private final int start;
        private final int end;

        public RenderStep(RenderTarget target, int start, int end) {
            this.target = target;
            this.start = start;
            this.end = end;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderStep.class), RenderStep.class, "target;start;end", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->target:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->start:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->end:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderStep.class), RenderStep.class, "target;start;end", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->target:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->start:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->end:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderStep.class, Object.class), RenderStep.class, "target;start;end", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->target:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->start:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorder$RenderStep;->end:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public RenderTarget target() {
            return this.target;
        }

        public int start() {
            return this.start;
        }

        public int end() {
            return this.end;
        }
    }
}
