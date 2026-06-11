package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget.class */
@AutoWidget
@SettingWidget
@Link(value = "widget/tag-input.lss", priority = -64)
public class TagInputWidget extends WrappedListWidget<Widget> {
    private final TagCollection tags;
    private TagTextFieldWidget pendingWidget;
    private final List<TagTextFieldWidget> deletionPending;
    private boolean falsifyHover;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget$TagInputSetting.class */
    @SettingElement(extended = true)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TagInputSetting {
    }

    public TagInputWidget(TagCollection tagCollection) {
        this.falsifyHover = false;
        this.tags = tagCollection;
        this.deletionPending = new ArrayList();
    }

    public TagInputWidget() {
        this(new TagCollection());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        for (TagCollection.Tag tag : this.tags.getTags()) {
            addChild(createInput(tag));
        }
        this.pendingWidget = new TagTextFieldWidget(this.tags.unsavedTag, true);
        this.pendingWidget.addId("tag-text-field-widget", "unsubmitted");
        this.pendingWidget.submitHandler(value -> {
            if (value.trim().isEmpty()) {
                return;
            }
            TagCollection.Tag tag2 = this.tags.add(value);
            this.tags.unsavedTag.setContent("");
            this.pendingWidget.updateInput = false;
            this.pendingWidget.setText("");
            this.pendingWidget.updateInput = true;
            int index = -1;
            int i = 0;
            while (true) {
                if (i >= this.children.size()) {
                    break;
                }
                Widget child = (Widget) this.children.get(i);
                if (child != this.pendingWidget) {
                    i++;
                } else {
                    index = i;
                    break;
                }
            }
            addChildInitialized(index, createInput(tag2));
        });
        addChild(this.pendingWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        handlePending();
        super.renderWidget(context);
    }

    private void handlePending() {
        TagTextFieldWidget tagTextFieldWidget;
        if (this.deletionPending == null || this.deletionPending.isEmpty()) {
            tagTextFieldWidget = null;
        } else {
            tagTextFieldWidget = this.deletionPending.get(0);
        }
        TagTextFieldWidget widgetToDelete = tagTextFieldWidget;
        if (widgetToDelete != null) {
            this.deletionPending.remove(widgetToDelete);
            if (widgetToDelete == this.pendingWidget) {
                widgetToDelete.submitHandler.accept(widgetToDelete.getText());
            } else {
                widgetToDelete.text = "";
                widgetToDelete.submitHandler.accept("");
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        handlePending();
        super.dispose();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        this.falsifyHover = true;
        boolean handled = super.mouseClicked(mouse, mouseButton);
        this.falsifyHover = false;
        if (handled) {
            return true;
        }
        for (T child : this.children) {
            if (child.bounds().isInRectangle(BoundsType.OUTER, mouse)) {
                return false;
            }
        }
        this.pendingWidget.setFocused(true);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return !this.falsifyHover && super.isHovered();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : super.isHovered();
    }

    private Widget createInput(TagCollection.Tag text) {
        return createInput(text, (tagTextFieldWidget, widget) -> {
            if (tagTextFieldWidget.getText().trim().isEmpty()) {
                this.tags.remove(tagTextFieldWidget.tag);
                if (this.children.removeIf((Predicate<? super T>) child -> {
                    return child == widget;
                })) {
                    update();
                    return;
                }
                return;
            }
            tagTextFieldWidget.setFocused(false);
        });
    }

    private void update() {
        updateChildren();
        updateBounds();
        if (this.parent instanceof ListWidget) {
            ((ListWidget) this.parent).updateBounds();
        }
        if (hasId("extended-input-widget")) {
            Parent parent1 = this.parent.getParent();
            if (parent1 instanceof net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget) {
                ((AbstractWidget) parent1).updateBounds();
            }
        }
    }

    private Widget createInput(TagCollection.Tag tag, BiConsumer<TagTextFieldWidget, Widget> submitHandler) {
        TagTextFieldWidget tagTextFieldWidget = new TagTextFieldWidget(tag, false);
        tagTextFieldWidget.addId("tag-text-field-widget", "submitted");
        FlexibleContentWidget wrapper = new FlexibleContentWidget();
        wrapper.addId("tag-text-field-wrapper", "submitted");
        wrapper.addContent(tagTextFieldWidget);
        DivWidget deleteWrapper = new DivWidget();
        deleteWrapper.addId("tag-text-field-delete-wrapper");
        deleteWrapper.setPressable(() -> {
            tagTextFieldWidget.text = "";
            tagTextFieldWidget.submitHandler.accept("");
        });
        IconWidget iconWidget = new IconWidget(Textures.SpriteCommon.SMALL_X);
        iconWidget.addId("tag-text-field-delete-icon");
        deleteWrapper.addChild(iconWidget);
        wrapper.addContent(deleteWrapper);
        if (submitHandler != null) {
            tagTextFieldWidget.submitHandler(value -> {
                submitHandler.accept(tagTextFieldWidget, wrapper);
            });
        }
        return wrapper;
    }

    public TagCollection tagCollection() {
        return this.tags;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget$TagCollection.class */
    public static class TagCollection {
        private final List<Tag> tags = new ArrayList();
        private final transient Tag unsavedTag = new Tag("");

        public List<Tag> getTags() {
            return this.tags;
        }

        public Tag add(String text) {
            Tag tag = new Tag(text);
            this.tags.add(tag);
            return tag;
        }

        public void add(Tag tag) {
            this.tags.add(tag);
        }

        public boolean remove(Tag tag) {
            return this.tags.remove(tag);
        }

        public boolean isEmpty() {
            return this.tags.isEmpty();
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget$TagCollection$Tag.class */
        public static class Tag {
            private String content;

            public Tag(String content) {
                this.content = content;
            }

            public String getContent() {
                return this.content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<TagInputSetting, TagInputWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public TagInputWidget[] create(Setting setting, TagInputSetting annotation, SettingAccessor accessor) {
            return new TagInputWidget[]{new TagInputWidget((TagCollection) accessor.get())};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{TagCollection.class};
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/TagInputWidget$TagTextFieldWidget.class */
    @AutoWidget
    public class TagTextFieldWidget extends TextFieldWidget {
        private final TagCollection.Tag tag;
        private final boolean unsaved;
        private float width = -1.0f;
        private boolean updateInput = true;

        private TagTextFieldWidget(TagCollection.Tag tag, boolean unsaved) {
            this.tag = tag;
            this.text = tag.getContent();
            this.unsaved = unsaved;
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public void postStyleSheetLoad() {
            super.postStyleSheetLoad();
            if (this.initialized) {
                updateBounds();
                TagInputWidget.this.update();
            }
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public float getContentWidth(BoundsType type) {
            if (this.width == -1.0f) {
                this.width = this.labyAPI.renderPipeline().textRenderer().getWidth(this.text) * fontSize().get().getSize();
            }
            return this.width + bounds().getHorizontalOffset(type);
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
        protected void applyText(String text) {
            super.applyText(text);
            this.tag.setContent(text);
            this.width = -1.0f;
            if (this.initialized) {
                if (this.parent instanceof FlexibleContentWidget) {
                    ((FlexibleContentWidget) this.parent).updateBounds();
                } else {
                    updateBounds();
                }
                if (this.updateInput) {
                    TagInputWidget.this.update();
                }
            }
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
        public boolean keyPressed(Key key, InputType type) {
            if (this.unsaved && isFocused() && key == Key.TAB) {
                this.submitHandler.accept(this.text);
                return true;
            }
            return super.keyPressed(key, type);
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
        public boolean charTyped(Key key, char character) {
            return super.charTyped(key, character);
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public boolean isFocused() {
            if (this.parent instanceof TagInputWidget) {
                return ((TagInputWidget) this.parent).isFocused();
            }
            if (this.parent instanceof FlexibleContentWidget) {
                return ((FlexibleContentWidget) this.parent).isFocused();
            }
            return super.isFocused();
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public void setFocused(boolean focused) {
            if (!focused && this.text.trim().isEmpty() && this != TagInputWidget.this.pendingWidget) {
                TagInputWidget.this.deletionPending.add(this);
            }
            if (!focused && this.unsaved) {
                TagInputWidget.this.deletionPending.add(this);
            }
            if (this.parent instanceof TagInputWidget) {
                ((TagInputWidget) this.parent).setFocused(focused);
                this.labyAPI.minecraft().updateKeyRepeatingMode(true);
            } else if (this.parent instanceof FlexibleContentWidget) {
                ((FlexibleContentWidget) this.parent).setFocused(focused);
                this.labyAPI.minecraft().updateKeyRepeatingMode(true);
            } else {
                super.setFocused(focused);
            }
        }
    }
}
