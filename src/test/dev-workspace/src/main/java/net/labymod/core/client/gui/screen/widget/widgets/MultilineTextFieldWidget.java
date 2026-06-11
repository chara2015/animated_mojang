package net.labymod.core.client.gui.screen.widget.widgets;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/MultilineTextFieldWidget.class */
@AutoWidget
@Deprecated
public class MultilineTextFieldWidget extends AbstractWidget<Widget> {
    protected static final char PARAGRAPH = 167;
    private static final TextRenderer TEXT_RENDERER = Laby.labyAPI().renderPipeline().textRenderer();
    private static final int TEXT_FOREGROUND_COLOR = ColorFormat.ARGB32.pack(255, 0, 0, 255);
    private static final int TEXT_FOREGROUND_ERROR_COLOR = ColorFormat.ARGB32.pack(255, 175, 175, 255);
    protected String[] lines;
    protected int cursorX;
    protected int cursorY;
    protected int maxWidth;
    protected String text = "Sweet little bumblebee\nI know what you want from me\nDoo bi doo bi doo da da\nDoo bi doo bi doo da da";
    private final LssProperty<Integer> textColor = new LssProperty<>(Integer.valueOf(Color.WHITE.get()));

    public MultilineTextFieldWidget() {
        this.lazy = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        ScreenCanvas canvas = context.canvas();
        float lineHeight = canvas.getLineHeight();
        Bounds bounds = bounds();
        float startX = bounds.getX();
        float y = bounds.getY();
        int textColor = this.textColor.get().intValue();
        for (int i = 0; i < this.lines.length; i++) {
            try {
                String line = this.lines[i];
                if (line.charAt(line.length() - 1) == '\n') {
                    line = line.substring(0, line.length() - 1);
                }
                canvas.submitText(line, startX, y, textColor, 0, 1);
                if (i == this.cursorY) {
                    String substring = line.substring(0, Math.max(0, Math.min(line.length(), this.cursorX)));
                    canvas.submitRelativeRect((startX + canvas.getTextWidth(substring)) - 1.0f, y, 1.0f, lineHeight, TEXT_FOREGROUND_COLOR);
                }
            } catch (Exception e) {
                canvas.submitRelativeRect(startX, y, bounds.getWidth(), lineHeight, TEXT_FOREGROUND_ERROR_COLOR);
                e.printStackTrace();
            }
            y += lineHeight;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateBounds() {
        super.updateBounds();
        recalculateLines();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        int cursorIndex;
        if (!isFocused()) {
            return false;
        }
        try {
            cursorIndex = getCursorIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (key == Key.ENTER) {
            insert("\n");
            this.cursorX = 0;
            this.cursorY++;
            return true;
        }
        if (key == Key.ARROW_LEFT && cursorIndex > 0) {
            if (this.cursorX > 0) {
                this.cursorX--;
                return true;
            }
            if (this.cursorY > 0) {
                this.cursorY--;
                this.cursorX = this.lines[this.cursorY].length();
                return true;
            }
            return true;
        }
        if (key == Key.ARROW_RIGHT && cursorIndex < this.text.length()) {
            int maxLineIndex = this.lines[this.cursorY].length();
            if (this.cursorX < maxLineIndex) {
                this.cursorX++;
                return true;
            }
            if (this.cursorY < this.lines.length) {
                this.cursorY++;
                this.cursorX = 0;
                return true;
            }
            return true;
        }
        if (key == Key.ARROW_UP && this.cursorY > 0) {
            this.cursorY--;
            this.cursorX = Math.min(this.cursorX, this.lines[this.cursorY].length() - 1);
            return true;
        }
        if (key == Key.ARROW_DOWN && this.cursorY < this.lines.length) {
            this.cursorY++;
            this.cursorX = Math.min(this.cursorX, this.lines[this.cursorY].length() - 1);
            return true;
        }
        if (key == Key.BACK) {
            delete(-1);
            return true;
        }
        if (key == Key.DELETE) {
            delete(1);
            return true;
        }
        return super.keyPressed(key, type);
    }

    private int getCursorIndex() {
        int cursorIndex = 0;
        int i = 0;
        while (true) {
            if (i >= this.lines.length) {
                break;
            }
            String line = this.lines[i];
            if (i < this.cursorY) {
                cursorIndex += line.length();
            } else if (i == this.cursorY) {
                cursorIndex += this.cursorX;
                break;
            }
            i++;
        }
        return cursorIndex;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        if (!isFocused() || character == 167) {
            return false;
        }
        try {
            insert(Character.toString(character));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public void delete(int offset) {
        int cursorIndex = getCursorIndex();
        if (cursorIndex < 0 || cursorIndex > this.text.length()) {
            return;
        }
        int beforeIndex = offset < 0 ? cursorIndex + offset : cursorIndex;
        int afterIndex = offset < 0 ? cursorIndex : cursorIndex + offset;
        String textBuilder = this.text.substring(0, beforeIndex) + this.text.substring(afterIndex);
        this.text = textBuilder;
        if (offset < 0) {
            int cursorX = this.cursorX + offset;
            if (cursorX < 0 && this.cursorY > 0) {
                this.cursorY--;
                this.cursorX = this.lines[this.cursorY].length() - 1;
            } else {
                this.cursorX = cursorX;
            }
            this.cursorX += offset;
        }
        recalculateLines();
    }

    public void insert(String string) {
        insertAt(string, this.cursorX, this.cursorY);
    }

    public void insertAt(String string, int x, int y) {
        int length = this.text.length();
        if (length == 0) {
            this.text = string;
            return;
        }
        StringBuilder textBuilder = new StringBuilder();
        int cursorIndex = getCursorIndex();
        textBuilder.append((CharSequence) this.text, 0, cursorIndex);
        textBuilder.append(string);
        textBuilder.append((CharSequence) this.text, cursorIndex, length);
        this.text = textBuilder.toString();
        this.cursorX++;
        recalculateLines();
    }

    protected void recalculateLines() {
        Bounds bounds = bounds();
        this.maxWidth = (int) bounds.getWidth();
        List<String> lines = new ArrayList<>();
        String[] artificialLines = this.text.split("\\n");
        StringBuilder line = new StringBuilder();
        int lastLineIndex = artificialLines.length - 1;
        for (int l = 0; l <= lastLineIndex; l++) {
            String artificialLine = artificialLines[l];
            String[] words = artificialLine.split(" ");
            int lastWordIndex = words.length - 1;
            for (int w = 0; w <= lastWordIndex; w++) {
                String word = words[w];
                if (TEXT_RENDERER.getWidth(String.valueOf(line) + word) > this.maxWidth) {
                    lines.add(line.toString());
                    if (w != lastWordIndex) {
                        line = new StringBuilder();
                    }
                }
                line.append(word);
                if (w != lastWordIndex) {
                    line.append(" ");
                }
            }
            if (line.length() > 0) {
                String lineString = line.toString();
                if (l != lastLineIndex) {
                    lineString = lineString + "\n";
                    line = new StringBuilder();
                }
                lines.add(lineString);
            }
        }
        this.lines = (String[]) lines.toArray(new String[0]);
    }

    public LssProperty<Integer> textColor() {
        return this.textColor;
    }
}
