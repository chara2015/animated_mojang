package net.labymod.api.client.gui.input;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/input/PreeditText.class */
public final class PreeditText extends Record {
    private final String fullText;
    private final int caretPosition;
    private final List<String> blocks;
    private final int focusedBlock;

    public PreeditText(String fullText, int caretPosition, List<String> blocks, int focusedBlock) {
        this.fullText = fullText;
        this.caretPosition = caretPosition;
        this.blocks = blocks;
        this.focusedBlock = focusedBlock;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PreeditText.class), PreeditText.class, "fullText;caretPosition;blocks;focusedBlock", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->fullText:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->caretPosition:I", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->blocks:Ljava/util/List;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->focusedBlock:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PreeditText.class), PreeditText.class, "fullText;caretPosition;blocks;focusedBlock", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->fullText:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->caretPosition:I", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->blocks:Ljava/util/List;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->focusedBlock:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PreeditText.class, Object.class), PreeditText.class, "fullText;caretPosition;blocks;focusedBlock", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->fullText:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->caretPosition:I", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->blocks:Ljava/util/List;", "FIELD:Lnet/labymod/api/client/gui/input/PreeditText;->focusedBlock:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String fullText() {
        return this.fullText;
    }

    public int caretPosition() {
        return this.caretPosition;
    }

    public List<String> blocks() {
        return this.blocks;
    }

    public int focusedBlock() {
        return this.focusedBlock;
    }

    @Nullable
    public static PreeditText fromCallback(int preeditSize, long preeditPtr, int blockCount, long blockSizesPtr, int focusedBlock, int caret) {
        if (preeditSize == 0) {
            return null;
        }
        int[] codepoints = readIntBuffer(preeditSize, preeditPtr);
        int[] blockSizes = readIntBuffer(blockCount, blockSizesPtr);
        StringBuilder fullText = new StringBuilder();
        List<String> blocks = new ArrayList<>(blockCount);
        int offset = 0;
        int convertedCaret = 0;
        for (int blockSize : blockSizes) {
            StringBuilder blockBuilder = new StringBuilder();
            for (int i = 0; i < blockSize; i++) {
                if (offset == caret) {
                    convertedCaret = fullText.length() + blockBuilder.length();
                }
                blockBuilder.appendCodePoint(codepoints[offset]);
                offset++;
            }
            String block = blockBuilder.toString();
            blocks.add(block);
            fullText.append(block);
        }
        if (offset == caret) {
            convertedCaret = fullText.length();
        }
        return new PreeditText(fullText.toString(), convertedCaret, Collections.unmodifiableList(blocks), focusedBlock);
    }

    private static int[] readIntBuffer(int size, long ptr) {
        IntBuffer buffer = MemoryUtil.memIntBuffer(ptr, size);
        int[] result = new int[size];
        buffer.get(result);
        return result;
    }
}
