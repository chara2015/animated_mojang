package net.labymod.v1_21_5.mixins.client.gui;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/gui/MixinPlayerTabOverlay.class */
@Mixin({fuz.class})
public class MixinPlayerTabOverlay implements TabList {

    @Shadow
    private boolean u;
    private final DefaultTabListHolder labyMod$tabListHolder = new DefaultTabListHolder(() -> {
        return this.u;
    });

    @Insert(method = {"setHeader(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setHeader(xg header, InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }

    @Insert(method = {"setFooter(Lnet/minecraft/network/chat/Component;)V"}, at = @At("TAIL"))
    private void setFooter(xg footer, InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }

    @Insert(method = {"reset()V"}, at = @At("TAIL"))
    private void reset(InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }

    @Insert(method = {"render"}, at = @At("HEAD"), cancellable = true)
    private void shouldRenderWidgetList(ftk graphics, int screenWidth, fhh scoreboard, fgz objective, InsertInfo ci) {
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
