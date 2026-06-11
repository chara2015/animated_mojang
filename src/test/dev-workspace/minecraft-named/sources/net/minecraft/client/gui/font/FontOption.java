package net.minecraft.client.gui.font;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/FontOption.class */
public enum FontOption implements StringRepresentable {
    UNIFORM("uniform"),
    JAPANESE_VARIANTS("jp");

    public static final Codec<FontOption> CODEC = StringRepresentable.fromEnum(FontOption::values);
    private final String name;

    FontOption(String $$0) {
        this.name = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/FontOption$Filter.class */
    public static class Filter {
        private final Map<FontOption, Boolean> values;
        public static final Codec<Filter> CODEC = Codec.unboundedMap(FontOption.CODEC, Codec.BOOL).xmap(Filter::new, $$0 -> {
            return $$0.values;
        });
        public static final Filter ALWAYS_PASS = new Filter(Map.of());

        public Filter(Map<FontOption, Boolean> $$0) {
            this.values = $$0;
        }

        public boolean apply(Set<FontOption> $$0) {
            for (Map.Entry<FontOption, Boolean> $$1 : this.values.entrySet()) {
                if ($$0.contains($$1.getKey()) != $$1.getValue().booleanValue()) {
                    return false;
                }
            }
            return true;
        }

        public Filter merge(Filter $$0) {
            Map<FontOption, Boolean> $$1 = new HashMap<>($$0.values);
            $$1.putAll(this.values);
            return new Filter(Map.copyOf($$1));
        }
    }
}
