package net.labymod.core.client.worldsharing.model;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/PlayerConsumer.class */
@FunctionalInterface
public interface PlayerConsumer {
    void accept(String str, GameMode gameMode, boolean z);
}
