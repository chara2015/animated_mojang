package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/TypeParser.class */
@Referenceable
public interface TypeParser {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/TypeParser$Parser.class */
    public interface Parser<T> {
        T parseValue(Class<T> cls, String str) throws Exception;
    }

    <T> void register(Parser<T> parser, Class<T>... clsArr);

    Object parseValue(Class<?> cls, String str) throws Exception;
}
