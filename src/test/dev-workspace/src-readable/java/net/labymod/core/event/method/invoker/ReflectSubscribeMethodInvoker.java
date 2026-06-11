package net.labymod.core.event.method.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.labymod.core.event.method.SubscribeMethodInvoker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/invoker/ReflectSubscribeMethodInvoker.class */
public class ReflectSubscribeMethodInvoker implements SubscribeMethodInvoker {
    private final Method method;

    public ReflectSubscribeMethodInvoker(Method method) {
        this.method = method;
    }

    @Override // net.labymod.core.event.method.SubscribeMethodInvoker
    public void invoke(Object listener, Object event) throws Throwable {
        try {
            this.method.invoke(listener, event);
        } catch (InvocationTargetException exception) {
            throw exception.getCause();
        }
    }
}
