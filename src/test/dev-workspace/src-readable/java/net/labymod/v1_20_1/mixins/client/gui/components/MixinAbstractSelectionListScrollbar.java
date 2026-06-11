package net.labymod.v1_20_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollWidget;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollbarWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/gui/components/MixinAbstractSelectionListScrollbar.class */
@Mixin({epc.class})
public abstract class MixinAbstractSelectionListScrollbar {

    @Shadow
    protected int g;

    @Shadow
    protected int f;

    @Shadow
    protected int e;

    @Shadow
    protected int d;

    @Shadow
    protected int k;

    @Shadow
    protected abstract int a();

    @Shadow
    public abstract double l();

    @Shadow
    protected abstract int c();

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;getMaxScroll()I"))
    private int labyMod$render(epc instance) {
        int maxScroll = instance.m();
        if (maxScroll <= 0) {
            return 0;
        }
        LabyAPI labyAPI = Laby.labyAPI();
        Minecraft minecraft = labyAPI.minecraft();
        Theme theme = labyAPI.themeService().currentTheme();
        ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        int x = c();
        Bounds scrollBar = new PositionedBounds(x, this.f, x + 6, this.g);
        float scrollBarHeight = scrollBar.getHeight();
        int contentHeight = a() - this.k;
        int scrollButtonHeight = MathHelper.ceilOrFloor((scrollBarHeight * scrollBarHeight) / contentHeight);
        int scrolled = (((int) l()) * (((int) scrollBarHeight) - scrollButtonHeight)) / maxScroll;
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
