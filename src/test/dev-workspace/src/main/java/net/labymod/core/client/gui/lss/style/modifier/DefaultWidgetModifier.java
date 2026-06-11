package net.labymod.core.client.gui.lss.style.modifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.ElementArray;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.Query;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.WidgetAttributeAliasHandler;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.FilterType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.lss.style.modifier.cache.CacheKey;
import net.labymod.core.client.gui.lss.style.modifier.cache.NOPWidgetModifierCache;
import net.labymod.core.client.gui.lss.style.modifier.cache.SimpleWidgetModifierCache;
import net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderColorForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderRadiusForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderWidthForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.MarginForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.MinMaxSizeForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ObjectPositionForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PaddingForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ScaleForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ShadowForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.StencilForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.WidthHeightForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.priority.HighPriorityForwarder;
import net.labymod.core.client.gui.lss.style.modifier.processors.AnimationTimingFunctionProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.BorderPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ColorPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ComponentPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.FilterPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.FontSizePostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.IconPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.MathPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ResourceLocationPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.VarPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.WidgetSizePostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.queries.MediaQuery;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/DefaultWidgetModifier.class */
@Singleton
@Implements(WidgetModifier.class)
public class DefaultWidgetModifier implements WidgetModifier {
    private static final boolean CACHE_DISABLED = SystemProperties.DISABLE_WIDGET_MODIFIER_CACHE.get().booleanValue();
    private static final Comparator<PostProcessor> COMPARATOR = Comparator.comparingInt((v0) -> {
        return v0.getPriority();
    });
    private final WidgetModifierCache cache;
    private final List<PostProcessor> postProcessors;
    private final List<Forwarder> forwarders;
    private final List<Query> queries;
    private final List<WidgetAttributeAliasHandler> aliasHandlers;
    private final PostProcessor fallbackPostProcessor;
    private final ElementParser elementParser;
    private final PropertyValueAccessorForwarder propertyValueAccessorForwarder;

    @Inject
    public DefaultWidgetModifier(FunctionRegistry functionRegistry, TypeParser typeParser, ElementParser elementParser, PostProcessor fallbackPostProcessor) {
        WidgetModifierCache simpleWidgetModifierCache;
        if (CACHE_DISABLED) {
            simpleWidgetModifierCache = new NOPWidgetModifierCache();
        } else {
            simpleWidgetModifierCache = new SimpleWidgetModifierCache();
        }
        this.cache = simpleWidgetModifierCache;
        this.postProcessors = new ArrayList();
        this.forwarders = new ArrayList();
        this.queries = new ArrayList();
        this.aliasHandlers = new ArrayList();
        this.propertyValueAccessorForwarder = new PropertyValueAccessorForwarder();
        this.elementParser = elementParser;
        this.fallbackPostProcessor = fallbackPostProcessor;
        functionRegistry.registerFunction("sprite", ResourceLocation.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        functionRegistry.registerFunction("location", String.class, String.class);
        functionRegistry.registerFunction("rgba", Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE);
        functionRegistry.registerFunction(ItemMetadata.RGB_KEY, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        functionRegistry.registerFunction("rgba", Integer.TYPE, Float.TYPE);
        functionRegistry.registerFunction("cubic-bezier", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        functionRegistry.registerFunction("var", String.class);
        functionRegistry.registerFunction("min", Float.TYPE, Float.TYPE);
        functionRegistry.registerFunction("max", Float.TYPE, Float.TYPE);
        for (FilterType type : FilterType.values()) {
            if (type.getTypes() != null) {
                functionRegistry.registerFunction(type.getName(), type.getTypes());
            }
        }
        registerPostProcessor(new PercentagePostProcessor(typeParser));
        registerPostProcessor(new ColorPostProcessor());
        registerPostProcessor(new AnimationTimingFunctionProcessor());
        registerPostProcessor(new ResourceLocationPostProcessor());
        registerPostProcessor(new BorderPostProcessor(this));
        registerPostProcessor(new IconPostProcessor());
        registerPostProcessor(new ComponentPostProcessor());
        registerPostProcessor(new FilterPostProcessor());
        registerPostProcessor(new VarPostProcessor(this));
        registerPostProcessor(new WidgetSizePostProcessor(this));
        registerPostProcessor(new MathPostProcessor());
        registerPostProcessor(new FontSizePostProcessor());
        registerForwarder(new WidthHeightForwarder());
        registerForwarder(new PaddingForwarder());
        registerForwarder(new MarginForwarder());
        registerForwarder(new ScaleForwarder());
        registerForwarder(new StencilForwarder());
        registerForwarder(new ShadowForwarder(this));
        registerForwarder(new HighPriorityForwarder());
        registerForwarder(new ObjectPositionForwarder(this));
        registerForwarder(new MinMaxSizeForwarder());
        registerForwarder(new BorderForwarder());
        registerForwarder(new BorderWidthForwarder());
        registerForwarder(new BorderColorForwarder());
        registerForwarder(new BorderRadiusForwarder());
        this.queries.add(new MediaQuery());
    }

    private Element parseValue(String value) {
        return this.elementParser.parseElement(value);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public boolean isVariableKey(String key) {
        return key.length() >= 3 && key.charAt(0) == '-' && key.charAt(1) == '-' && key.charAt(2) != '-';
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public void registerForwarder(Forwarder forwarder) {
        this.forwarders.add(forwarder);
        this.cache.clearForwarders();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public void registerPostProcessor(PostProcessor postProcessor) {
        this.postProcessors.add(postProcessor);
        this.postProcessors.sort(COMPARATOR);
        this.cache.clearPostProcessors();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public void registerAliasHandler(WidgetAttributeAliasHandler aliasHandler) {
        this.aliasHandlers.add(aliasHandler);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public AttributePatch makeAttributePatch(Widget widget, Forwarder forwarder, SingleInstruction instruction, String value) {
        return makeAttributePatch(widget, forwarder, instruction, value, parseValue(value));
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public AttributePatch makeAttributePatch(Widget widget, Forwarder forwarder, SingleInstruction instruction, String rawValue, Element element) {
        Class<?> type = forwarder.getType(widget, instruction.getKey(), element.getRawValue());
        if (type == null) {
            return null;
        }
        return new PropertyAttributePatch(forwarder, type, instruction, element, () -> {
            return processValue(widget, type, instruction.getKey(), rawValue, element);
        });
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public ProcessedObject[] processValue(Widget widget, Class<?> type, String key, String value) throws Exception {
        return processValue(widget, type, key, value, parseValue(value));
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public ProcessedObject[] processValue(Widget widget, Class<?> type, String key, Element element) throws Exception {
        return processValue(widget, type, key, element.getRawValue(), element);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public ProcessedObject[] processValue(Widget widget, Class<?> type, String key, String rawValue, Element value) throws Exception {
        ProcessedObject[] objects;
        if (type.isArray()) {
            if (value instanceof ElementArray) {
                ElementArray elementArray = (ElementArray) value;
                List<Element> elements = elementArray.getElements();
                if (elements.isEmpty() || elements.get(0).getRawValue().equalsIgnoreCase("null")) {
                    return null;
                }
                objects = new ProcessedObject[elements.size()];
                for (int i = 0; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    PostProcessor processor = findProcessor(key, element, type.getComponentType());
                    objects[i] = ProcessedObject.makeObject(processor, element.getRawValue(), processor.process(widget, type.getComponentType(), key, element));
                }
            } else {
                PostProcessor processor2 = findProcessor(key, value, type.getComponentType());
                Object processed = processor2.process(widget, type.getComponentType(), key, value);
                if (processed == null) {
                    return null;
                }
                objects = new ProcessedObject[]{ProcessedObject.makeObject(processor2, rawValue, processed)};
            }
            return objects;
        }
        PostProcessor processor3 = findProcessor(key, value, type);
        ProcessedObject[] objects2 = {ProcessedObject.makeObject(processor3, rawValue, processor3.process(widget, type, key, value))};
        return objects2;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public Forwarder findForwarder(Widget widget, String key) {
        String aliasKey = findAlias(widget, key);
        Forwarder cachedForwarder = this.cache.findForwarder(aliasKey);
        if (cachedForwarder != null) {
            return cachedForwarder;
        }
        Forwarder finalForwarder = this.propertyValueAccessorForwarder;
        Iterator<Forwarder> it = this.forwarders.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Forwarder forwarder = it.next();
            if (forwarder.requiresForwarding(widget, aliasKey)) {
                finalForwarder = forwarder;
                break;
            }
        }
        this.cache.addForwarder(aliasKey, finalForwarder);
        return finalForwarder;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public String findAlias(Widget widget, String key) {
        for (WidgetAttributeAliasHandler aliasHandler : this.aliasHandlers) {
            String alias = aliasHandler.mapKey(widget, key);
            if (!alias.equals(key)) {
                return findAlias(widget, alias);
            }
        }
        return key;
    }

    private PostProcessor findProcessor(String key, Element value, Class<?> type) {
        CacheKey cacheKey = this.cache.createKey(key, value, type);
        PostProcessor cachedPostProcessor = this.cache.findPostProcessor(cacheKey);
        if (cachedPostProcessor != null) {
            return cachedPostProcessor;
        }
        PostProcessor finalProcessor = this.fallbackPostProcessor;
        Iterator<PostProcessor> it = this.postProcessors.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PostProcessor postProcessor = it.next();
            if (postProcessor.requiresPostProcessing(key, value, type)) {
                finalProcessor = postProcessor;
                break;
            }
        }
        this.cache.addPostProcessor(cacheKey, finalProcessor);
        return finalProcessor;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.WidgetModifier
    public Query findQuery(String key) {
        Query result = null;
        Iterator<Query> it = this.queries.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Query query = it.next();
            if (query.matches(key)) {
                result = query;
                break;
            }
        }
        return result;
    }
}
