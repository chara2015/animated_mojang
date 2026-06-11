package org.lwjgl.input;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.nio.ByteBuffer;
import net.labymod.api.util.ColorUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue.EventQueueKeyboardInput;
import org.lwjgl.BufferUtils;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/input/Keyboard.class */
public class Keyboard {
    public static final int EVENT_SIZE = 18;
    private static final int BUFFER_SIZE = 50;
    private static boolean created;
    private static boolean repeat_enabled;
    private static ByteBuffer readBuffer;
    private static KeyboardInput keyboardInput;
    public static final int CHAR_NONE = 0;
    private static KeyEvent current_event = new KeyEvent();
    private static KeyEvent temp_event = new KeyEvent();
    public static final int KEYBOARD_SIZE = 349;
    private static ByteBuffer key_down_buffer = BufferUtils.createByteBuffer(KEYBOARD_SIZE);
    private static final String[] KEY_NAMES = new String[KEYBOARD_SIZE];
    private static final Object2IntMap<String> NAME_TO_KEY = new Object2IntOpenHashMap(253);
    public static final int KEY_NONE = register("NONE", 0);
    public static final int KEY_SPACE = register("SPACE", 57);
    public static final int KEY_APOSTROPHE = register("APOSTROPHE", 40);
    public static final int KEY_COMMA = register("COMMA", 51);
    public static final int KEY_MINUS = register("MINUS", 12);
    public static final int KEY_PERIOD = register("PERIOD", 52);
    public static final int KEY_SLASH = register("SLASH", 53);
    public static final int KEY_0 = register("0", 11);
    public static final int KEY_1 = register("1", 2);
    public static final int KEY_2 = register("2", 3);
    public static final int KEY_3 = register("3", 4);
    public static final int KEY_4 = register("4", 5);
    public static final int KEY_5 = register("5", 6);
    public static final int KEY_6 = register("6", 7);
    public static final int KEY_7 = register("7", 8);
    public static final int KEY_8 = register("8", 9);
    public static final int KEY_9 = register("9", 10);
    public static final int KEY_SEMICOLON = register("SEMICOLON", 39);
    public static final int KEY_EQUALS = register("EQUALS", 13);
    public static final int KEY_A = register("A", 30);
    public static final int KEY_B = register("B", 48);
    public static final int KEY_C = register("C", 46);
    public static final int KEY_D = register("D", 32);
    public static final int KEY_E = register("E", 18);
    public static final int KEY_F = register("F", 33);
    public static final int KEY_G = register("G", 34);
    public static final int KEY_H = register("H", 35);
    public static final int KEY_I = register("I", 23);
    public static final int KEY_J = register("J", 36);
    public static final int KEY_K = register("K", 37);
    public static final int KEY_L = register("L", 38);
    public static final int KEY_M = register("M", 50);
    public static final int KEY_N = register("N", 49);
    public static final int KEY_O = register("O", 24);
    public static final int KEY_P = register("P", 25);
    public static final int KEY_Q = register("Q", 16);
    public static final int KEY_R = register("R", 19);
    public static final int KEY_S = register("S", 31);
    public static final int KEY_T = register("T", 20);
    public static final int KEY_U = register("U", 22);
    public static final int KEY_V = register("V", 47);
    public static final int KEY_W = register("W", 17);
    public static final int KEY_X = register("X", 45);
    public static final int KEY_Y = register("Y", 21);
    public static final int KEY_Z = register("Z", 44);
    public static final int KEY_LBRACKET = register("LBRACKET", 26);
    public static final int KEY_BACKSLASH = register("BACKSLASH", 43);
    public static final int KEY_RBRACKET = register("RBRACKET", 27);
    public static final int KEY_GRAVE = register("GRAVE", 41);
    public static final int KEY_WORLD_1 = register("WORLD_1", ColorUtil.LEGACY_COLOR_CHAR_PREFIX);
    public static final int KEY_WORLD_2 = register("WORLD_2", 316);
    public static final int KEY_ESCAPE = register("ESCAPE", 1);
    public static final int KEY_RETURN = register("RETURN", 28);
    public static final int KEY_TAB = register("TAB", 15);
    public static final int KEY_BACK = register("BACK", 14);
    public static final int KEY_INSERT = register("INSERT", 210);
    public static final int KEY_DELETE = register("DELETE", 211);
    public static final int KEY_RIGHT = register("RIGHT", 205);
    public static final int KEY_LEFT = register("LEFT", 203);
    public static final int KEY_DOWN = register("DOWN", 208);
    public static final int KEY_UP = register("UP", 200);
    public static final int KEY_PRIOR = register("PRIOR", 201);
    public static final int KEY_NEXT = register("NEXT", 209);
    public static final int KEY_HOME = register("HOME", 199);
    public static final int KEY_END = register("END", 207);
    public static final int KEY_CAPITAL = register("CAPITAL", 58);
    public static final int KEY_SCROLL = register("SCROLL", 70);
    public static final int KEY_NUMLOCK = register("NUMLOCK", 69);
    public static final int KEY_PRINT_SCREEN = register("PRINT_SCREEN", 28);
    public static final int KEY_PAUSE = register("PAUSE", 197);
    public static final int KEY_F1 = register("F1", 59);
    public static final int KEY_F2 = register("F2", 60);
    public static final int KEY_F3 = register("F3", 61);
    public static final int KEY_F4 = register("F4", 62);
    public static final int KEY_F5 = register("F5", 63);
    public static final int KEY_F6 = register("F6", 64);
    public static final int KEY_F7 = register("F7", 65);
    public static final int KEY_F8 = register("F8", 66);
    public static final int KEY_F9 = register("F9", 67);
    public static final int KEY_F10 = register("F10", 68);
    public static final int KEY_F11 = register("F11", 87);
    public static final int KEY_F12 = register("F12", 88);
    public static final int KEY_F13 = register("F13", 100);
    public static final int KEY_F14 = register("F14", 101);
    public static final int KEY_F15 = register("F15", 102);
    public static final int KEY_F16 = register("F16", 103);
    public static final int KEY_F17 = register("F17", 104);
    public static final int KEY_F18 = register("F18", 105);
    public static final int KEY_F19 = register("F19", 113);
    public static final int KEY_F20 = register("F20", 309);
    public static final int KEY_F21 = register("F21", 310);
    public static final int KEY_F22 = register("F22", 311);
    public static final int KEY_F23 = register("F23", 312);
    public static final int KEY_F24 = register("F24", 313);
    public static final int KEY_F25 = register("F25", 314);
    public static final int KEY_NUMPAD0 = register("NUMPAD0", 82);
    public static final int KEY_NUMPAD1 = register("NUMPAD1", 79);
    public static final int KEY_NUMPAD2 = register("NUMPAD2", 80);
    public static final int KEY_NUMPAD3 = register("NUMPAD3", 81);
    public static final int KEY_NUMPAD4 = register("NUMPAD4", 75);
    public static final int KEY_NUMPAD5 = register("NUMPAD5", 76);
    public static final int KEY_NUMPAD6 = register("NUMPAD6", 77);
    public static final int KEY_NUMPAD7 = register("NUMPAD7", 71);
    public static final int KEY_NUMPAD8 = register("NUMPAD8", 72);
    public static final int KEY_NUMPAD9 = register("NUMPAD9", 73);
    public static final int KEY_DECIMAL = register("DECIMAL", 83);
    public static final int KEY_DIVIDE = register("DIVIDE", 181);
    public static final int KEY_MULTIPLY = register("MULTIPLY", 55);
    public static final int KEY_SUBTRACT = register("SUBTRACT", 74);
    public static final int KEY_ADD = register("ADD", 78);
    public static final int KEY_NUMPADENTER = register("NUMPADENTER", 156);
    public static final int KEY_NUMPADEQUALS = register("NUMPADEQUALS", 141);
    public static final int KEY_LSHIFT = register("LSHIFT", 42);
    public static final int KEY_LCONTROL = register("LCONTROL", 29);
    public static final int KEY_LMENU = register("LMENU", 56);
    public static final int KEY_LMETA = register("LMETA", 219);
    public static final int KEY_RSHIFT = register("RSHIFT", 54);
    public static final int KEY_RCONTROL = register("RCONTROL", 157);
    public static final int KEY_RMENU = register("RMENU", 184);
    public static final int KEY_RMETA = register("RMETA", 220);
    public static final int KEY_MENU = register("MENU", 348);

    public static void create() {
        create(new EventQueueKeyboardInput());
    }

    public static void create(KeyboardInput input) {
        if (created) {
            return;
        }
        keyboardInput = input;
        keyboardInput.create();
        created = true;
        readBuffer = ByteBuffer.allocate(900);
        reset();
    }

    public static boolean next() {
        boolean result;
        if (!created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        do {
            result = readNext(current_event);
            if (!result || !current_event.repeat) {
                break;
            }
        } while (!repeat_enabled);
        return result;
    }

    public static void poll() {
        if (!created) {
            throw new IllegalStateException("Keyboard not created");
        }
        keyboardInput.poll(key_down_buffer);
        read();
    }

    public static String getKeyName(int keyCode) {
        return KEY_NAMES[keyCode];
    }

    public static int getKeyIndex(String name) {
        return NAME_TO_KEY.getOrDefault(name, KEY_NONE);
    }

    public static int getNumKeyboardEvents() {
        if (created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        int oldPosition = readBuffer.position();
        int eventCount = 0;
        while (readNext(temp_event) && (!temp_event.repeat || repeat_enabled)) {
            eventCount++;
        }
        readBuffer.position(oldPosition);
        return eventCount;
    }

    public static int getKeyCount() {
        return NAME_TO_KEY.size();
    }

    public static char getEventCharacter() {
        return (char) current_event.character;
    }

    public static int getEventKey() {
        return current_event.key;
    }

    public static boolean getEventKeyState() {
        return current_event.state;
    }

    public static long getEventNanoseconds() {
        return current_event.time;
    }

    public static boolean isRepeatEvent() {
        return current_event.repeat;
    }

    public static boolean isKeyDown(int key) {
        if (isCreated()) {
            return key_down_buffer.get(key) != 0;
        }
        throw new IllegalStateException("Keyboard must be created before your can query key state");
    }

    public static boolean isCreated() {
        return created;
    }

    public static void destroy() {
        if (!isCreated()) {
            return;
        }
        created = false;
        keyboardInput.dispose();
        reset();
    }

    public static void enableRepeatEvents(boolean enable) {
        repeat_enabled = enable;
    }

    public static boolean areRepeatEventsEnabled() {
        return repeat_enabled;
    }

    private static void reset() {
        readBuffer.limit(0);
        for (int index = 0; index < key_down_buffer.remaining(); index++) {
            key_down_buffer.put(index, (byte) 0);
        }
        current_event.reset();
    }

    private static void read() {
        readBuffer.compact();
        keyboardInput.read(readBuffer);
        readBuffer.flip();
    }

    private static boolean readNext(KeyEvent event) {
        if (!readBuffer.hasRemaining()) {
            return false;
        }
        event.key = readBuffer.getInt();
        event.state = readBuffer.get() != 0;
        event.character = readBuffer.getInt();
        event.time = readBuffer.getLong();
        event.repeat = readBuffer.get() == 1;
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/input/Keyboard$KeyEvent.class */
    private static final class KeyEvent {
        private int character;
        private int key;
        private boolean state;
        private boolean repeat;
        private long time;

        private KeyEvent() {
        }

        private void reset() {
            this.character = 0;
            this.key = 0;
            this.state = false;
            this.repeat = false;
        }

        public String toString() {
            return "KeyEvent{character=" + this.character + ", key=" + this.key + ", state=" + this.state + ", repeat=" + this.repeat + ", time=" + this.time + "}";
        }
    }

    private static int register(String name, int lwjglCode) {
        KEY_NAMES[lwjglCode] = name;
        NAME_TO_KEY.put(name, lwjglCode);
        return lwjglCode;
    }
}
