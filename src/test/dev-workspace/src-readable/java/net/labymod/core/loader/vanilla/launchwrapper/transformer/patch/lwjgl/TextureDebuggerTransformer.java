package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/TextureDebuggerTransformer.class */
public class TextureDebuggerTransformer implements IClassTransformer {
    private static final String TEXTURE_DEBUGGER_NAME = "net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.debug.TextureDebugger";
    private static final String GL11_NAME = "org.lwjgl.opengl.GL11";
    private static final String GL13_NAME = "org.lwjgl.opengl.GL13";
    private static final String ARBMULTITEXTURE_NAME = "org.lwjgl.opengl.ARBMultitexture";
    private static final String[] METHODS = {"glActiveTexture", "glEnable", "glDisable", "glGenTextures", "glBindTexture", "glDeleteTextures", "glTexImage2D", "glTexParameteri", "glTexParameteriv", "glTexParameterf", "glTexParameterfv", "glCopyTexSubImage2D", "glTexSubImage2D", "glMultiTexCoord2f", "glClientActiveTexture", "glClientActiveTextureARB", "glMultiTexCoord2fARB", "glActiveTextureARB", "glTexGeni", "glTexGeniv", "glTexGenf", "glTexGenfv"};

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("org.lwjgl") || name.equals(TEXTURE_DEBUGGER_NAME)) {
            return classData;
        }
        ClassReader reader = new ClassReader(classData);
        ClassWriter writer = new ClassWriter(reader, 1);
        ClassVisitor visitor = new TextureDebuggerClassVisitor(writer);
        reader.accept(visitor, 8);
        byte[] newClassData = writer.toByteArray();
        if (!Arrays.equals(classData, newClassData)) {
            writeClassData("debug." + name, newClassData);
        }
        return newClassData;
    }

    private static void writeClassData(String name, byte[] classData) {
        try {
            Path modifiedClassDataPath = Paths.get("labymod-neo/debug/asm/" + name.replace('.', '/').concat(".class"), new String[0]);
            Files.createDirectories(modifiedClassDataPath.getParent(), new FileAttribute[0]);
            Files.write(modifiedClassDataPath, classData, new OpenOption[0]);
        } catch (IOException e) {
        }
    }

    public static boolean containsMethod(String name) {
        for (String method : METHODS) {
            if (method.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String getMethodName(String name) {
        for (String method : METHODS) {
            if (method.equals(name)) {
                return method;
            }
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/TextureDebuggerTransformer$TextureDebuggerClassVisitor.class */
    private static class TextureDebuggerClassVisitor extends ClassVisitor {
        protected TextureDebuggerClassVisitor(ClassVisitor classVisitor) {
            super(ASMHelper.ASM_VERSION, classVisitor);
        }

        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new TextureDebuggerMethodVisitor(this.api, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/TextureDebuggerTransformer$TextureDebuggerMethodVisitor.class */
    private static class TextureDebuggerMethodVisitor extends MethodVisitor {
        public TextureDebuggerMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if ((owner.equals(TextureDebuggerTransformer.getName(TextureDebuggerTransformer.GL11_NAME)) || owner.equals(TextureDebuggerTransformer.getName(TextureDebuggerTransformer.GL13_NAME)) || owner.equals(TextureDebuggerTransformer.getName(TextureDebuggerTransformer.ARBMULTITEXTURE_NAME))) && TextureDebuggerTransformer.containsMethod(name)) {
                owner = TextureDebuggerTransformer.TEXTURE_DEBUGGER_NAME.replace('.', '/');
                name = TextureDebuggerTransformer.getMethodName(name);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    private static String getName(String name) {
        return name.replace('.', '/');
    }
}
