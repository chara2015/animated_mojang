package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import net.labymod.api.util.function.Functional;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/GLFWTransformer.class */
public class GLFWTransformer implements IClassTransformer {
    private static final String GLFW_NAME = "org/lwjgl/glfw/GLFW";
    private static final String WGL_NAME = "org/lwjgl/opengl/WGL";
    private static final String STB_IMAGE_RESIZE_NAME = "org/lwjgl/stb/STBImageResize";
    private static final String TINY_FILE_DIALOGS_NAME = "org/lwjgl/util/tinyfd/TinyFileDialogs";
    private static final String MEMORY_STACK_DESC = "Lorg/lwjgl/system/MemoryStack;";
    private static final boolean DEBUG = Boolean.getBoolean("net.labymod.debugging.asm");
    private static final String GLFW_REDIRECTOR_NAME = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector";
    private static final String GLFW_REDIRECTOR_NAME_CANONICAL = GLFW_REDIRECTOR_NAME.replace('/', '.');
    private static final String STB_IMAGE_RESIZE_REDIRECTOR_NAME = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/STBImageResizeRedirector";
    private static final String STB_IMAGE_RESIZE_REDIRECTOR_NAME_CANONICAL = STB_IMAGE_RESIZE_REDIRECTOR_NAME.replace('/', '.');
    private static final String TINY_FILE_DIALOGS_REDIRECTOR_NAME = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/TinyFileDialogsRedirector";
    private static final String TINY_FILE_DIALOGS_REDIRECTOR_NAME_CANONICAL = TINY_FILE_DIALOGS_REDIRECTOR_NAME.replace('/', '.');
    private static final Map<String, String> MAPPINGS = (Map) Functional.of(new HashMap(), mappings -> {
        registerMappings(mappings, GLFW_NAME, GLFW_REDIRECTOR_NAME, "nglfwCreateWindow(IIJJJ)J");
        registerMappings(mappings, GLFW_NAME, GLFW_REDIRECTOR_NAME, "glfwShowWindow(J)V");
        registerMappings(mappings, GLFW_NAME, GLFW_REDIRECTOR_NAME, "glfwDestroyWindow(J)V");
    });

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (GLFW_REDIRECTOR_NAME_CANONICAL.equals(name) || STB_IMAGE_RESIZE_REDIRECTOR_NAME_CANONICAL.equals(name) || TINY_FILE_DIALOGS_REDIRECTOR_NAME_CANONICAL.equals(name)) {
            return classData;
        }
        ClassReader reader = new ClassReader(classData);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);
        boolean patched = patchGLFWMethods(node);
        ClassWriter classWriter = ASMHelper.newClassWriter(reader, false);
        node.accept(classWriter);
        byte[] newClassData = classWriter.toByteArray();
        if (DEBUG && patched) {
            System.out.println("Transformed GLFW methods in " + name);
            ASMHelper.writeClassData(name, newClassData, true);
        }
        return newClassData;
    }

    private boolean patchGLFWMethods(ClassNode node) {
        boolean transformed = false;
        for (MethodNode method : node.methods) {
            ListIterator it = method.instructions.iterator();
            while (it.hasNext()) {
                AbstractInsnNode instruction = (AbstractInsnNode) it.next();
                if (instruction.getOpcode() == 184 && (instruction instanceof MethodInsnNode)) {
                    MethodInsnNode methodInstruction = (MethodInsnNode) instruction;
                    String methodClass = methodInstruction.owner + "." + methodInstruction.name + methodInstruction.desc;
                    String newMethodClass = MAPPINGS.get(methodClass);
                    if (newMethodClass != null) {
                        int dotIndex = newMethodClass.lastIndexOf(46);
                        methodInstruction.owner = newMethodClass.substring(0, dotIndex);
                        int methodNameEndIndex = newMethodClass.indexOf(40);
                        methodInstruction.name = newMethodClass.substring(dotIndex + 1, methodNameEndIndex);
                        methodInstruction.desc = newMethodClass.substring(methodNameEndIndex);
                        transformed = true;
                    } else if (methodInstruction.owner.equals(WGL_NAME) && methodInstruction.name.equals("wglGetCurrentContext") && methodInstruction.desc.equals("()J")) {
                        method.instructions.insertBefore(methodInstruction, new InsnNode(1));
                        methodInstruction.desc = "(Ljava/nio/IntBuffer;)J";
                        transformed = true;
                    } else if (methodInstruction.owner.equals(STB_IMAGE_RESIZE_NAME) && redirectSTBImageResize(methodInstruction)) {
                        transformed = true;
                    } else if (methodInstruction.owner.equals(TINY_FILE_DIALOGS_NAME)) {
                        methodInstruction.owner = TINY_FILE_DIALOGS_REDIRECTOR_NAME;
                        transformed = true;
                    } else if (methodInstruction.name.equals("mallocStack") && methodInstruction.desc.contains(MEMORY_STACK_DESC)) {
                        methodInstruction.name = "malloc";
                        transformed = true;
                    }
                }
            }
        }
        return transformed;
    }

    private boolean redirectSTBImageResize(MethodInsnNode methodInstruction) {
        boolean z;
        String name = methodInstruction.name;
        String desc = methodInstruction.desc;
        switch (name) {
            case "nstbir_resize_uint8":
            case "stbir_resize_uint8":
            case "nstbir_resize_uint8_generic":
            case "stbir_resize_uint8_generic":
            case "nstbir_resize_uint8_srgb_edgemode":
            case "stbir_resize_uint8_srgb_edgemode":
            case "nstbir_resize_uint16_generic":
            case "stbir_resize_uint16_generic":
            case "nstbir_resize_float_generic":
            case "stbir_resize_float_generic":
                z = true;
                break;
            default:
                z = false;
                break;
        }
        boolean absent = z;
        if (absent) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        if (name.equals("nstbir_resize_float") || name.equals("stbir_resize_float")) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        if (name.equals("nstbir_resize_uint8_srgb") && desc.equals("(JIIIJIIIIII)I")) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        if (name.equals("stbir_resize_uint8_srgb") && desc.endsWith(")Z")) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        if (name.equals("nstbir_resize") && desc.equals("(JIIIJIIIIIIIIIIIJ)I")) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        if (name.equals("stbir_resize") && desc.endsWith(")Z")) {
            methodInstruction.owner = STB_IMAGE_RESIZE_REDIRECTOR_NAME;
            return true;
        }
        return false;
    }

    private static void registerMappings(Map<String, String> mappings, String oldName, String newName, String method) {
        mappings.put(oldName + "." + method, newName + "." + method);
    }
}
