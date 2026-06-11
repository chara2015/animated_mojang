package net.labymod.v1_21_8.mixins.client.screen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import net.labymod.v1_21_8.client.gui.GuiGraphicsAccessor;
import net.labymod.v1_21_8.client.gui.ScreenAccessor;
import net.labymod.v1_21_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/screen/MixinScreen.class */
@Mixin({get.class})
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor {

    @Shadow
    @Nullable
    protected fue n;

    @Shadow
    protected fwz q;

    @Shadow
    @Final
    private List<fzn> d;

    @Shadow
    public abstract void a(List<Path> list);

    @Shadow
    abstract void a(fue fueVar, xm xmVar);

    @Insert(method = {"init(Lnet/minecraft/client/Minecraft;II)V"}, at = @At("HEAD"))
    public void fireScreenInitEvent(fue mc, int width, int height, InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }

    @Insert(method = {"renderBackground"}, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(fxb graphics, int mouseX, int mouseY, float tickDelta, InsertInfo ci) {
        Stack stack = Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, mouseX, mouseY, tickDelta, context -> {
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
    private void labyMod$hideMenuBackground(fxb $$0, CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Insert(method = {"renderBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(fxb graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public void renderComponentHoverEffect(Stack stack, Style style, int x, int y) {
        GuiGraphicsAccessor currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.setPose((Matrix3x2fStack) stack.getProvider().getPoseStack());
        currentGuiGraphics.a(this.q, (yl) style, x, y);
    }

    @Override // net.labymod.v1_21_8.client.gui.ScreenAccessor
    public void setMinecraft(fue minecraft) {
        this.n = minecraft;
    }

    @Override // net.labymod.v1_21_8.client.gui.ScreenAccessor
    public void handleClickEvent(ClickEvent event) {
        a(this.n, (xm) event);
    }

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        a(paths);
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public List<Object> getWrappedWidgets() {
        List<Object> wrappedWidgets = new ArrayList<>();
        for (fzn child : this.d) {
            wrappedWidgets.add(asVersionedWidget(child));
        }
        return wrappedWidgets;
    }

    private VersionedWidget asVersionedWidget(fzn child) {
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
        if (child instanceof fzm) {
            fzm container = (fzm) child;
            for (fzn entry : container.aH_()) {
                versionedWidget2.addChild(asVersionedWidget(entry));
            }
        }
        return versionedWidget2;
    }
}
