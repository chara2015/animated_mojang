package net.labymod.api.volt.asm.util;

import java.io.PrintStream;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/util/ASMDebugger.class */
public final class ASMDebugger {
    private static final String OUT_NAME = "out";
    private static final String PRINTLN_NAME = "println";
    private static final Type SYSTEM_TYPE = Type.getType(System.class);
    private static final Type PRINT_STREAM_TYPE = Type.getType(PrintStream.class);
    private static final Type STRING_TYPE = Type.getType(String.class);
    private static final Type VOID_TYPE = Type.VOID_TYPE;
    private static final String PRINTLN_DESCRIPTOR = Type.getMethodDescriptor(VOID_TYPE, new Type[]{STRING_TYPE});

    public static InsnList buildPrintStatementInstructions(String content) {
        return InsnListBuilder.create().addField(178, SYSTEM_TYPE.getInternalName(), OUT_NAME, PRINT_STREAM_TYPE.getDescriptor()).addLDC(content).addMethod(182, PRINT_STREAM_TYPE.getInternalName(), PRINTLN_NAME, PRINTLN_DESCRIPTOR).build();
    }
}
