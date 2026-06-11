package net.labymod.v1_21_11.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/screen/MixinJoinMultiplayerScreen.class */
@Mixin({gvp.class})
public class MixinJoinMultiplayerScreen {
    @WrapOperation(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;addTitleHeader(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V")})
    public void clearTitle(gou instance, yh component, gio font, Operation<Void> original) {
    }

    @Insert(method = {"join(Lnet/minecraft/client/multiplayer/ServerData;)V"}, at = @At("TAIL"))
    private void getServerData(hit serverData, InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(labyMod$getServerController().createServerData(serverData));
    }

    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
