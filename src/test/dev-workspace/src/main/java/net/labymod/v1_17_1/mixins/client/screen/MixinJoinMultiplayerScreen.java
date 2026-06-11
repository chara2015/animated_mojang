package net.labymod.v1_17_1.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/screen/MixinJoinMultiplayerScreen.class */
@Mixin({edc.class})
public class MixinJoinMultiplayerScreen {
    @Redirect(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    public void clearTitle(dql param0, dwl param1, os param2, int param3, int param4, int param5) {
    }

    @Insert(method = {"join(Lnet/minecraft/client/multiplayer/ServerData;)V"}, at = @At("TAIL"))
    private void getServerData(ejn serverData, InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(labyMod$getServerController().createServerData(serverData));
    }

    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
