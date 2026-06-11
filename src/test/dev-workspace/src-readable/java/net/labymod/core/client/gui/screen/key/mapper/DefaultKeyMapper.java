package net.labymod.core.client.gui.screen.key.mapper;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.lang.StackWalker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.I18n;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/key/mapper/DefaultKeyMapper.class */
public abstract class DefaultKeyMapper extends KeyMapper {
    protected static final Int2ObjectArrayMap<Key> pressedKeys = new Int2ObjectArrayMap<>();
    protected static final Int2ObjectArrayMap<MouseButton> pressedMouseButtons = new Int2ObjectArrayMap<>();
    private static final StackWalker WALKER = StackWalker.getInstance();
    private static final Logging LOGGER = Logging.getLogger();
    private static Key lastPressed;
    private static Key lastReleased;
    private static int glfwScancode;
    private static int glfwModifiers;
    private static int glfwMouseModifiers;
    protected final Map<String, DefaultKey> keyCodes = new HashMap();

    protected abstract DefaultKey createKey(Key key, int i, int i2);

    public static int getGlfwScancode() {
        return glfwScancode;
    }

    public static void setGlfwScancode(int glfwScancode2) {
        glfwScancode = glfwScancode2;
    }

    public static int getGlfwModifiers() {
        return glfwModifiers;
    }

    public static void setGlfwModifiers(int glfwModifiers2) {
        glfwModifiers = glfwModifiers2;
    }

    public static int getGlfwMouseModifiers() {
        return glfwMouseModifiers;
    }

    public static void setGlfwMouseModifiers(int glfwMouseModifiers2) {
        glfwMouseModifiers = glfwMouseModifiers2;
    }

    public static void setLastPressed(Key lastPressed2) {
        lastPressed = lastPressed2;
    }

    public static Key lastPressed() {
        return lastPressed == null ? Key.NONE : lastPressed;
    }

    public static void setLastReleased(Key lastReleased2) {
        lastReleased = lastReleased2;
    }

    public static Key lastReleased() {
        return lastReleased == null ? Key.NONE : lastReleased;
    }

    public static Int2ObjectArrayMap<Key> getPressedKeys() {
        return pressedKeys;
    }

    public static Int2ObjectArrayMap<MouseButton> getPressedMouseButtons() {
        return pressedMouseButtons;
    }

    public static Key pressKey(int keyCode) {
        Key key = (Key) pressedKeys.get(keyCode);
        if (key != null) {
            return key;
        }
        Key key2 = getKey(keyCode);
        pressedKeys.put(keyCode, key2);
        return key2;
    }

    public static MouseButton pressMouse(int keyCode) {
        MouseButton mouseButton = (MouseButton) pressedMouseButtons.get(keyCode);
        if (mouseButton != null) {
            return mouseButton;
        }
        MouseButton mouseButton2 = getMouseButton(keyCode);
        pressedMouseButtons.put(keyCode, mouseButton2);
        return mouseButton2;
    }

    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.containsKey(keyCode);
    }

    public static boolean isMousePressed(int keyCode) {
        return pressedMouseButtons.containsKey(keyCode);
    }

    public static Key releaseKey(int keyCode) {
        Key releasedKey = (Key) pressedKeys.remove(keyCode);
        if (releasedKey != null) {
            return releasedKey;
        }
        Key key = KeyMapper.getKey(keyCode);
        return key;
    }

    public static MouseButton releaseMouse(int keyCode) {
        MouseButton releasedMouse = (MouseButton) pressedMouseButtons.remove(keyCode);
        if (releasedMouse != null) {
            return releasedMouse;
        }
        MouseButton mouseButton = KeyMapper.getMouseButton(keyCode);
        return mouseButton;
    }

    public static boolean isSyntheticCtrl(int glfwKey, int modifiers, int rightAltKey) {
        return glfwKey == 341 && (modifiers & 4) != 0 && isKeyPressed(rightAltKey);
    }

    public static Key resolveGlfwKey(boolean press, int glfwKey, int modifiers, int scancode) {
        Key key;
        setGlfwModifiers(modifiers);
        setGlfwScancode(scancode);
        boolean syntheticCtrl = isSyntheticCtrl(glfwKey, modifiers, 346);
        if (syntheticCtrl) {
            return null;
        }
        if (press) {
            key = pressKey(glfwKey);
            setLastPressed(key);
        } else {
            key = releaseKey(glfwKey);
            setLastReleased(key);
        }
        return key;
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    @NotNull
    public String getNameByKey(@NotNull Key key) {
        char character;
        DefaultKey versionedKey = this.keyCodes.get(key.getActualName());
        if (versionedKey == null) {
            return key.getActualName();
        }
        if (!key.isAction() && (character = versionedKey.getCharacter()) != 0 && character != ' ') {
            if (character == 223) {
                return "ß";
            }
            return Character.toString(character).toUpperCase(Locale.ROOT);
        }
        String translationKey = key.getTranslationKey();
        if (translationKey == null) {
            return key.getActualName();
        }
        String translatedName = I18n.getTranslation(translationKey, new Object[0]);
        if (translatedName != null) {
            return translatedName;
        }
        return key.getActualName();
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public int getKeyCodeByKey(@NotNull Key key) {
        return getKeyCodeByKey(this.keys, key, KeyMapper.KeyCodeType.CURRENT);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public int getKeyCodeByKey(@NotNull Key key, @NotNull KeyMapper.KeyCodeType type) {
        if (type == KeyMapper.KeyCodeType.CURRENT) {
            return getKeyCodeByKey(key);
        }
        return getKeyCodeByKey(this.keys, key, type);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public int getKeyCodeByMouseButton(@NotNull MouseButton mouseButton) {
        return getKeyCodeByKey(this.mouseButtons, mouseButton, KeyMapper.KeyCodeType.CURRENT);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public int getKeyCodeByMouseButton(@NotNull MouseButton mouseButton, @NotNull KeyMapper.KeyCodeType type) {
        if (type == KeyMapper.KeyCodeType.CURRENT) {
            return getKeyCodeByMouseButton(mouseButton);
        }
        return getKeyCodeByKey(this.mouseButtons, mouseButton, KeyMapper.KeyCodeType.CURRENT);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    @Nullable
    public Key getKeyByKeyCode(int keyCode) {
        return getKeyByKeyCode(this.keys, keyCode, KeyMapper.KeyCodeType.CURRENT);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    @Nullable
    public Key getKeyByKeyCode(int keyCode, @NotNull KeyMapper.KeyCodeType type) {
        if (type == KeyMapper.KeyCodeType.CURRENT) {
            return getKeyByKeyCode(keyCode);
        }
        return getKeyByKeyCode(this.keys, keyCode, type);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    @Nullable
    public MouseButton getMouseButtonByKeyCode(int keyCode) {
        return (MouseButton) getKeyByKeyCode(this.mouseButtons, keyCode, KeyMapper.KeyCodeType.CURRENT);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    @Nullable
    public MouseButton getMouseButtonByKeyCode(int keyCode, @NotNull KeyMapper.KeyCodeType type) {
        if (type == KeyMapper.KeyCodeType.CURRENT) {
            return getMouseButtonByKeyCode(keyCode);
        }
        return (MouseButton) getKeyByKeyCode(this.mouseButtons, keyCode, type);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public char getChar(@NotNull Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        DefaultKey versionedKey = this.keyCodes.get(key.getActualName());
        if (versionedKey == null) {
            return (char) 0;
        }
        return versionedKey.getCharacter();
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public boolean isKeyPressed(@NotNull Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (key instanceof MouseButton) {
            return isMousePressed(key.getId());
        }
        return isKeyPressed(key.getId());
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public void register(@NotNull Key key, int glfwKeyCode, int lwjglKeyCode) {
        Objects.requireNonNull(key, "Key cannot be null");
        String actualName = key.getActualName();
        if (this.keyCodes.containsKey(actualName)) {
            throw new IllegalArgumentException("Key " + actualName + " is already registered");
        }
        DefaultKey versionedKey = createKey(key, glfwKeyCode, lwjglKeyCode);
        if (key instanceof MouseButton) {
            this.mouseButtons.putIfAbsent(actualName, (MouseButton) key);
            DEBUG_LOGGER.info("Registered mouse button {} with key code {}", actualName, key);
        } else {
            this.keys.putIfAbsent(actualName, key);
            DEBUG_LOGGER.info("Registered keyboard key {} with key code {}", actualName, Integer.valueOf(glfwKeyCode));
        }
        this.keyCodes.put(actualName, versionedKey);
    }

    @Override // net.labymod.api.client.gui.screen.key.mapper.KeyMapper
    public void initialize() {
        register(Key.NONE, -1, 0);
        register(MouseButton.LEFT, 0, 0);
        register(MouseButton.RIGHT, 1, 1);
        register(MouseButton.MIDDLE, 2, 2);
        register(MouseButton.M4, 3, 3);
        register(MouseButton.M5, 4, 4);
        register(MouseButton.M6, 5, 5);
        register(MouseButton.M7, 6, 6);
        register(MouseButton.M8, 7, 7);
        register(Key.ESCAPE, 256, 1);
        register(Key.F1, 290, 59);
        register(Key.F2, 291, 60);
        register(Key.F3, 292, 61);
        register(Key.F4, 293, 62);
        register(Key.F5, 294, 63);
        register(Key.F6, 295, 64);
        register(Key.F7, 296, 65);
        register(Key.F8, 297, 66);
        register(Key.F9, 298, 67);
        register(Key.F10, 299, 68);
        register(Key.F11, 300, 87);
        register(Key.F12, 301, 88);
        register(Key.F13, 302, 100);
        register(Key.F14, 303, 101);
        register(Key.F15, 304, 102);
        register(Key.F16, 305, 103);
        register(Key.F17, 306, 104);
        register(Key.F18, 307, 105);
        register(Key.F19, 308, 113);
        register(Key.F20, 309, Integer.MIN_VALUE);
        register(Key.F21, 310, Integer.MIN_VALUE);
        register(Key.F22, 311, Integer.MIN_VALUE);
        register(Key.F23, 312, Integer.MIN_VALUE);
        register(Key.F24, 313, Integer.MIN_VALUE);
        register(Key.F25, 314, Integer.MIN_VALUE);
        register(Key.GRAVE, 96, 41);
        register(Key.NUM1, 49, 2);
        register(Key.NUM2, 50, 3);
        register(Key.NUM3, 51, 4);
        register(Key.NUM4, 52, 5);
        register(Key.NUM5, 53, 6);
        register(Key.NUM6, 54, 7);
        register(Key.NUM7, 55, 8);
        register(Key.NUM8, 56, 9);
        register(Key.NUM9, 57, 10);
        register(Key.NUM0, 48, 11);
        register(Key.MINUS, 45, 12);
        register(Key.EQUAL, 61, 13);
        register(Key.BACK, GlConst.GL_MULT, 14);
        register(Key.TAB, GlConst.GL_RETURN, 15);
        register(Key.Q, 81, 16);
        register(Key.W, 87, 17);
        register(Key.E, 69, 18);
        register(Key.R, 82, 19);
        register(Key.T, 84, 20);
        register(Key.Y, 89, 21);
        register(Key.U, 85, 22);
        register(Key.I, 73, 23);
        register(Key.O, 79, 24);
        register(Key.P, 80, 25);
        register(Key.L_BRACKET, 91, 26);
        register(Key.R_BRACKET, 93, 27);
        register(Key.BACKSLASH, 92, 43);
        register(Key.CAPS_LOCK, 280, 58);
        register(Key.A, 65, 30);
        register(Key.S, 83, 31);
        register(Key.D, 68, 32);
        register(Key.F, 70, 33);
        register(Key.G, 71, 34);
        register(Key.H, 72, 35);
        register(Key.J, 74, 36);
        register(Key.K, 75, 37);
        register(Key.L, 76, 38);
        register(Key.SEMICOLON, 59, 39);
        register(Key.APOSTROPHE, 39, 40);
        register(Key.ENTER, GlConst.GL_LOAD, 28);
        register(Key.L_SHIFT, 340, 42);
        register(Key.Z, 90, 44);
        register(Key.X, 88, 45);
        register(Key.C, 67, 46);
        register(Key.V, 86, 47);
        register(Key.B, 66, 48);
        register(Key.N, 78, 49);
        register(Key.M, 77, 50);
        register(Key.COMMA, 44, 51);
        register(Key.PERIOD, 46, 52);
        register(Key.SLASH, 47, 53);
        register(Key.R_SHIFT, 344, 54);
        register(Key.L_CONTROL, 341, 29);
        register(Key.L_WIN, 343, 219);
        register(Key.L_ALT, 342, 56);
        register(Key.SPACE, 32, 57);
        register(Key.R_ALT, 346, 184);
        register(Key.R_WIN, 347, 220);
        register(Key.MENU, 348, 221);
        register(Key.R_CONTROL, 345, 157);
        register(Key.ARROW_LEFT, 263, 203);
        register(Key.ARROW_DOWN, 264, 208);
        register(Key.ARROW_RIGHT, 262, 205);
        register(Key.ARROW_UP, 265, 200);
        register(Key.INSERT, GlConst.GL_ADD, 210);
        register(Key.HOME, 268, 199);
        register(Key.PAGE_UP, 266, 201);
        register(Key.DELETE, 261, 211);
        register(Key.END, 269, 207);
        register(Key.PAGE_DOWN, 267, 209);
        register(Key.PRINT, 283, 183);
        register(Key.SCROLL, 281, 70);
        register(Key.PAUSE, 284, 197);
        register(Key.NUM_LOCK, 282, 69);
        register(Key.DIVIDE, 331, 181);
        register(Key.MULTIPLY, 332, 55);
        register(Key.SUBTRACT, 333, 74);
        register(Key.ADD, 334, 78);
        register(Key.NUMPAD_EQUAL, 336, 141);
        register(Key.NUMPAD_ENTER, 335, 156);
        register(Key.DECIMAL, 330, 83);
        register(Key.NUMPAD0, 320, 82);
        register(Key.NUMPAD1, 321, 79);
        register(Key.NUMPAD2, 322, 80);
        register(Key.NUMPAD3, 323, 81);
        register(Key.NUMPAD4, 324, 75);
        register(Key.NUMPAD5, 325, 76);
        register(Key.NUMPAD6, 326, 77);
        register(Key.NUMPAD7, 327, 71);
        register(Key.NUMPAD8, 328, 72);
        register(Key.NUMPAD9, 329, 73);
        register(Key.WORLD_1, 161, ColorUtil.LEGACY_COLOR_CHAR_PREFIX);
        register(Key.WORLD_2, 162, 316);
    }

    private static void printStacktrace(String message, Object... arguments) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        List<StackWalker.StackFrame> frames = (List) WALKER.walk((v0) -> {
            return v0.toList();
        });
        if (frames.size() < 2) {
            return;
        }
        for (StackWalker.StackFrame frame : frames) {
            stackTraceBuilder.append('\t').append(frame.toString()).append('\n');
        }
        LOGGER.warn(String.format(Locale.ROOT, message, arguments) + " - Stacktrace: \n" + String.valueOf(stackTraceBuilder), new Object[0]);
    }

    private <T extends Key> T getKeyByKeyCode(Map<String, T> map, int keyCode, KeyMapper.KeyCodeType type) {
        int keyCodeToMatch;
        DefaultKey key = null;
        Iterator<DefaultKey> it = this.keyCodes.values().iterator();
        while (true) {
            if (it.hasNext()) {
                DefaultKey value = it.next();
                switch (type) {
                    case LWJGL:
                        keyCodeToMatch = value.getLwjglKeyCode();
                        break;
                    case GLFW:
                        keyCodeToMatch = value.getGlfwKeyCode();
                        break;
                    default:
                        keyCodeToMatch = value.getKeyCode();
                        break;
                }
                if (map.containsKey(value.getKeyName()) && keyCodeToMatch == keyCode) {
                    key = value;
                }
            }
        }
        if (key == null) {
            return null;
        }
        return map.get(key.getKeyName());
    }

    private <T extends Key> int getKeyCodeByKey(Map<String, T> map, T key, KeyMapper.KeyCodeType type) {
        DefaultKey versionedKey;
        Objects.requireNonNull(key, "Key cannot be null");
        String actualName = key.getActualName();
        if (!map.containsKey(actualName) || (versionedKey = this.keyCodes.get(actualName)) == null) {
            return 0;
        }
        switch (type) {
            case LWJGL:
                return versionedKey.getLwjglKeyCode();
            case GLFW:
                return versionedKey.getGlfwKeyCode();
            default:
                return versionedKey.getKeyCode();
        }
    }
}
