package net.labymod.api.client.chat.autotext;

import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/autotext/AutoTextService.class */
@Referenceable
public interface AutoTextService {
    void addEntry(AutoTextEntry autoTextEntry);

    boolean removeEntry(AutoTextEntry autoTextEntry);

    boolean removeEntry(Predicate<AutoTextEntry> predicate);

    List<AutoTextEntry> getEntries();
}
