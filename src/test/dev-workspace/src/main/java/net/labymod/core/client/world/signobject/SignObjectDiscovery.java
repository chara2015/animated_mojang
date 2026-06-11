package net.labymod.core.client.world.signobject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.signobject.PlacedSignObject;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.world.canvas.DefaultPlacedSignObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/signobject/SignObjectDiscovery.class */
public class SignObjectDiscovery {
    private static final int MAX_CANVAS = 15;
    private final DefaultSignObjectRegistry registry;
    private final Map<BlockPosition, PlacedSignObject> trackedEntities = new HashMap();
    private final AtomicInteger renderedCanvas = new AtomicInteger();
    private final LabyAPI labyAPI = Laby.labyAPI();

    public SignObjectDiscovery(DefaultSignObjectRegistry registry) {
        this.registry = registry;
    }

    public Map<BlockPosition, PlacedSignObject> trackedEntities() {
        return this.trackedEntities;
    }

    @Subscribe
    public void renderWorld(RenderWorldEvent event) {
        if (event.phase() != Phase.POST || this.trackedEntities.isEmpty()) {
            return;
        }
        float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        MinecraftCamera cam = event.camera();
        DoubleVector3 camPos = cam.renderPosition();
        this.renderedCanvas.set(0);
        boolean cleanup = false;
        for (PlacedSignObject placedSignObject : this.trackedEntities.values()) {
            SignBlockEntity sign = placedSignObject.sign();
            if (sign.isRemoved()) {
                cleanup = true;
            } else {
                for (int i = 0; i < DefaultPlacedSignObject.SIDES.length; i++) {
                    SignObject<?> object = placedSignObject.objects()[i];
                    if (object != null && object.hasRendering() && object.isEnabled() && this.renderedCanvas.getAndIncrement() < 15) {
                        BlockPosition location = sign.position();
                        double x = ((double) location.getX()) - camPos.getX();
                        double y = ((double) location.getY()) - camPos.getY();
                        double z = ((double) location.getZ()) - camPos.getZ();
                        object.render(event.stack(), x, y, z, tickDelta);
                    }
                }
            }
        }
        if (cleanup) {
            checkForRemovedSigns();
        }
    }

    private void disposeSigns() {
        for (PlacedSignObject placedSignObject : this.trackedEntities.values()) {
            dispose(placedSignObject);
        }
        this.trackedEntities.clear();
    }

    private void checkForRemovedSigns() {
        this.trackedEntities.values().removeIf(signCanvas -> {
            SignBlockEntity sign = signCanvas.sign();
            if (sign.isRemoved()) {
                dispose(signCanvas);
                return true;
            }
            return false;
        });
    }

    private void dispose(PlacedSignObject placedSignObject) {
        for (SignObject<?> object : placedSignObject.objects()) {
            if (object != null) {
                object.dispose();
            }
        }
    }

    @Subscribe
    public void discoverCanvas(SignBlockEntityPostLoadEvent event) {
        discoverCanvas(event.sign());
    }

    @Subscribe
    public void discoverCanvas(BlockEntityUpdateEvent event) {
        if (event.blockEntity() instanceof SignBlockEntity) {
            discoverCanvas((SignBlockEntity) event.blockEntity());
        }
    }

    @Subscribe
    public void onSubServerSwitch(SubServerSwitchEvent event) {
        disposeSigns();
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        disposeSigns();
    }

    public void rediscoverAllSigns() {
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (world == null) {
            return;
        }
        for (Chunk chunk : world.getChunks()) {
            for (BlockEntity blockEntity : chunk.getBlockEntities()) {
                if (blockEntity instanceof SignBlockEntity) {
                    discoverCanvas((SignBlockEntity) blockEntity);
                }
            }
        }
    }

    private void discoverCanvas(SignBlockEntity sign) {
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (world == null) {
            return;
        }
        SignObject<?>[] objects = new SignObject[DefaultPlacedSignObject.SIDES.length];
        boolean validCanvas = false;
        for (int i = 0; i < objects.length; i++) {
            SignObject<?> c = createObject(sign, DefaultPlacedSignObject.SIDES[i]);
            objects[i] = c;
            validCanvas = validCanvas || c != null;
        }
        if (!validCanvas) {
            PlacedSignObject removed = this.trackedEntities.remove(sign.position());
            if (removed != null) {
                dispose(removed);
                return;
            }
            return;
        }
        PlacedSignObject placedSignObject = new DefaultPlacedSignObject(sign, objects);
        PlacedSignObject overridden = this.trackedEntities.put(sign.position(), placedSignObject);
        if (overridden != null) {
            dispose(overridden);
        }
    }

    private SignObject<?> createObject(SignBlockEntity sign, SignBlockEntity.SignSide side) {
        if (!sign.hasSide(side)) {
            return null;
        }
        return this.registry.createObject(new DefaultSignObjectPosition(sign.position(), side, sign.getRotationYaw()), sign.getStringLines(side));
    }
}
