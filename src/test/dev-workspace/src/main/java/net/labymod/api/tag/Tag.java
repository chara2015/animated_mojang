package net.labymod.api.tag;

import java.lang.StackWalker;
import java.util.Optional;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/tag/Tag.class */
public final class Tag {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final String STATIC_CONSTRUCTOR_NAME = "<clinit>";
    private final int id = TagRegistry.ID_COUNTER.getAndIncrement();
    private final String namespace;
    private final String name;

    private Tag(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public static Tag of(String namespace, String name) {
        return TagRegistry.getOrRegister(namespace, name, Tag::createTag);
    }

    @Deprecated(forRemoval = true, since = "4.1.12")
    public static Tag ofInternal(String name) {
        return TagRegistry.getOrRegister("deprecated", name, Tag::new);
    }

    private static Tag createTag(String namespace, String name) {
        StackWalker.StackFrame frame = (StackWalker.StackFrame) ((Optional) WALKER.walk(frames -> {
            return frames.skip(3L).findFirst();
        })).orElse(null);
        if (frame == null) {
            throw new IllegalStateException("Unable to retrieve stack frame");
        }
        String methodName = frame.getMethodName();
        if (!STATIC_CONSTRUCTOR_NAME.equals(methodName)) {
            throw new IllegalStateException("Tag.of(namespace, name) may only be used for constants as this is very performance intensive");
        }
        return new Tag(namespace, name);
    }

    public int getId() {
        return this.id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.namespace + ":" + this.name + "[" + this.id + "]";
    }
}
