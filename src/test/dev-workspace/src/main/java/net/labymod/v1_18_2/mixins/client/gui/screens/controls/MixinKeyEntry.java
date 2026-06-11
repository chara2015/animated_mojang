package net.labymod.v1_18_2.mixins.client.gui.screens.controls;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.options.MinecraftKeyEntry;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/screens/controls/MixinKeyEntry.class */
@Mixin({c.class})
public class MixinKeyEntry implements MinecraftKeyEntry {
    private static final ModifyReason LABYMOD$MINECRAFT_BOUNDS = ModifyReason.of("minecraftBounds");
    private DivWidget labyMod$customWidget;
    private boolean labyMod$customInitialized;

    @Override // net.labymod.api.client.options.MinecraftKeyEntry
    public Widget getCustomWidget() {
        return this.labyMod$customWidget;
    }

    @Override // net.labymod.api.client.options.MinecraftKeyEntry
    public void setCustomWidget(Widget widget) {
        ThreadSafe.ensureRenderThread();
        this.labyMod$customWidget = new DivWidget();
        this.labyMod$customWidget.addId("controls-custom-wrapper");
        this.labyMod$customWidget.addChild(widget);
        this.labyMod$customInitialized = false;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$injectCustomWidget(eeo list, dyo keyMapping, qk component, CallbackInfo ci) {
        Widget replacement = ((MinecraftInputMapping) keyMapping).getReplacement();
        if (replacement != null) {
            setCustomWidget(replacement);
        }
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$forwardMouseClicked(double mouseX, double mouseY, int mouseButton, CallbackInfoReturnable<Boolean> cir) {
        MouseButton button;
        if (this.labyMod$customWidget == null) {
            return;
        }
        cir.setReturnValue(false);
        if (this.labyMod$customWidget.isHovered() && (button = DefaultKeyMapper.pressMouse(mouseButton)) != null) {
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                this.labyMod$customWidget.mouseClicked(mouse, button);
            });
            cir.setReturnValue(true);
        }
    }

    @Inject(method = {"mouseReleased"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$forwardMouseReleased(double mouseX, double mouseY, int mouseButton, CallbackInfoReturnable<Boolean> cir) {
        MouseButton button;
        if (this.labyMod$customWidget == null) {
            return;
        }
        cir.setReturnValue(false);
        if (this.labyMod$customWidget.isHovered() && (button = DefaultKeyMapper.getMouseButton(mouseButton)) != null) {
            boolean result = Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                return this.labyMod$customWidget.mouseReleased(mouse, button);
            });
            cir.setReturnValue(Boolean.valueOf(result));
        }
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I", shift = At.Shift.AFTER)})
    private void labyMod$renderCustomWidget(dtm stack, int i1, int y, int x, int i4, int i5, int mouseX, int mouseY, boolean b1, float tickDelta, CallbackInfo ci) {
        if (this.labyMod$customWidget != null) {
            Minecraft minecraft = Laby.labyAPI().minecraft();
            if (!this.labyMod$customInitialized) {
                this.labyMod$customInitialized = true;
                Window window = minecraft.minecraftWindow();
                this.labyMod$customWidget.initialize(window);
                this.labyMod$customWidget.postInitialize();
                ThemeFile file = ThemeFile.create(Laby.labyAPI().themeService().currentTheme(), "labymod", "lss/minecraft-widget.lss");
                StyleSheet styleSheet = Laby.references().styleSheetLoader().load(file);
                if (styleSheet != null) {
                    this.labyMod$customWidget.applyStyleSheet(styleSheet);
                }
                this.labyMod$customWidget.postStyleSheetLoad();
            }
            this.labyMod$customWidget.bounds().setOuterPosition(x + 105, y, LABYMOD$MINECRAFT_BOUNDS);
            this.labyMod$customWidget.bounds().setOuterSize(135.0f, 20.0f, LABYMOD$MINECRAFT_BOUNDS);
            ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            screenContext.runInContext(((VanillaStackAccessor) stack).stack(), mouseX, mouseY, tickDelta, context -> {
                this.labyMod$customWidget.render(context);
            });
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", ordinal = 0))
    private void labyMod$preventResetButtonRender(eae instance, dtm stack, int mouseX, int mouseY, float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(stack, mouseX, mouseY, partialTicks);
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", ordinal = 1))
    private void labyMod$preventChangeButtonRender(eae instance, dtm stack, int mouseX, int mouseY, float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(stack, mouseX, mouseY, partialTicks);
        }
    }
}
