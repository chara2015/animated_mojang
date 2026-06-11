package net.minecraft.client.gui.components.debug;

import java.util.Collection;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/debug/DebugScreenDisplayer.class */
public interface DebugScreenDisplayer {
    void addPriorityLine(String str);

    void addLine(String str);

    void addToGroup(Identifier identifier, Collection<String> collection);

    void addToGroup(Identifier identifier, String str);
}
