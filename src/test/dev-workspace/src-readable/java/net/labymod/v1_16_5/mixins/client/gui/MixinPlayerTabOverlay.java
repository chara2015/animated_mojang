package net.labymod.v1_16_5.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.scoreboard.TabListHolder;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.scoreboard.DefaultTabListHolder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/MixinPlayerTabOverlay.class */
@Mixin({dly.class})
public class MixinPlayerTabOverlay implements TabList {

    @Shadow
    private boolean j;
    private final DefaultTabListHolder labyMod$tabListHolder = new DefaultTabListHolder(() -> {
        return this.j;
    });

    @Insert(method = {"setHeader(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setHeader(nr header, InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }

    @Insert(method = {"setFooter(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setFooter(nr footer, InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }

    @Insert(method = {"reset()V"}, at = @At("TAIL"))
    private void reset(InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/scores/Scoreboard;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("HEAD"), cancellable = true)
    private void shouldRenderWidgetList(dfm poseStack, int screenWidth, ddn scoreboard, ddk objective, InsertInfo ci) {
        if (Laby.labyAPI().config().multiplayer().customPlayerList().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.scoreboard.TabList
    @NotNull
    public TabListHolder holder() {
        return this.labyMod$tabListHolder;
    }
}
