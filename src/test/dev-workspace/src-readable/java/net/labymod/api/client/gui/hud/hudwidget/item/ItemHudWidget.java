package net.labymod.api.client.gui.hud.hudwidget.item;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.world.item.ItemStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/item/ItemHudWidget.class */
public abstract class ItemHudWidget<T extends HudWidgetConfig> extends HudWidget<T> {
    public static final int ITEM_SIZE = 16;
    private static final String REASON_NAME_WIDTH = "item_name_width";
    private Icon placeHolderIcon;
    private ItemStack itemStack;
    private Component itemName;
    private Component editorItemName;
    private RenderableComponent renderableItemName;
    private RenderableComponent renderableEditorItemName;

    protected ItemHudWidget(String id) {
        this(id, TextHudWidgetConfig.class);
    }

    protected ItemHudWidget(String id, Class<T> configClass) {
        super(id, configClass);
        bindCategory(HudWidgetCategory.ITEM);
        bindDropzones(NamedHudWidgetDropzones.ITEMS);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onUpdate() {
        super.onUpdate();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        float f;
        ScreenCanvas renderState = context.canvas();
        if (this.itemStack != null && !this.itemStack.isAir() && !isEditorContext) {
            Laby.references().itemStackVisualizer().submitItem(context, this.itemStack, 0, 0, decorate());
        } else {
            if (this.placeHolderIcon == null) {
                this.placeHolderIcon = createPlaceholderIcon();
            }
            renderState.submitIcon(this.placeHolderIcon, 0.0f, 0.0f, 16.0f, 16.0f);
            if (decorate()) {
                renderState.submitAbsoluteRect(1.0f, 15.0f, 15.0f, 1.0f, -1);
            }
        }
        RenderableComponent renderableComponent = getRenderableItemName(isEditorContext);
        if (renderableComponent != null) {
            float fontHeight = Laby.references().textRendererProvider().getRenderer().getLineHeight();
            if (this.anchor.isRight()) {
                f = (-renderableComponent.getWidth()) - 2.0f;
            } else {
                f = 18.0f;
            }
            float x = f;
            renderState.submitRenderableComponent(renderableComponent, x, (8.0f - (fontHeight / 2.0f)) + 1.5f, -1, 1);
        }
    }

    public RenderableComponent getRenderableItemName(boolean isEditorContext) {
        if (isEditorContext) {
            return this.renderableEditorItemName;
        }
        return this.renderableItemName;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void updateSize(HudWidgetWidget widget, boolean editorContext, HudSize size) {
        size.set(16, 16);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return (this.itemStack == null || this.itemStack.isAir()) ? false : true;
    }

    public void updateItemStack(ItemStack itemStack, boolean isEditorContext) {
        if (!Objects.equals(this.itemStack, itemStack)) {
            this.itemStack = itemStack;
            updateName(isEditorContext);
        }
    }

    public void updateItemName(Component itemName, boolean isEditorContext) {
        if (Objects.equals(isEditorContext ? this.editorItemName : this.itemName, itemName)) {
            return;
        }
        if (isEditorContext) {
            this.editorItemName = itemName;
        } else {
            this.itemName = itemName;
        }
        updateName(isEditorContext);
    }

    private void updateName(boolean isEditorContext) {
        RenderableComponent renderableComponentOf;
        RenderableComponent renderableComponentOf2;
        if (isEditorContext) {
            if (this.editorItemName == null) {
                renderableComponentOf2 = null;
            } else {
                renderableComponentOf2 = RenderableComponent.of(this.editorItemName);
            }
            this.renderableEditorItemName = renderableComponentOf2;
        } else {
            if (this.itemName == null) {
                renderableComponentOf = null;
            } else {
                renderableComponentOf = RenderableComponent.of(this.itemName);
            }
            this.renderableItemName = renderableComponentOf;
        }
        if (isEnabled()) {
            requestUpdate(REASON_NAME_WIDTH);
        }
    }

    public Icon createPlaceholderIcon() {
        return Textures.SpriteCommon.QUESTION_MARK;
    }

    protected boolean decorate() {
        return false;
    }
}
