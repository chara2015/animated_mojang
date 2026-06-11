package net.labymod.v1_12_2.mixins.client.gui;

import java.util.Collection;
import java.util.Collections;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollWidget;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollbarWidget;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiSlot.class */
@Mixin({bjr.class})
public abstract class MixinGuiSlot {
    private static final Collection<Class<?>> BACKGROUND_IGNORED_SLOTS = Collections.singletonList(blc.class.getDeclaredClasses()[0]);

    @Shadow
    protected int b;

    @Shadow
    protected int c;

    @Shadow
    protected int d;

    @Shadow
    protected int e;

    @Shadow
    protected int g;

    @Shadow
    protected int i;

    @Shadow
    protected int j;

    @Shadow
    protected float n;

    @Shadow
    protected boolean s;

    @Shadow
    protected boolean q;

    @Shadow
    protected abstract void a();

    @Shadow
    protected abstract int d();

    @Shadow
    protected abstract int k();

    @Shadow
    protected abstract void a(int i, int i2, int i3, int i4, float f);

    @Shadow
    protected abstract void b(int i, int i2);

    @Insert(method = {"drawScreen"}, at = @At("HEAD"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            return;
        }
        if (this.q) {
            this.i = mouseX;
            this.j = mouseY;
            if (BACKGROUND_IGNORED_SLOTS.contains(getClass())) {
                a();
            }
            l();
            bus.g();
            bus.p();
            bve var6 = bve.a();
            int x = ((this.g + (this.b / 2)) - (c() / 2)) + 2;
            int y = (this.d + 4) - ((int) this.n);
            if (this.s) {
                a(x, y, var6);
            }
            labyMod$drawSelectionBox(x, y, mouseX, mouseY, partialTicks);
            bus.j();
            labyMod$renderScrollbar((bjr) this);
            b(mouseX, mouseY);
            bus.y();
            bus.j(GlConst.GL_FLAT);
            bus.e();
            bus.l();
        }
        ci.cancel();
    }

    @Redirect(method = {"drawScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;drawSelectionBox(IIIIF)V"))
    public void labyMod$drawSelectionBox(bjr instance, int x, int y, int mouseX, int mouseY, float partialTicks) {
        labyMod$drawSelectionBox(x, y, mouseX, mouseY, partialTicks);
    }

    @Shadow
    protected void a(int x, int y, bve tessellator) {
    }

    private void labyMod$drawSelectionBox(int x, int y, int mouseX, int mouseY, float partialTicks) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(VersionedStackProvider.DEFAULT_STACK, mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(this.d, this.e - this.d);
            references.laby3D().runWithScissor(screenContext, () -> {
                a(x, y, mouseX, mouseY, partialTicks);
            });
            scissor.pop();
        });
    }

    @Shadow
    public int c() {
        return 0;
    }

    @Shadow
    protected void l() {
    }

    @Redirect(method = {"drawScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;getMaxScroll()I"))
    private int laybMod$render(bjr instance) {
        return labyMod$renderScrollbar(instance);
    }

    private int labyMod$renderScrollbar(bjr slot) {
        int maxScroll = slot.m();
        if (maxScroll <= 0) {
            return 0;
        }
        LabyAPI labyAPI = Laby.labyAPI();
        Minecraft minecraft = labyAPI.minecraft();
        Theme theme = labyAPI.themeService().currentTheme();
        ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        float x = d();
        float contentHeight = k();
        Bounds scrollBar = new PositionedBounds(x, this.d + 2, x + 6.0f, this.e - 2);
        float scrollBarHeight = scrollBar.getHeight();
        int scrollButtonHeight = MathHelper.ceilOrFloor((scrollBarHeight * scrollBarHeight) / contentHeight);
        int scrolled = (((int) this.n) * (((int) scrollBarHeight) - scrollButtonHeight)) / maxScroll;
        if (scrolled < 0) {
            scrolled = 0;
        }
        Stack stack = labyAPI.renderPipeline().renderContexts().currentStack();
        ScrollbarWidget scrollbarWidget = new DummyScrollbarWidget(scrollBar, contentHeight, scrolled, new DummyScrollWidget());
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, minecraft.mouse(), minecraft.getTickDelta(), context -> {
            scrollbarWidgetThemeRenderer.render(scrollbarWidget, context);
        });
        return 0;
    }
}
