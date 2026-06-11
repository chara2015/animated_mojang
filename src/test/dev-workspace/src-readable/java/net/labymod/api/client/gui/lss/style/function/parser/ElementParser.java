package net.labymod.api.client.gui.lss.style.function.parser;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/function/parser/ElementParser.class */
@Referenceable
public interface ElementParser {
    @Nullable
    Element parseElement(@NotNull String str) throws ElementParseException;
}
