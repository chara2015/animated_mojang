package net.labymod.v1_20_6.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/screen/MixinJoinMultiplayerScreen.class */
@Mixin({fqd.class})
public class MixinJoinMultiplayerScreen {
    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    public void clearTitle(fgt param0, fgr param1, xp param2, int param3, int param4, int param5) {
    }

    @Insert(method = {"join(Lnet/minecraft/client/multiplayer/ServerData;)V"}, at = @At("TAIL"))
    private void getServerData(fyl serverData, InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(labyMod$getServerController().createServerData(serverData));
    }

    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
