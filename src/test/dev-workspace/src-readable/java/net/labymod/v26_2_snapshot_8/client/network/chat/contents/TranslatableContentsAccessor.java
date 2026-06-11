package net.labymod.v26_2_snapshot_8.client.network.chat.contents;

import java.util.List;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/network/chat/contents/TranslatableContentsAccessor.class */
public interface TranslatableContentsAccessor {
    List<Component> getLabyArguments();

    String getKey();

    String getFallback();
}
