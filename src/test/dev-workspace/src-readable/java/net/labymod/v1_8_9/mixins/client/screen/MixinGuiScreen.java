package net.labymod.v1_8_9.mixins.client.screen;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import net.labymod.v1_8_9.client.component.VersionedClickEvent;
import net.labymod.v1_8_9.client.gui.GuiScreenAccessor;
import net.labymod.v1_8_9.client.gui.ScrollableGuiScreen;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/screen/MixinGuiScreen.class */
@Mixin({axu.class})
public abstract class MixinGuiScreen extends avp implements LabyScreenRendererAccessor, GuiScreenAccessor {

    @Shadow
    protected List<avs> n;

    @Shadow
    protected avn q;

    @Shadow
    protected bjh k;
    private static final char KEY_PRESSED_CHAR = 27;

    @Shadow
    public int l;

    @Shadow
    public int m;

    @Shadow
    protected ave j;
    private ChatMessageSendEvent labyMod$currentMessageSendEvent;
    private boolean labyMod$handleMouseInput = true;

    @Shadow
    public abstract void b();

    @Shadow
    protected abstract void a(int i, int i2, int i3);

    @Shadow
    protected abstract void b(int i, int i2, int i3);

    @Shadow
    protected abstract void a(char c, int i);

    @Shadow
    protected abstract void a(int i, int i2, int i3, long j);

    @Shadow
    protected abstract void a(eu euVar, int i, int i2);

    @Shadow
    public abstract void a(ave aveVar, int i, int i2);

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseClicked(MutableMouse mouse, int mouseButton) {
        a(mouse.getX(), mouse.getY(), mouseButton);
        return false;
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseReleased(MutableMouse mouse, int mouseButton) {
        b(mouse.getX(), mouse.getY(), mouseButton);
        return false;
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseClickMove(MutableMouse mouse, int button, double deltaX, double deltaY) {
        a(mouse.getX(), mouse.getY(), button, ave.J());
        return false;
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedKeyPressed(Key key, InputType type) {
        if (type == InputType.ACTION) {
            a((char) 0, key.getId());
            return false;
        }
        return false;
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedKeyReleased(Key key, InputType type) {
        return false;
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedCharTyped(Key key, char character) {
        a(character, key.getId());
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean mouseScrolled(MutableMouse mouse, double delta) {
        if (this instanceof ScrollableGuiScreen) {
            return ((ScrollableGuiScreen) this).mouseScrolled(delta);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"handleMouseInput"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$redirectMouseInput(InsertInfo ci) {
        if (!this.labyMod$handleMouseInput) {
            ci.cancel();
        } else if (this instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this).screen().doScreenAction("handleMouseInput", null);
        }
    }

    @Insert(method = {"handleMouseInput()V"}, at = @At("TAIL"))
    private void labyMod$injectMouseScrolled(InsertInfo ci) {
        double scrollDelta = ((double) Mouse.getDWheel()) * 1.0d;
        if (scrollDelta != 0.0d) {
            float scrollDeltaDirection = scrollDelta > 0.0d ? 1.0f : -1.0f;
            int mouseX = (Mouse.getEventX() * this.l) / this.j.d;
            int mouseY = (this.m - ((Mouse.getEventY() * this.m) / this.j.e)) - 1;
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                mouseScrolled(mouse, scrollDeltaDirection);
            });
        }
    }

    @Insert(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$fireChatMessageSendEvent(String message, boolean addToHistory, InsertInfo callbackInfo) {
        String history;
        this.labyMod$currentMessageSendEvent = (ChatMessageSendEvent) Laby.fireEvent(new ChatMessageSendEvent(message, addToHistory));
        if (this.labyMod$currentMessageSendEvent.isCancelled()) {
            callbackInfo.cancel();
            if (addToHistory && (history = this.labyMod$currentMessageSendEvent.getHistoryText()) != null && !history.trim().isEmpty()) {
                this.j.q.d().a(history);
            }
        }
    }

    @Insert(method = {"setWorldAndResolution"}, at = @At("HEAD"))
    public void labyMod$fireScreenInitEvent(ave mc, int width, int height, InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }

    @Insert(method = {"handleComponentClick"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleCustomClickEvent(eu component, InsertInfoReturnable<Boolean> ci) {
        if (component == null) {
            return;
        }
        ClickEvent clickEventH = component.b().h();
        if (!(clickEventH instanceof VersionedClickEvent) || r()) {
            return;
        }
        ClickEvent.Action action = clickEventH.action();
        if (action == ClickEvent.Action.COPY_TO_CLIPBOARD) {
            Laby.references().chatExecutor().copyToClipboard(clickEventH.b());
            ci.setReturnValue(true);
        }
    }

    @ModifyVariable(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, at = @At("HEAD"))
    public boolean labyMod$modifyAddToHistory(boolean addToHistory) {
        return this.labyMod$currentMessageSendEvent.getHistoryText() != null;
    }

    @ModifyVariable(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, at = @At("HEAD"))
    public String labyMod$modifyHistoryText(String message) {
        return this.labyMod$currentMessageSendEvent.getHistoryText();
    }

    @ModifyVariable(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;", shift = At.Shift.BEFORE, ordinal = 0))
    public String labyMod$modifyMessage(String message) {
        return this.labyMod$currentMessageSendEvent.getMessage();
    }

    @Insert(method = {"drawWorldBackground(I)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWorldBackground(int p1, InsertInfo ci) {
        Stack stack = Laby.references().renderPipeline().renderContexts().currentStack();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            labyMod$renderCustomBackground(context, ci);
        });
    }

    @Insert(method = {"drawBackground(I)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderBackground(int p1, InsertInfo ci) {
        Stack stack = Laby.references().renderPipeline().renderContexts().currentStack();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            labyMod$renderCustomBackground(context, ci);
        });
    }

    private void labyMod$renderCustomBackground(ScreenContext context, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer != null && backgroundRenderer.renderBackground(context, this)) {
            ci.cancel();
        }
    }

    @Redirect(method = {"drawWorldBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawGradientRect(IIIIII)V"))
    private void labyMod$disableBackground(axu instance, int x, int y, int width, int height, int color0, int color1) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get().booleanValue()) {
            return;
        }
        a(x, y, width, height, color0, color1);
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiScreenAccessor
    public void resize(int width, int height) {
        a(ave.A(), width, height);
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiScreenAccessor
    public void setMinecraft(ave minecraft) {
        this.j = minecraft;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiScreenAccessor
    public boolean isHandleMouseInput() {
        return this.labyMod$handleMouseInput;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiScreenAccessor
    public void setHandleMouseInput(boolean handleMouseInput) {
        this.labyMod$handleMouseInput = handleMouseInput;
    }

    @Inject(method = {"openWebLink"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$openWebLink(URI url, CallbackInfo ci) {
        try {
            OperatingSystem.getPlatform().openUrl(url.toURL());
            ci.cancel();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public void renderComponentHoverEffect(Stack stack, Style style, int x, int y) {
        TextComponent component = Component.empty().style(style);
        a((eu) component, x, y);
    }

    @Shadow
    public static boolean r() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.VanillaScreen
    public List<Object> getWrappedWidgets() {
        List<Object> wrappedWidgets = new ArrayList<>();
        for (avs child : this.n) {
            wrappedWidgets.add(asVersionedWidget(child));
        }
        return wrappedWidgets;
    }

    private VersionedWidget asVersionedWidget(avs child) {
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
        return new VersionedWidget(child.getClass());
    }
}
