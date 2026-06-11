package net.labymod.core.client.gui.lss.style.modifier;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/DefaultTypeParser.class */
@Singleton
@Implements(TypeParser.class)
public class DefaultTypeParser implements TypeParser {
    private final Map<Class<?>, TypeParser.Parser> parsers = new HashMap();

    @Inject
    public DefaultTypeParser(ThemeRendererParser themeRendererParser) {
        register((type, value) -> {
            return Constants.NamedThemeResource.valueOf(value).resource();
        }, ResourceLocation.class);
        register((type2, value2) -> {
            return Float.valueOf(Float.parseFloat(value2));
        }, Float.TYPE, Float.class);
        register((type3, value3) -> {
            return Double.valueOf(Double.parseDouble(value3));
        }, Double.TYPE, Double.class);
        register((type4, value4) -> {
            return Integer.valueOf(Integer.parseInt(value4));
        }, Integer.TYPE, Integer.class);
        register((type5, value5) -> {
            return Byte.valueOf(Byte.parseByte(value5));
        }, Byte.TYPE, Byte.class);
        register((type6, value6) -> {
            return Long.valueOf(Long.parseLong(value6));
        }, Long.TYPE, Long.class);
        register((type7, value7) -> {
            return Boolean.valueOf(Boolean.parseBoolean(value7));
        }, Boolean.TYPE, Boolean.class);
        register((type8, value8) -> {
            return Short.valueOf(Short.parseShort(value8));
        }, Short.TYPE, Short.class);
        register((type9, value9) -> {
            return themeRendererParser.parse(value9);
        }, ThemeRenderer.class);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.TypeParser
    @SafeVarargs
    public final <T> void register(TypeParser.Parser<T> parser, Class<T>... types) {
        for (Class<T> cls : types) {
            if (this.parsers.containsKey(cls)) {
                throw new IllegalArgumentException("Parser for type " + cls.getName() + " is already registered: " + this.parsers.get(cls).getClass().getName());
            }
            this.parsers.put(cls, parser);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.TypeParser
    public Object parseValue(Class<?> type, String value) throws Exception {
        if (type == null || type == String.class) {
            return value;
        }
        if (value.equalsIgnoreCase("null")) {
            return null;
        }
        if (type.isEnum()) {
            return Enum.valueOf(type, StringUtil.toUppercase(value).replace("-", "_"));
        }
        TypeParser.Parser parser = this.parsers.get(type);
        if (parser != null) {
            return parser.parseValue(type, value);
        }
        return null;
    }
}
