package net.labymod.core.client.chat.autotext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.models.Implements;
import net.labymod.core.configuration.labymod.DefaultAutoTextConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/autotext/DefaultAutoTextService.class */
@Singleton
@Implements(AutoTextService.class)
public class DefaultAutoTextService implements AutoTextService {
    private final List<AutoTextEntry> entries = new ArrayList();
    private final ChatProvider chatProvider;

    @Inject
    public DefaultAutoTextService(ChatProvider chatProvider) {
        this.chatProvider = chatProvider;
    }

    public void initialize() {
        this.entries.addAll(this.chatProvider.autoTextConfigAccessor().getEntries());
    }

    @Override // net.labymod.api.client.chat.autotext.AutoTextService
    public void addEntry(AutoTextEntry entry) {
        this.entries.add(entry);
        ((DefaultAutoTextConfig) this.chatProvider.autoTextConfigAccessor()).addEntry(entry);
        DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
    }

    @Override // net.labymod.api.client.chat.autotext.AutoTextService
    public boolean removeEntry(AutoTextEntry entry) {
        if (this.entries.remove(entry) && ((DefaultAutoTextConfig) this.chatProvider.autoTextConfigAccessor()).removeEntry(entry)) {
            DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.chat.autotext.AutoTextService
    public boolean removeEntry(Predicate<AutoTextEntry> predicate) {
        if (this.entries.removeIf(predicate) && ((DefaultAutoTextConfig) this.chatProvider.autoTextConfigAccessor()).removeEntry(predicate)) {
            DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.chat.autotext.AutoTextService
    public List<AutoTextEntry> getEntries() {
        return this.entries;
    }
}
