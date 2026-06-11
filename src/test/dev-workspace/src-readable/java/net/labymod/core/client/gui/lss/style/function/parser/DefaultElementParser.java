package net.labymod.core.client.gui.lss.style.function.parser;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParseException;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.lss.style.function.DefaultElementArray;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/function/parser/DefaultElementParser.class */
@Singleton
@Implements(ElementParser.class)
public class DefaultElementParser implements ElementParser {
    @Override // net.labymod.api.client.gui.lss.style.function.parser.ElementParser
    @Nullable
    public Element parseElement(@NotNull String function) throws ElementParseException {
        List<Element> elements = new ArrayList<>();
        ParsingFunction parser = new ParsingFunction(this);
        do {
            elements.add(parser.parseFunction(CharBuffer.wrap(function, parser.position(), function.length()).toString()));
        } while (parser.position() != function.length());
        return elements.size() == 1 ? elements.get(0) : new DefaultElementArray(elements);
    }
}
