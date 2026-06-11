package net.labymod.api.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.classloader.ClassLoaderUtil;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CrossVersionServiceLoader.class */
@Deprecated
public class CrossVersionServiceLoader<T> implements Iterable<T> {
    private static final Logging LOGGER = new CrossVersionServiceLoaderLogging();
    private static final String LABYMOD_SERVICES_DIRECTORY = "META-INF/services/";
    private final List<T> serviceClasses = new ArrayList();
    private final Class<T> serviceClass;
    private final ClassLoader loader;

    private CrossVersionServiceLoader(Class<T> serviceClass, ClassLoader loader) {
        this.serviceClass = serviceClass;
        this.loader = loader;
        prepare();
    }

    private void prepare() {
        Constructor<?> constructorSearchEmptyConstructor;
        try {
            ArrayList<String> arrayList = new ArrayList();
            String str = "META-INF/services/" + this.serviceClass.getName();
            Enumeration<URL> resources = PlatformEnvironment.getResources(this.loader, str);
            while (resources.hasMoreElements()) {
                String[] strArrSplit = IOUtil.toString(resources.nextElement().openStream()).split("\n");
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : strArrSplit) {
                    String[] strArrSplit2 = str2.split(";", 2);
                    try {
                        double d = Double.parseDouble(strArrSplit2[1]);
                        if (ASMHelper.canLoadClass((int) d, ASMHelper.getCurrentJavaClassVersion())) {
                            arrayList2.add(Pair.of(strArrSplit2[0], Double.valueOf(d)));
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.error("Invalid service format. ({})", str2);
                    }
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) ((Pair) it.next()).getFirst());
                }
            }
            for (String strTrim : arrayList) {
                try {
                    strTrim = strTrim.trim();
                    constructorSearchEmptyConstructor = Reflection.searchEmptyConstructor(ClassLoaderUtil.forName(str, false, this.loader));
                } catch (Exception e2) {
                    if (e2 instanceof ClassNotFoundException) {
                        LOGGER.error("The class \"{}\" was not found.", strTrim, e2);
                    } else {
                        LOGGER.error("An error occurred while invoking the constructor.", e2);
                    }
                }
                if (constructorSearchEmptyConstructor != null) {
                    this.serviceClasses.add((T) constructorSearchEmptyConstructor.newInstance(new Object[0]));
                }
            }
        } catch (IOException e3) {
            LOGGER.error("An error occurred while reading the {} services.", Constants.Branding.NAME, e3);
        }
    }

    public static <T> CrossVersionServiceLoader<T> load(Class<T> serviceClass, ClassLoader loader) {
        return new CrossVersionServiceLoader<>(serviceClass, loader);
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<T> iterator() {
        return this.serviceClasses.iterator();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CrossVersionServiceLoader$CrossVersionServiceLoaderLogging.class */
    private static class CrossVersionServiceLoaderLogging implements Logging {
        private CrossVersionServiceLoaderLogging() {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void info(CharSequence message, Throwable throwable) {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void info(CharSequence message, Object... arguments) {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void warn(CharSequence message, Throwable throwable) {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void warn(CharSequence message, Object... arguments) {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void error(CharSequence message, Throwable throwable) {
            System.out.println(message);
            System.out.println("Stacktrace: ");
            for (StackTraceElement element : throwable.getStackTrace()) {
                System.out.println("\t" + element.getClassName() + "/" + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
            }
        }

        @Override // net.labymod.api.util.logging.Logging
        public void error(CharSequence message, Object... arguments) {
            System.out.println(String.format(Locale.ROOT, CharSequences.toString(message), arguments));
        }

        @Override // net.labymod.api.util.logging.Logging
        public void debug(CharSequence message, Throwable throwable) {
        }

        @Override // net.labymod.api.util.logging.Logging
        public void debug(CharSequence message, Object... arguments) {
        }
    }
}
