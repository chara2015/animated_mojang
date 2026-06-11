package net.labymod.v1_21_11.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollWidget;
import net.labymod.core.client.gui.screen.widget.widgets.dummy.DummyScrollbarWidget;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinAbstractScrollArea.class */
@Mixin({AbstractScrollArea.class})
public abstract class MixinAbstractScrollArea extends AbstractWidget {
    @Shadow
    public abstract double scrollAmount();

    @Shadow
    protected abstract int contentHeight();

    @Shadow
    protected abstract int scrollBarX();

    public MixinAbstractScrollArea(int $$0, int $$1, int $$2, int $$3, Component $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @WrapOperation(method = {"renderScrollbar"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractScrollArea;scrollbarVisible()Z")})
    private boolean labyMod$render(AbstractScrollArea instance, Operation<Boolean> original) {
        int maxScroll = instance.maxScrollAmount();
        if (maxScroll <= 0) {
            return false;
        }
        LabyAPI labyAPI = Laby.labyAPI();
        Minecraft minecraft = labyAPI.minecraft();
        Theme theme = labyAPI.themeService().currentTheme();
        ThemeRenderer<ScrollbarWidget> scrollbarWidgetThemeRenderer = theme.getWidgetRendererByName("Scrollbar");
        int x = scrollBarX();
        PositionedBounds positionedBounds = new PositionedBounds(x, getY(), x + 6, getBottom());
        float scrollBarHeight = positionedBounds.getHeight();
        int contentHeight = contentHeight();
        int scrollButtonHeight = MathHelper.ceilOrFloor((scrollBarHeight * scrollBarHeight) / contentHeight);
        int scrolled = (((int) scrollAmount()) * (((int) scrollBarHeight) - scrollButtonHeight)) / maxScroll;
        if (scrolled < 0) {
            scrolled = 0;
        }
        Stack stack = labyAPI.renderPipeline().renderContexts().currentStack();
        DummyScrollbarWidget dummyScrollbarWidget = new DummyScrollbarWidget(positionedBounds, contentHeight, scrolled, new DummyScrollWidget());
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, minecraft.mouse(), minecraft.getTickDelta(), context -> {
            scrollbarWidgetThemeRenderer.render(dummyScrollbarWidget, context);
        });
        return false;
    }
}

