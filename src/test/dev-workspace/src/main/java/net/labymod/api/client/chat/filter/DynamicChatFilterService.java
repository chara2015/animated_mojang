package net.labymod.api.client.chat.filter;

import java.util.List;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/filter/DynamicChatFilterService.class */
@Referenceable
public interface DynamicChatFilterService {
    List<DynamicChatFilter> getAllFilters();

    List<DynamicChatFilter> getServerFilters();

    void addFilter(DynamicChatFilter dynamicChatFilter);

    void removeFilter(UUID uuid);

    void removeFilter(DynamicChatFilter dynamicChatFilter);

    void addServerFilter(DynamicChatFilter dynamicChatFilter);
}
