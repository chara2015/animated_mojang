package net.labymod.api.client.animation.playback;

import net.labymod.api.client.animation.KeyframeAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/playback/AnimationListener.class */
public interface AnimationListener {
    default void onStart(KeyframeAnimation animation) {
    }

    default void onComplete(KeyframeAnimation animation) {
    }

    default void onLoop(KeyframeAnimation animation, int loopCount) {
    }

    default void onPause(KeyframeAnimation animation) {
    }

    default void onResume(KeyframeAnimation animation) {
    }
}
