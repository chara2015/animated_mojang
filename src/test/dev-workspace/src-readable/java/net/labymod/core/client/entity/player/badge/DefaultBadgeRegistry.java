package net.labymod.core.client.entity.player.badge;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.badge.PositionType;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/badge/DefaultBadgeRegistry.class */
@Singleton
@Implements(BadgeRegistry.class)
public class DefaultBadgeRegistry implements BadgeRegistry {
    private static final Logging LOGGER = Logging.getLogger();
    private final Map<PositionType, DefaultRegistry<BadgeRenderer>> positionRegistries = new HashMap();

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public int render(ScreenContext context, PositionType type, float x, float y, NetworkPlayerInfo player) {
        DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            return type.renderer().render(context, x, y, player, positionRenderer.getElements());
        }
        return 0;
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public int getWidth(PositionType type, NetworkPlayerInfo player) {
        DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            return type.renderer().getWidth(player, positionRenderer.getElements());
        }
        return 0;
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void beginRender(ScreenContext context, PositionType type) {
        DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            for (KeyValue<BadgeRenderer> element : positionRenderer.getElements()) {
                element.getValue().beginRender(context);
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void endRender(ScreenContext context, PositionType type) {
        DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            for (KeyValue<BadgeRenderer> element : positionRenderer.getElements()) {
                element.getValue().endRender(context);
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void register(String id, PositionType type, BadgeRenderer tag) {
        if (!isCompatible(id, tag)) {
            return;
        }
        getRegistry(type).register(id, tag);
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void registerAfter(String afterId, String id, PositionType type, BadgeRenderer tag) {
        if (!isCompatible(id, tag)) {
            return;
        }
        getRegistry(type).registerAfter(afterId, id, tag);
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void registerBefore(String beforeId, String id, PositionType type, BadgeRenderer tag) {
        if (!isCompatible(id, tag)) {
            return;
        }
        getRegistry(type).registerBefore(beforeId, id, tag);
    }

    @Override // net.labymod.api.client.entity.player.badge.BadgeRegistry
    public void unregister(String id) {
        for (Map.Entry<PositionType, DefaultRegistry<BadgeRenderer>> entry : this.positionRegistries.entrySet()) {
            entry.getValue().unregister(id);
        }
    }

    private DefaultRegistry<BadgeRenderer> getRegistry(PositionType type) {
        DefaultRegistry<BadgeRenderer> registry = this.positionRegistries.get(type);
        if (registry == null) {
            Map<PositionType, DefaultRegistry<BadgeRenderer>> map = this.positionRegistries;
            DefaultRegistry<BadgeRenderer> defaultRegistry = new DefaultRegistry<>();
            registry = defaultRegistry;
            map.put(type, defaultRegistry);
        }
        return registry;
    }

    private boolean isCompatible(String id, BadgeRenderer renderer) {
        Class<?> cls = renderer.getClass();
        Method legacyRender = findMethod(cls, "render", Stack.class, Float.TYPE, Float.TYPE, NetworkPlayerInfo.class);
        if (legacyRender != null && legacyRender.getDeclaringClass() != BadgeRenderer.class) {
            LOGGER.warn("The badge renderer {} is not compatible with the new badge system.", id);
            return false;
        }
        Method legacyBegin = findMethod(cls, "beginRender", Stack.class);
        if (legacyBegin != null && legacyBegin.getDeclaringClass() != BadgeRenderer.class) {
            LOGGER.warn("The badge renderer {} is not compatible with the new badge system.", id);
            return false;
        }
        Method legacyEnd = findMethod(cls, "endRender", Stack.class);
        if (legacyEnd != null && legacyEnd.getDeclaringClass() != BadgeRenderer.class) {
            LOGGER.warn("The badge renderer {} is not compatible with the new badge system.", id);
            return false;
        }
        return true;
    }

    private Method findMethod(Class<?> owner, String name, Class<?>... parameterTypes) {
        try {
            return owner.getMethod(name, parameterTypes);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
