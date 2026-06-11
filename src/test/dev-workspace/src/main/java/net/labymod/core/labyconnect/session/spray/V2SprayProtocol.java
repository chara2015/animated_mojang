package net.labymod.core.labyconnect.session.spray;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectSprayEvent;
import net.labymod.api.util.math.Direction;
import net.labymod.core.labyconnect.DefaultLabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/spray/V2SprayProtocol.class */
public class V2SprayProtocol implements SprayProtocol {
    @Override // net.labymod.core.labyconnect.session.spray.SprayProtocol
    public void handle(DefaultLabyConnect labyConnect, short sprayId, int protocolVersion, UUID uniqueId, ByteBuffer buffer) {
        double x = buffer.getDouble();
        double y = buffer.getDouble();
        double z = buffer.getDouble();
        Direction direction = Direction.byIndex(buffer.getInt());
        float rotation = buffer.getFloat();
        labyConnect.fireEventSync(new LabyConnectSprayEvent(labyConnect, uniqueId, sprayId, protocolVersion, x, y, z, direction, rotation));
    }
}
