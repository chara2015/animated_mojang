package net.minecraft.client.tutorial;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.ClientInput;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/tutorial/TutorialStepInstance.class */
public interface TutorialStepInstance {
    default void clear() {
    }

    default void tick() {
    }

    default void onInput(ClientInput $$0) {
    }

    default void onMouse(double $$0, double $$1) {
    }

    default void onLookAt(ClientLevel $$0, HitResult $$1) {
    }

    default void onDestroyBlock(ClientLevel $$0, BlockPos $$1, BlockState $$2, float $$3) {
    }

    default void onOpenInventory() {
    }

    default void onGetItem(ItemStack $$0) {
    }
}
