package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.npc.villager.VillagerData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/VillagerRenderState.class */
public class VillagerRenderState extends HoldingEntityRenderState implements VillagerDataHolderRenderState {
    public boolean isUnhappy;
    public VillagerData villagerData;

    @Override // net.minecraft.client.renderer.entity.state.VillagerDataHolderRenderState
    public VillagerData getVillagerData() {
        return this.villagerData;
    }
}
