package net.labymod.core.main.user.shop.emote.abort;

import net.labymod.api.client.entity.Entity;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/abort/EmoteAbortWatcher.class */
public interface EmoteAbortWatcher<E extends Entity> {
    boolean shouldAbort(EmoteAnimationStorage emoteAnimationStorage, E e, float f);
}
