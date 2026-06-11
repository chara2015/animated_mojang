package net.labymod.core.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.mapper.EnumMapperRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapper/DefaultEnumMapperRegistry.class */
@Singleton
@Implements(EnumMapperRegistry.class)
public final class DefaultEnumMapperRegistry implements EnumMapperRegistry {
    private static final Logging LOGGER = Logging.getLogger();
    private static final Set<EnumMapper<?, ?>> SEEN_MAPPERS = new HashSet();
    private final Map<Class<?>, EnumMapper> mappers = new HashMap();
    private final EventBus eventBus;

    public DefaultEnumMapperRegistry(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override // net.labymod.api.mapper.EnumMapperRegistry
    public <S extends Enum<S>, D extends Enum<D>> D from(@NotNull S s) {
        EnumMapper enumMapper = this.mappers.get(s.getClass());
        if (enumMapper == null) {
            throw new IllegalArgumentException("No mapper found for enum " + String.valueOf(s.getClass()));
        }
        return (D) enumMapper.from(s);
    }

    @Override // net.labymod.api.mapper.EnumMapperRegistry
    public <S extends Enum<S>, D extends Enum<D>> S to(@NotNull D d) {
        EnumMapper enumMapper = this.mappers.get(d.getClass());
        if (enumMapper == null) {
            throw new IllegalArgumentException("No mapper found for enum " + String.valueOf(d.getClass()));
        }
        return (S) enumMapper.to(d);
    }

    @Subscribe
    public void onServiceLoad(ServiceLoadEvent serviceLoadEvent) {
        for (EnumMapper<?, ?> enumMapper : serviceLoadEvent.load(EnumMapper.class, CustomServiceLoader.ServiceType.ADVANCED)) {
            if (SEEN_MAPPERS.add(enumMapper)) {
                Object sourceEnum = enumMapper.getSourceEnum();
                if (this.mappers.containsKey(sourceEnum)) {
                    LOGGER.debug("Duplicate mapper for enum {}: {}", sourceEnum, enumMapper);
                } else {
                    this.mappers.put((Class<?>) sourceEnum, enumMapper);
                }
            }
        }
    }

    public void initialize() {
        this.eventBus.registerListener(this);
    }
}
