package net.labymod.core.client.render.font.component;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.component.format.Style;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/CollectingFlattenerListener.class */
public class CollectingFlattenerListener implements FlattenerListener {
    private final List<Component> components = new ArrayList();
    private final List<Component> styleQueue = new ArrayList();

    public List<Component> getComponents() {
        return this.components;
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void push(@NotNull Component source) {
        this.styleQueue.add(source);
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void component(@NotNull String text) {
        Style mergedStyle = Style.empty();
        for (int i = this.styleQueue.size() - 1; i >= 0; i--) {
            mergedStyle = mergedStyle.merge(this.styleQueue.get(i).style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        Component lastComponent = this.styleQueue.isEmpty() ? null : this.styleQueue.get(this.styleQueue.size() - 1);
        if (lastComponent instanceof IconComponent) {
            IconComponent icon = (IconComponent) lastComponent;
            this.components.add(Component.icon(icon.getIcon(), mergedStyle, icon.getWidth(), icon.getHeight(), icon.getPlaceholder()));
        } else {
            this.components.add(Component.text(text).style(mergedStyle));
        }
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void pop(@NotNull Component source) {
        this.styleQueue.remove(source);
    }
}
