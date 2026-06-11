package net.labymod.v26_1.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.scoreboard.TabListHolder;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.scoreboard.DefaultTabListHolder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/gui/MixinPlayerTabOverlay.class */
@Mixin({PlayerTabOverlay.class})
public class MixinPlayerTabOverlay implements TabList {

    @Shadow
    private boolean visible;
    private final DefaultTabListHolder labyMod$tabListHolder = new DefaultTabListHolder(() -> {
        return this.visible;
    });

    @Insert(method = {"setHeader(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setHeader(Component header, InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }

    @Insert(method = {"setFooter(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setFooter(Component footer, InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }

    @Insert(method = {"reset()V"}, at = @At("TAIL"))
    private void reset(InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }

    @Insert(method = {"extractRenderState"}, at = @At("HEAD"), cancellable = true)
    private void shouldRenderWidgetList(GuiGraphicsExtractor graphics, int screenWidth, Scoreboard scoreboard, Objective objective, InsertInfo ci) {
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
