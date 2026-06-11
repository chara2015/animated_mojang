package net.labymod.v1_12_2.mixins.client.settings;

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
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/settings/MixinKeyEntry.class */
@Mixin({b.class})
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
        if (widget != null) {
            this.labyMod$customWidget = new DivWidget();
            this.labyMod$customWidget.addId("controls-custom-wrapper");
            this.labyMod$customWidget.addChild(widget);
        } else {
            this.labyMod$customWidget = null;
        }
        this.labyMod$customInitialized = false;
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/gui/GuiKeyBindingList;Lnet/minecraft/client/settings/KeyBinding;)V"}, at = {@At("TAIL")})
    private void labyMod$injectCustomWidget(bmd list, bhy keyBinding, CallbackInfo ci) {
        Widget replacement = ((MinecraftInputMapping) keyBinding).getReplacement();
        if (replacement != null) {
            setCustomWidget(replacement);
        }
    }

    @Inject(method = {"mousePressed"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$forwardMouseClicked(int mouseX, int mouseY, int i1, int mouseButton, int i2, int i3, CallbackInfoReturnable<Boolean> cir) {
        MouseButton button;
        if (this.labyMod$customWidget == null) {
            return;
        }
        cir.setReturnValue(false);
        if (this.labyMod$customWidget.isHovered() && (button = DefaultKeyMapper.getMouseButton(mouseButton)) != null) {
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                this.labyMod$customWidget.mouseClicked(mouse, button);
            });
            cir.setReturnValue(true);
        }
    }

    @Inject(method = {"mouseReleased"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$forwardMouseReleased(int mouseX, int mouseY, int i1, int mouseButton, int i2, int i3, CallbackInfo ci) {
        MouseButton button;
        if (this.labyMod$customWidget == null) {
            return;
        }
        ci.cancel();
        if (this.labyMod$customWidget.isHovered() && (button = DefaultKeyMapper.getMouseButton(mouseButton)) != null) {
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                this.labyMod$customWidget.mouseReleased(mouse, button);
            });
        }
    }

    @Inject(method = {"drawEntry"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", shift = At.Shift.AFTER)})
    private void labyMod$renderCustomWidget(int i1, int x, int y, int i4, int i5, int mouseX, int mouseY, boolean b1, float tickDelta, CallbackInfo ci) {
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
            screenContext.runInContext(VersionedStackProvider.DEFAULT_STACK, mouseX, mouseY, tickDelta, context -> {
                this.labyMod$customWidget.render(context);
            });
        }
    }

    @Redirect(method = {"drawEntry"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;drawButton(Lnet/minecraft/client/Minecraft;IIF)V", ordinal = 0))
    private void labyMod$preventResetButtonRender(bja instance, bib mc, int mouseX, int mouseY, float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(mc, mouseX, mouseY, partialTicks);
        }
    }

    @Redirect(method = {"drawEntry"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;drawButton(Lnet/minecraft/client/Minecraft;IIF)V", ordinal = 1))
    private void labyMod$preventChangeButtonRender(bja instance, bib mc, int mouseX, int mouseY, float partialTicks) {
        if (this.labyMod$customWidget == null) {
            instance.a(mc, mouseX, mouseY, partialTicks);
        }
    }
}
