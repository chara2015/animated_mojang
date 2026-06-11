package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue;

import java.nio.ByteBuffer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/input/queue/EventQueueKeyboardInput.class */
public class EventQueueKeyboardInput implements KeyboardInput {
    private GLFWKeyCallbackI keyCallback;
    private GLFWCharCallbackI charCallback;
    private int retainedKey;
    private int retainedChar;
    private long retainedMillis;
    private byte retainedState;
    private boolean retainedRepeat;
    private boolean retainedEvent;
    private static final byte[] KEY_DOWN_BUFFER = new byte[Keyboard.KEYBOARD_SIZE];
    private static final EventQueue EVENT_QUEUE = new EventQueue(18);
    private static final ByteBuffer TEMP_EVENT = ByteBuffer.allocate(18);
    private static final int[] GLFW_TO_LWJGL = new int[Keyboard.KEYBOARD_SIZE];

    static {
        GLFW_TO_LWJGL[0] = Keyboard.KEY_NONE;
        GLFW_TO_LWJGL[32] = Keyboard.KEY_SPACE;
        GLFW_TO_LWJGL[39] = Keyboard.KEY_APOSTROPHE;
        GLFW_TO_LWJGL[44] = Keyboard.KEY_COMMA;
        GLFW_TO_LWJGL[45] = Keyboard.KEY_MINUS;
        GLFW_TO_LWJGL[46] = Keyboard.KEY_PERIOD;
        GLFW_TO_LWJGL[47] = Keyboard.KEY_SLASH;
        GLFW_TO_LWJGL[48] = Keyboard.KEY_0;
        GLFW_TO_LWJGL[49] = Keyboard.KEY_1;
        GLFW_TO_LWJGL[50] = Keyboard.KEY_2;
        GLFW_TO_LWJGL[51] = Keyboard.KEY_3;
        GLFW_TO_LWJGL[52] = Keyboard.KEY_4;
        GLFW_TO_LWJGL[53] = Keyboard.KEY_5;
        GLFW_TO_LWJGL[54] = Keyboard.KEY_6;
        GLFW_TO_LWJGL[55] = Keyboard.KEY_7;
        GLFW_TO_LWJGL[56] = Keyboard.KEY_8;
        GLFW_TO_LWJGL[57] = Keyboard.KEY_9;
        GLFW_TO_LWJGL[59] = Keyboard.KEY_SEMICOLON;
        GLFW_TO_LWJGL[61] = Keyboard.KEY_EQUALS;
        GLFW_TO_LWJGL[65] = Keyboard.KEY_A;
        GLFW_TO_LWJGL[66] = Keyboard.KEY_B;
        GLFW_TO_LWJGL[67] = Keyboard.KEY_C;
        GLFW_TO_LWJGL[68] = Keyboard.KEY_D;
        GLFW_TO_LWJGL[69] = Keyboard.KEY_E;
        GLFW_TO_LWJGL[70] = Keyboard.KEY_F;
        GLFW_TO_LWJGL[71] = Keyboard.KEY_G;
        GLFW_TO_LWJGL[72] = Keyboard.KEY_H;
        GLFW_TO_LWJGL[73] = Keyboard.KEY_I;
        GLFW_TO_LWJGL[74] = Keyboard.KEY_J;
        GLFW_TO_LWJGL[75] = Keyboard.KEY_K;
        GLFW_TO_LWJGL[76] = Keyboard.KEY_L;
        GLFW_TO_LWJGL[77] = Keyboard.KEY_M;
        GLFW_TO_LWJGL[78] = Keyboard.KEY_N;
        GLFW_TO_LWJGL[79] = Keyboard.KEY_O;
        GLFW_TO_LWJGL[80] = Keyboard.KEY_P;
        GLFW_TO_LWJGL[81] = Keyboard.KEY_Q;
        GLFW_TO_LWJGL[82] = Keyboard.KEY_R;
        GLFW_TO_LWJGL[83] = Keyboard.KEY_S;
        GLFW_TO_LWJGL[84] = Keyboard.KEY_T;
        GLFW_TO_LWJGL[85] = Keyboard.KEY_U;
        GLFW_TO_LWJGL[86] = Keyboard.KEY_V;
        GLFW_TO_LWJGL[87] = Keyboard.KEY_W;
        GLFW_TO_LWJGL[88] = Keyboard.KEY_X;
        GLFW_TO_LWJGL[89] = Keyboard.KEY_Y;
        GLFW_TO_LWJGL[90] = Keyboard.KEY_Z;
        GLFW_TO_LWJGL[91] = Keyboard.KEY_LBRACKET;
        GLFW_TO_LWJGL[92] = Keyboard.KEY_BACKSLASH;
        GLFW_TO_LWJGL[93] = Keyboard.KEY_RBRACKET;
        GLFW_TO_LWJGL[96] = Keyboard.KEY_GRAVE;
        GLFW_TO_LWJGL[161] = Keyboard.KEY_WORLD_1;
        GLFW_TO_LWJGL[162] = Keyboard.KEY_WORLD_2;
        GLFW_TO_LWJGL[256] = Keyboard.KEY_ESCAPE;
        GLFW_TO_LWJGL[257] = Keyboard.KEY_RETURN;
        GLFW_TO_LWJGL[258] = Keyboard.KEY_TAB;
        GLFW_TO_LWJGL[259] = Keyboard.KEY_BACK;
        GLFW_TO_LWJGL[260] = Keyboard.KEY_INSERT;
        GLFW_TO_LWJGL[261] = Keyboard.KEY_DELETE;
        GLFW_TO_LWJGL[262] = Keyboard.KEY_RIGHT;
        GLFW_TO_LWJGL[263] = Keyboard.KEY_LEFT;
        GLFW_TO_LWJGL[264] = Keyboard.KEY_DOWN;
        GLFW_TO_LWJGL[265] = Keyboard.KEY_UP;
        GLFW_TO_LWJGL[266] = Keyboard.KEY_PRIOR;
        GLFW_TO_LWJGL[267] = Keyboard.KEY_NEXT;
        GLFW_TO_LWJGL[268] = Keyboard.KEY_HOME;
        GLFW_TO_LWJGL[269] = Keyboard.KEY_END;
        GLFW_TO_LWJGL[280] = Keyboard.KEY_CAPITAL;
        GLFW_TO_LWJGL[281] = Keyboard.KEY_SCROLL;
        GLFW_TO_LWJGL[282] = Keyboard.KEY_NUMLOCK;
        GLFW_TO_LWJGL[283] = Keyboard.KEY_PRINT_SCREEN;
        GLFW_TO_LWJGL[284] = Keyboard.KEY_PAUSE;
        GLFW_TO_LWJGL[290] = Keyboard.KEY_F1;
        GLFW_TO_LWJGL[291] = Keyboard.KEY_F2;
        GLFW_TO_LWJGL[292] = Keyboard.KEY_F3;
        GLFW_TO_LWJGL[293] = Keyboard.KEY_F4;
        GLFW_TO_LWJGL[294] = Keyboard.KEY_F5;
        GLFW_TO_LWJGL[295] = Keyboard.KEY_F6;
        GLFW_TO_LWJGL[296] = Keyboard.KEY_F7;
        GLFW_TO_LWJGL[297] = Keyboard.KEY_F8;
        GLFW_TO_LWJGL[298] = Keyboard.KEY_F9;
        GLFW_TO_LWJGL[299] = Keyboard.KEY_F10;
        GLFW_TO_LWJGL[300] = Keyboard.KEY_F11;
        GLFW_TO_LWJGL[301] = Keyboard.KEY_F12;
        GLFW_TO_LWJGL[302] = Keyboard.KEY_F13;
        GLFW_TO_LWJGL[303] = Keyboard.KEY_F14;
        GLFW_TO_LWJGL[304] = Keyboard.KEY_F15;
        GLFW_TO_LWJGL[305] = Keyboard.KEY_F16;
        GLFW_TO_LWJGL[306] = Keyboard.KEY_F17;
        GLFW_TO_LWJGL[307] = Keyboard.KEY_F18;
        GLFW_TO_LWJGL[308] = Keyboard.KEY_F19;
        GLFW_TO_LWJGL[309] = Keyboard.KEY_F20;
        GLFW_TO_LWJGL[310] = Keyboard.KEY_F21;
        GLFW_TO_LWJGL[311] = Keyboard.KEY_F22;
        GLFW_TO_LWJGL[312] = Keyboard.KEY_F23;
        GLFW_TO_LWJGL[313] = Keyboard.KEY_F24;
        GLFW_TO_LWJGL[314] = Keyboard.KEY_F25;
        GLFW_TO_LWJGL[320] = Keyboard.KEY_NUMPAD0;
        GLFW_TO_LWJGL[321] = Keyboard.KEY_NUMPAD1;
        GLFW_TO_LWJGL[322] = Keyboard.KEY_NUMPAD2;
        GLFW_TO_LWJGL[323] = Keyboard.KEY_NUMPAD3;
        GLFW_TO_LWJGL[324] = Keyboard.KEY_NUMPAD4;
        GLFW_TO_LWJGL[325] = Keyboard.KEY_NUMPAD5;
        GLFW_TO_LWJGL[326] = Keyboard.KEY_NUMPAD6;
        GLFW_TO_LWJGL[327] = Keyboard.KEY_NUMPAD7;
        GLFW_TO_LWJGL[328] = Keyboard.KEY_NUMPAD8;
        GLFW_TO_LWJGL[329] = Keyboard.KEY_NUMPAD9;
        GLFW_TO_LWJGL[330] = Keyboard.KEY_DECIMAL;
        GLFW_TO_LWJGL[331] = Keyboard.KEY_DIVIDE;
        GLFW_TO_LWJGL[332] = Keyboard.KEY_MULTIPLY;
        GLFW_TO_LWJGL[333] = Keyboard.KEY_SUBTRACT;
        GLFW_TO_LWJGL[334] = Keyboard.KEY_ADD;
        GLFW_TO_LWJGL[335] = Keyboard.KEY_NUMPADENTER;
        GLFW_TO_LWJGL[336] = Keyboard.KEY_NUMPADEQUALS;
        GLFW_TO_LWJGL[340] = Keyboard.KEY_LSHIFT;
        GLFW_TO_LWJGL[341] = Keyboard.KEY_LCONTROL;
        GLFW_TO_LWJGL[342] = Keyboard.KEY_LMENU;
        GLFW_TO_LWJGL[343] = Keyboard.KEY_LMETA;
        GLFW_TO_LWJGL[344] = Keyboard.KEY_RSHIFT;
        GLFW_TO_LWJGL[345] = Keyboard.KEY_RCONTROL;
        GLFW_TO_LWJGL[346] = Keyboard.KEY_RMENU;
        GLFW_TO_LWJGL[347] = Keyboard.KEY_RMETA;
        GLFW_TO_LWJGL[348] = Keyboard.KEY_MENU;
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput
    public void create() {
        long windowHandle = Display.getWindowHandle();
        this.keyCallback = this::onKey;
        this.charCallback = this::onChar;
        GLFW.glfwSetKeyCallback(windowHandle, this.keyCallback);
        GLFW.glfwSetCharCallback(windowHandle, this.charCallback);
        GLFW.glfwSetInputMode(windowHandle, 208900, 1);
    }

    private void onChar(long windowHandle, int codepoint) {
        if (this.retainedEvent && this.retainedChar != 0) {
            flushRetainedEvent();
        }
        if (!this.retainedEvent) {
            writeKeyboardEvent(0, (byte) 0, codepoint, TimeUtil.getMillis(), true);
        } else {
            this.retainedChar = codepoint;
        }
    }

    private void onKey(long windowHandle, int key, int scancode, int action, int mods) {
        int key2 = GLFWUtil.getNumpadActionKey(key, scancode, mods);
        if (DefaultKeyMapper.isSyntheticCtrl(key2, mods, Keyboard.KEY_RMENU)) {
            return;
        }
        int translatedKey = translate(key2);
        boolean repeat = action == 2;
        flushRetainedEvent();
        this.retainedEvent = true;
        this.retainedKey = translatedKey;
        if (action == 1) {
            KEY_DOWN_BUFFER[translatedKey] = 1;
        } else if (action == 0) {
            KEY_DOWN_BUFFER[translatedKey] = 0;
        }
        this.retainedState = KEY_DOWN_BUFFER[translatedKey];
        this.retainedMillis = TimeUtil.getMillis();
        this.retainedChar = 0;
        this.retainedRepeat = repeat;
    }

    private void flushRetainedEvent() {
        if (!this.retainedEvent) {
            return;
        }
        this.retainedEvent = false;
        writeKeyboardEvent(this.retainedKey, this.retainedState, this.retainedChar, this.retainedMillis, this.retainedRepeat);
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput
    public void poll(ByteBuffer buffer) {
        int oldPosition = buffer.position();
        buffer.put(KEY_DOWN_BUFFER);
        buffer.position(oldPosition);
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput
    public void read(ByteBuffer buffer) {
        flushRetainedEvent();
        EVENT_QUEUE.fireEvents(buffer);
    }

    private int translate(int key) {
        if (key == -1) {
            return GLFW_TO_LWJGL[0];
        }
        if (key < GLFW_TO_LWJGL.length) {
            return GLFW_TO_LWJGL[key];
        }
        return key;
    }

    private void writeKeyboardEvent(int keycode, byte state, int character, long time, boolean repeat) {
        TEMP_EVENT.clear();
        TEMP_EVENT.putInt(keycode);
        TEMP_EVENT.put(state);
        TEMP_EVENT.putInt(character);
        TEMP_EVENT.putLong(time);
        TEMP_EVENT.put((byte) (repeat ? 1 : 0));
        TEMP_EVENT.flip();
        EVENT_QUEUE.putEvent(TEMP_EVENT);
    }
}
