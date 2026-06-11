package net.labymod.api.util.reflection;

import java.lang.StackWalker;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.labymod.api.util.MethodOrder;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.reflection.exception.ReflectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/reflection/Reflection.class */
public final class Reflection {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final Map<Class<?>, List<Class<?>>> CLASS_TREES = new HashMap();
    private static final Map<FieldKey, Field> FIELDS = new HashMap();
    private static final String DEFAULT_NULL_NAME = "<NULL>";
    private static final String STATIC_CONSTRUCTOR_NAME = "<clinit>";
    private static final int DEFAULT_SKIP_LEVEL = 3;

    private Reflection() {
    }

    public static Class<?> getCallerClass() {
        return getCallerClass(3);
    }

    public static Class<?> getCallerClass(int skipLevel) {
        return (Class) ((Optional) WALKER.walk(s -> {
            return s.map((v0) -> {
                return v0.getDeclaringClass();
            }).skip(skipLevel).findFirst();
        })).orElseThrow(() -> {
            return new IllegalStateException("Could not find caller class");
        });
    }

    public static void validateStaticConstructorInvocation(int depth, Supplier<String> errorMessage) {
        if (!IdeUtil.RUNNING_IN_IDE) {
            return;
        }
        int skipLevel = depth + 1;
        StackWalker.StackFrame frame = (StackWalker.StackFrame) WALKER.walk(stream -> {
            return (StackWalker.StackFrame) stream.skip(skipLevel).findFirst().orElse(null);
        });
        StackTraceElement element = frame.toStackTraceElement();
        if (!STATIC_CONSTRUCTOR_NAME.equals(element.getMethodName())) {
            throw new IllegalStateException(errorMessage.get());
        }
    }

    public static String getClassName(@Nullable Object obj) {
        if (obj == null) {
            return DEFAULT_NULL_NAME;
        }
        return getClassName(obj.getClass());
    }

    public static String getClassName(@Nullable Class<?> cls) {
        if (cls == null) {
            return DEFAULT_NULL_NAME;
        }
        return cls.getName();
    }

    public static Class<?> getNoneAnonymousClass(Class<?> cls) {
        while (cls.isAnonymousClass()) {
            cls = cls.getSuperclass();
        }
        return cls;
    }

    @NotNull
    public static List<Class<?>> getClassTree(Class<?> clazz) {
        List<Class<?>> classes = null;
        try {
            List<Class<?>> classes2 = CLASS_TREES.get(clazz);
            if (classes2 != null) {
                return classes2;
            }
            classes = getClassTree0(clazz);
            CLASS_TREES.put(clazz, classes);
            return classes;
        } catch (Exception exception) {
            System.err.println("Failed to cache class tree for " + clazz.getName());
            exception.printStackTrace(System.err);
            return classes == null ? getClassTree0(clazz) : classes;
        }
    }

    private static List<Class<?>> getClassTree0(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(clazz);
        while (clazz != Object.class) {
            clazz = clazz.getSuperclass();
            classes.add(clazz);
        }
        return classes;
    }

    @Nullable
    public static Field getField(Class<?> clazz, String name) {
        List<Class<?>> classTree = getClassTree(clazz);
        int cacheCount = 0;
        Iterator<Class<?>> it = classTree.iterator();
        while (it.hasNext()) {
            FieldKey fieldKey = new FieldKey(it.next(), name);
            if (FIELDS.containsKey(fieldKey)) {
                cacheCount++;
                Field field = FIELDS.get(fieldKey);
                if (field != null) {
                    return field;
                }
            }
        }
        if (cacheCount == classTree.size()) {
            return null;
        }
        for (Class<?> treeClass : classTree) {
            try {
                Field field2 = treeClass.getDeclaredField(name);
                field2.setAccessible(true);
                FIELDS.put(new FieldKey(treeClass, name), field2);
                return field2;
            } catch (Exception e) {
            }
        }
        Iterator<Class<?>> it2 = classTree.iterator();
        while (it2.hasNext()) {
            FIELDS.put(new FieldKey(it2.next(), name), null);
        }
        return null;
    }

    public static <T> T getFieldValue(Object obj, String str) {
        Field field = getField(obj.getClass(), str);
        if (field == null) {
            return null;
        }
        return (T) getFieldValue(obj, field);
    }

    public static <T> T getFieldValue(Object obj, Field field) {
        try {
            return (T) getUnreflectFieldValue(obj, field);
        } catch (ReflectionException e) {
            field.setAccessible(true);
            try {
                return (T) field.get(obj);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static <T> T getUnreflectFieldValue(Object obj, Field field) throws ReflectionException {
        try {
            return (T) MethodHandles.publicLookup().unreflectGetter(field).invokeWithArguments(obj);
        } catch (Throwable th) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not get field value %s.%s", obj.getClass().getName(), field.getName()), th);
        }
    }

    public static <T extends Annotation> T getAnnotation(Class<?> cls, Class<T> cls2) {
        for (Class<?> cls3 : getClassTree(cls)) {
            if (cls3.isAnnotationPresent(cls2)) {
                return (T) cls3.getAnnotation(cls2);
            }
        }
        return null;
    }

    public static void getFields(Class<?> cls, boolean reversed, Consumer<Field> fieldConsumer) {
        if (fieldConsumer == null) {
            return;
        }
        List<Class<?>> classTree = getClassTree(cls);
        if (reversed) {
            for (int index = classTree.size(); index > 0; index--) {
                Class<?> clazz = classTree.get(index - 1);
                for (Field declaredField : clazz.getDeclaredFields()) {
                    fieldConsumer.accept(declaredField);
                }
            }
            return;
        }
        for (Class<?> clazz2 : classTree) {
            for (Field declaredField2 : clazz2.getDeclaredFields()) {
                fieldConsumer.accept(declaredField2);
            }
        }
    }

    public static void getMethods(Class<?> cls, boolean reversed, Consumer<Method> methodConsumer) {
        List<Class<?>> classTree = getClassTree(cls);
        if (methodConsumer == null) {
            return;
        }
        if (reversed) {
            for (int index = classTree.size(); index > 0; index--) {
                Class<?> clazz = classTree.get(index - 1);
                if (clazz != Object.class) {
                    for (Method declaredMethod : clazz.getDeclaredMethods()) {
                        methodConsumer.accept(declaredMethod);
                    }
                }
            }
            return;
        }
        for (Class<?> clazz2 : classTree) {
            if (clazz2 != Object.class) {
                for (Method declaredMethod2 : clazz2.getDeclaredMethods()) {
                    methodConsumer.accept(declaredMethod2);
                }
            }
        }
    }

    public static <T extends Member & AnnotatedElement> void getMembers(Class<?> cls, boolean reversed, Consumer<T> methodConsumer) {
        List<Class<?>> classTree = getClassTree(cls);
        if (methodConsumer == null) {
            return;
        }
        if (reversed) {
            for (int index = classTree.size(); index > 0; index--) {
                Class<?> clazz = classTree.get(index - 1);
                getMembers(clazz, methodConsumer);
            }
            return;
        }
        for (Class<?> clazz2 : classTree) {
            getMembers(clazz2, methodConsumer);
        }
    }

    private static <T extends Member & AnnotatedElement> void getMembers(Class<?> clazz, Consumer<T> memberConsumer) {
        if (clazz == Object.class) {
            return;
        }
        Collection<Method> orderedMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MethodOrder.class)) {
                orderedMethods.add(method);
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            sort(orderedMethods, field, memberConsumer);
        }
    }

    private static <T extends Member & AnnotatedElement> void sort(Collection<Method> orderedMethods, Member member, Consumer<T> memberConsumer) {
        for (Method orderedMethod : orderedMethods) {
            if (orderedMethod != member && ((MethodOrder) orderedMethod.getAnnotation(MethodOrder.class)).before().equals(member.getName())) {
                sort(orderedMethods, orderedMethod, memberConsumer);
            }
        }
        memberConsumer.accept(member);
        for (Method method : orderedMethods) {
            if (method != member && ((MethodOrder) method.getAnnotation(MethodOrder.class)).after().equals(member.getName())) {
                sort(orderedMethods, method, memberConsumer);
            }
        }
    }

    public static Constructor<?> searchEmptyConstructor(Class<?> cls) {
        Constructor<?> emptyConstructor = null;
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        int length = declaredConstructors.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> declaredConstructor = declaredConstructors[i];
            if (declaredConstructor.getParameterCount() != 0) {
                i++;
            } else {
                emptyConstructor = declaredConstructor;
                break;
            }
        }
        return emptyConstructor;
    }

    public static MethodHandle findVirtual(Class<?> reference, String name, MethodType methodType) throws IllegalAccessException, NoSuchMethodException {
        return MethodHandles.publicLookup().findVirtual(reference, name, methodType);
    }

    public static MethodHandle findStatic(Class<?> reference, String name, MethodType methodType) throws IllegalAccessException, NoSuchMethodException {
        return MethodHandles.publicLookup().findStatic(reference, name, methodType);
    }

    public static <T> T invokeGetterField(Object obj, Field field) {
        try {
            return (T) invokeUnreflectGetterField(obj, field);
        } catch (ReflectionException e) {
            field.setAccessible(true);
            try {
                return (T) field.get(obj);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static void invokeSetterField(Object instance, Field field, Object value) {
        try {
            invokeUnreflectSetterField(instance, field, value);
        } catch (ReflectionException e) {
            field.setAccessible(true);
            try {
                field.set(instance, value);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void invokeUnreflectSetterField(Object instance, Field field, Object value) throws ReflectionException {
        try {
            MethodHandle methodHandle = MethodHandles.publicLookup().unreflectSetter(field);
            methodHandle.invokeWithArguments(instance, value);
        } catch (Throwable throwable) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not invoke setter field %s.%s (Value: %s)", instance.getClass().getName(), field.getName(), value), throwable);
        }
    }

    public static <T> T invokeUnreflectGetterField(Object obj, Field field) throws ReflectionException {
        try {
            return (T) MethodHandles.publicLookup().unreflectGetter(field).invokeWithArguments(obj);
        } catch (Throwable th) {
            throw new ReflectionException(String.format(Locale.ROOT, "Could not invoke getter field %s.%s", obj.getClass().getName(), field.getName()), th);
        }
    }

    public static <T> T instantiateWithArgs(Class<?> cls, Object... objArr) throws ReflectiveOperationException {
        Class<?>[] clsArrBuildParameterTypes = buildParameterTypes(objArr);
        Constructor<?> constructor = null;
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        int length = declaredConstructors.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor2 = declaredConstructors[i];
            if (constructor2.getParameterCount() != objArr.length || !Arrays.equals(constructor2.getParameterTypes(), clsArrBuildParameterTypes)) {
                i++;
            } else {
                constructor = constructor2;
                break;
            }
        }
        if (constructor == null) {
            throw new IllegalStateException("No constructor found in " + cls.getName() + " with " + ((String) Arrays.stream(clsArrBuildParameterTypes).map((v0) -> {
                return v0.getName();
            }).collect(Collectors.joining(", "))) + " parameters");
        }
        constructor.setAccessible(true);
        return (T) constructor.newInstance(objArr);
    }

    public static Class<?>[] buildParameterTypes(Object[] arguments) {
        Class<?>[] parameterTypes = new Class[arguments.length];
        for (int index = 0; index < parameterTypes.length; index++) {
            parameterTypes[index] = arguments[index].getClass();
        }
        return parameterTypes;
    }

    public static boolean isType(Type type, Class<?>... classes) {
        for (Class<?> cls : classes) {
            if (type == cls) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMethodOverridden(Class<?> superClass, Class<?> subClass, String name, Class<?>... parameters) {
        try {
            Method superClassMethod = superClass.getDeclaredMethod(name, parameters);
            Method subClassMethod = subClass.getDeclaredMethod(name, parameters);
            if (!Modifier.isAbstract(superClassMethod.getModifiers())) {
                if (!subClassMethod.equals(superClassMethod)) {
                    return true;
                }
            }
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/reflection/Reflection$FieldKey.class */
    private static final class FieldKey extends Record {
        private final Class<?> clazz;
        private final String field;

        private FieldKey(Class<?> clazz, String field) {
            this.clazz = clazz;
            this.field = field;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FieldKey.class), FieldKey.class, "clazz;field", "FIELD:Lnet/labymod/api/util/reflection/Reflection$FieldKey;->clazz:Ljava/lang/Class;", "FIELD:Lnet/labymod/api/util/reflection/Reflection$FieldKey;->field:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public Class<?> clazz() {
            return this.clazz;
        }

        public String field() {
            return this.field;
        }

        @Override // java.lang.Record
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FieldKey fieldKey = (FieldKey) o;
            if (!Objects.equals(this.clazz, fieldKey.clazz)) {
                return false;
            }
            return Objects.equals(this.field, fieldKey.field);
        }

        @Override // java.lang.Record
        public int hashCode() {
            int result = this.clazz != null ? this.clazz.hashCode() : 0;
            return (31 * result) + (this.field != null ? this.field.hashCode() : 0);
        }
    }
}
