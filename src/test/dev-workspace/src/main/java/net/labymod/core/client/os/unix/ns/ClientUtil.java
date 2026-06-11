package net.labymod.core.client.os.unix.ns;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/unix/ns/ClientUtil.class */
public final class ClientUtil {
    public static Proxy newArray(Proxy... objects) {
        return newArray(Client.getInstance(), objects);
    }

    public static Proxy newArray(Client client, Proxy... objets) {
        Proxy arrayProxy = client.sendProxy("NSMutableArray", "array", new Object[0]);
        for (Proxy objet : objets) {
            arrayProxy.send("addObject:", new Object[]{objet});
        }
        return arrayProxy;
    }

    public static Proxy newNSImageInitWithData(Proxy nsData) {
        return newNSImageInitWithData(Client.getInstance(), nsData);
    }

    public static Proxy newNSImageInitWithData(Client client, Proxy nsData) {
        return client.sendProxy("NSImage", "alloc", new Object[0]).sendProxy("initWithData:", new Object[]{nsData});
    }

    public static Proxy newNSData(byte[] buffer) {
        return newNSData(Client.getInstance(), buffer);
    }

    public static Proxy newNSData(Client client, byte[] buffer) {
        return client.sendProxy("NSData", "dataWithBytes:length:", new Object[]{buffer, Integer.valueOf(buffer.length)});
    }

    public static Proxy allocNSData() {
        return allocNSData(Client.getInstance());
    }

    public static Proxy allocNSData(Client client) {
        return client.sendProxy("NSData", "alloc", new Object[0]);
    }

    public static Proxy allocNSImage() {
        return allocNSImage(Client.getInstance());
    }

    public static Proxy allocNSImage(Client client) {
        return client.sendProxy("NSImage", "alloc", new Object[0]);
    }

    public static Proxy objectAtIndex(Proxy proxy, int index) {
        return proxy.sendProxy("objectAtIndex:", new Object[]{Integer.valueOf(index)});
    }

    public static Object rawObjectAtIndex(Proxy proxy, int index) {
        return proxy.send("objectAtIndex:", new Object[]{Integer.valueOf(index)});
    }

    public static <T> Collection<T> forEach(Proxy object, String selector, Function<Object, T> mappingFunction) {
        Proxy result = object.sendProxy(selector, new Object[0]);
        int count = result.sendInt("count", new Object[0]);
        List<T> entries = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            entries.add(mappingFunction.apply(rawObjectAtIndex(result, index)));
        }
        return entries;
    }

    public static <T> Optional<Proxy> findFirst(Collection<T> entries, Proxy object, String selector) {
        Proxy finalResult = null;
        for (T entry : entries) {
            finalResult = object.sendProxy(selector, new Object[]{entry});
            if (finalResult != null) {
                break;
            }
        }
        return Optional.ofNullable(finalResult);
    }
}
