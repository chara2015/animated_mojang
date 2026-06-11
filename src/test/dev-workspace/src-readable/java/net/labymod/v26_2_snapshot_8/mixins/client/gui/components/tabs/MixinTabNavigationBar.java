package net.labymod.v26_2_snapshot_8.mixins.client.gui.components.tabs;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v26_2_snapshot_8.client.gui.TabNavigationBarAccessor;
import net.labymod.v26_2_snapshot_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/components/tabs/MixinTabNavigationBar.class */
@Mixin({TabNavigationBar.class})
public abstract class MixinTabNavigationBar<T extends AbstractWidget<?>> extends AbstractContainerEventHandler implements ConvertableMinecraftWidget<T>, TabNavigationBarAccessor {
    private final WidgetWatcher<T> watcher = new WidgetWatcher<>();
    private final Map<Tab, TabNavigationBarAccessor.VersionedTab> versionedTabs = new HashMap();

    @Shadow
    @Final
    private ImmutableList<Tab> tabs;

    @Shadow
    @Final
    private TabManager tabManager;
    private Tab labyMod$currentTab;

    @Override // net.labymod.v26_2_snapshot_8.client.gui.TabNavigationBarAccessor
    public ImmutableList<Tab> getTabs() {
        return this.tabs;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.gui.TabNavigationBarAccessor
    public TabManager getTabManager() {
        return this.tabManager;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<T> getWatcher() {
        return this.watcher;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.gui.TabNavigationBarAccessor
    public TabNavigationBarAccessor.VersionedTab versionedTabOf(Tab tab) {
        return this.versionedTabs.computeIfAbsent(tab, TabNavigationBarAccessor.VersionedTab::new);
    }

    @Insert(method = {"extractRenderState"}, at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        Tab tab = this.tabManager.getCurrentTab();
        if (this.labyMod$currentTab != tab) {
            int previousIndex = this.tabs.indexOf(this.labyMod$currentTab);
            int newIndex = this.tabs.indexOf(tab);
            boolean transitionToRight = newIndex > previousIndex;
            versionedTabOf(this.labyMod$currentTab).setTransitionToRight(transitionToRight);
            versionedTabOf(tab).setTransitionToRight(transitionToRight);
        }
        this.labyMod$currentTab = tab;
        this.watcher.update(this, tab);
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = this.watcher.render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();
        AbstractWidget<?> widget = this.watcher.getWidget();
        if (widget == null || !widget.bounds().isInRectangle(BoundsType.BORDER, (float) mouseX, (float) mouseY)) {
            return super.mouseClicked(event, doubleClick);
        }
        AbstractWidgetConverter<?, ?> widgetConverter = this.watcher.getWidgetConverter();
        if (widgetConverter == null) {
            return super.mouseClicked(event, doubleClick);
        }
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            if (mouseButton == null) {
                return false;
            }
            return widgetConverter.mouseClicked(widget, mouse, mouseButton);
        });
    }
}
