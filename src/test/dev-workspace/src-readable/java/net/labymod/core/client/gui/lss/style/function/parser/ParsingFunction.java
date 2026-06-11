package net.labymod.core.client.gui.lss.style.function.parser;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParseException;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.core.client.gui.lss.style.function.DefaultFunction;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/function/parser/ParsingFunction.class */
public class ParsingFunction {
    public static final char PARAMS_OPEN = '(';
    public static final char PARAMS_CLOSE = ')';
    public static final char PARAM_SPLIT = ',';
    public static final char PARAM_SPACE_SPLIT = ' ';
    private final ElementParser elementParser;
    private final StringBuilder text = new StringBuilder();
    private ParserState state = ParserState.FUNCTION_NAME;
    private int position = 0;

    public ParsingFunction(ElementParser elementParser) {
        this.elementParser = elementParser;
    }

    private String selectState(ParserState state) {
        this.state = state;
        return resetText();
    }

    private String resetText() {
        String text = this.text.toString();
        this.text.setLength(0);
        return text;
    }

    public Element parseFunction(String input) throws ElementParseException {
        this.state = ParserState.FUNCTION_NAME;
        String functionName = null;
        int functionLevel = 0;
        List<Element> parameters = new ArrayList<>();
        char[] chars = input.toCharArray();
        int length = chars.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            char c = chars[i];
            this.position++;
            if (c == ' ' && this.state == ParserState.FUNCTION_NAME) {
                if (!this.text.isEmpty()) {
                    return new FixedElement(resetText());
                }
            } else {
                if (c == '(') {
                    functionLevel++;
                    if (this.state == ParserState.FUNCTION_NAME) {
                        functionName = selectState(ParserState.FUNCTION_PARAMS);
                    }
                }
                if ((c == ',' || c == ')') && functionLevel == 1) {
                    parameters.add(parseParameter(resetText()));
                    if (c == ')') {
                        functionLevel--;
                        break;
                    }
                } else {
                    if (c == ')') {
                        functionLevel--;
                    }
                    this.text.append(c);
                }
            }
            i++;
        }
        if (functionName == null || functionName.trim().isEmpty()) {
            String value = this.text.toString();
            this.text.setLength(0);
            return new FixedElement(value.trim());
        }
        if (functionLevel != 0) {
            throw new ElementParseException("Function \"" + functionName + "\" not closed (missing bracket): " + input);
        }
        if (!this.text.isEmpty()) {
            char[] charArray = resetText().toCharArray();
            boolean quoted = false;
            int level = 1;
            int length2 = charArray.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length2) {
                    break;
                }
                char c2 = charArray[i2];
                if (c2 == '\"') {
                    quoted = !quoted;
                }
                if (!quoted) {
                    if (c2 == '(') {
                        level++;
                    }
                    if (c2 == ')') {
                        level--;
                        if (level == 0) {
                            String text = this.text.toString();
                            parameters.add(parseParameter(text.trim()));
                            this.text.setLength(0);
                            this.position -= (charArray.length - text.length()) - 1;
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                this.text.append(c2);
                i2++;
            }
        }
        return new DefaultFunction(functionName.trim(), (Element[]) parameters.toArray(new Element[0]));
    }

    private Element parseParameter(String rawParam) {
        if (rawParam.indexOf(40) > 0 && rawParam.indexOf(41) != -1) {
            return this.elementParser.parseElement(rawParam);
        }
        String rawParam2 = rawParam.trim();
        if (rawParam2.indexOf(34) == 0 && rawParam2.lastIndexOf(34) == rawParam2.length() - 1) {
            rawParam2 = rawParam2.substring(1, rawParam2.length() - 1);
        }
        return new FixedElement(rawParam2);
    }

    public int position() {
        return this.position;
    }
}
