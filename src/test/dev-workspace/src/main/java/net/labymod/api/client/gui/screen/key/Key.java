package net.labymod.api.client.gui.screen.key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/Key.class */
public class Key {
    protected static final KeyMapper KEY_MAPPER = Laby.references().keyMapper();
    public static final Key NONE = new Key("NONE").register();
    public static final Key ESCAPE = new Key("ESCAPE").register().action();
    public static final Key F1 = new Key("F_1").register().action();
    public static final Key F2 = new Key("F_2").register().action();
    public static final Key F3 = new Key("F_3").register().action();
    public static final Key F4 = new Key("F_4").register().action();
    public static final Key F5 = new Key("F_5").register().action();
    public static final Key F6 = new Key("F_6").register().action();
    public static final Key F7 = new Key("F_7").register().action();
    public static final Key F8 = new Key("F_8").register().action();
    public static final Key F9 = new Key("F_9").register().action();
    public static final Key F10 = new Key("F_10").register().action();
    public static final Key F11 = new Key("F_11").register().action();
    public static final Key F12 = new Key("F_12").register().action();
    public static final Key F13 = new Key("F_13").register().action();
    public static final Key F14 = new Key("F_14").register().action();
    public static final Key F15 = new Key("F_15").register().action();
    public static final Key F16 = new Key("F_16").register().action();
    public static final Key F17 = new Key("F_17").register().action();
    public static final Key F18 = new Key("F_18").register().action();
    public static final Key F19 = new Key("F_19").register().action();
    public static final Key F20 = new Key("F_20").register().action();
    public static final Key F21 = new Key("F_21").register().action();
    public static final Key F22 = new Key("F_22").register().action();
    public static final Key F23 = new Key("F_23").register().action();
    public static final Key F24 = new Key("F_24").register().action();
    public static final Key F25 = new Key("F_25").register().action();
    public static final Key GRAVE = new Key("GRAVE").register();
    public static final Key NUM1 = new Key("NUM_1").register();
    public static final Key NUM2 = new Key("NUM_2").register();
    public static final Key NUM3 = new Key("NUM_3").register();
    public static final Key NUM4 = new Key("NUM_4").register();
    public static final Key NUM5 = new Key("NUM_5").register();
    public static final Key NUM6 = new Key("NUM_6").register();
    public static final Key NUM7 = new Key("NUM_7").register();
    public static final Key NUM8 = new Key("NUM_8").register();
    public static final Key NUM9 = new Key("NUM_9").register();
    public static final Key NUM0 = new Key("NUM_0").register();
    public static final Key MINUS = new Key("MINUS").register();
    public static final Key EQUAL = new Key("EQUAL").register();
    public static final Key BACK = new Key("BACK").register().action();
    public static final Key TAB = new Key("TAB").register().action();
    public static final Key Q = new Key("Q").register();
    public static final Key W = new Key("W").register();
    public static final Key E = new Key("E").register();
    public static final Key R = new Key("R").register();
    public static final Key T = new Key("T").register();
    public static final Key Y = new Key("Y").register();
    public static final Key U = new Key("U").register();
    public static final Key I = new Key("I").register();
    public static final Key O = new Key("O").register();
    public static final Key P = new Key("P").register();
    public static final Key L_BRACKET = new Key("LEFT_BRACKET").register();
    public static final Key R_BRACKET = new Key("RIGHT_BRACKET").register();
    public static final Key BACKSLASH = new Key("BACKSLASH").register();
    public static final Key CAPS_LOCK = new Key("CAPSLOCK").register().action();
    public static final Key A = new Key("A").register();
    public static final Key S = new Key("S").register();
    public static final Key D = new Key("D").register();
    public static final Key F = new Key("F").register();
    public static final Key G = new Key("G").register();
    public static final Key H = new Key("H").register();
    public static final Key J = new Key("J").register();
    public static final Key K = new Key("K").register();
    public static final Key L = new Key("L").register();
    public static final Key SEMICOLON = new Key("SEMICOLON").register();
    public static final Key APOSTROPHE = new Key("APOSTROPHE").register();
    public static final Key ENTER = new Key("ENTER").register().action();
    public static final Key L_SHIFT = new Key("LEFT_SHIFT").register().action();
    public static final Key Z = new Key("Z").register();
    public static final Key X = new Key("X").register();
    public static final Key C = new Key("C").register();
    public static final Key V = new Key("V").register();
    public static final Key B = new Key("B").register();
    public static final Key N = new Key("N").register();
    public static final Key M = new Key("M").register();
    public static final Key COMMA = new Key("COMMA").register();
    public static final Key PERIOD = new Key("PERIOD").register();
    public static final Key SLASH = new Key("SLASH").register();
    public static final Key R_SHIFT = new Key("RIGHT_SHIFT").register().action();
    public static final Key L_CONTROL = new Key("LEFT_CONTROL").register().action();
    public static final Key L_WIN = new Key("LEFT_WIN").register().action();
    public static final Key L_ALT = new Key("LEFT_ALT").register().action();
    public static final Key SPACE = new Key("SPACE").register();
    public static final Key R_ALT = new Key("RIGHT_ALT").register().action();
    public static final Key R_WIN = new Key("RIGHT_WIN").register().action();
    public static final Key MENU = new Key("MENU").register().action();
    public static final Key R_CONTROL = new Key("RIGHT_CONTROL").register().action();
    public static final Key ARROW_LEFT = new Key("ARROW_LEFT").register().action();
    public static final Key ARROW_DOWN = new Key("ARROW_DOWN").register().action();
    public static final Key ARROW_RIGHT = new Key("ARROW_RIGHT").register().action();
    public static final Key ARROW_UP = new Key("ARROW_UP").register().action();
    public static final Key INSERT = new Key("INSERT").register().action();
    public static final Key HOME = new Key("HOME").register().action();
    public static final Key PAGE_UP = new Key("PAGE_UP").register().action();
    public static final Key DELETE = new Key("DELETE").register().action();
    public static final Key END = new Key("END").register().action();
    public static final Key PAGE_DOWN = new Key("PAGE_DOWN").register().action();
    public static final Key PRINT = new Key("PRINT").register().action();
    public static final Key SCROLL = new Key("SCROLL").register().action();
    public static final Key PAUSE = new Key("PAUSE").register().action();
    public static final Key NUM_LOCK = new Key("NUMPAD_LOCK").register().action();
    public static final Key DIVIDE = new Key("NUMPAD_DIVIDE").register();
    public static final Key MULTIPLY = new Key("NUMPAD_MULTIPLY").register();
    public static final Key SUBTRACT = new Key("NUMPAD_SUBTRACT").register();
    public static final Key ADD = new Key("NUMPAD_ADD").register();
    public static final Key NUMPAD_EQUAL = new Key("NUMPAD_EQUAL").register();
    public static final Key NUMPAD_ENTER = new Key("NUMPAD_ENTER").register().action();
    public static final Key DECIMAL = new Key("DUMPAD_DECIMAL").register();
    public static final Key NUMPAD0 = new Key("NUMPAD_0").register();
    public static final Key NUMPAD1 = new Key("NUMPAD_1").register();
    public static final Key NUMPAD2 = new Key("NUMPAD_2").register();
    public static final Key NUMPAD3 = new Key("NUMPAD_3").register();
    public static final Key NUMPAD4 = new Key("NUMPAD_4").register();
    public static final Key NUMPAD5 = new Key("NUMPAD_5").register();
    public static final Key NUMPAD6 = new Key("NUMPAD_6").register();
    public static final Key NUMPAD7 = new Key("NUMPAD_7").register();
    public static final Key NUMPAD8 = new Key("NUMPAD_8").register();
    public static final Key NUMPAD9 = new Key("NUMPAD_9").register();
    public static final Key WORLD_1 = new Key("WORLD_1").register();
    public static final Key WORLD_2 = new Key("WORLD_2").register();
    protected final String actualName;
    protected final String translationKey;
    protected String name;
    protected int id;
    protected char character;
    protected boolean unknown;
    protected boolean action;

    protected Key(String name, String translationKeyPrefix) {
        this.id = -1;
        this.character = (char) 0;
        this.actualName = name;
        String name2 = name.toLowerCase(Locale.ROOT);
        this.translationKey = translationKeyPrefix + (name2.length() == 1 ? "key." + name2 : name2.replace("_", "."));
    }

    protected Key(String name) {
        this(name, "labymod.keys.keyboard.");
    }

    protected Key action() {
        this.action = true;
        return this;
    }

    protected Key register() {
        KeyMapper.registerKey(this);
        return this;
    }

    public String getName() {
        if (this.name == null) {
            this.name = KeyMapper.getKeyName(this);
        }
        return this.name;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public String getActualName() {
        return this.actualName;
    }

    public int getId() {
        if (this.id == -1) {
            this.id = KeyMapper.getKeyCode(this);
        }
        return this.id;
    }

    public boolean isAction() {
        return this.action;
    }

    public boolean isUnknown() {
        return this.unknown;
    }

    public boolean isPressed() {
        return KeyMapper.isPressed(this);
    }

    public static Key get(int keyCode) {
        Key key = KeyMapper.getKey(keyCode);
        if (key != null) {
            return key;
        }
        Key key2 = new Key("UNKNOWN_" + keyCode);
        key2.unknown = true;
        key2.id = keyCode;
        KEY_MAPPER.register(key2, keyCode, keyCode);
        return key2;
    }

    @Nullable
    public static Key getByName(@NotNull String name) {
        return KeyMapper.getKey(name);
    }

    public static String concat(Collection<Key> keys) {
        List<Key> toSort = new ArrayList<>();
        toSort.addAll(keys);
        toSort.sort(Collections.reverseOrder(Comparator.comparing((v0) -> {
            return v0.getId();
        })));
        StringJoiner joiner = new StringJoiner(" + ");
        for (Key key : toSort) {
            if (key != null) {
                String keyName = key.getName();
                joiner.add(keyName);
            }
        }
        return joiner.toString();
    }

    public String toString() {
        return this.actualName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Key key = (Key) o;
        return this.actualName.equals(key.actualName) && this.action == key.action && getId() == key.getId();
    }

    public int hashCode() {
        int result = this.name != null ? this.name.hashCode() : 0;
        return (31 * result) + (this.action ? 1 : 0);
    }

    public static boolean isCharacter(char character) {
        return (character == 167 || character < ' ' || character == 127) ? false : true;
    }
}
