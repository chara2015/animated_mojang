package net.labymod.core.labyconnect.session.spray;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectSprayEvent;
import net.labymod.api.util.math.Direction;
import net.labymod.core.labyconnect.DefaultLabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/spray/V1SprayProtocol.class */
public class V1SprayProtocol implements SprayProtocol {
    @Override // net.labymod.core.labyconnect.session.spray.SprayProtocol
    public void handle(DefaultLabyConnect labyConnect, short sprayId, int protocolVersion, UUID uniqueId, ByteBuffer buffer) {
        float x = buffer.getFloat();
        float y = buffer.getFloat();
        float z = buffer.getFloat();
        Direction direction = Direction.byIndex(buffer.getInt());
        float rotation = buffer.getFloat();
        labyConnect.fireEventSync(new LabyConnectSprayEvent(labyConnect, uniqueId, sprayId, protocolVersion, x, y, z, direction, rotation));
    }
}
