package net.minecraft.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/KeyMapping.class */
public class KeyMapping implements Comparable<KeyMapping> {
    private static final Map<String, KeyMapping> ALL = Maps.newHashMap();
    private static final Map<InputConstants.Key, List<KeyMapping>> MAP = Maps.newHashMap();
    private final String name;
    private final InputConstants.Key defaultKey;
    private final Category category;
    protected InputConstants.Key key;
    private boolean isDown;
    private int clickCount;
    private final int order;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/KeyMapping$Category.class */
    public static final class Category extends Record {
        private final Identifier id;
        static final List<Category> SORT_ORDER = new ArrayList();
        public static final Category MOVEMENT = register("movement");
        public static final Category MISC = register("misc");
        public static final Category MULTIPLAYER = register("multiplayer");
        public static final Category GAMEPLAY = register("gameplay");
        public static final Category INVENTORY = register("inventory");
        public static final Category CREATIVE = register("creative");
        public static final Category SPECTATOR = register("spectator");
        public static final Category DEBUG = register("debug");

        public Category(Identifier $$0) {
            this.id = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Category.class), Category.class, "id", "FIELD:Lnet/minecraft/client/KeyMapping$Category;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Category.class), Category.class, "id", "FIELD:Lnet/minecraft/client/KeyMapping$Category;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Category.class, Object.class), Category.class, "id", "FIELD:Lnet/minecraft/client/KeyMapping$Category;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        private static Category register(String $$0) {
            return register(Identifier.withDefaultNamespace($$0));
        }

        public static Category register(Identifier $$0) {
            Category $$1 = new Category($$0);
            if (SORT_ORDER.contains($$1)) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Category '%s' is already registered.", $$0));
            }
            SORT_ORDER.add($$1);
            return $$1;
        }

        public Component label() {
            return Component.translatable(this.id.toLanguageKey("key.category"));
        }
    }

    public static void click(InputConstants.Key $$0) {
        forAllKeyMappings($$0, $$02 -> {
            $$02.clickCount++;
        });
    }

    public static void set(InputConstants.Key $$0, boolean $$1) {
        forAllKeyMappings($$0, $$12 -> {
            $$12.setDown($$1);
        });
    }

    private static void forAllKeyMappings(InputConstants.Key $$0, Consumer<KeyMapping> $$1) {
        List<KeyMapping> $$2 = MAP.get($$0);
        if ($$2 != null && !$$2.isEmpty()) {
            for (KeyMapping $$3 : $$2) {
                $$1.accept($$3);
            }
        }
    }

    public static void setAll() {
        Window $$0 = Minecraft.getInstance().getWindow();
        for (KeyMapping $$1 : ALL.values()) {
            if ($$1.shouldSetOnIngameFocus()) {
                $$1.setDown(InputConstants.isKeyDown($$0, $$1.key.getValue()));
            }
        }
    }

    public static void releaseAll() {
        for (KeyMapping $$0 : ALL.values()) {
            $$0.release();
        }
    }

    public static void restoreToggleStatesOnScreenClosed() {
        for (KeyMapping $$0 : ALL.values()) {
            if ($$0 instanceof ToggleKeyMapping) {
                ToggleKeyMapping $$1 = (ToggleKeyMapping) $$0;
                if ($$1.shouldRestoreStateOnScreenClosed()) {
                    $$1.setDown(true);
                }
            }
        }
    }

    public static void resetToggleKeys() {
        for (KeyMapping $$0 : ALL.values()) {
            if ($$0 instanceof ToggleKeyMapping) {
                ToggleKeyMapping $$1 = (ToggleKeyMapping) $$0;
                $$1.reset();
            }
        }
    }

    public static void resetMapping() {
        MAP.clear();
        for (KeyMapping $$0 : ALL.values()) {
            $$0.registerMapping($$0.key);
        }
    }

    public KeyMapping(String $$0, int $$1, Category $$2) {
        this($$0, InputConstants.Type.KEYSYM, $$1, $$2);
    }

    public KeyMapping(String $$0, InputConstants.Type $$1, int $$2, Category $$3) {
        this($$0, $$1, $$2, $$3, 0);
    }

    public KeyMapping(String $$0, InputConstants.Type $$1, int $$2, Category $$3, int $$4) {
        this.name = $$0;
        this.key = $$1.getOrCreate($$2);
        this.defaultKey = this.key;
        this.category = $$3;
        this.order = $$4;
        ALL.put($$0, this);
        registerMapping(this.key);
    }

    public boolean isDown() {
        return this.isDown;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean consumeClick() {
        if (this.clickCount == 0) {
            return false;
        }
        this.clickCount--;
        return true;
    }

    protected void release() {
        this.clickCount = 0;
        setDown(false);
    }

    protected boolean shouldSetOnIngameFocus() {
        return this.key.getType() == InputConstants.Type.KEYSYM && this.key.getValue() != InputConstants.UNKNOWN.getValue();
    }

    public String getName() {
        return this.name;
    }

    public InputConstants.Key getDefaultKey() {
        return this.defaultKey;
    }

    public void setKey(InputConstants.Key $$0) {
        this.key = $$0;
    }

    @Override // java.lang.Comparable
    public int compareTo(KeyMapping $$0) {
        if (this.category == $$0.category) {
            if (this.order == $$0.order) {
                return I18n.get(this.name, new Object[0]).compareTo(I18n.get($$0.name, new Object[0]));
            }
            return Integer.compare(this.order, $$0.order);
        }
        return Integer.compare(Category.SORT_ORDER.indexOf(this.category), Category.SORT_ORDER.indexOf($$0.category));
    }

    public static Supplier<Component> createNameSupplier(String $$0) {
        KeyMapping $$1 = ALL.get($$0);
        if ($$1 == null) {
            return () -> {
                return Component.translatable($$0);
            };
        }
        Objects.requireNonNull($$1);
        return $$1::getTranslatedKeyMessage;
    }

    public boolean same(KeyMapping $$0) {
        return this.key.equals($$0.key);
    }

    public boolean isUnbound() {
        return this.key.equals(InputConstants.UNKNOWN);
    }

    public boolean matches(KeyEvent $$0) {
        return $$0.key() == InputConstants.UNKNOWN.getValue() ? this.key.getType() == InputConstants.Type.SCANCODE && this.key.getValue() == $$0.scancode() : this.key.getType() == InputConstants.Type.KEYSYM && this.key.getValue() == $$0.key();
    }

    public boolean matchesMouse(MouseButtonEvent $$0) {
        return this.key.getType() == InputConstants.Type.MOUSE && this.key.getValue() == $$0.button();
    }

    public Component getTranslatedKeyMessage() {
        return this.key.getDisplayName();
    }

    public boolean isDefault() {
        return this.key.equals(this.defaultKey);
    }

    public String saveString() {
        return this.key.getName();
    }

    public void setDown(boolean $$0) {
        this.isDown = $$0;
    }

    private void registerMapping(InputConstants.Key $$0) {
        MAP.computeIfAbsent($$0, $$02 -> {
            return new ArrayList();
        }).add(this);
    }

    public static KeyMapping get(String $$0) {
        return ALL.get($$0);
    }
}
