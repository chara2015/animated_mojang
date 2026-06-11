package net.labymod.core.main.user.shop.spray;

import java.util.Collection;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.collection.map.HashMultimap;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.main.user.util.SprayCooldownTracker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/SprayRegistry.class */
@Singleton
@Referenceable
public class SprayRegistry {
    private static final int SPRAY_EMOTE_ID = 336;
    private static final int PROTOCOL_VERSION = 2;
    private final EmoteService emoteService;
    private final ClientWorld level;
    private final Multimap<Direction, SprayObject> objects = new HashMultimap();
    private final Multimap<GameUser, SprayObject> ownerSprays = new HashMultimap();
    private final SprayConfig config = Laby.labyAPI().config().ingame().spray();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/SprayRegistry$SprayState.class */
    public enum SprayState {
        NOT_CONNECTED,
        INVISIBLE_BLOCK,
        SPRAY_COOLDOWN,
        SUCCESS
    }

    public SprayRegistry(EmoteService emoteService, ClientWorld level) {
        this.emoteService = emoteService;
        this.level = level;
    }

    public void sprayServer(GameUser owner, short id, double x, double y, double z, Direction direction, float rotation) {
        SprayObject newObject = new SprayObject(owner, id, x, y, z, direction, rotation);
        addSpray(newObject);
    }

    public SprayState sprayClient(short id, double x, double y, double z, Direction direction, float rotation) {
        LabyConnect labyConnect = Laby.references().labyConnect();
        LabyConnectSession session = labyConnect.getSession();
        if (!labyConnect.isAuthenticated() || session == null) {
            return SprayState.NOT_CONNECTED;
        }
        DefaultGameUser owner = (DefaultGameUser) clientGameUser();
        SprayCooldownTracker sprayCooldownTracker = owner.sprayCooldownTracker();
        if (!sprayCooldownTracker.canSprayServer()) {
            return SprayState.SPRAY_COOLDOWN;
        }
        sprayCooldownTracker.sprayServer();
        if (isInvisibleBlock(direction, x, y, z)) {
            return SprayState.INVISIBLE_BLOCK;
        }
        session.spray(id, 2, x, y, z, direction, rotation);
        return SprayState.SUCCESS;
    }

    public Multimap<Direction, SprayObject> getObjects() {
        return this.objects;
    }

    public Multimap<GameUser, SprayObject> getOwnerSprays() {
        return this.ownerSprays;
    }

    public boolean isEnabled() {
        return this.config.enabled().get().booleanValue();
    }

    private void addSpray(SprayObject object) {
        if (!isEnabled()) {
            return;
        }
        DefaultGameUser owner = (DefaultGameUser) object.getOwner();
        SprayCooldownTracker sprayCooldownTracker = owner.sprayCooldownTracker();
        if (!canSpray(owner, sprayCooldownTracker) || isInvisibleBlock(object.direction(), object.getX(), object.getY(), object.getZ())) {
            return;
        }
        sprayCooldownTracker.sprayServer();
        boolean isClientOwner = owner == clientGameUser();
        sprayCooldownTracker.playSound(object, isClientOwner);
        playSprayEmote(owner);
        Collection<SprayObject> sprayObjects = this.ownerSprays.get(owner);
        if (!sprayObjects.isEmpty()) {
            adjustTimes(sprayObjects);
        }
        sprayObjects.add(object);
        this.objects.put(object.direction(), object);
    }

    private boolean canSpray(DefaultGameUser owner, SprayCooldownTracker sprayCooldownTracker) {
        if (owner == clientGameUser()) {
            return true;
        }
        return sprayCooldownTracker.canSprayServer();
    }

    private boolean isInvisibleBlock(Direction direction, double x, double y, double z) {
        double newX = x;
        double newY = y;
        double newZ = z;
        if (direction == Direction.UP) {
            newY -= 0.0625d;
        } else if (direction == Direction.EAST) {
            newX -= 0.0625d;
        } else if (direction == Direction.SOUTH) {
            newZ -= 0.0625d;
        }
        IntVector3 blockPosition = new IntVector3(newX, newY, newZ);
        BlockState blockState = this.level.getBlockState(blockPosition);
        if (blockState.renderShape() == RenderShape.INVISIBLE) {
            return true;
        }
        AxisAlignedBoundingBox bounds = blockState.bounds();
        double blockY = ((double) blockPosition.getY()) + bounds.getMaxY();
        return y > blockY;
    }

    private void adjustTimes(Collection<SprayObject> sprayObjects) {
        for (SprayObject sprayObject : sprayObjects) {
            long adjustedCreationTime = sprayObject.getAdjustedCreationTime();
            if (adjustedCreationTime == 0) {
                sprayObject.setAdjustCreationTime(true);
            }
        }
    }

    private void playSprayEmote(GameUser owner) {
        EmoteItem emote = this.emoteService.getEmote(SPRAY_EMOTE_ID);
        if (emote == null) {
            return;
        }
        this.emoteService.playClientEmote(owner.getUniqueId(), emote);
    }

    private GameUser clientGameUser() {
        return Laby.references().gameUserService().clientGameUser();
    }
}
