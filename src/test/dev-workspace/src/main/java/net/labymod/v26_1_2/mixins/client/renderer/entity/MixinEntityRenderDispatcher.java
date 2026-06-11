package net.labymod.v26_1_2.mixins.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/entity/MixinEntityRenderDispatcher.class */
@Mixin({EntityRenderDispatcher.class})
public abstract class MixinEntityRenderDispatcher implements net.labymod.api.client.entity.EntityRenderDispatcher {
    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public boolean isRenderDebugHitBoxes() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.debugEntries.isCurrentlyEnabled(DebugScreenEntries.ENTITY_HITBOXES) && !minecraft.showOnlyReducedInfo();
    }

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public void setRenderDebugHitBoxes(boolean renderDebugHitBoxes) {
        DebugScreenEntryStatus debugScreenEntryStatus;
        DebugScreenEntryList debugScreenEntryList = Minecraft.getInstance().debugEntries;
        Identifier identifier = DebugScreenEntries.ENTITY_HITBOXES;
        if (renderDebugHitBoxes) {
            debugScreenEntryStatus = DebugScreenEntryStatus.ALWAYS_ON;
        } else {
            debugScreenEntryStatus = DebugScreenEntryStatus.NEVER;
        }
        debugScreenEntryList.setStatus(identifier, debugScreenEntryStatus);
    }
}
