package net.labymod.api.client.gui.screen.widget.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenUpdateVanillaWidgetEvent;
import net.labymod.api.event.client.gui.screen.VanillaWidgetReplacementEvent;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/WidgetConverterRegistry.class */
@Singleton
@Referenceable
public final class WidgetConverterRegistry {
    private final StyleSheetLoader styleSheetLoader;
    private final ThemeRendererParser themeRendererParser;
    private Class<?> currentScreen;
    private boolean screenExcluded;
    private final Set<Class<? extends WidgetConverterInitializer>> initializers = new HashSet();
    private final Set<WidgetWatcher<?>> widgetWatchers = new HashSet();
    private final Map<AbstractWidgetConverter<?, ?>, List<Class<?>>> widgetConverters = new HashMap();
    private final Map<Class<?>, AbstractWidgetConverter<?, ?>> classLookup = new HashMap();
    private final List<StyleSheet> styleSheets = new ArrayList();
    private final List<ExclusionStrategy> exclusionStrategies = new ArrayList();
    private final LabyAPI labyAPI = Laby.labyAPI();

    @Inject
    public WidgetConverterRegistry() {
        this.labyAPI.eventBus().registerListener(this);
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        this.themeRendererParser = Laby.references().themeRendererParser();
    }

    public void initializeStyle() {
        this.styleSheets.clear();
        String namespace = Laby.labyAPI().getNamespace(this);
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        ThemeFile file = ThemeFile.create(theme, namespace, "lss/minecraft-widget.lss");
        StyleSheet styleSheet = this.styleSheetLoader.load(file);
        if (styleSheet != null) {
            this.styleSheets.add(styleSheet);
        }
        invalidateWatchers();
    }

    public void sync(WidgetWatcher<?> widgetWatcher, Object source, AbstractWidget<?> destination) {
        AbstractWidgetConverter converter;
        if (this.screenExcluded) {
            return;
        }
        Class<?> sourceClass = source.getClass();
        if (isExcluded(sourceClass) || (converter = getConverter(sourceClass)) == null) {
            return;
        }
        converter.update(source, destination, widgetWatcher.getReplacementStrategy());
        Laby.fireEvent(new ScreenUpdateVanillaWidgetEvent(destination));
    }

    @Nullable
    public AbstractWidget convertWidget(Object source, AbstractWidgetConverter converter) {
        return convertWidget(source, converter, null);
    }

    @Nullable
    public AbstractWidget convertWidget(Object source, AbstractWidgetConverter converter, @Nullable Consumer<WidgetReplacementStrategy> widgetReplacementListener) {
        if (this.screenExcluded) {
            return null;
        }
        Class<?> sourceClass = source.getClass();
        if (isExcluded(sourceClass)) {
            return null;
        }
        AbstractWidget<?> abstractWidgetConvert = converter.convert(source);
        String currentScreenName = this.labyAPI.screenService().getScreenNameByClass(this.currentScreen);
        if (currentScreenName != null) {
            String screenId = String.format(Locale.ROOT, "%s-screen", currentScreenName.toLowerCase(Locale.ENGLISH).replace('_', '-'));
            abstractWidgetConvert.addId(screenId);
        }
        List<String> widgetIds = converter.findWidgetIds(source);
        if (!widgetIds.isEmpty()) {
            for (String widgetId : widgetIds) {
                abstractWidgetConvert.addId(widgetId.toLowerCase(Locale.ROOT).replace('.', '-') + "-widget");
            }
        }
        String[] segments = sourceClass.getTypeName().split("\\.");
        String className = segments[segments.length - 1];
        for (String part : className.split("\\$")) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                abstractWidgetConvert.addId(TextFormat.CAMEL_CASE.toDashCase(part));
            }
        }
        VanillaWidgetReplacementEvent event = (VanillaWidgetReplacementEvent) Laby.fireEvent(new VanillaWidgetReplacementEvent(abstractWidgetConvert));
        WidgetReplacementStrategy replacementStrategy = event.getReplacementStrategy();
        if (replacementStrategy == null) {
            replacementStrategy = WidgetReplacementStrategies.DEFAULT;
        }
        if (event.isCancelled() && replacementStrategy != WidgetReplacementStrategies.DEFAULT) {
            AbstractWidget<?> replacementWidget = replacementStrategy.createReplacement();
            List<CharSequence> ids = abstractWidgetConvert.getIds();
            for (CharSequence id : ids) {
                replacementWidget.addId(id);
            }
            abstractWidgetConvert = replacementWidget;
            if (widgetReplacementListener != null) {
                widgetReplacementListener.accept(replacementStrategy);
            }
            Bounds bounds = replacementWidget.bounds();
            if (bounds.hasSize()) {
                updateMinecraftWidgetBounds(source, replacementWidget);
            }
        }
        updateThemeRenderer(abstractWidgetConvert, converter);
        initializeWidget(abstractWidgetConvert, converter);
        return abstractWidgetConvert;
    }

    private void updateMinecraftWidgetBounds(Object source, AbstractWidget<?> replacementWidget) {
        MinecraftWidgetBounds minecraftBounds = MinecraftWidgetBounds.self(source);
        if (minecraftBounds == null) {
            return;
        }
        ReasonableMutableRectangle outerBounds = replacementWidget.bounds().rectangle(BoundsType.OUTER);
        minecraftBounds.setBoundsX(MathHelper.ceil(outerBounds.getX()));
        minecraftBounds.setBoundsY(MathHelper.ceil(outerBounds.getY()));
        minecraftBounds.setBoundsWidth(MathHelper.ceil(outerBounds.getWidth()));
        minecraftBounds.setBoundsHeight(MathHelper.ceil(outerBounds.getHeight()));
    }

    @Nullable
    public AbstractWidgetConverter getConverter(Class<?> cls) {
        AbstractWidgetConverter<?, ?> converter = this.classLookup.get(cls);
        if (converter != null) {
            return converter;
        }
        Class<?> noneAnonymousClass = Reflection.getNoneAnonymousClass(cls);
        while (true) {
            Class<?> clazz = noneAnonymousClass;
            if (clazz != Object.class) {
                for (Map.Entry<AbstractWidgetConverter<?, ?>, List<Class<?>>> entry : this.widgetConverters.entrySet()) {
                    for (Class<?> widgetClass : entry.getValue()) {
                        if (widgetClass == clazz) {
                            AbstractWidgetConverter<?, ?> converter2 = entry.getKey();
                            this.classLookup.putIfAbsent(cls, converter2);
                            return converter2;
                        }
                    }
                }
                noneAnonymousClass = clazz.getSuperclass();
            } else {
                return null;
            }
        }
    }

    public void initializeWidget(AbstractWidget<?> widget, AbstractWidgetConverter<?, ?> converter) {
        widget.initialize(converter);
        widget.postInitialize();
        for (StyleSheet styleSheet : this.styleSheets) {
            widget.applyStyleSheet(styleSheet);
        }
        LinkMetaList meta = Laby.references().linkMetaLoader().getMeta(widget.getClass());
        if (meta != null) {
            for (LinkReference link : meta.getLinks()) {
                widget.applyStyleSheet(link.loadStyleSheet());
            }
        }
    }

    public Optional<AbstractWidgetConverter> findConverter(Class<?> cls) {
        return Optional.ofNullable(getConverter(cls));
    }

    public Optional<AbstractWidgetConverter> findConverter(String name) {
        for (AbstractWidgetConverter<?, ?> abstractWidgetConverter : this.widgetConverters.keySet()) {
            if (Objects.equals(name, abstractWidgetConverter.getName())) {
                return Optional.of(abstractWidgetConverter);
            }
        }
        return Optional.empty();
    }

    public Optional<AbstractWidgetConverter> findConverter(Enum<?> enumType) {
        return findConverter(enumType.toString());
    }

    public void exclude(ExclusionStrategy strategy) {
        this.exclusionStrategies.add(strategy);
    }

    public void exclude(Class<?> cls) {
        exclude(ExclusionStrategy.screen(cls));
    }

    public void exclude(Class<?>... classes) {
        for (Class<?> cls : classes) {
            exclude(cls);
        }
    }

    public boolean isExcluded(Class<?> cls) {
        for (ExclusionStrategy strategy : this.exclusionStrategies) {
            if (strategy.shouldExclude(cls)) {
                return true;
            }
        }
        return false;
    }

    public void register(AbstractWidgetConverter<?, ?> converter, Class<?>... classes) {
        List<Class<?>> classList = this.widgetConverters.computeIfAbsent(converter, list -> {
            return new ArrayList(classes.length);
        });
        for (Class<?> cls : classes) {
            if (!classList.contains(cls)) {
                classList.add(cls);
            }
        }
    }

    public void registerIfPresent(String name, Class<?>... classes) {
        findConverter(converter -> {
            return Boolean.valueOf(Objects.equals(name, converter.getName()));
        }, list -> {
            Collections.addAll(list, classes);
        }, () -> {
            return new IllegalStateException("No WidgetConverter with the name \"" + name + "\" could be found.");
        });
    }

    public void registerIfPresent(Class<? extends AbstractWidgetConverter<?, ?>> converterClass, Class<?>... classes) {
        findConverter(converter -> {
            return Boolean.valueOf(converter.getClass() == converterClass);
        }, list -> {
            Collections.addAll(list, classes);
        }, () -> {
            return new IllegalStateException(converterClass.getName() + " could not be found.");
        });
    }

    public <W extends AbstractWidget<?>> void registerWatcher(WidgetWatcher watcher) {
        this.widgetWatchers.add(watcher);
    }

    private void updateThemeRenderer(AbstractWidget<?> widget, AbstractWidgetConverter<?, ?> converter) {
        ThemeRenderer<Widget> renderer = this.themeRendererParser.parse(converter.getName());
        if (renderer != null) {
            widget.renderer().set(renderer);
        }
    }

    private void findConverter(Function<AbstractWidgetConverter<?, ?>, Boolean> searchFunction, Consumer<List<Class<?>>> listConsumer, Supplier<? extends RuntimeException> throwableSupplier) {
        boolean found = false;
        Iterator<Map.Entry<AbstractWidgetConverter<?, ?>, List<Class<?>>>> it = this.widgetConverters.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<AbstractWidgetConverter<?, ?>, List<Class<?>>> entry = it.next();
            AbstractWidgetConverter<?, ?> converter = entry.getKey();
            if (searchFunction.apply(converter).booleanValue()) {
                listConsumer.accept(entry.getValue());
                found = true;
                break;
            }
        }
        if (found) {
        } else {
            throw throwableSupplier.get();
        }
    }

    @Subscribe
    public void onThemeUpdate(ThemeUpdateEvent event) {
        invalidateWatchers();
    }

    @Subscribe
    public void onVersionedScreenInit(VersionedScreenInitEvent event) {
        Object screen = event.getVersionedScreen();
        Object currentScreen = this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen();
        if (screen != currentScreen) {
            if (currentScreen instanceof LabyScreenAccessor) {
                LabyScreenAccessor accessor = (LabyScreenAccessor) currentScreen;
                if (accessor.screen().mostInnerScreen() != screen) {
                    return;
                }
            } else {
                return;
            }
        }
        Class<?> currentScreenClass = screen.getClass();
        this.screenExcluded = isExcluded(currentScreenClass);
        this.currentScreen = currentScreenClass;
        if (!(screen instanceof LabyScreenAccessor)) {
            invalidateWatchers();
        }
    }

    @Subscribe
    public void onServiceLoad(ServiceLoadEvent serviceLoadEvent) {
        for (WidgetConverterInitializer widgetConverterInitializer : serviceLoadEvent.load(WidgetConverterInitializer.class, CustomServiceLoader.ServiceType.ADVANCED)) {
            if (this.initializers.add((Class<? extends WidgetConverterInitializer>) widgetConverterInitializer.getClass())) {
                widgetConverterInitializer.initialize(this);
            }
        }
    }

    private void invalidateWatchers() {
        for (WidgetWatcher<?> widgetWatcher : this.widgetWatchers) {
            widgetWatcher.invalidate();
        }
        this.widgetWatchers.clear();
    }
}
