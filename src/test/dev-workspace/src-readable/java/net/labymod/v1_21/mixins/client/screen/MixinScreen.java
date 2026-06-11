package net.labymod.v1_21.mixins.client.screen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import net.labymod.v1_21.client.gui.GuiGraphicsAccessor;
import net.labymod.v1_21.client.gui.ScreenAccessor;
import net.labymod.v1_21.client.util.MinecraftUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/screen/MixinScreen.class */
@Mixin({fod.class})
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor {

    @Shadow
    @Nullable
    protected fgo l;

    @Shadow
    protected fhx o;

    @Shadow
    @Final
    private List<fki> r;

    @Shadow
    public abstract void a(List<Path> list);

    @Insert(method = {"init(Lnet/minecraft/client/Minecraft;II)V"}, at = @At("HEAD"))
    public void fireScreenInitEvent(fgo mc, int width, int height, InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }

    @Insert(method = {"renderBackground"}, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(fhz graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(graphics.c().stack(), mouseX, mouseY, partialTicks, context -> {
            renderThemeBackground(context, ci);
        });
    }

    private void renderThemeBackground(ScreenContext context, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer != null && backgroundRenderer.renderBackground(context, this)) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderTransparentBackground"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$hideMenuBackground(fhz $$0, CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Insert(method = {"renderBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(fhz graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public void renderComponentHoverEffect(Stack stack, Style style, int x, int y) {
        GuiGraphicsAccessor currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.setPose((fbi) stack.getProvider().getPoseStack());
        currentGuiGraphics.a(this.o, (xw) style, x, y);
    }

    @Override // net.labymod.v1_21.client.gui.ScreenAccessor
    public void setMinecraft(fgo minecraft) {
        this.l = minecraft;
    }

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        a(paths);
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public List<Object> getWrappedWidgets() {
        List<Object> wrappedWidgets = new ArrayList<>();
        for (fki child : this.r) {
            wrappedWidgets.add(asVersionedWidget(child));
        }
        return wrappedWidgets;
    }

    private VersionedWidget asVersionedWidget(fki child) {
        VersionedWidget versionedWidget = null;
        if (child instanceof ConvertableMinecraftWidget) {
            ConvertableMinecraftWidget<?> convertable = (ConvertableMinecraftWidget) child;
            WidgetWatcher watcher = convertable.getWatcher();
            if (watcher != null && watcher.getWidget() != null) {
                versionedWidget = new VersionedWidget(child.getClass(), watcher.getWidget());
            }
        }
        if (versionedWidget != null) {
            return versionedWidget;
        }
        VersionedWidget versionedWidget2 = new VersionedWidget(child.getClass());
        if (child instanceof fkh) {
            fkh container = (fkh) child;
            for (fki entry : container.aK_()) {
                versionedWidget2.addChild(asVersionedWidget(entry));
            }
        }
        return versionedWidget2;
    }
}
