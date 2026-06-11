package net.labymod.api.annotation.processing.util;

import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/util/ProcessorUtil.class */
public final class ProcessorUtil {
    public static boolean shouldProcess(Object processor, Messager messager, Path path) {
        return shouldProcess(processor, messager, () -> {
            return path == null;
        });
    }

    public static boolean shouldProcess(Object processor, Messager messager, BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Processor \"" + String.valueOf(processor) + "\" could not be executed because of Single HotSwap. (If you should not use Single HotSwap, please report the error.)");
            return false;
        }
        return true;
    }
}
