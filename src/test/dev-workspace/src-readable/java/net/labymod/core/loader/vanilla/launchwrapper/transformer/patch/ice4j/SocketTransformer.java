package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.ice4j;

import java.io.InputStream;
import java.net.Socket;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.core.util.Ice4jSocketFix;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/ice4j/SocketTransformer.class */
public class SocketTransformer implements IClassTransformer {
    private static final String NAME = "org.ice4j.pseudotcp.PseudoTcpSocket";
    private static final Type PSEUDO_TCP_SOCKET = Type.getType("Lorg/ice4j/pseudotcp/PseudoTcpSocket;");
    private static final Type PSEUDO_TCP_SOCKET_IMPL = Type.getType("Lorg/ice4j/pseudotcp/PseudoTcpSocketImpl;");
    private static final Type INPUT_STREAM = Type.getType(InputStream.class);
    private static final Type GETTER_INPUT_STREAM = Type.getMethodType(INPUT_STREAM, new Type[0]);
    private static final Type SOCKET = Type.getType(Socket.class);
    private static final Type ICE4J_SOCKET_FIX = Type.getType(Ice4jSocketFix.class);
    private static final Type GETTER_FIX_INPUT_STREAM = Type.getMethodType(INPUT_STREAM, new Type[]{SOCKET, INPUT_STREAM});

    public byte[] transform(String name, String s1, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (NAME.equals(name)) {
            return ASMHelper.transformClassData(classData, this::patch);
        }
        return classData;
    }

    private void patch(ClassNode classNode) {
        classNode.methods.add(ASMHelper.createMethod(1, "getInputStream", GETTER_INPUT_STREAM.getDescriptor(), null, null, methodNode -> {
            InsnListBuilder builder = InsnListBuilder.create();
            builder.addVar(25, 0);
            builder.addVar(25, 0);
            builder.addField(180, PSEUDO_TCP_SOCKET.getInternalName(), "socketImpl", PSEUDO_TCP_SOCKET_IMPL.getDescriptor());
            builder.addMethod(182, PSEUDO_TCP_SOCKET_IMPL.getInternalName(), methodNode.name, GETTER_INPUT_STREAM.getDescriptor());
            builder.addMethod(184, ICE4J_SOCKET_FIX.getInternalName(), methodNode.name, GETTER_FIX_INPUT_STREAM.getDescriptor());
            builder.addInstruction(176);
            methodNode.instructions.add(builder.build());
        }));
    }
}
