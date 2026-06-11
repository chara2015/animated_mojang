package net.labymod.v1_19_4.mixins.client.gui.components.tabs;

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
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_19_4.client.gui.TabNavigationBarAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/components/tabs/MixinTabNavigationBar.class */
@Mixin({ept.class})
public abstract class MixinTabNavigationBar<T extends AbstractWidget<?>> extends epj implements ConvertableMinecraftWidget<T>, TabNavigationBarAccessor {
    private final WidgetWatcher<T> watcher = new WidgetWatcher<>();
    private final Map<epr, TabNavigationBarAccessor.VersionedTab> versionedTabs = new HashMap();

    @Shadow
    @Final
    private ImmutableList<epr> m;

    @Shadow
    @Final
    private eps l;
    private epr labyMod$currentTab;

    @Override // net.labymod.v1_19_4.client.gui.TabNavigationBarAccessor
    public ImmutableList<epr> getTabs() {
        return this.m;
    }

    @Override // net.labymod.v1_19_4.client.gui.TabNavigationBarAccessor
    public eps getTabManager() {
        return this.l;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget
    public WidgetWatcher<T> getWatcher() {
        return this.watcher;
    }

    @Override // net.labymod.v1_19_4.client.gui.TabNavigationBarAccessor
    public TabNavigationBarAccessor.VersionedTab versionedTabOf(epr tab) {
        return this.versionedTabs.computeIfAbsent(tab, TabNavigationBarAccessor.VersionedTab::new);
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At("HEAD"), cancellable = true)
    public void render(ehe stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        epr tab = this.l.a();
        if (this.labyMod$currentTab != tab) {
            int previousIndex = this.m.indexOf(this.labyMod$currentTab);
            int newIndex = this.m.indexOf(tab);
            boolean transitionToRight = newIndex > previousIndex;
            versionedTabOf(this.labyMod$currentTab).setTransitionToRight(transitionToRight);
            versionedTabOf(tab).setTransitionToRight(transitionToRight);
        }
        this.labyMod$currentTab = tab;
        this.watcher.update(this, tab);
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = this.watcher.render(((VanillaStackAccessor) stack).stack(), mouse, partialTicks);
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
