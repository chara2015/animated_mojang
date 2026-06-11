package net.minecraft.client.renderer.item;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/TrackingItemStackRenderState.class */
public class TrackingItemStackRenderState extends ItemStackRenderState {
    private final List<Object> modelIdentityElements = new ArrayList();

    @Override // net.minecraft.client.renderer.item.ItemStackRenderState
    public void appendModelIdentityElement(Object $$0) {
        this.modelIdentityElements.add($$0);
    }

    public Object getModelIdentity() {
        return this.modelIdentityElements;
    }
}
