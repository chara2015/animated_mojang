package net.labymod.api.client.resources;

import net.labymod.api.util.concurrent.AbstractCompletable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/CompletableResourceLocation.class */
public class CompletableResourceLocation extends AbstractCompletable<ResourceLocation> {
    public CompletableResourceLocation(ResourceLocation fallback) {
        super(fallback);
    }

    public CompletableResourceLocation(ResourceLocation completed, boolean isCompleted) {
        super(completed, isCompleted);
    }
}
