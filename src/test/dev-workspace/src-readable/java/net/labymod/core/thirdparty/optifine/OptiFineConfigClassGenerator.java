package net.labymod.core.thirdparty.optifine;

import java.util.function.Consumer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.thirdparty.optifine.OptiFineConfig;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.volt.generator.ClassBytecodeComposer;
import net.labymod.api.volt.generator.ClassGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/optifine/OptiFineConfigClassGenerator.class */
public final class OptiFineConfigClassGenerator extends ClassGenerator {
    private static final Logging LOGGER = Logging.create((Class<?>) OptiFineConfigClassGenerator.class);
    private final Consumer<OptiFineConfig> setterConsumer;

    OptiFineConfigClassGenerator(Consumer<OptiFineConfig> setterConsumer) {
        super((Class<?>) OptiFine.class);
        this.setterConsumer = setterConsumer;
    }

    @Override // net.labymod.api.volt.generator.ClassGenerator
    public Class<?> generateClass(@NotNull String name, @Nullable ClassGenerator.Context context) {
        Class<?> generatedClass = super.generateClass(name, context);
        try {
            this.setterConsumer.accept((OptiFineConfig) generatedClass.getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ReflectiveOperationException exception) {
            LOGGER.warn("OptiFineConfig could not be set, this could cause problems.", exception);
        }
        return generatedClass;
    }

    @Override // net.labymod.api.volt.generator.ClassGenerator
    public byte[] generateClass(@Nullable ClassGenerator.Context context) {
        ClassBytecodeComposer composer = ClassBytecodeComposer.builder().withName("net/labymod/core/generated/thirdparty/optifine/DefaultOptiFineConfig").withVersion(52).withAccess(17).addInterface("net/labymod/api/thirdparty/optifine/OptiFineConfig").build();
        composer.compose(this::writeClass);
        return composer.getClassData();
    }

    private void writeClass(ClassWriter writer) {
        buildConstructor(writer);
        buildMethod(writer, "hasShaders", "()Z", methodVisitor -> {
            methodVisitor.visitMethodInsn(184, PlatformEnvironment.isAncientOpenGL() ? "Config" : "net/optifine/Config", "isShaders", "()Z", false);
            methodVisitor.visitInsn(172);
            methodVisitor.visitMaxs(0, 0);
        });
        buildMethod(writer, "getActiveProgramId", "()I", methodVisitor2 -> {
            methodVisitor2.visitFieldInsn(178, "net/optifine/shaders/Shaders", "activeProgramID", "I");
            methodVisitor2.visitInsn(172);
            methodVisitor2.visitMaxs(0, 0);
        });
    }

    private void buildMethod(ClassWriter writer, String methodName, String descriptor, Consumer<MethodVisitor> visitorConsumer) {
        MethodVisitor method = writer.visitMethod(1, methodName, descriptor, (String) null, (String[]) null);
        method.visitCode();
        visitorConsumer.accept(method);
        method.visitEnd();
    }

    private void buildConstructor(ClassWriter writer) {
        MethodVisitor methodVisitor = writer.visitMethod(1, "<init>", "()V", (String) null, (String[]) null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
    }
}
