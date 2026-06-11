package net.labymod.api.volt.rename;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.volt.asm.util.ASMHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/rename/ClassProvider.class */
public class ClassProvider {
    private static ClassProvider singleton;
    private final Map<String, ClassInfo> classes = new ConcurrentHashMap();
    private final ResourceSupplier resourceSupplier;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/rename/ClassProvider$ResourceSupplier.class */
    public interface ResourceSupplier {
        URL findResource(String str);
    }

    public ClassProvider(ResourceSupplier resourceSupplier) {
        this.resourceSupplier = resourceSupplier;
    }

    public static ClassProvider getSingleton(ResourceSupplier supplier) {
        if (singleton == null) {
            singleton = new ClassProvider(supplier);
        }
        return singleton;
    }

    public ClassInfo getOrLoad(String name) {
        if (name == null) {
            return null;
        }
        ClassInfo classInfo = this.classes.get(name);
        if (classInfo != null) {
            return classInfo;
        }
        String resourceName = name.replace('.', '/').concat(".class");
        URL resource = this.resourceSupplier.findResource(resourceName);
        if (resource == null) {
            return null;
        }
        try {
            InputStream stream = resource.openStream();
            try {
                ClassInfo classInfo2 = ClassInfo.parse(this, ASMHelper.getClassNode(stream));
                this.classes.put(name, classInfo2);
                if (stream != null) {
                    stream.close();
                }
                return classInfo2;
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load class info", exception);
        }
    }
}
