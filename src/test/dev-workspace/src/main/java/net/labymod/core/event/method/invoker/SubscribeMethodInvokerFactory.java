package net.labymod.core.event.method.invoker;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.volt.generator.ClassBytecodeComposer;
import net.labymod.api.volt.generator.ClassGenerator;
import net.labymod.core.event.method.SubscribeMethodInvoker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory.class */
public class SubscribeMethodInvokerFactory extends ClassGenerator {
    private static final String GENERATED_ASM_PACKAGE = "net.labymod.autogen.events.generated";
    private final AtomicInteger id;
    private final String sessionId;
    private static final Logging LOGGER = Logging.create((Class<?>) SubscribeMethodInvokerFactory.class);
    private static final String[] GENERATED_SUBSCRIBE_METHOD_INVOKER = {Type.getInternalName(SubscribeMethodInvoker.class)};

    public SubscribeMethodInvokerFactory() {
        this(SubscribeMethodInvokerFactory.class.getClassLoader());
    }

    public SubscribeMethodInvokerFactory(ClassLoader parent) {
        super(parent);
        this.id = new AtomicInteger();
        this.sessionId = UUID.randomUUID().toString().replace("-", "");
    }

    public SubscribeMethodInvoker create(@NotNull Method method, @NotNull Class<?> eventClass) {
        String className = generateInvokerName(method.getDeclaringClass(), method, eventClass);
        try {
            Class<?> generatedClass = generateClass(className.replace('/', '.'), new SubscribeMethodClassGeneratorContext(className, method, eventClass));
            try {
                return (SubscribeMethodInvoker) generatedClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (ReflectiveOperationException exception) {
                LOGGER.error("Constructor of the generated class \"{}\" could not be called", generatedClass.getName(), exception);
                return null;
            }
        } catch (Throwable throwable) {
            LOGGER.error("{} for {}.{} could not be generated.", SubscribeMethodInvoker.class.getName(), method.getDeclaringClass().getName(), method.getName(), throwable);
            return null;
        }
    }

    @Override // net.labymod.api.volt.generator.ClassGenerator
    public byte[] generateClass(@Nullable ClassGenerator.Context context) {
        if (!(context instanceof SubscribeMethodClassGeneratorContext)) {
            throw new IllegalArgumentException();
        }
        SubscribeMethodClassGeneratorContext generatorContext = (SubscribeMethodClassGeneratorContext) context;
        Method method = generatorContext.method();
        Class<?> eventClass = generatorContext.eventClass();
        Class<?> listener = method.getDeclaringClass();
        String listenerName = Type.getInternalName(listener);
        String className = generatorContext.className();
        ClassBytecodeComposer composer = ClassBytecodeComposer.builder().withName(className.replace('.', '/')).addInterfaces(GENERATED_SUBSCRIBE_METHOD_INVOKER).build();
        composer.compose(writer -> {
            writeClass(writer, listenerName, eventClass, method);
        });
        return composer.getClassData();
    }

    private void writeClass(ClassWriter writer, String listenerName, Class<?> eventClass, Method method) {
        MethodVisitor methodVisitor = writer.visitMethod(1, "<init>", "()V", (String) null, (String[]) null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        MethodVisitor methodVisitor2 = writer.visitMethod(1, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)V", (String) null, (String[]) null);
        methodVisitor2.visitCode();
        methodVisitor2.visitVarInsn(25, 1);
        methodVisitor2.visitTypeInsn(192, listenerName);
        methodVisitor2.visitVarInsn(25, 2);
        methodVisitor2.visitTypeInsn(192, Type.getInternalName(eventClass));
        methodVisitor2.visitMethodInsn(182, listenerName, method.getName(), Type.getMethodDescriptor(method), false);
        methodVisitor2.visitInsn(177);
        methodVisitor2.visitMaxs(0, 0);
        methodVisitor2.visitEnd();
    }

    @NotNull
    private String generateInvokerName(@NotNull Class<?> listener, @NotNull Method method, @NotNull Class<?> eventClass) {
        return String.format(Locale.ROOT, "%s.%s.%s-%s-%s-%d", GENERATED_ASM_PACKAGE, this.sessionId, listener.getSimpleName(), method.getName(), eventClass.getSimpleName(), Integer.valueOf(this.id.incrementAndGet()));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext.class */
    private static final class SubscribeMethodClassGeneratorContext extends Record implements ClassGenerator.Context {

        @NotNull
        private final String className;

        @NotNull
        private final Method method;

        @NotNull
        private final Class<?> eventClass;

        private SubscribeMethodClassGeneratorContext(@NotNull String className, @NotNull Method method, @NotNull Class<?> eventClass) {
            this.className = className;
            this.method = method;
            this.eventClass = eventClass;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SubscribeMethodClassGeneratorContext.class), SubscribeMethodClassGeneratorContext.class, "className;method;eventClass", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->className:Ljava/lang/String;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->method:Ljava/lang/reflect/Method;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->eventClass:Ljava/lang/Class;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SubscribeMethodClassGeneratorContext.class), SubscribeMethodClassGeneratorContext.class, "className;method;eventClass", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->className:Ljava/lang/String;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->method:Ljava/lang/reflect/Method;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->eventClass:Ljava/lang/Class;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SubscribeMethodClassGeneratorContext.class, Object.class), SubscribeMethodClassGeneratorContext.class, "className;method;eventClass", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->className:Ljava/lang/String;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->method:Ljava/lang/reflect/Method;", "FIELD:Lnet/labymod/core/event/method/invoker/SubscribeMethodInvokerFactory$SubscribeMethodClassGeneratorContext;->eventClass:Ljava/lang/Class;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @NotNull
        public String className() {
            return this.className;
        }

        @NotNull
        public Method method() {
            return this.method;
        }

        @NotNull
        public Class<?> eventClass() {
            return this.eventClass;
        }
    }
}
