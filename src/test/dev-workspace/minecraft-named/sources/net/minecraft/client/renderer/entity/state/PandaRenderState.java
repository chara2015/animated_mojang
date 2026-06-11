package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.animal.panda.Panda;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/PandaRenderState.class */
public class PandaRenderState extends HoldingEntityRenderState {
    public Panda.Gene variant = Panda.Gene.NORMAL;
    public boolean isUnhappy;
    public boolean isSneezing;
    public int sneezeTime;
    public boolean isEating;
    public boolean isScared;
    public boolean isSitting;
    public float sitAmount;
    public float lieOnBackAmount;
    public float rollAmount;
    public float rollTime;
}
