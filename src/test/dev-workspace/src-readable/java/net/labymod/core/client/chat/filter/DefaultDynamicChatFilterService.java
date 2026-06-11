package net.labymod.core.client.chat.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.filter.DynamicChatFilter;
import net.labymod.api.client.chat.filter.DynamicChatFilterService;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/filter/DefaultDynamicChatFilterService.class */
@Singleton
@Implements(DynamicChatFilterService.class)
public class DefaultDynamicChatFilterService implements DynamicChatFilterService {
    private final LabyAPI labyAPI;
    private final List<DynamicChatFilter> allFilters = new ArrayList();
    private final List<DynamicChatFilter> serverFilters = new ArrayList();

    @Inject
    public DefaultDynamicChatFilterService(LabyAPI labyAPI, EventBus eventBus) {
        this.labyAPI = labyAPI;
        eventBus.registerListener(this);
    }

    @Subscribe
    public void clearCustomFilters(NetworkDisconnectEvent event) {
        for (DynamicChatFilter filter : this.serverFilters) {
            this.allFilters.remove(filter);
        }
        List<DynamicChatFilter> list = this.serverFilters;
        Objects.requireNonNull(list);
        removeTabs((v1) -> {
            return r1.contains(v1);
        });
        this.serverFilters.clear();
    }

    private void removeTabs(Predicate<DynamicChatFilter> predicate) {
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public List<DynamicChatFilter> getAllFilters() {
        return this.allFilters;
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public List<DynamicChatFilter> getServerFilters() {
        return this.serverFilters;
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public void addFilter(DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            removeFilter(filter.identifier());
            this.allFilters.add(filter);
        });
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public void removeFilter(UUID identifier) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            Predicate<DynamicChatFilter> predicate = filter -> {
                return filter.identifier().equals(identifier);
            };
            this.allFilters.removeIf(predicate);
            this.serverFilters.removeIf(predicate);
            removeTabs(f -> {
                return f.identifier().equals(identifier);
            });
        });
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public void removeFilter(DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.allFilters.remove(filter);
            this.serverFilters.remove(filter);
            removeTabs(f -> {
                return f.identifier().equals(filter.identifier());
            });
        });
    }

    @Override // net.labymod.api.client.chat.filter.DynamicChatFilterService
    public void addServerFilter(DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (!this.labyAPI.serverController().isConnected()) {
                return;
            }
            this.allFilters.add(filter);
            this.serverFilters.add(filter);
        });
    }
}
