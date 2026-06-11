package net.labymod.v1_17_1.mixins.client.screen;

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
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import net.labymod.v1_17_1.client.gui.ScreenAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/screen/MixinScreen.class */
@Mixin({eaq.class})
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor {

    @Shadow
    @Nullable
    protected dvp e;

    @Shadow
    @Final
    private List<dxy> o;

    @Shadow
    public abstract void a(List<Path> list);

    @Shadow
    protected abstract void shadow$a(dql dqlVar, @Nullable pc pcVar, int i, int i2);

    @Insert(method = {"init(Lnet/minecraft/client/Minecraft;II)V"}, at = @At("HEAD"))
    public void fireScreenInitEvent(dvp mc, int width, int height, InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }

    @Insert(method = {"renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"}, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(dql stack, int param1, InsertInfo ci) {
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), mouse, dvp.C().ak(), context -> {
            renderThemeBackground(context, ci);
        });
    }

    @Insert(method = {"renderDirtBackground(I)V"}, at = @At("HEAD"), cancellable = true)
    public void renderThemeDirtBackground(int param1, InsertInfo ci) {
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(Laby.labyAPI().renderPipeline().renderContexts().currentStack(), mouse, dvp.C().ak(), context -> {
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

    @Insert(method = {"renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(dql poseStack, int vOffset, InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public void renderComponentHoverEffect(Stack stack, Style style, int x, int y) {
        shadow$a((dql) stack.getProvider().getPoseStack(), (pc) style, x, y);
    }

    @Override // net.labymod.v1_17_1.client.gui.ScreenAccessor
    public void setMinecraft(dvp minecraft) {
        this.e = minecraft;
    }

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        a(paths);
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public List<Object> getWrappedWidgets() {
        List<Object> wrappedWidgets = new ArrayList<>();
        for (dxy child : this.o) {
            wrappedWidgets.add(asVersionedWidget(child));
        }
        return wrappedWidgets;
    }

    private VersionedWidget asVersionedWidget(dxy child) {
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
        if (child instanceof dxx) {
            dxx container = (dxx) child;
            for (dxy entry : container.j()) {
                versionedWidget2.addChild(asVersionedWidget(entry));
            }
        }
        return versionedWidget2;
    }
}
