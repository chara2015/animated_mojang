package net.labymod.core.event.client.render.post;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/post/PostProcessingScreenEventCaller.class */
public final class PostProcessingScreenEventCaller {
    public static void callBeforeHand(float partialTicks) {
        call(PostProcessingScreenEvent.Phase.BEFORE_HAND, partialTicks);
    }

    public static void callWorld(float partialTicks) {
        call(PostProcessingScreenEvent.Phase.WORLD, partialTicks);
    }

    private static void call(PostProcessingScreenEvent.Phase phase, float partialTicks) {
        Laby.fireEvent(new PostProcessingScreenEvent(phase, partialTicks));
    }
}
