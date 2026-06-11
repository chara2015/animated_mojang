package net.labymod.core.client.gui.lss.style.modifier.state;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassFactory;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.lss.style.DefaultSelector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/state/DefaultPseudoClassRegistry.class */
@Singleton
@Implements(PseudoClassRegistry.class)
public class DefaultPseudoClassRegistry implements PseudoClassRegistry {
    private final ElementParser elementParser;
    private final Map<String, PseudoClassFactory> factories = new HashMap();

    @Inject
    public DefaultPseudoClassRegistry(ElementParser elementParser) {
        this.elementParser = elementParser;
        registerFactory("not", (function, value) -> {
            return new InvertedPseudoClass(function, parse(value));
        });
        registerFactory("has", (function2, value2) -> {
            return new HasSelectorPseudoClass(function2, new DefaultSelector(value2.getRawValue()));
        });
        registerFactory("has-parent", (function3, value3) -> {
            return new HasParentPseudoClass(function3, new DefaultSelector(value3.getRawValue()));
        });
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry
    public void registerFactory(String key, PseudoClassFactory factory) {
        this.factories.put(key, factory);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry
    public PseudoClass parse(String rawValue) {
        return parse(this.elementParser.parseElement(rawValue));
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry
    public PseudoClass parse(Element value) {
        if (!(value instanceof Function)) {
            AttributeState state = AttributeState.getByName(processKey(value.getRawValue()));
            if (state == AttributeState.NORMAL) {
                return null;
            }
            return new SimplePseudoClass(value, state);
        }
        Function function = (Function) value;
        String key = processKey(function.getName());
        PseudoClassFactory factory = this.factories.get(key);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid attribute state \"" + key + "(...)\"");
        }
        if (function.parameters().length != 1) {
            throw new IllegalArgumentException("Invalid attribute state parameters in \"" + key + "(...)\"");
        }
        return factory.create(function, function.parameters()[0]);
    }

    private String processKey(String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Invalid element: \"\"");
        }
        String processed = key.charAt(0) == ':' ? key.substring(1) : key;
        if (processed.isEmpty()) {
            throw new IllegalArgumentException("Invalid element: \":\"");
        }
        return processed;
    }
}
