package net.labymod.api.laby3d.buffer;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferDescription;
import net.labymod.laby3d.api.buffers.BufferType;
import net.labymod.laby3d.api.buffers.DeviceBuffer;
import net.labymod.laby3d.api.vertex.VertexDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/ImmediateDeviceBufferStorage.class */
public class ImmediateDeviceBufferStorage {
    private final Laby3D laby3D;
    private final Map<VertexDescription, ImmediateDeviceBuffer> buffers = new HashMap();

    public ImmediateDeviceBufferStorage(Laby3D laby3D) {
        this.laby3D = laby3D;
    }

    public DeviceBuffer uploadImmediateVertexBuffer(VertexDescription description, ByteBuffer dataBuffer) {
        ImmediateDeviceBuffer immediateDeviceBuffer = getOrCreate(description);
        return immediateDeviceBuffer.uploadVertexBuffer(dataBuffer);
    }

    private ImmediateDeviceBuffer getOrCreate(VertexDescription description) {
        return this.buffers.computeIfAbsent(description, key -> {
            return new ImmediateDeviceBuffer(this.laby3D, key);
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/ImmediateDeviceBufferStorage$ImmediateDeviceBuffer.class */
    public static class ImmediateDeviceBuffer {
        private final Laby3D laby3D;
        private final VertexDescription description;
        private DeviceBuffer drawVertexBuffer;

        public ImmediateDeviceBuffer(Laby3D laby3D, VertexDescription description) {
            this.laby3D = laby3D;
            this.description = description;
        }

        public DeviceBuffer uploadVertexBuffer(ByteBuffer buffer) {
            this.drawVertexBuffer = uploadBuffer(this.drawVertexBuffer, buffer, BufferType.VERTEX);
            return this.drawVertexBuffer;
        }

        private DeviceBuffer uploadBuffer(DeviceBuffer buffer, ByteBuffer dataBuffer, BufferType bufferType) {
            RenderDevice renderDevice = this.laby3D.renderDevice();
            if (buffer == null) {
                buffer = renderDevice.createBuffer(bufferType.name() + " (" + String.valueOf(this.description) + ")", BufferDescription.builder().setType(bufferType).setInitialData(dataBuffer).build());
            } else if (buffer.description().size() < dataBuffer.remaining()) {
                buffer.close();
                buffer = renderDevice.createBuffer(bufferType.name() + " (" + String.valueOf(this.description) + ")", BufferDescription.builder().setType(bufferType).setInitialData(dataBuffer).build());
            } else {
                renderDevice.writeToBuffer(buffer, dataBuffer);
            }
            return buffer;
        }
    }
}
