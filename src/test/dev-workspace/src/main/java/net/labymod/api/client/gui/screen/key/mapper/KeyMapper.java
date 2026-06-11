package net.labymod.api.client.gui.screen.key.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.logging.Logging;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/mapper/KeyMapper.class */
@Referenceable
public abstract class KeyMapper {
    public static final char DEFAULT_CHAR = 0;
    protected final Map<String, Key> keys = new HashMap();
    protected final Map<String, MouseButton> mouseButtons = new HashMap();
    protected static final Logging DEBUG_LOGGER = Logging.create((Class<?>) KeyMapper.class, SystemProperties.KEYMAPPING.get().booleanValue());

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/mapper/KeyMapper$KeyCodeType.class */
    public enum KeyCodeType {
        CURRENT,
        LWJGL,
        GLFW
    }

    @NotNull
    public abstract String getNameByKey(@NotNull Key key);

    public abstract int getKeyCodeByKey(@NotNull Key key);

    public abstract int getKeyCodeByKey(@NotNull Key key, @NotNull KeyCodeType keyCodeType);

    public abstract int getKeyCodeByMouseButton(@NotNull MouseButton mouseButton);

    public abstract int getKeyCodeByMouseButton(@NotNull MouseButton mouseButton, @NotNull KeyCodeType keyCodeType);

    @Nullable
    public abstract Key getKeyByKeyCode(int i);

    @Nullable
    public abstract Key getKeyByKeyCode(int i, @NotNull KeyCodeType keyCodeType);

    @Nullable
    public abstract MouseButton getMouseButtonByKeyCode(int i);

    @Nullable
    public abstract MouseButton getMouseButtonByKeyCode(int i, @NotNull KeyCodeType keyCodeType);

    public abstract char getChar(@NotNull Key key);

    public abstract boolean isKeyPressed(@NotNull Key key);

    public abstract void initialize();

    public abstract void register(@NotNull Key key, int i, int i2);

    public static void registerKey(@NotNull Key key) {
        Laby.references().keyMapper().register(key);
    }

    @NotNull
    public static String getKeyName(@NotNull Key key) {
        return Laby.references().keyMapper().getNameByKey(key);
    }

    @Nullable
    public static Key getKey(@NotNull String name) {
        return Laby.references().keyMapper().getKeyByName(name);
    }

    @Nullable
    public static Key getKey(int keyCode) {
        return Laby.references().keyMapper().getKeyByKeyCode(keyCode);
    }

    @Nullable
    public static Key getKey(int keyCode, @NotNull KeyCodeType type) {
        return Laby.references().keyMapper().getKeyByKeyCode(keyCode, type);
    }

    @Nullable
    public static MouseButton getMouseButton(int keyCode) {
        return Laby.references().keyMapper().getMouseButtonByKeyCode(keyCode);
    }

    @Nullable
    public static MouseButton getMouseButton(int keyCode, @NotNull KeyCodeType type) {
        return Laby.references().keyMapper().getMouseButtonByKeyCode(keyCode, type);
    }

    public static int getKeyCode(@NotNull Key key) {
        if (key instanceof MouseButton) {
            return Laby.references().keyMapper().getKeyCodeByMouseButton((MouseButton) key);
        }
        return Laby.references().keyMapper().getKeyCodeByKey(key);
    }

    public static int getKeyCode(@NotNull Key key, @NotNull KeyCodeType type) {
        if (key instanceof MouseButton) {
            return Laby.references().keyMapper().getKeyCodeByMouseButton((MouseButton) key, type);
        }
        return Laby.references().keyMapper().getKeyCodeByKey(key, type);
    }

    @Deprecated
    public static int getMouseButton(@NotNull MouseButton mouseButton) {
        return Laby.references().keyMapper().getKeyCodeByMouseButton(mouseButton);
    }

    public static char getCharacter(@NotNull Key key) {
        return Laby.references().keyMapper().getChar(key);
    }

    public static boolean isPressed(@NotNull Key key) {
        return Laby.references().keyMapper().isKeyPressed(key);
    }

    public static InputType getInputType(@NotNull Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (key.isAction() || (KeyHandler.isControlDown() && !KeyHandler.isAltDown())) {
            return InputType.ACTION;
        }
        return InputType.CHARACTER;
    }

    protected KeyMapper() {
    }

    public void register(@NotNull Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        String actualName = key.getActualName();
        if (this.keys.containsKey(actualName) || this.mouseButtons.containsKey(actualName)) {
            throw new IllegalArgumentException("Key " + actualName + " is already registered!");
        }
        if (key instanceof MouseButton) {
            this.mouseButtons.put(actualName, (MouseButton) key);
        } else {
            this.keys.put(actualName, key);
        }
    }

    @Nullable
    public Key getKeyByName(@NotNull String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        Key mouseButton = this.mouseButtons.get(name);
        if (mouseButton != null) {
            return mouseButton;
        }
        return this.keys.get(name);
    }
}
