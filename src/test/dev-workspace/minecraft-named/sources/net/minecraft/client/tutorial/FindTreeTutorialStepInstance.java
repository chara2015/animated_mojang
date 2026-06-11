package net.minecraft.client.tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/tutorial/FindTreeTutorialStepInstance.class */
public class FindTreeTutorialStepInstance implements TutorialStepInstance {
    private static final int HINT_DELAY = 6000;
    private static final Component TITLE = Component.translatable("tutorial.find_tree.title");
    private static final Component DESCRIPTION = Component.translatable("tutorial.find_tree.description");
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public FindTreeTutorialStepInstance(Tutorial $$0) {
        this.tutorial = $$0;
    }

    @Override // net.minecraft.client.tutorial.TutorialStepInstance
    public void tick() {
        LocalPlayer $$1;
        this.timeWaiting++;
        if (!this.tutorial.isSurvival()) {
            this.tutorial.setStep(TutorialSteps.NONE);
            return;
        }
        Minecraft $$0 = this.tutorial.getMinecraft();
        if (this.timeWaiting == 1 && ($$1 = $$0.player) != null && (hasCollectedTreeItems($$1) || hasPunchedTreesPreviously($$1))) {
            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
        } else if (this.timeWaiting >= HINT_DELAY && this.toast == null) {
            this.toast = new TutorialToast($$0.font, TutorialToast.Icons.TREE, TITLE, DESCRIPTION, false);
            $$0.getToastManager().addToast(this.toast);
        }
    }

    @Override // net.minecraft.client.tutorial.TutorialStepInstance
    public void clear() {
        if (this.toast != null) {
            this.toast.hide();
            this.toast = null;
        }
    }

    @Override // net.minecraft.client.tutorial.TutorialStepInstance
    public void onLookAt(ClientLevel $$0, HitResult $$1) {
        if ($$1.getType() == HitResult.Type.BLOCK) {
            BlockState $$2 = $$0.getBlockState(((BlockHitResult) $$1).getBlockPos());
            if ($$2.is(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)) {
                this.tutorial.setStep(TutorialSteps.PUNCH_TREE);
            }
        }
    }

    @Override // net.minecraft.client.tutorial.TutorialStepInstance
    public void onGetItem(ItemStack $$0) {
        if ($$0.is(ItemTags.COMPLETES_FIND_TREE_TUTORIAL)) {
            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
        }
    }

    private static boolean hasCollectedTreeItems(LocalPlayer $$0) {
        return $$0.getInventory().hasAnyMatching($$02 -> {
            return $$02.is(ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
        });
    }

    public static boolean hasPunchedTreesPreviously(LocalPlayer $$0) {
        for (Holder<Block> $$1 : BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)) {
            Block $$2 = $$1.value();
            if ($$0.getStats().getValue(Stats.BLOCK_MINED.get($$2)) > 0) {
                return true;
            }
        }
        return false;
    }
}
