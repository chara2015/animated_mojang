package net.labymod.v26_1_2.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.component.event.ClickEvent;
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
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v26_1_2.client.gui.GuiGraphicsAccessor;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/screen/VersionedScreenWrapper.class */
public class VersionedScreenWrapper implements ScreenWrapper {
    private final Screen screen;
    private Metadata metadata;
    private boolean initialized;
    private long lastClick;
    private int lastClickMouseButton;

    public VersionedScreenWrapper(Screen screen) {
        if (screen == null) {
            throw new NullPointerException("screen is null");
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
            this.screen.init((int) parent.bounds().getWidth(BoundsType.INNER), (int) parent.bounds().getHeight(BoundsType.INNER));
        } else {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.screen.init(window.getScaledWidth(), window.getScaledHeight());
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
        this.screen.init(width, height);
        this.initialized = true;
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        GuiGraphicsAccessor currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.setPose((Matrix3x2fStack) context.stack().getProvider().getPoseStack());
        MutableMouse mouse = context.mouse();
        this.screen.extractRenderStateWithTooltipAndSubtitles(currentGuiGraphics, mouse.getX(), mouse.getY(), context.getTickDelta());
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void tick() {
        this.screen.tick();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        if (this.initialized) {
            this.screen.removed();
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
        return this.screen.keyPressed(new KeyEvent(key.getId(), DefaultKeyMapper.getGlfwScancode(), DefaultKeyMapper.getGlfwModifiers()));
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return this.screen.keyReleased(new KeyEvent(key.getId(), DefaultKeyMapper.getGlfwScancode(), DefaultKeyMapper.getGlfwModifiers()));
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return this.screen.charTyped(new CharacterEvent(character));
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        return this.screen.preeditUpdated(MinecraftUtil.toMinecraft(text));
    }

    @Override // net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        if (this.screen instanceof LabyScreenRenderer) {
            ((LabyScreenRenderer) this.screen).screen().doScreenAction(action, parameters);
        }
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return this.screen.mouseReleased(new MouseButtonEvent(mouse.getXDouble(), mouse.getYDouble(), new MouseButtonInfo(mouseButton.getId(), DefaultKeyMapper.getGlfwMouseModifiers())));
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        boolean doubleClick = TimeUtil.getCurrentTimeMillis() - this.lastClick < 250 && this.lastClickMouseButton == mouseButton.getId();
        if (this.screen.mouseClicked(new MouseButtonEvent(mouse.getXDouble(), mouse.getYDouble(), new MouseButtonInfo(mouseButton.getId(), DefaultKeyMapper.getGlfwMouseModifiers())), doubleClick)) {
            this.lastClick = TimeUtil.getCurrentTimeMillis();
            this.lastClickMouseButton = mouseButton.getId();
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDeltaY) {
        return this.screen.mouseScrolled(mouse.getXDouble(), mouse.getYDouble(), 0.0d, scrollDeltaY);
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return this.screen.mouseDragged(new MouseButtonEvent(mouse.getXDouble(), mouse.getYDouble(), new MouseButtonInfo(button.getId(), DefaultKeyMapper.getGlfwMouseModifiers())), deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return this.screen.handleDroppedFiles(mouse, paths);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public void handleClickEvent(ClickEvent event) {
        this.screen.handleClickEvent(event);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenWrapper
    public boolean isPauseScreen() {
        return this.screen.isPauseScreen();
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
