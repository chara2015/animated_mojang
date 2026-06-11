package net.labymod.v1_12_2.mixins.client.input;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.input.MouseDragEvent;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_12_2.client.VersionedMinecraft;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/input/MixinMinecraftInput.class */
@Mixin({bib.class})
public abstract class MixinMinecraftInput implements VersionedMinecraft {

    @Shadow
    @Nullable
    public blk m;
    private ScreenTreeTopRegistry treeTopRegistry;
    private MouseButton labyMod$currentEventButton;
    private long labyMod$lastMouseEvent;

    @Override // net.labymod.v1_12_2.client.VersionedMinecraft
    public boolean dispatchKeyboardInput(LabyScreenRenderer screenRenderer) {
        Key key;
        char character;
        KeyEvent.State state;
        boolean press = Keyboard.getEventKeyState();
        int eventKey = Keyboard.getEventKey();
        boolean alreadyPressed = press && DefaultKeyMapper.isKeyPressed(eventKey);
        boolean multiChar = false;
        if (!press && eventKey == 0 && Keyboard.getEventCharacter() != 0) {
            key = Key.NONE;
            DefaultKeyMapper.setLastPressed(Key.NONE);
            multiChar = true;
        } else if (press) {
            key = DefaultKeyMapper.pressKey(eventKey);
            DefaultKeyMapper.setLastPressed(key);
        } else {
            key = DefaultKeyMapper.releaseKey(eventKey);
            DefaultKeyMapper.setLastReleased(key);
        }
        if (key == null) {
            return false;
        }
        InputType inputType = KeyMapper.getInputType(key);
        if (key != Key.NONE) {
            if (alreadyPressed) {
                state = KeyEvent.State.HOLDING;
            } else if (press) {
                state = KeyEvent.State.PRESS;
            } else {
                state = KeyEvent.State.UNPRESSED;
            }
            KeyEvent keyEvent = (KeyEvent) Laby.fireEvent(new KeyEvent(state, key));
            if (keyEvent.isCancelled()) {
                return true;
            }
        }
        boolean result = false;
        if (key != Key.ESCAPE && key != Key.NONE) {
            for (IngameOverlayActivity activity : labyMod$getActivities()) {
                if (activity.isAcceptingInput()) {
                    if (press) {
                        labyMod$treeTopRegistry().keyPressed(ScreenPhase.PRE, activity, key, inputType);
                        if (activity.keyPressed(key, inputType)) {
                            result = true;
                        }
                        labyMod$treeTopRegistry().keyPressed(ScreenPhase.POST, activity, key, inputType);
                    } else {
                        labyMod$treeTopRegistry().keyReleased(ScreenPhase.PRE, activity, key, inputType);
                        if (activity.keyReleased(key, inputType)) {
                            result = true;
                        }
                        labyMod$treeTopRegistry().keyReleased(ScreenPhase.POST, activity, key, inputType);
                    }
                }
            }
            if (result) {
                return true;
            }
        }
        if (screenRenderer != null && key != Key.NONE) {
            if (press) {
                result = screenRenderer.wrappedKeyPressed(key, inputType);
            } else {
                result = screenRenderer.wrappedKeyReleased(key, inputType);
            }
        }
        if (result) {
            return true;
        }
        if ((!press && !multiChar) || inputType != InputType.CHARACTER || (character = Keyboard.getEventCharacter()) == 0) {
            return false;
        }
        if (((CharacterTypedEvent) Laby.fireEvent(new CharacterTypedEvent(key, character))).isCancelled()) {
            return true;
        }
        for (IngameOverlayActivity activity2 : labyMod$getActivities()) {
            if (activity2.isAcceptingInput()) {
                labyMod$treeTopRegistry().charTyped(ScreenPhase.PRE, activity2, key, character);
                if (activity2.charTyped(key, character)) {
                    result = true;
                }
                labyMod$treeTopRegistry().charTyped(ScreenPhase.POST, activity2, key, character);
            }
        }
        if (result) {
            return true;
        }
        if (screenRenderer != null) {
            result = screenRenderer.wrappedCharTyped(key, character);
        }
        return result;
    }

    @Override // net.labymod.v1_12_2.client.VersionedMinecraft
    public boolean dispatchMouseInput() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        MutableMouse mouse = minecraft.absoluteMouse().mutable();
        Window window = minecraft.minecraftWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();
        double widthFactor = ((double) width) / ((double) window.getRawWidth());
        double heightFactor = ((double) height) / ((double) window.getRawHeight());
        int rawMouseX = Mouse.getEventX();
        int rawMouseY = Mouse.getEventY();
        double mouseX = ((double) rawMouseX) * widthFactor;
        double mouseY = ((double) height) - (((double) rawMouseY) * heightFactor);
        return mouse.set(mouseX, mouseY, () -> {
            List<IngameOverlayActivity> activities = null;
            int scrollDelta = Mouse.getEventDWheel();
            if (scrollDelta != 0) {
                activities = labyMod$getActivities();
                if (labyMod$handleMouseScroll(scrollDelta, mouse, activities)) {
                    return true;
                }
            }
            int button = Mouse.getEventButton();
            KeyEvent.State state = Mouse.getEventButtonState() ? KeyEvent.State.PRESS : KeyEvent.State.UNPRESSED;
            MouseButton mouseButton = null;
            if (button == -1) {
                if (this.labyMod$currentEventButton == null) {
                    return false;
                }
                state = KeyEvent.State.HOLDING;
                mouseButton = this.labyMod$currentEventButton;
            }
            if (mouseButton == null) {
                if (state != KeyEvent.State.UNPRESSED || (this.m instanceof LabyScreenRenderer)) {
                    mouseButton = DefaultKeyMapper.pressMouse(button);
                } else {
                    mouseButton = DefaultKeyMapper.releaseMouse(button);
                }
                this.labyMod$currentEventButton = mouseButton;
            }
            if (mouseButton == null) {
                return false;
            }
            if (activities == null) {
                activities = labyMod$getActivities();
            }
            if (state == KeyEvent.State.PRESS) {
                return labyMod$handleMousePressed(mouse, mouseButton, activities);
            }
            if (state == KeyEvent.State.UNPRESSED) {
                return labyMod$handleMouseReleased(mouse, mouseButton, activities);
            }
            if (this.labyMod$lastMouseEvent <= 0) {
                this.labyMod$currentEventButton = null;
                return false;
            }
            return false;
        });
    }

    @Override // net.labymod.v1_12_2.client.VersionedMinecraft
    public boolean handleMouseDragged(MutableMouse mouse, MouseButton mouseButton, double deltaX, double deltaY) {
        return labyMod$handleMouseDragged(mouse, mouseButton, labyMod$getActivities(), deltaX, deltaY);
    }

    private boolean labyMod$handleMouseDragged(MutableMouse mouse, MouseButton mouseButton, List<IngameOverlayActivity> activities, double deltaX, double deltaY) {
        if (((MouseDragEvent) Laby.fireEvent(new MouseDragEvent(mouse, mouseButton, deltaX, deltaY))).isCancelled()) {
            return true;
        }
        boolean result = false;
        for (IngameOverlayActivity activity : activities) {
            if (activity.isAcceptingInput()) {
                labyMod$treeTopRegistry().mouseDragged(ScreenPhase.PRE, activity, mouse, mouseButton, deltaX, deltaY);
                if (activity.mouseDragged(mouse, mouseButton, deltaX, deltaY)) {
                    result = true;
                }
                labyMod$treeTopRegistry().mouseDragged(ScreenPhase.POST, activity, mouse, mouseButton, deltaX, deltaY);
            }
        }
        return result;
    }

    private boolean labyMod$handleMouseReleased(MutableMouse mouse, MouseButton mouseButton, List<IngameOverlayActivity> activities) {
        this.labyMod$currentEventButton = null;
        if (labyMod$fireMouseButtonEvent(MouseButtonEvent.Action.RELEASE, mouse, mouseButton)) {
            return true;
        }
        boolean result = false;
        for (IngameOverlayActivity activity : activities) {
            if (activity.isAcceptingInput()) {
                labyMod$treeTopRegistry().mouseReleased(ScreenPhase.PRE, activity, mouse, mouseButton);
                if (activity.mouseReleased(mouse, mouseButton)) {
                    result = true;
                }
                labyMod$treeTopRegistry().mouseReleased(ScreenPhase.POST, activity, mouse, mouseButton);
            }
        }
        return result;
    }

    private boolean labyMod$handleMousePressed(MutableMouse mouse, MouseButton mouseButton, List<IngameOverlayActivity> activities) {
        this.labyMod$lastMouseEvent = bib.I();
        if (labyMod$fireMouseButtonEvent(MouseButtonEvent.Action.CLICK, mouse, mouseButton)) {
            return true;
        }
        boolean result = false;
        for (IngameOverlayActivity activity : activities) {
            if (activity.isAcceptingInput()) {
                labyMod$treeTopRegistry().mouseClicked(ScreenPhase.PRE, activity, mouse, mouseButton);
                if (activity.mouseClicked(mouse, mouseButton)) {
                    result = true;
                }
                labyMod$treeTopRegistry().mouseClicked(ScreenPhase.POST, activity, mouse, mouseButton);
            }
        }
        return result;
    }

    private boolean labyMod$handleMouseScroll(int scrollDelta, MutableMouse mouse, List<IngameOverlayActivity> activities) {
        int scrollDelta2 = scrollDelta < 0 ? -1 : 1;
        if (((MouseScrollEvent) Laby.fireEvent(new MouseScrollEvent(mouse, scrollDelta2))).isCancelled()) {
            return true;
        }
        boolean result = false;
        for (Activity activity : activities) {
            if (!result) {
                labyMod$treeTopRegistry().mouseScrolled(ScreenPhase.PRE, activity, mouse, scrollDelta2);
                if (activity.mouseScrolled(mouse, scrollDelta2)) {
                    result = true;
                }
                labyMod$treeTopRegistry().mouseScrolled(ScreenPhase.POST, activity, mouse, scrollDelta2);
            }
        }
        return result;
    }

    private boolean labyMod$fireMouseButtonEvent(MouseButtonEvent.Action action, MutableMouse mouse, MouseButton mouseButton) {
        return ((MouseButtonEvent) Laby.fireEvent(new MouseButtonEvent(action, mouse, mouseButton))).isCancelled();
    }

    private List<IngameOverlayActivity> labyMod$getActivities() {
        return Laby.labyAPI().ingameOverlay().getActivities();
    }

    private ScreenTreeTopRegistry labyMod$treeTopRegistry() {
        return Laby.references().screenTreeTopRegistry();
    }

    @Override // net.labymod.v1_12_2.client.VersionedMinecraft
    public MouseButton getCurrentEventButton() {
        return this.labyMod$currentEventButton;
    }
}
