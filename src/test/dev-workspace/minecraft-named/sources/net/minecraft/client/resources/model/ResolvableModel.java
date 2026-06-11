package net.minecraft.client.resources.model;

import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ResolvableModel.class */
public interface ResolvableModel {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ResolvableModel$Resolver.class */
    public interface Resolver {
        void markDependency(Identifier identifier);
    }

    void resolveDependencies(Resolver resolver);
}
