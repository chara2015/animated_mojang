package net.labymod.v1_21_5.mixins.client.gui.components.tabs;

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
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_21_5.client.gui.TabNavigationBarAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/gui/components/tabs/MixinTabNavigationBar.class */
@Mixin({fwd.class})
public abstract class MixinTabNavigationBar<T extends AbstractWidget<?>> extends fvt implements ConvertableMinecraftWidget<T>, TabNavigationBarAccessor {
    private final WidgetWatcher<T> watcher = new WidgetWatcher<>();
    private final Map<fwb, TabNavigationBarAccessor.VersionedTab> versionedTabs = new HashMap();

    @Shadow
    @Final
    private ImmutableList<fwb> i;

    @Shadow
    @Final
    private fwc h;
    private fwb labyMod$currentTab;

    @Override // net.labymod.v1_21_5.client.gui.TabNavigationBarAccessor
    public ImmutableList<fwb> getTabs() {
        return this.i;
    }

    @Override // net.labymod.v1_21_5.client.gui.TabNavigationBarAccessor
    public fwc getTabManager() {
        return this.h;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<T> getWatcher() {
        return this.watcher;
    }

    @Override // net.labymod.v1_21_5.client.gui.TabNavigationBarAccessor
    public TabNavigationBarAccessor.VersionedTab versionedTabOf(fwb tab) {
        return this.versionedTabs.computeIfAbsent(tab, TabNavigationBarAccessor.VersionedTab::new);
    }

    @Insert(method = {"render"}, at = @At("HEAD"), cancellable = true)
    public void render(ftk graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        fwb tab = this.h.a();
        if (this.labyMod$currentTab != tab) {
            int previousIndex = this.i.indexOf(this.labyMod$currentTab);
            int newIndex = this.i.indexOf(tab);
            boolean transitionToRight = newIndex > previousIndex;
            versionedTabOf(this.labyMod$currentTab).setTransitionToRight(transitionToRight);
            versionedTabOf(tab).setTransitionToRight(transitionToRight);
        }
        this.labyMod$currentTab = tab;
        this.watcher.update(this, tab);
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = this.watcher.render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    public boolean a(double mouseX, double mouseY, int button) {
        AbstractWidget<?> widget = this.watcher.getWidget();
        if (widget == null || !widget.bounds().isInRectangle(BoundsType.BORDER, (float) mouseX, (float) mouseY)) {
            return super.a(mouseX, mouseY, button);
        }
        AbstractWidgetConverter<?, ?> widgetConverter = this.watcher.getWidgetConverter();
        if (widgetConverter == null) {
            return super.a(mouseX, mouseY, button);
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
