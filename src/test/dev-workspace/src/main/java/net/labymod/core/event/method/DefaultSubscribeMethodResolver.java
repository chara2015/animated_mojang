package net.labymod.core.event.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.exception.ListenerResolveException;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.method.SubscribeMethodList;
import net.labymod.api.event.method.SubscribeMethodResolver;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.event.method.invoker.ReflectSubscribeMethodInvoker;
import net.labymod.core.event.method.invoker.SubscribeMethodInvokerFactory;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/DefaultSubscribeMethodResolver.class */
@Singleton
@Implements(SubscribeMethodResolver.class)
public class DefaultSubscribeMethodResolver implements SubscribeMethodResolver {
    private static final int REPEAT_COUNT = 100;
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) DefaultSubscribeMethodResolver.class);
    private final LabyAPI labyAPI;
    private final Map<Class<?>, ListenerRegistry> cachedListeners = new HashMap();
    private final Map<AddonClassLoader, SubscribeMethodInvokerFactory> addonInvokerFactories = new HashMap();
    private final Map<Class<?>, LabyEvent> labyEventCache = new HashMap();
    private final SubscribeMethodInvokerFactory invokerFactory = new SubscribeMethodInvokerFactory();

    @Inject
    public DefaultSubscribeMethodResolver(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.event.method.SubscribeMethodResolver
    public ListenerRegistry resolve(@NotNull Object listener) {
        AddonClassLoader addonClassLoader;
        Class<?> listenerClass = listener.getClass();
        ListenerRegistry cachedRegistry = this.cachedListeners.get(listenerClass);
        if (cachedRegistry != null) {
            return copyRegistry(cachedRegistry, listener, false);
        }
        ClassLoader classLoader = listenerClass.getClassLoader();
        try {
            if (classLoader instanceof AddonClassLoader) {
                addonClassLoader = (AddonClassLoader) classLoader;
            } else {
                addonClassLoader = null;
            }
            ListenerRegistry registry = resolve(addonClassLoader, listener);
            this.cachedListeners.put(listenerClass, copyRegistry(registry, null, true));
            return registry;
        } catch (Throwable throwable) {
            throw new ListenerResolveException("Listener " + listenerClass.getName() + " from ClassLoader " + classLoader.getClass().getName() + " could not be resolved", throwable);
        }
    }

    @Override // net.labymod.api.event.method.SubscribeMethodResolver
    public SubscribeMethod createCustom(byte priority, Class<?> eventType, @NotNull Consumer<Event> listener) {
        LoadedAddon addon = this.labyAPI.addonService().getLastCallerAddon();
        return createCustom(addon != null ? addon.getClassLoader() : null, priority, eventType, listener);
    }

    @Override // net.labymod.api.event.method.SubscribeMethodResolver
    public SubscribeMethod createCustom(ClassLoader classLoader, byte priority, Class<?> eventType, @NotNull Consumer<Event> listener) {
        return new DefaultSubscribeMethod(classLoader instanceof AddonClassLoader ? (AddonClassLoader) classLoader : null, null, priority, null, eventType, (l, event) -> {
            listener.accept((Event) event);
        }, null);
    }

    private ListenerRegistry copyRegistry(ListenerRegistry registry, Object newListener, boolean internal) {
        DefaultListenerRegistry copy = new DefaultListenerRegistry();
        for (SubscribeMethodList list : registry.getListeners().values()) {
            for (SubscribeMethod method : list.getSubscribeMethods()) {
                SubscribeMethod methodCopy = method.copy(newListener);
                if (internal) {
                    copy.registerInternal(methodCopy);
                } else {
                    copy.register(methodCopy);
                }
            }
        }
        return copy;
    }

    private ListenerRegistry resolve(@Nullable AddonClassLoader classLoader, @NotNull Object listener) {
        SubscribeMethodInvoker methodInvoker;
        ListenerRegistry registry = new DefaultListenerRegistry();
        Class<?> listenerClass = listener.getClass();
        List<Method> invalidMethods = new ArrayList<>();
        for (Method method : listenerClass.getMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                int parameterCount = method.getParameterCount();
                if (parameterCount != 1) {
                    invalidMethods.add(method);
                } else {
                    Class<?> eventClass = method.getParameterTypes()[0];
                    if (Event.class.isAssignableFrom(eventClass)) {
                        if (classLoader == null) {
                            methodInvoker = getMethodInvoker(this.invokerFactory, method, eventClass);
                        } else {
                            SubscribeMethodInvokerFactory invokerFactory = this.addonInvokerFactories.computeIfAbsent(classLoader, factory -> {
                                return new SubscribeMethodInvokerFactory(classLoader);
                            });
                            methodInvoker = getMethodInvoker(invokerFactory, method, eventClass);
                        }
                        Subscribe subscribe = (Subscribe) method.getAnnotation(Subscribe.class);
                        registry.getListeners().computeIfAbsent(eventClass, list -> {
                            return new DefaultSubscribeMethodList();
                        }).add(new DefaultSubscribeMethod(classLoader, listener, subscribe.value(), method, eventClass, methodInvoker, getLabyEvent(eventClass)));
                    }
                }
            }
        }
        if (!invalidMethods.isEmpty() && IdeUtil.RUNNING_IN_IDE) {
            throw new IllegalStateException(buildInvalidMethodErrorMessage(listenerClass, invalidMethods));
        }
        if (registry.getListeners().isEmpty()) {
            LOGGER.debug("No listeners for events were found in class \"{}\"!", listenerClass.getSimpleName());
        }
        return registry;
    }

    private SubscribeMethodInvoker getMethodInvoker(SubscribeMethodInvokerFactory invokerFactory, Method method, Class<?> eventClass) {
        SubscribeMethodInvoker methodInvoker = invokerFactory.create(method, eventClass);
        if (methodInvoker == null) {
            methodInvoker = new ReflectSubscribeMethodInvoker(method);
        }
        return methodInvoker;
    }

    private LabyEvent getLabyEvent(Class<?> eventClass) {
        LabyEvent event = this.labyEventCache.get(eventClass);
        if (event != null) {
            return event;
        }
        LabyEvent annotation = (LabyEvent) eventClass.getAnnotation(LabyEvent.class);
        this.labyEventCache.put(eventClass, annotation);
        return annotation;
    }

    private String buildInvalidMethodErrorMessage(Class<?> listenerClass, List<Method> invalidMethods) {
        String listenerClassName = listenerClass.getName();
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("\n").append("#".repeat(REPEAT_COUNT));
        bobTheBuilder.append("\nSome methods had no or more than one parameter: ");
        for (Method invalidMethod : invalidMethods) {
            int parameterCount = invalidMethod.getParameterCount();
            bobTheBuilder.append("\n\t - ").append(listenerClassName).append(".").append(invalidMethod.getName()).append("(");
            String[] names = new String[parameterCount];
            for (int index = 0; index < parameterCount; index++) {
                Class<?> parameterType = invalidMethod.getParameterTypes()[index];
                names[index] = parameterType.getName();
            }
            bobTheBuilder.append(String.join(", ", names));
            bobTheBuilder.append(")");
        }
        bobTheBuilder.append("\n").append("#".repeat(REPEAT_COUNT));
        return bobTheBuilder.toString();
    }
}
