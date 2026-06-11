package net.minecraft.resources;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.util.function.UnaryOperator;
import net.minecraft.IdentifierException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/Identifier.class */
public final class Identifier implements Comparable<Identifier> {
    public static final Codec<Identifier> CODEC;
    public static final StreamCodec<ByteBuf, Identifier> STREAM_CODEC;
    public static final SimpleCommandExceptionType ERROR_INVALID;
    public static final char NAMESPACE_SEPARATOR = ':';
    public static final String DEFAULT_NAMESPACE = "minecraft";
    public static final String REALMS_NAMESPACE = "realms";
    private final String namespace;
    private final String path;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Identifier.class.desiredAssertionStatus();
        CODEC = Codec.STRING.comapFlatMap(Identifier::read, (v0) -> {
            return v0.toString();
        }).stable();
        STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(Identifier::parse, (v0) -> {
            return v0.toString();
        });
        ERROR_INVALID = new SimpleCommandExceptionType(Component.translatable("argument.id.invalid"));
    }

    private Identifier(String $$0, String $$1) {
        if (!$assertionsDisabled && !isValidNamespace($$0)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !isValidPath($$1)) {
            throw new AssertionError();
        }
        this.namespace = $$0;
        this.path = $$1;
    }

    private static Identifier createUntrusted(String $$0, String $$1) {
        return new Identifier(assertValidNamespace($$0, $$1), assertValidPath($$0, $$1));
    }

    public static Identifier fromNamespaceAndPath(String $$0, String $$1) {
        return createUntrusted($$0, $$1);
    }

    public static Identifier parse(String $$0) {
        return bySeparator($$0, ':');
    }

    public static Identifier withDefaultNamespace(String $$0) {
        return new Identifier("minecraft", assertValidPath("minecraft", $$0));
    }

    public static Identifier tryParse(String $$0) {
        return tryBySeparator($$0, ':');
    }

    public static Identifier tryBuild(String $$0, String $$1) {
        if (isValidNamespace($$0) && isValidPath($$1)) {
            return new Identifier($$0, $$1);
        }
        return null;
    }

    public static Identifier bySeparator(String $$0, char $$1) {
        int $$2 = $$0.indexOf($$1);
        if ($$2 >= 0) {
            String $$3 = $$0.substring($$2 + 1);
            if ($$2 != 0) {
                String $$4 = $$0.substring(0, $$2);
                return createUntrusted($$4, $$3);
            }
            return withDefaultNamespace($$3);
        }
        return withDefaultNamespace($$0);
    }

    public static Identifier tryBySeparator(String $$0, char $$1) {
        int $$2 = $$0.indexOf($$1);
        if ($$2 >= 0) {
            String $$3 = $$0.substring($$2 + 1);
            if (!isValidPath($$3)) {
                return null;
            }
            if ($$2 != 0) {
                String $$4 = $$0.substring(0, $$2);
                if (isValidNamespace($$4)) {
                    return new Identifier($$4, $$3);
                }
                return null;
            }
            return new Identifier("minecraft", $$3);
        }
        if (isValidPath($$0)) {
            return new Identifier("minecraft", $$0);
        }
        return null;
    }

    public static DataResult<Identifier> read(String $$0) {
        try {
            return DataResult.success(parse($$0));
        } catch (IdentifierException $$1) {
            return DataResult.error(() -> {
                return "Not a valid resource location: " + $$0 + " " + $$1.getMessage();
            });
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Identifier withPath(String $$0) {
        return new Identifier(this.namespace, assertValidPath(this.namespace, $$0));
    }

    public Identifier withPath(UnaryOperator<String> $$0) {
        return withPath((String) $$0.apply(this.path));
    }

    public Identifier withPrefix(String $$0) {
        return withPath($$0 + this.path);
    }

    public Identifier withSuffix(String $$0) {
        return withPath(this.path + $$0);
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof Identifier)) {
            return false;
        }
        Identifier $$1 = (Identifier) $$0;
        return this.namespace.equals($$1.namespace) && this.path.equals($$1.path);
    }

    public int hashCode() {
        return (31 * this.namespace.hashCode()) + this.path.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Identifier $$0) {
        int $$1 = this.path.compareTo($$0.path);
        if ($$1 == 0) {
            $$1 = this.namespace.compareTo($$0.namespace);
        }
        return $$1;
    }

    public String toDebugFileName() {
        return toString().replace('/', '_').replace(':', '_');
    }

    public String toLanguageKey() {
        return this.namespace + "." + this.path;
    }

    public String toShortLanguageKey() {
        return this.namespace.equals("minecraft") ? this.path : toLanguageKey();
    }

    public String toShortString() {
        return this.namespace.equals("minecraft") ? this.path : toString();
    }

    public String toLanguageKey(String $$0) {
        return $$0 + "." + toLanguageKey();
    }

    public String toLanguageKey(String $$0, String $$1) {
        return $$0 + "." + toLanguageKey() + "." + $$1;
    }

    private static String readGreedy(StringReader $$0) {
        int $$1 = $$0.getCursor();
        while ($$0.canRead() && isAllowedInIdentifier($$0.peek())) {
            $$0.skip();
        }
        return $$0.getString().substring($$1, $$0.getCursor());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public static Identifier read(StringReader $$0) throws CommandSyntaxException {
        int $$1 = $$0.getCursor();
        String $$2 = readGreedy($$0);
        try {
            return parse($$2);
        } catch (IdentifierException e) {
            $$0.setCursor($$1);
            throw ERROR_INVALID.createWithContext($$0);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public static Identifier readNonEmpty(StringReader $$0) throws CommandSyntaxException {
        int $$1 = $$0.getCursor();
        String $$2 = readGreedy($$0);
        if ($$2.isEmpty()) {
            throw ERROR_INVALID.createWithContext($$0);
        }
        try {
            return parse($$2);
        } catch (IdentifierException e) {
            $$0.setCursor($$1);
            throw ERROR_INVALID.createWithContext($$0);
        }
    }

    public static boolean isAllowedInIdentifier(char $$0) {
        return ($$0 >= '0' && $$0 <= '9') || ($$0 >= 'a' && $$0 <= 'z') || $$0 == '_' || $$0 == ':' || $$0 == '/' || $$0 == '.' || $$0 == '-';
    }

    public static boolean isValidPath(String $$0) {
        for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
            if (!validPathChar($$0.charAt($$1))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidNamespace(String $$0) {
        for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
            if (!validNamespaceChar($$0.charAt($$1))) {
                return false;
            }
        }
        return true;
    }

    private static String assertValidNamespace(String $$0, String $$1) {
        if (!isValidNamespace($$0)) {
            throw new IdentifierException("Non [a-z0-9_.-] character in namespace of location: " + $$0 + ":" + $$1);
        }
        return $$0;
    }

    public static boolean validPathChar(char $$0) {
        return $$0 == '_' || $$0 == '-' || ($$0 >= 'a' && $$0 <= 'z') || (($$0 >= '0' && $$0 <= '9') || $$0 == '/' || $$0 == '.');
    }

    private static boolean validNamespaceChar(char $$0) {
        return $$0 == '_' || $$0 == '-' || ($$0 >= 'a' && $$0 <= 'z') || (($$0 >= '0' && $$0 <= '9') || $$0 == '.');
    }

    private static String assertValidPath(String $$0, String $$1) {
        if (!isValidPath($$1)) {
            throw new IdentifierException("Non [a-z0-9/._-] character in path of location: " + $$0 + ":" + $$1);
        }
        return $$1;
    }
}
