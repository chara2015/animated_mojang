package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TextFieldWidget.class */
@AutoWidget
@SettingWidget
public class TextFieldWidget extends SimpleWidget {
    private static final Logging LOGGER = Logging.getLogger();
    private static final ModifyReason MODIFY_REASON = ModifyReason.of("textFieldActionBounds");
    private static final String COOLDOWN_ID = "cooldown";
    protected static final char PARAGRAPH = 167;
    protected Component placeholder;
    protected String placeholderTranslatable;
    protected int cursorIndex;
    protected int viewIndex;
    protected long timeLastActivity;
    protected int markerIndex;
    protected boolean password;
    protected Consumer<String> updateListener;
    protected Consumer<String> submitHandler;
    protected Consumer<GameImage> imagePasteHandler;
    protected boolean handleCharacterKeyPress;
    private boolean blockFirstKeyPress;
    protected IconWidget clearButtonWidget;
    protected IconWidget submitButtonWidget;
    private final LssProperty<WidgetAlignment> textAlignmentX = new LssProperty<>(WidgetAlignment.LEFT);
    private final LssProperty<WidgetAlignment> textAlignmentY = new LssProperty<>(WidgetAlignment.TOP);
    private final LssProperty<Integer> placeHolderColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(NamedTextColor.DARK_GRAY.getValue(), 255)));
    private final LssProperty<Boolean> textShadow = new LssProperty<>(true);
    private final LssProperty<Integer> textColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(NamedTextColor.WHITE.getValue(), 255)));
    private final LssProperty<Integer> markTextColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(2105514, 255)));
    private final LssProperty<Integer> markColor = new LssProperty<>(-1);
    private final LssProperty<Integer> cursorColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(NamedTextColor.WHITE.getValue(), 255)));
    private final LssProperty<Boolean> clearButton = new LssProperty<>(false);
    private final LssProperty<Boolean> submitButton = new LssProperty<>(false);
    private final LssProperty<FontSize> fontSize = new LssProperty<>(FontSize.predefined(FontSize.PredefinedFontSize.MEDIUM));
    protected String text = "";
    private String previousText = null;
    protected boolean marked = false;
    protected boolean editable = true;
    private int maximalLength = 32767;
    private long cooldownMillis = 0;
    private long currentCooldownEnd = -1;
    private Predicate<String> validator = content -> {
        return true;
    };

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TextFieldWidget$TextFieldSetting.class */
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TextFieldSetting {
        int maxLength() default -1;
    }

    public TextFieldWidget() {
        setHoverCursor(CursorTypes.IBEAM);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String placeholderTranslation;
        super.initialize(parent);
        if (this.placeholderTranslatable != null && (placeholderTranslation = I18n.getTranslation(this.placeholderTranslatable, new Object[0])) != null) {
            this.placeholder = Component.text(placeholderTranslation);
        }
        setAttributeState(AttributeState.ENABLED, this.editable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        if (this.clearButton.get().booleanValue()) {
            this.clearButtonWidget = new IconWidget(Textures.SpriteCommon.SMALL_X);
            this.clearButtonWidget.addId("clear", "action-button");
            this.clearButtonWidget.setPressable(() -> {
                setText("");
            });
            addChildInitialized(this.clearButtonWidget);
        }
        if (this.submitButton.get().booleanValue()) {
            this.submitButtonWidget = new IconWidget(Textures.SpriteCommon.SUBMIT);
            this.submitButtonWidget.addId("submit", "action-button");
            this.submitButtonWidget.setPressable(() -> {
                if (this.submitHandler != null) {
                    submit();
                }
            });
            addChildInitialized(this.submitButtonWidget);
        }
        super.postStyleSheetLoad();
        updateActionButtons();
    }

    private void updateActionButtons() {
        if (this.clearButtonWidget != null) {
            this.clearButtonWidget.setVisible(!this.text.isEmpty() && this.clearButton.get().booleanValue());
        }
        if (this.submitButtonWidget != null) {
            this.submitButtonWidget.setVisible(this.submitButton.get().booleanValue());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        super.updateContentBounds();
        Bounds bounds = bounds();
        float offsetRight = 0.0f;
        if (this.submitButtonWidget != null && this.submitButtonWidget.visible().get().booleanValue()) {
            ReasonableMutableRectangle submitBounds = this.submitButtonWidget.bounds().rectangle(BoundsType.OUTER);
            float width = submitBounds.getWidth();
            submitBounds.setX(bounds.getRight() - width, MODIFY_REASON);
            offsetRight = width;
        }
        if (this.clearButtonWidget != null && this.clearButtonWidget.visible().get().booleanValue()) {
            ReasonableMutableRectangle clearBounds = this.clearButtonWidget.bounds().rectangle(BoundsType.OUTER);
            clearBounds.setX((bounds.getRight() - offsetRight) - clearBounds.getWidth(), MODIFY_REASON);
        }
        super.updateContentBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "TextField";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasTabFocus() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        updateCooldownId();
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        int iMin;
        int iMax;
        if (!isFocused() || !this.editable) {
            return false;
        }
        if (this.blockFirstKeyPress) {
            this.blockFirstKeyPress = false;
            return false;
        }
        boolean handled = false;
        if (this.submitHandler != null && KeyHandler.isEnter(key)) {
            submit();
            handled = true;
        }
        if (KeyHandler.isSelectAll(key)) {
            markAll();
            return true;
        }
        if (KeyHandler.isSelectLeft(key)) {
            markLeft();
            return true;
        }
        if (KeyHandler.isSelectRight(key)) {
            markRight();
            return true;
        }
        if (KeyHandler.isPaste(key)) {
            String data = this.labyAPI.minecraft().getClipboard();
            if (data.isEmpty() && this.imagePasteHandler != null) {
                try {
                    GameImage image = this.labyAPI.operatingSystemAccessor().getImageInClipboard();
                    if (image != null) {
                        this.imagePasteHandler.accept(image);
                        return true;
                    }
                } catch (UnsupportedOperationException exception) {
                    LOGGER.error("Failed to paste image from clipboard", exception);
                }
            }
            insertText(data);
            this.marked = false;
            handled = true;
        }
        if (KeyHandler.isCopy(key) && this.marked) {
            this.labyAPI.minecraft().setClipboard(getMarkedText());
            return true;
        }
        if (KeyHandler.isCut(key) && this.marked) {
            this.labyAPI.minecraft().setClipboard(getMarkedText());
            handled = true;
            insertText("");
            this.marked = false;
        }
        if ((key == Key.BACK || (this.marked && (key == Key.DELETE || type == InputType.CHARACTER))) && this.cursorIndex > 0) {
            deleteText(-1);
            handled = type != InputType.CHARACTER;
        }
        if (key == Key.HOME) {
            setCursorAtStart();
            this.marked = false;
        }
        if (key == Key.END) {
            setCursorAtEnd();
            this.marked = false;
        }
        if (key == Key.ARROW_LEFT) {
            if (KeyHandler.isShiftDown()) {
                handleMarked();
            } else {
                this.marked = false;
            }
            int current = this.marked ? this.cursorIndex : Math.min(this.cursorIndex, this.markerIndex);
            if (KeyHandler.isControlDown()) {
                iMax = wordIndex(-1);
            } else {
                iMax = Math.max(current - 1, 0);
            }
            moveCursorIndex(iMax);
            handled = true;
        }
        if (key == Key.ARROW_RIGHT) {
            if (KeyHandler.isShiftDown()) {
                handleMarked();
            } else {
                this.marked = false;
            }
            int current2 = this.marked ? this.cursorIndex : Math.max(this.cursorIndex, this.markerIndex);
            if (KeyHandler.isControlDown()) {
                iMin = wordIndex(1);
            } else {
                iMin = Math.min(current2 + 1, getMaxCursorIndex());
            }
            moveCursorIndex(iMin);
            handled = true;
        }
        if ((key == Key.DELETE && this.cursorIndex < getMaxCursorIndex()) || (this.marked && key == Key.BACK && this.cursorIndex < 1)) {
            deleteText(1);
            handled = true;
        }
        if ((key == Key.DELETE || key == Key.BACK) && !handled) {
            handled = true;
        }
        if (this.handleCharacterKeyPress && type == InputType.CHARACTER) {
            handled = true;
        }
        if (handled) {
            this.timeLastActivity = TimeUtil.getMillis();
        }
        updateViewIndex();
        return handled;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        if (!isFocused() || !this.editable) {
            return false;
        }
        insertText(Character.toString(character));
        this.marked = false;
        updateViewIndex();
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (!isHovered() || !this.editable || mouseButton != MouseButton.LEFT) {
            if (this.clearButtonWidget != null && this.clearButtonWidget.isHovered()) {
                return this.clearButtonWidget.mouseClicked(mouse, mouseButton);
            }
            return false;
        }
        if (this.text.isEmpty()) {
            moveCursorIndex(0);
            super.mouseClicked(mouse, mouseButton);
            return true;
        }
        boolean shiftPressed = KeyHandler.isShiftDown();
        if (!shiftPressed) {
            this.marked = false;
        }
        mouse.transform(bounds().rectangle(BoundsType.INNER), () -> {
            int index = -1;
            float currentX = 0.0f;
            String visibleText = getVisibleText();
            int i = 0;
            while (true) {
                if (i >= visibleText.length()) {
                    break;
                }
                float charWidth = this.labyAPI.renderPipeline().textRenderer().getWidth(String.valueOf(visibleText.charAt(i))) * this.fontSize.get().getSize();
                if (currentX + (charWidth / 2.0f) > mouse.getX()) {
                    index = i;
                    break;
                } else {
                    currentX += charWidth;
                    i++;
                }
            }
            int maxVisibleIndex = this.text.indexOf(visibleText) + visibleText.length();
            if (index == -1) {
                index = maxVisibleIndex;
            }
            int index2 = index + this.viewIndex;
            if (!this.lastMouseClick.isDoubleClick()) {
                moveCursorIndex(index2);
                return;
            }
            int startIndex = index2 == 0 ? 0 : -1;
            int endIndex = index2 == maxVisibleIndex ? index2 : -1;
            if (startIndex == -1) {
                int i2 = index2 == maxVisibleIndex ? maxVisibleIndex - 1 : index2;
                while (true) {
                    if (i2 < 0) {
                        break;
                    }
                    if (this.text.charAt(i2) != ' ') {
                        i2--;
                    } else {
                        startIndex = i2;
                        break;
                    }
                }
                if (startIndex == -1) {
                    startIndex = 0;
                }
            }
            if (endIndex == -1) {
                int i3 = index2;
                while (true) {
                    if (i3 >= maxVisibleIndex) {
                        break;
                    }
                    if (this.text.charAt(i3) != ' ') {
                        i3++;
                    } else {
                        endIndex = i3;
                        break;
                    }
                }
                if (endIndex == -1) {
                    endIndex = maxVisibleIndex;
                }
            }
            moveCursorIndex(startIndex);
            handleMarked();
            moveCursorIndex(endIndex);
        });
        super.mouseClicked(mouse, mouseButton);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void unfocus() {
        super.unfocus();
        this.marked = false;
    }

    public void insertText(String text) {
        int minPosition = this.marked ? Math.min(this.cursorIndex, this.markerIndex) : this.cursorIndex;
        int maxPosition = this.marked ? Math.max(this.cursorIndex, this.markerIndex) : this.cursorIndex;
        int newPosition = (this.maximalLength - this.text.length()) - (minPosition - maxPosition);
        int contentLength = text.length();
        if (newPosition < contentLength) {
            text = text.substring(0, newPosition);
            contentLength = newPosition;
        }
        String newText = new StringBuilder(this.text).replace(minPosition, maxPosition, text.replace(Character.toString((char) 167), "").replace("\n", "").replace("\r", "")).toString();
        if (this.validator.test(newText)) {
            applyText(newText);
            moveCursorIndex(minPosition + contentLength);
        }
    }

    public void setCursorAtStart() {
        this.cursorIndex = 0;
    }

    public void setCursorAtEnd() {
        this.cursorIndex = this.text.length();
    }

    public float getTextWidthOfRange(int start, int end) {
        if (start >= end) {
            return 0.0f;
        }
        return this.labyAPI.renderPipeline().textRenderer().getWidth(getFormattedText().substring(Math.max(start, 0), Math.min(end, this.text.length()))) * this.fontSize.get().getSize();
    }

    public String getVisibleText() {
        String formattedText = getFormattedText();
        int end = Math.min(this.viewIndex + getVisibleLength(), formattedText.length());
        return formattedText.substring(Math.min(this.viewIndex, end), end);
    }

    public int getVisibleLength() {
        String strSubstring;
        String formattedText = getFormattedText();
        if (formattedText.length() == 0) {
            return 0;
        }
        if (formattedText.length() > this.viewIndex) {
            strSubstring = formattedText.substring(this.viewIndex);
        } else {
            strSubstring = formattedText;
        }
        String text = strSubstring;
        TextRenderer textRenderer = this.labyAPI.renderPipeline().textRenderer();
        for (int i = 0; i < text.length(); i++) {
            if (textRenderer.getWidth(text.substring(0, i)) * this.fontSize.get().getSize() >= getFieldWidth()) {
                return i;
            }
        }
        return text.length();
    }

    protected float getFieldWidth() {
        Bounds bounds = bounds();
        float originalWidth = bounds.getWidth(BoundsType.INNER);
        float width = originalWidth;
        if (this.clearButtonWidget != null && this.clearButtonWidget.visible().get().booleanValue()) {
            width -= this.clearButtonWidget.bounds().getWidth(BoundsType.OUTER);
        }
        if (this.submitButtonWidget != null && this.submitButtonWidget.visible().get().booleanValue()) {
            width -= this.submitButtonWidget.bounds().getWidth(BoundsType.OUTER);
        }
        if (width != originalWidth) {
            width -= bounds.getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT);
        }
        return width;
    }

    public int getMarkerStartIndex() {
        return Math.min(this.cursorIndex, this.markerIndex);
    }

    public int getMarkerEndIndex() {
        return Math.max(this.cursorIndex, this.markerIndex);
    }

    public String getMarkedText() {
        return getText(getMarkerStartIndex(), getMarkerEndIndex());
    }

    public String getText(int start, int end) {
        String formattedText = getFormattedText();
        try {
            return formattedText.substring(start, end);
        } catch (Exception exception) {
            LOGGER.error("Failed to parse text (Start: {}, End: {} <> MarkerStart: {}, MarkerEnd: {}, Text: {})", Integer.valueOf(start), Integer.valueOf(end), Integer.valueOf(getMarkerStartIndex()), Integer.valueOf(getMarkerEndIndex()), getText(), exception);
            int end2 = Math.min(end, formattedText.length());
            return formattedText.substring(Math.min(start, end2), end2);
        }
    }

    public float getOffsetX(int index) {
        return getTextWidthOfRange(this.viewIndex, getVisibleIndex(index));
    }

    public int getVisibleIndex(int index) {
        int maxLength = getVisibleLength();
        if (index - this.viewIndex > maxLength) {
            index = this.viewIndex + maxLength;
        }
        return Math.max(index, this.viewIndex);
    }

    public float getMarkerStartOffsetX() {
        return getOffsetX(getMarkerStartIndex());
    }

    public float getMarkerEndOffsetX() {
        return getOffsetX(getMarkerEndIndex());
    }

    public float getCursorOffsetX() {
        return getTextWidthOfRange(this.viewIndex, this.cursorIndex);
    }

    public boolean isCursorVisible() {
        int maxLength = getVisibleLength();
        return this.cursorIndex - this.viewIndex <= maxLength && this.cursorIndex >= this.viewIndex;
    }

    public void setText(String text, boolean skipValidator) {
        if (Objects.equals(this.text, text)) {
            return;
        }
        if (skipValidator || this.validator.test(text)) {
            if (text.length() > this.maximalLength) {
                applyText(text.substring(0, this.maximalLength));
            } else {
                applyText(text);
            }
            updateViewIndex();
            setCursorAtStart();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        this.labyAPI.minecraft().updateKeyRepeatingMode(true);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        setText(text, false);
    }

    public int getCursorIndex() {
        return this.cursorIndex;
    }

    public void setCursorIndex(int index) {
        this.cursorIndex = MathHelper.clamp(index, 0, this.text.length());
        if (getFormattedText().length() <= getVisibleLength()) {
            this.viewIndex = 0;
        }
    }

    public int getViewIndex() {
        return this.viewIndex;
    }

    public int getMaxCursorIndex() {
        return this.text.length();
    }

    public boolean isCursorAtEnd() {
        return this.cursorIndex == getMaxCursorIndex();
    }

    public long getTimeLastActivity() {
        return this.timeLastActivity;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public Component placeholder() {
        return this.placeholder;
    }

    public TextFieldWidget placeholder(Component placeHolder) {
        this.placeholder = placeHolder;
        return this;
    }

    public boolean isPassword() {
        return this.password;
    }

    public TextFieldWidget password(boolean isPassword) {
        this.password = isPassword;
        return this;
    }

    public boolean hasMarked() {
        return this.marked;
    }

    public boolean shouldDisplayPlaceHolder() {
        return this.text.isEmpty() && !isFocused();
    }

    public TextFieldWidget updateListener(Consumer<String> updateListener) {
        this.updateListener = updateListener;
        return this;
    }

    public TextFieldWidget validator(Predicate<String> validator) {
        this.validator = validator;
        return this;
    }

    public TextFieldWidget submitHandler(Consumer<String> submitHandler) {
        this.submitHandler = submitHandler;
        return this;
    }

    public TextFieldWidget imagePasteHandler(Consumer<GameImage> imagePasteHandler) {
        this.imagePasteHandler = imagePasteHandler;
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return Math.max(75.0f, super.getContentWidth(type));
    }

    public void setEditable(boolean editable) {
        setAttributeState(AttributeState.ENABLED, editable);
        this.editable = editable;
    }

    public int maximalLength() {
        return this.maximalLength;
    }

    public void maximalLength(int maximalLength) {
        this.maximalLength = maximalLength;
    }

    public LssProperty<WidgetAlignment> textAlignmentX() {
        return this.textAlignmentX;
    }

    public LssProperty<WidgetAlignment> textAlignmentY() {
        return this.textAlignmentY;
    }

    public LssProperty<Integer> placeHolderColor() {
        return this.placeHolderColor;
    }

    public LssProperty<Integer> textColor() {
        return this.textColor;
    }

    public LssProperty<Integer> markTextColor() {
        return this.markTextColor;
    }

    public LssProperty<Integer> markColor() {
        return this.markColor;
    }

    public LssProperty<Integer> cursorColor() {
        return this.cursorColor;
    }

    public LssProperty<Boolean> clearButton() {
        return this.clearButton;
    }

    public LssProperty<Boolean> submitButton() {
        return this.submitButton;
    }

    public LssProperty<FontSize> fontSize() {
        return this.fontSize;
    }

    public LssProperty<Boolean> textShadow() {
        return this.textShadow;
    }

    public TextFieldWidget blockFirstKeyPress(boolean shouldBlock) {
        this.blockFirstKeyPress = shouldBlock;
        return this;
    }

    public void setCooldownMillis(long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }

    public long getCurrentCooldownEnd() {
        return this.currentCooldownEnd;
    }

    public boolean isCooledDown() {
        return this.currentCooldownEnd != -1 && this.currentCooldownEnd >= TimeUtil.getMillis();
    }

    private void submit() {
        if (isCooledDown()) {
            return;
        }
        if (this.cooldownMillis > 0) {
            this.currentCooldownEnd = TimeUtil.getMillis() + this.cooldownMillis;
        }
        this.submitHandler.accept(this.text);
    }

    private void updateCooldownId() {
        if (this.cooldownMillis <= 0) {
            return;
        }
        boolean hasId = hasId(COOLDOWN_ID);
        boolean cooledDown = isCooledDown();
        if (cooledDown && !hasId) {
            addId(COOLDOWN_ID);
        } else if (!cooledDown && hasId) {
            removeId(COOLDOWN_ID);
        }
    }

    private void updateViewIndex() {
        int viewStartIndex = this.viewIndex;
        int viewEndIndex = viewStartIndex + getVisibleLength();
        if (this.viewIndex > 0 && this.text.length() <= getVisibleLength()) {
            this.viewIndex = 0;
        }
        if (this.cursorIndex < viewStartIndex && this.viewIndex > 0) {
            this.viewIndex = Math.max(this.viewIndex - getVisibleLength(), 0);
        }
        if (this.cursorIndex > viewEndIndex && this.viewIndex < (this.text.length() - getVisibleLength()) + 1) {
            this.viewIndex = (this.text.length() - getVisibleLength()) + 1;
        }
        if (this.updateListener != null && !Objects.equals(this.previousText, this.text)) {
            this.updateListener.accept(this.text);
            this.previousText = this.text;
        }
        callActionListeners();
        updateActionButtons();
    }

    protected String getFormattedText() {
        return this.password ? encrypt(this.text) : this.text;
    }

    private String encrypt(String text) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            string.append("*");
        }
        return string.toString();
    }

    private void handleMarked() {
        if (this.marked) {
            return;
        }
        this.marked = true;
        this.markerIndex = this.cursorIndex;
    }

    private void markAll() {
        this.marked = true;
        this.markerIndex = 0;
        this.viewIndex = 0;
        setCursorAtEnd();
    }

    private void markLeft() {
        this.marked = true;
        this.markerIndex = 0;
    }

    private void markRight() {
        this.marked = true;
        this.markerIndex = this.text.length();
    }

    private void deleteText(int index) {
        if (KeyHandler.isControlDown()) {
            deleteWords(index);
        } else {
            deleteCharacters(index);
        }
    }

    private void deleteWords(int index) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.markerIndex != this.cursorIndex) {
            insertText("");
        } else {
            deleteCharacters(wordIndex(index) - this.cursorIndex);
        }
    }

    private int wordIndex(int index) {
        return wordIndex(index, this.cursorIndex);
    }

    private int wordIndex(int index, int cursorIndex) {
        int cursor = cursorIndex;
        boolean lowerIndex = index < 0;
        int absIndex = Math.abs(index);
        for (int i = 0; i < absIndex; i++) {
            if (!lowerIndex) {
                int contentLength = this.text.length();
                cursor = this.text.indexOf(32, cursor);
                if (cursor == -1) {
                    cursor = contentLength;
                } else {
                    while (cursor < contentLength && this.text.charAt(cursor) == ' ') {
                        cursor++;
                    }
                }
            } else {
                while (cursor > 0 && this.text.charAt(cursor - 1) == ' ') {
                    cursor--;
                }
                while (cursor > 0 && this.text.charAt(cursor - 1) != ' ') {
                    cursor--;
                }
            }
        }
        return cursor;
    }

    public boolean isHandlingCharacterKeyPress() {
        return this.handleCharacterKeyPress;
    }

    public TextFieldWidget handleCharacterKeyPress(boolean handleCharacterKeyPress) {
        this.handleCharacterKeyPress = handleCharacterKeyPress;
        return this;
    }

    public TextFieldWidget handleCharacterKeyPress() {
        return handleCharacterKeyPress(true);
    }

    private void deleteCharacters(int index) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.marked && this.markerIndex != this.cursorIndex) {
            insertText("");
            this.marked = false;
            return;
        }
        int newCursorIndex = this.cursorIndex + index;
        int minIndex = Math.min(newCursorIndex, this.cursorIndex);
        int maxIndex = Math.max(newCursorIndex, this.cursorIndex);
        if (minIndex != maxIndex) {
            String newText = new StringBuilder(this.text).delete(minIndex, maxIndex).toString();
            if (this.validator.test(newText)) {
                applyText(newText);
                moveCursorIndex(minIndex);
            }
        }
    }

    protected void applyText(String text) {
        this.text = text;
    }

    private void moveCursorIndex(int index) {
        setCursorIndex(index);
        if (!this.marked) {
            this.markerIndex = this.cursorIndex;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : super.isHovered();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TextFieldWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<TextFieldSetting, TextFieldWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public TextFieldWidget[] create(Setting setting, TextFieldSetting annotation, SettingAccessor accessor) {
            Object data = accessor.get();
            boolean isFloat = data instanceof Float;
            boolean isInteger = data instanceof Integer;
            TextFieldWidget widget = new TextFieldWidget();
            widget.setText(String.valueOf(data));
            widget.updateListener(value -> {
                if (isFloat) {
                    if (!value.isEmpty() && !value.startsWith(".") && !value.endsWith(".")) {
                        accessor.set(Float.valueOf(Float.parseFloat(value)));
                        return;
                    }
                    return;
                }
                if (isInteger) {
                    if (!value.isEmpty()) {
                        accessor.set(Integer.valueOf(Integer.parseInt(value)));
                        return;
                    }
                    return;
                }
                accessor.set(value);
            });
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            if (annotation.maxLength() >= 0) {
                widget.maximalLength(annotation.maxLength());
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                widget.setText(String.valueOf(newValue));
            });
            return new TextFieldWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{String.class, Float.class, Integer.class};
        }
    }
}
