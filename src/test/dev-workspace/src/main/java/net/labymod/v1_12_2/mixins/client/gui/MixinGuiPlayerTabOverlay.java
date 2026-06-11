package net.labymod.v1_12_2.mixins.client.gui;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiPlayerTabOverlay.class */
@Mixin({bjq.class})
public class MixinGuiPlayerTabOverlay implements TabList {

    @Shadow
    private boolean k;
    private final DefaultTabListHolder labyMod$tabListHolder = new DefaultTabListHolder(() -> {
        return this.k;
    });

    @Insert(method = {"setHeader(Lnet/minecraft/util/text/ITextComponent;)V"}, at = @At("TAIL"))
    private void labyMod$setHeader(hh header, InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }

    @Insert(method = {"setFooter(Lnet/minecraft/util/text/ITextComponent;)V"}, at = @At("TAIL"))
    private void labyMod$setFooter(hh footer, InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }

    @Insert(method = {"resetFooterHeader()V"}, at = @At("TAIL"))
    private void labyMod$reset(InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }

    @Insert(method = {"renderPlayerlist(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$shouldRenderWidgetList(int screenWidth, bhk scoreboard, bhg objective, InsertInfo ci) {
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
