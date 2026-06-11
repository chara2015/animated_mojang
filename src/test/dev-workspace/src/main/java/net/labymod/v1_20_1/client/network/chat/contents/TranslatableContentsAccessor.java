package net.labymod.v1_20_1.client.network.chat.contents;

import java.util.List;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/network/chat/contents/TranslatableContentsAccessor.class */
public interface TranslatableContentsAccessor {
    List<Component> getLabyArguments();

    String getKey();

    String getFallback();
}
