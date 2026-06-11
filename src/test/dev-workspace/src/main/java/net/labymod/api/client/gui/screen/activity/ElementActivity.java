package net.labymod.api.client.gui.screen.activity;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.element.CompositeElement;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.util.metadata.ActivityMetadata;
import net.labymod.api.client.gui.screen.util.metadata.ActivityMetadataRegistry;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/ElementActivity.class */
public abstract class ElementActivity<D extends CompositeElement<?>> extends LabyScreen {
    protected static final Logging LOGGER = Logging.create((Class<?>) ElementActivity.class);
    private static final ModifyReason ACTIVITY_RESIZE = ModifyReason.of("activityResize");
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final D document;
    private ActivityMetadata activityMetadata;

    protected abstract D createDocument();

    public ElementActivity() {
        ActivityMetadataRegistry.collectActivityMetadata(getClass(), metadata -> {
            this.activityMetadata = metadata;
        });
        this.document = (D) createDocument();
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    protected void postInitialize() {
        this.document.initialize(this);
        this.document.postInitialize();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        this.document.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public boolean renderBackground(ScreenContext context) {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void renderOverlay(ScreenContext context) {
        this.document.renderOverlay(context);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void renderHoverComponent(ScreenContext context) {
        this.document.renderHoverComponent(context);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void resize(int width, int height) {
        this.document.bounds().setSize(width, height, ACTIVITY_RESIZE);
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        this.document.tick();
    }

    public boolean keyPressed(Key key, InputType type) {
        boolean shouldHandle = shouldDocumentHandleKey(key, type);
        if ((key != Key.ESCAPE || shouldHandle) && this.document.keyPressed(key, type)) {
            return true;
        }
        if (key == Key.ESCAPE && !shouldHandle) {
            return displayPreviousScreen();
        }
        return false;
    }

    public boolean shouldDocumentHandleKey(Key key, InputType type) {
        return true;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return this.document.keyReleased(key, type);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return this.document.charTyped(key, character);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        return this.document.handlePreeditText(text);
    }

    public boolean displayPreviousScreen() {
        if (this.previousScreen == null) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        if (this.previousScreen == this) {
            return false;
        }
        displayScreenRaw(this.previousScreen);
        return true;
    }

    public String getName() {
        return this.activityMetadata.simpleName();
    }

    public String getQualifiedName() {
        return this.activityMetadata.qualifiedName();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public void updateKeyRepeatingMode(boolean enabled) {
        this.labyAPI.minecraft().updateKeyRepeatingMode(enabled);
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getParent() {
        return this.parent;
    }

    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return this.document.mouseReleased(mouse, mouseButton);
    }

    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return this.document.mouseClicked(mouse, mouseButton);
    }

    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return this.document.mouseScrolled(mouse, scrollDelta);
    }

    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return this.document.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return this.document.fileDropped(mouse, paths);
    }

    @Override // net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        this.document.doScreenAction(action, parameters);
    }

    public D document() {
        return this.document;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Bounds bounds() {
        return this.document.bounds();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getRoot() {
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance, net.labymod.api.client.gui.screen.ParentScreen
    @NotNull
    public Object mostInnerScreen() {
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    @NotNull
    public ScreenInstance mostInnerScreenInstance() {
        return this;
    }

    public String getIdentifier() {
        return this.activityMetadata.identifier();
    }

    public String getNamespace() {
        return this.activityMetadata.namespace();
    }
}
