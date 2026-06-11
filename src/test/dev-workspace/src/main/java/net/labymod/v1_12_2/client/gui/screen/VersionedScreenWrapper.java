package net.labymod.v1_12_2.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/VersionedScreenWrapper.class */
public class VersionedScreenWrapper implements ScreenWrapper {
    private final blk screen;
    private Metadata metadata;
    private boolean initialized;
    private boolean resizing;

    public VersionedScreenWrapper(blk screen) {
        if (screen == null) {
            throw new IllegalArgumentException("screen cannot be null");
        }
        this.screen = screen;
        this.metadata = Metadata.create();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (parent != null) {
            resizeScreen((int) parent.bounds().getWidth(BoundsType.INNER), (int) parent.bounds().getHeight(BoundsType.INNER));
        } else {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            resizeScreen(window.getScaledWidth(), window.getScaledHeight());
        }
        this.initialized = true;
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).setParentScreen(this);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public boolean renderBackground(ScreenContext context) {
        if (this.screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) this.screen).screen().renderBackground(context);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void renderOverlay(ScreenContext context) {
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).screen().renderOverlay(context);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void renderHoverComponent(ScreenContext context) {
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).screen().renderHoverComponent(context);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void resize(int width, int height) {
        resizeScreen(width, height);
        this.initialized = true;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        MutableMouse mouse = context.mouse();
        this.screen.a(mouse.getX(), mouse.getY(), context.getTickDelta());
    }

    private void resizeScreen(int width, int height) {
        if (this.resizing) {
            return;
        }
        this.resizing = true;
        this.screen.resize(width, height);
        this.resizing = false;
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void tick() {
        this.screen.e();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        if (this.initialized) {
            this.screen.m();
        }
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).setParentScreen(null);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance, net.labymod.api.client.gui.screen.ParentScreen
    @NotNull
    public Object mostInnerScreen() {
        if (this.screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) this.screen).screen().mostInnerScreen();
        }
        return this.screen;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    @NotNull
    public ScreenInstance mostInnerScreenInstance() {
        if (this.screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) this.screen).screen().mostInnerScreenInstance();
        }
        return this;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        return this.screen.wrappedKeyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return this.screen.wrappedKeyReleased(key, type);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return this.screen.wrappedCharTyped(key, character);
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        if (this.screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) this.screen).handlePreeditText(text);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).screen().doScreenAction(action, parameters);
        }
        if (action.equals("handleMouseInput")) {
            this.screen.setHandleMouseInput(false);
            this.screen.k();
            this.screen.setHandleMouseInput(true);
        }
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return this.screen.wrappedMouseReleased(mouse, mouseButton.getId());
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return this.screen.wrappedMouseClicked(mouse, mouseButton.getId());
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        this.screen.mouseScrolled(mouse, scrollDelta);
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return this.screen.wrappedMouseClickMove(mouse, button.getId(), deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return this.screen.handleDroppedFiles(mouse, paths);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public boolean isPauseScreen() {
        return this.screen.d();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public Object getVersionedScreen() {
        return this.screen;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public boolean isActivity() {
        return (this.screen instanceof LabyScreenRenderer) && (((LabyScreenRenderer) this.screen).screen() instanceof Activity);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public Activity asActivity() {
        return (Activity) ((LabyScreenRenderer) this.screen).screen();
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }
}
