package net.labymod.api.client.component;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/BaseComponent.class */
public interface BaseComponent<T extends Component> extends Component {
    @Override // net.labymod.api.client.component.Component
    T plainCopy();

    @Override // net.labymod.api.client.component.Component
    T copy();

    @Override // net.labymod.api.client.component.Component
    T style(Style style);

    @Override // net.labymod.api.client.component.Component
    T append(Component component);

    @Override // net.labymod.api.client.component.Component
    T append(int i, Component component);

    @Override // net.labymod.api.client.component.Component
    Component remove(int i);

    @Override // net.labymod.api.client.component.Component
    Component replace(int i, Component component);

    @Override // net.labymod.api.client.component.Component
    T setChildren(Collection<Component> collection);

    @Override // net.labymod.api.client.component.Component
    default T clickEvent(ClickEvent clickEvent) {
        return (T) style(style().clickEvent(clickEvent));
    }

    @Override // net.labymod.api.client.component.Component
    default T hoverEvent(HoverEvent<?> hoverEvent) {
        return (T) style(style().hoverEvent(hoverEvent));
    }

    @Override // net.labymod.api.client.component.Component
    default T color(TextColor textColor) {
        return (T) style(style().color(textColor));
    }

    @Override // net.labymod.api.client.component.Component
    default T colorIfAbsent(TextColor textColor) {
        return (T) style(style().colorIfAbsent(textColor));
    }

    @Override // net.labymod.api.client.component.Component
    default T decorate(TextDecoration textDecoration) {
        return (T) style(style().decorate(textDecoration));
    }

    @Override // net.labymod.api.client.component.Component
    default T decorate(TextDecoration... textDecorationArr) {
        return (T) style(style().decorate(textDecorationArr));
    }

    @Override // net.labymod.api.client.component.Component
    default T undecorate(TextDecoration textDecoration) {
        return (T) style(style().undecorate(textDecoration));
    }

    @Override // net.labymod.api.client.component.Component
    default T undecorate(TextDecoration... textDecorationArr) {
        return (T) style(style().undecorate(textDecorationArr));
    }

    @Override // net.labymod.api.client.component.Component
    default T unsetDecoration(TextDecoration textDecoration) {
        return (T) style(style().unsetDecoration(textDecoration));
    }

    @Override // net.labymod.api.client.component.Component
    default T unsetDecorations(TextDecoration... textDecorationArr) {
        return (T) style(style().unsetDecorations(textDecorationArr));
    }

    @Override // net.labymod.api.client.component.Component
    @Deprecated
    default T decoration(TextDecoration textDecoration, boolean z) {
        return (T) style(style().decoration(textDecoration, z));
    }

    @Override // net.labymod.api.client.component.Component
    @Deprecated
    default T children(List<Component> list) {
        return (T) setChildren(list);
    }
}
