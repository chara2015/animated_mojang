package net.labymod.core.client.gui.lss.style.reader;

import java.io.IOException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/reader/StyleParseException.class */
public class StyleParseException extends IOException {
    private final int line;
    private final String message;

    public StyleParseException(int line, String message) {
        this.line = line;
        this.message = message;
    }

    public int getLine() {
        return this.line;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Parse error at line " + this.line + ": " + this.message;
    }

    public static StyleParseException unexpectedCharacter(int line, char character) {
        return new StyleParseException(line, "Unexpected character: '" + character + "'");
    }
}
