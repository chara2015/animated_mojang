package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/SelectionWidget.class */
@AutoWidget
public class SelectionWidget extends SimpleWidget {
    private static final boolean GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
    private static final String VANILLA_SELECTION_ADD_LEFT_VARIABLE = "--vanilla-selection-add-left";
    private static final String VANILLA_SELECTION_DOWN_TOP_VARIABLE = "--vanilla-selection-down-top";
    private static final String VANILLA_SELECTION_TRANSFERABLE_SELECT_LEFT_VARIABLE = "--vanilla-selection-transferable-select-left";
    private static final String VANILLA_SELECTION_TRANSFERABLE_UNSELECT_LEFT_VARIABLE = "--vanilla-selection-transferable-unselect-left";
    private final SelectionIcon icon;

    public SelectionWidget(SelectionIcon icon) {
        this.icon = icon;
        addId(icon.getId());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean serverSelectionTextureFeature = isServerSelectionTextureFeature();
        setVariable(VANILLA_SELECTION_ADD_LEFT_VARIABLE, Float.valueOf(serverSelectionTextureFeature ? 0.0f : 16.0f));
        setVariable(VANILLA_SELECTION_DOWN_TOP_VARIABLE, Float.valueOf(serverSelectionTextureFeature ? 0.0f : 16.0f));
        setVariable(VANILLA_SELECTION_TRANSFERABLE_SELECT_LEFT_VARIABLE, Float.valueOf(0.0f));
        setVariable(VANILLA_SELECTION_TRANSFERABLE_UNSELECT_LEFT_VARIABLE, Float.valueOf(16.0f));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        Icon selectionIcon = this.icon.getIcon(isHovered());
        Bounds bounds = bounds();
        if (isVisible()) {
            context.canvas().submitIcon(selectionIcon, bounds);
        }
        super.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean isHovered(float mouseX, float mouseY) {
        boolean serverSelectionTextureFeature = isServerSelectionTextureFeature();
        if (!serverSelectionTextureFeature) {
            return super.isHovered(mouseX, mouseY);
        }
        Bounds bounds = bounds();
        MutableRectangle middleBounds = bounds.copy(BoundsType.MIDDLE);
        middleBounds.setPosition(middleBounds.getX() + this.icon.offset.getX(), middleBounds.getY() + this.icon.offset.getY());
        return (!(this.parent instanceof Widget) || ((Widget) this.parent).isHovered()) && middleBounds.isInRectangle(mouseX, mouseY) && (this.inOverlay || !this.labyAPI.screenOverlayHandler().isOverlayHovered()) && canHover();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHovered()) {
            callActionListeners();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private boolean isServerSelectionTextureFeature() {
        return GUI_ATLAS && this.labyAPI.themeService().currentTheme().metadata().getBoolean(DefaultThemeVariables.SERVER_SELECTION_TEXTURE_FEATURE);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/SelectionWidget$SelectionIcon.class */
    public enum SelectionIcon {
        ADD("join", Sprite.of(16.0f, 0.0f, 16.0f, 32.0f), 16.0f, 0.0f, Type.SERVER_LIST),
        UP("move_up", Sprite.of(96.0f, 0.0f, 16.0f, 16.0f), 0.0f, 0.0f, Type.SERVER_LIST),
        DOWN("move_down", Sprite.of(64.0f, 16.0f, 16.0f, 16.0f), 0.0f, 16.0f, Type.SERVER_LIST),
        TRANSFERABLE_SELECT("select", Sprite.of(16.0f, 0.0f, 16.0f, 32.0f), 0.0f, 0.0f, Type.TRANSFERABLE_LIST),
        TRANSFERABLE_UNSELECT("unselect", Sprite.of(32.0f, 0.0f, 16.0f, 32.0f), 0.0f, 0.0f, Type.TRANSFERABLE_LIST),
        TRANSFERABLE_UP("move_up", Sprite.of(96.0f, 0.0f, 16.0f, 16.0f), 16.0f, 0.0f, Type.TRANSFERABLE_LIST),
        TRANSFERABLE_DOWN("move_down", Sprite.of(64.0f, 16.0f, 16.0f, 16.0f), 16.0f, 16.0f, Type.TRANSFERABLE_LIST);

        private static final int HOVERED_Y = 32;
        private final Sprite sprite;
        private final FloatVector2 offset;
        private final ResourceLocation defaultResourceLocation;
        private final ResourceLocation highlightedResourceLocation;
        private final String id = "selection-" + TextFormat.SNAKE_CASE.toDashCase(name());

        SelectionIcon(String path, Sprite sprite, float xOffset, float yOffset, Type type) {
            this.sprite = sprite;
            this.offset = new FloatVector2(xOffset, yOffset);
            this.defaultResourceLocation = ResourceLocation.create(Namespaces.MINECRAFT, type.buildPath(path, false));
            this.highlightedResourceLocation = ResourceLocation.create(Namespaces.MINECRAFT, type.buildPath(path, true));
        }

        public Icon getIcon(boolean hovered) {
            ResourceLocation location = Textures.SpriteServerSelection.TEXTURE;
            if (!location.exists()) {
                location = Laby.labyAPI().minecraft().textures().serverSelectionTexture();
                if (SelectionWidget.GUI_ATLAS) {
                    TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(location);
                    TextureSprite sprite = atlas.findSprite(getResourceLocation(hovered));
                    return Icon.sprite(atlas, sprite, 32.0f, 32.0f);
                }
            }
            int spriteY = MathHelper.ceil(this.sprite.getY() + (hovered ? 32 : 0));
            return Icon.sprite(location, MathHelper.ceil(this.sprite.getX()), MathHelper.ceil(spriteY), MathHelper.ceil(this.sprite.getWidth()), MathHelper.ceil(this.sprite.getHeight()), 256, 256);
        }

        public String getId() {
            return this.id;
        }

        private ResourceLocation getResourceLocation(boolean hovered) {
            return hovered ? this.highlightedResourceLocation : this.defaultResourceLocation;
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/SelectionWidget$SelectionIcon$Type.class */
        enum Type {
            SERVER_LIST("server_list"),
            TRANSFERABLE_LIST("transferable_list");

            private final String prefix;

            Type(String prefix) {
                this.prefix = prefix;
            }

            public String buildPath(String name, boolean highlighted) {
                String path = this.prefix + "/" + name;
                if (highlighted) {
                    path = path + "_highlighted";
                }
                return path;
            }
        }
    }
}
