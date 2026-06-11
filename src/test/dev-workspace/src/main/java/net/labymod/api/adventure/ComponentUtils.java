package net.labymod.api.adventure;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/adventure/ComponentUtils.class */
@Deprecated
public class ComponentUtils {
    public static Component mergeStyleRecursive(Component component, Style style, Style.Merge.Strategy strategy) {
        Style mergedStyle = component.style().merge(style, strategy);
        Component component2 = component.style(mergedStyle);
        List<Component> oldChildren = component2.children();
        List<Component> children = new ArrayList<>(oldChildren.size());
        for (Component child : oldChildren) {
            children.add(mergeStyleRecursive(child, mergedStyle, strategy));
        }
        return component2.children(children);
    }
}
