package net.labymod.core.client.gui.lss.style.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.lss.style.DefaultStyleSheet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/reader/LssParser.class */
public class LssParser {
    private static final String IMPORTANT_SUFFIX = " !important";
    private final ThemeFile file;
    private final char[] input;
    private final int length;
    private int pos = 0;
    private int lineNumber = 1;
    private final List<StyleBlock> blocks = new ArrayList();
    private final List<DefaultStyleRule> rules = new ArrayList();
    private final StringBuilder buffer = new StringBuilder(256);
    private boolean skip;
    private boolean inlineSkip;
    private boolean prepareSkip;
    private boolean foundQuotationMark;
    private boolean searchNextQuotationMark;
    private static final Logging LOGGER = Logging.getLogger();
    private static final WidgetModifier WIDGET_MODIFIER = Laby.references().widgetModifier();
    private static final StyleSheetLoader STYLE_SHEET_LOADER = Laby.references().styleSheetLoader();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/reader/LssParser$ReadState.class */
    private enum ReadState {
        BLANK,
        INSTRUCTION_KEY,
        INSTRUCTION_VALUE,
        RULE
    }

    public LssParser(ThemeFile file) throws IOException {
        this.file = file;
        this.input = readInput(file.openInputStream());
        this.length = this.input.length;
    }

    private static char[] readInput(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try {
            StringBuilder sb = new StringBuilder(4096);
            char[] buf = new char[4096];
            while (true) {
                int read = reader.read(buf);
                if (read != -1) {
                    sb.append(buf, 0, read);
                } else {
                    char[] charArray = sb.toString().toCharArray();
                    reader.close();
                    return charArray;
                }
            }
        } catch (Throwable th) {
            try {
                reader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public StyleSheet parse() throws IOException {
        DefaultStyleSheet styleSheet = new DefaultStyleSheet(this.file);
        do {
            try {
            } catch (StyleParseException e) {
                throw e;
            } catch (Exception e2) {
                throw new StyleParseException(this.lineNumber, e2.getMessage());
            }
        } while (nextNestedStyleBlocks(styleSheet));
        for (StyleBlock block : this.blocks) {
            for (String subSelector : block.getRawSelector().split(",")) {
                block.add(subSelector, block);
            }
            styleSheet.addBlock(block);
        }
        for (DefaultStyleRule rule : this.rules) {
            if (rule.getKey().equals("import")) {
                processImportRule(styleSheet, rule);
            }
            for (StyleBlock block2 : rule.getBlocks()) {
                ((DefaultStyleBlock) block2).setStyleSheet(styleSheet);
                for (String subSelector2 : block2.getRawSelector().split(",")) {
                    block2.add(subSelector2, block2);
                }
            }
            styleSheet.addRule(rule);
        }
        styleSheet.setLoaded();
        return styleSheet;
    }

    private void processImportRule(DefaultStyleSheet styleSheet, DefaultStyleRule rule) {
        ThemeFile importFile;
        try {
            String value = rule.getValue();
            if (value.equals("super()")) {
                Theme theme = this.file.theme();
                if (theme instanceof ExtendingTheme) {
                    importFile = this.file.forTheme(((ExtendingTheme) theme).parentTheme()).normalize();
                } else {
                    throw new StyleParseException(this.lineNumber, "Can't import parent style sheet of " + this.file.theme().getId() + " because it has no parent theme");
                }
            } else {
                importFile = ThemeFile.create(this.file.theme(), this.file.getNamespace(), this.file.parent().getPath() + "/" + cleanUpBounds(value));
            }
            if (!Objects.equals(importFile, this.file)) {
                styleSheet.addImport(STYLE_SHEET_LOADER.load(importFile));
                return;
            }
            throw new StyleParseException(this.lineNumber, "Can't import style sheet into itself (" + this.file.getFullRawPath() + ")");
        } catch (Throwable throwable) {
            LOGGER.error("Could not import style sheet", throwable);
        }
    }

    private boolean nextNestedStyleBlocks(StyleSheet styleSheet) throws IOException {
        String string;
        String camelCase;
        boolean found = false;
        List<StyleBlock> outerBlocks = this.blocks;
        List<StyleBlock> currentBlocks = outerBlocks;
        int depth = 0;
        this.buffer.setLength(0);
        String secondBuffer = "";
        ReadState state = ReadState.BLANK;
        ReadState prevRuleState = null;
        boolean isString = false;
        int ruleDepth = -1;
        while (this.pos < this.length) {
            char[] cArr = this.input;
            int i = this.pos;
            this.pos = i + 1;
            char character = cArr[i];
            if (character == '\n') {
                this.lineNumber++;
            }
            if (character != '\r') {
                boolean isQuotationMark = character == '\"';
                handleQuotationMarkSearch(isQuotationMark);
                if (!this.foundQuotationMark) {
                    if (this.skip) {
                        if (this.prepareSkip && character == '/') {
                            this.skip = false;
                        }
                        if (this.inlineSkip && character == '\n') {
                            this.prepareSkip = false;
                            this.skip = false;
                            this.inlineSkip = false;
                        } else {
                            this.prepareSkip = character == '*';
                        }
                    } else if (this.prepareSkip) {
                        if (character == '*') {
                            this.skip = true;
                        } else if (character == '/') {
                            this.skip = true;
                            this.inlineSkip = true;
                        } else {
                            this.prepareSkip = false;
                            this.buffer.append('/');
                        }
                    } else if (character == '/') {
                        this.prepareSkip = true;
                    } else {
                        this.prepareSkip = false;
                    }
                }
                if (isQuotationMark && this.searchNextQuotationMark) {
                    this.foundQuotationMark = false;
                    this.searchNextQuotationMark = false;
                }
                if (character == '@' && state != ReadState.INSTRUCTION_VALUE) {
                    if (depth != 0) {
                        throw new StyleParseException(this.lineNumber, "Cannot handle @-rules inside other blocks");
                    }
                    prevRuleState = state;
                    state = ReadState.RULE;
                    this.buffer.setLength(0);
                } else {
                    if (state == ReadState.RULE) {
                        if (character == '{') {
                            DefaultStyleRule rule = new DefaultStyleRule(styleSheet, this.buffer.toString().trim());
                            ruleDepth = depth;
                            this.rules.add(rule);
                            found = true;
                            currentBlocks = rule.getBlocks();
                            this.buffer.setLength(0);
                            state = ReadState.INSTRUCTION_KEY;
                        } else if (character == '\n' || character == ';') {
                            this.rules.add(new DefaultStyleRule(styleSheet, this.buffer.toString().trim()));
                            found = true;
                            this.buffer.setLength(0);
                            state = prevRuleState;
                        } else {
                            this.buffer.append(character);
                        }
                    }
                    if (character == '}' && (state == ReadState.BLANK || state == ReadState.INSTRUCTION_KEY)) {
                        depth--;
                        if (depth >= 0 && depth == ruleDepth) {
                            currentBlocks = outerBlocks;
                            ruleDepth = -1;
                        }
                        if (depth == 0) {
                            return found;
                        }
                        if (depth < 0) {
                            throw StyleParseException.unexpectedCharacter(this.lineNumber, character);
                        }
                        this.buffer.setLength(0);
                    } else {
                        if (state == ReadState.BLANK) {
                            if (character == '{') {
                                throw StyleParseException.unexpectedCharacter(this.lineNumber, character);
                            }
                            if (character != ' ' && character != '\t' && character != '\n') {
                                state = ReadState.INSTRUCTION_KEY;
                                this.buffer.setLength(0);
                            }
                        }
                        if ((state == ReadState.INSTRUCTION_KEY || state == ReadState.INSTRUCTION_VALUE) && character == '{') {
                            StyleBlock parentBlock = null;
                            for (StyleBlock block : currentBlocks) {
                                if (block.getDepth() < depth) {
                                    parentBlock = block;
                                }
                            }
                            StringBuilder parentSelector = new StringBuilder();
                            if (parentBlock != null) {
                                parentSelector.append(parentBlock.getRawSelector()).append(" ");
                            }
                            if (state == ReadState.INSTRUCTION_VALUE) {
                                string = secondBuffer + ":" + String.valueOf(this.buffer);
                            } else {
                                string = this.buffer.toString();
                            }
                            String rawSelector = string;
                            String selector = cleanUp(rawSelector).replace(", ", ",");
                            if (selector.startsWith("&")) {
                                selector = selector.substring(1);
                                if (!parentSelector.isEmpty()) {
                                    parentSelector.deleteCharAt(parentSelector.length() - 1);
                                }
                            }
                            StyleBlock styleBlock = new DefaultStyleBlock();
                            styleBlock.setRawSelector(depth, String.valueOf(parentSelector) + selector);
                            depth++;
                            currentBlocks.add(styleBlock);
                            found = true;
                            state = ReadState.INSTRUCTION_KEY;
                            this.buffer.setLength(0);
                        } else {
                            if (state == ReadState.INSTRUCTION_KEY) {
                                if (character == ';') {
                                    throw StyleParseException.unexpectedCharacter(this.lineNumber, character);
                                }
                                if (character == ':') {
                                    secondBuffer = this.buffer.toString();
                                    state = ReadState.INSTRUCTION_VALUE;
                                    this.buffer.setLength(0);
                                } else {
                                    this.buffer.append(character);
                                }
                            }
                            if (state != ReadState.INSTRUCTION_VALUE) {
                                continue;
                            } else {
                                if (isString) {
                                    if (character == '\n') {
                                        throw StyleParseException.unexpectedCharacter(this.lineNumber, character);
                                    }
                                } else {
                                    if (character == ':') {
                                        this.buffer.insert(0, secondBuffer + ":");
                                        secondBuffer = "";
                                        state = ReadState.INSTRUCTION_KEY;
                                    }
                                    if (character == ';') {
                                        StyleBlock styleBlock2 = null;
                                        int i2 = currentBlocks.size() - 1;
                                        while (true) {
                                            if (i2 < 0) {
                                                break;
                                            }
                                            StyleBlock block2 = currentBlocks.get(i2);
                                            if (block2.getDepth() == depth - 1) {
                                                styleBlock2 = block2;
                                                break;
                                            }
                                            i2--;
                                        }
                                        if (styleBlock2 == null) {
                                            throw new StyleParseException(this.lineNumber, "Current depth " + depth + " has no block");
                                        }
                                        String value = cleanUpBounds(this.buffer.toString());
                                        if (value.startsWith("@")) {
                                            for (DefaultStyleRule rule2 : this.rules) {
                                                if (rule2.getKey().equals(value.substring(1))) {
                                                    value = cleanUpBounds(rule2.getValue());
                                                }
                                            }
                                        }
                                        String rawKey = cleanUp(secondBuffer);
                                        if (WIDGET_MODIFIER.isVariableKey(rawKey)) {
                                            camelCase = rawKey;
                                        } else {
                                            camelCase = TextFormat.DASH_CASE.toCamelCase(rawKey, true);
                                        }
                                        String key = camelCase;
                                        String resultValue = value;
                                        boolean important = false;
                                        if (resultValue.endsWith(IMPORTANT_SUFFIX)) {
                                            important = true;
                                            resultValue = resultValue.substring(0, resultValue.length() - IMPORTANT_SUFFIX.length());
                                        }
                                        styleBlock2.put(new SingleInstruction(key, resultValue, this.lineNumber, important, styleSheet));
                                        state = ReadState.INSTRUCTION_KEY;
                                        this.buffer.setLength(0);
                                    }
                                }
                                if (character == '\"') {
                                    isString = !isString;
                                }
                                this.buffer.append(character);
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    private void handleQuotationMarkSearch(boolean isQuotationMark) {
        boolean isFirstQuotationMark = false;
        if (isQuotationMark && !this.foundQuotationMark) {
            this.foundQuotationMark = true;
            isFirstQuotationMark = true;
        }
        if (isQuotationMark && !isFirstQuotationMark) {
            this.searchNextQuotationMark = true;
        }
    }

    private String cleanUp(String line) {
        this.buffer.setLength(0);
        boolean lastWasSpace = true;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\t' || c == '\n' || c == ' ') {
                if (!lastWasSpace) {
                    this.buffer.append(' ');
                    lastWasSpace = true;
                }
            } else {
                this.buffer.append(c);
                lastWasSpace = false;
            }
        }
        int len = this.buffer.length();
        if (len > 0 && this.buffer.charAt(len - 1) == ' ') {
            this.buffer.setLength(len - 1);
        }
        String result = this.buffer.toString();
        if (result.startsWith(" ")) {
            result = result.substring(1);
        }
        return result;
    }

    private String cleanUpBounds(String line) {
        int start = 0;
        int end = line.length();
        while (start < end && (line.charAt(start) == ' ' || line.charAt(start) == '\"')) {
            start++;
        }
        if (end > start && (line.charAt(end - 1) == ' ' || line.charAt(end - 1) == '\"')) {
            end--;
        }
        return (start == 0 && end == line.length()) ? line : line.substring(start, end);
    }
}
