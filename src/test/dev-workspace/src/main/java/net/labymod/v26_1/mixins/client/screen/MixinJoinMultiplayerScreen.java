package net.labymod.v26_1.mixins.client.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/screen/MixinJoinMultiplayerScreen.class */
@Mixin({JoinMultiplayerScreen.class})
public class MixinJoinMultiplayerScreen {
    @WrapOperation(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;addTitleHeader(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V")})
    public void clearTitle(HeaderAndFooterLayout instance, Component component, Font font, Operation<Void> original) {
    }

    @Insert(method = {"join(Lnet/minecraft/client/multiplayer/ServerData;)V"}, at = @At("TAIL"))
    private void getServerData(ServerData serverData, InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(labyMod$getServerController().createServerData(serverData));
    }

    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
