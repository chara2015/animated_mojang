package net.labymod.v26_2_snapshot_8.mixins.client.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.v26_2_snapshot_8.client.gui.screen.LabyScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/MixinGui.class */
@Mixin({Gui.class})
public class MixinGui {
    @WrapOperation(method = {"setScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V")})
    private void labyMod$cancelRemovedScreen(Screen instance, Operation<Void> original) {
    }

    @ModifyVariable(method = {"setScreen"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private Screen labyMod$fireScreenOpenEvent(Screen newScreen) {
        Screen previousScreen = Minecraft.getInstance().gui.screen();
        try {
            ScreenInstance instance = ((ScreenDisplayEvent) Laby.fireEvent(new ScreenDisplayEvent(labyMod$createScreenWrapper(previousScreen), labyMod$createScreenWrapper(newScreen)))).getScreen();
            newScreen = instance == null ? null : (Screen) instance.wrap().getVersionedScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.removed();
        }
        if (newScreen instanceof LabyScreenRenderer) {
            LabyScreen labyScreenScreen = ((LabyScreenRenderer) newScreen).screen();
            if (labyScreenScreen instanceof ElementActivity) {
                ElementActivity activity = (ElementActivity) labyScreenScreen;
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }

    private ScreenWrapper labyMod$createScreenWrapper(Screen screen) {
        if (screen == null) {
            return null;
        }
        return Laby.references().screenWrapperFactory().create(screen);
    }
}
