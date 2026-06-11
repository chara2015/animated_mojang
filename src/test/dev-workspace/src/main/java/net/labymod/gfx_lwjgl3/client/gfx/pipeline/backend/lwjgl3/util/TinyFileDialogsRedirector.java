package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.ByteBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/TinyFileDialogsRedirector.class */
public final class TinyFileDialogsRedirector {
    private TinyFileDialogsRedirector() {
    }

    public static long ntinyfd_getGlobalChar(long aCharVariableName) {
        return TinyFileDialogs.ntinyfd_getGlobalChar(aCharVariableName);
    }

    public static String tinyfd_getGlobalChar(ByteBuffer aCharVariableName) {
        return TinyFileDialogs.tinyfd_getGlobalChar(aCharVariableName);
    }

    public static String tinyfd_getGlobalChar(CharSequence aCharVariableName) {
        return TinyFileDialogs.tinyfd_getGlobalChar(aCharVariableName);
    }

    public static int ntinyfd_getGlobalInt(long aIntVariableName) {
        return TinyFileDialogs.ntinyfd_getGlobalInt(aIntVariableName);
    }

    public static int tinyfd_getGlobalInt(ByteBuffer aIntVariableName) {
        return TinyFileDialogs.tinyfd_getGlobalInt(aIntVariableName);
    }

    public static int tinyfd_getGlobalInt(CharSequence aIntVariableName) {
        return TinyFileDialogs.tinyfd_getGlobalInt(aIntVariableName);
    }

    public static int ntinyfd_setGlobalInt(long aIntVariableName, int aValue) {
        return TinyFileDialogs.ntinyfd_setGlobalInt(aIntVariableName, aValue);
    }

    public static int tinyfd_setGlobalInt(ByteBuffer aIntVariableName, int aValue) {
        return TinyFileDialogs.tinyfd_setGlobalInt(aIntVariableName, aValue);
    }

    public static int tinyfd_setGlobalInt(CharSequence aIntVariableName, int aValue) {
        return TinyFileDialogs.tinyfd_setGlobalInt(aIntVariableName, aValue);
    }

    public static void tinyfd_beep() {
        TinyFileDialogs.tinyfd_beep();
    }

    public static int ntinyfd_notifyPopup(long aTitle, long aMessage, long aIconType) {
        return TinyFileDialogs.ntinyfd_notifyPopup(aTitle, aMessage, aIconType);
    }

    public static int tinyfd_notifyPopup(ByteBuffer aTitle, ByteBuffer aMessage, ByteBuffer aIconType) {
        return TinyFileDialogs.tinyfd_notifyPopup(aTitle, aMessage, aIconType);
    }

    public static int tinyfd_notifyPopup(CharSequence aTitle, CharSequence aMessage, CharSequence aIconType) {
        return TinyFileDialogs.tinyfd_notifyPopup(aTitle, aMessage, aIconType);
    }

    public static int ntinyfd_messageBox(long aTitle, long aMessage, long aDialogType, long aIconType, int aDefaultButton) {
        return TinyFileDialogs.ntinyfd_messageBox(aTitle, aMessage, aDialogType, aIconType, aDefaultButton);
    }

    public static boolean tinyfd_messageBox(ByteBuffer aTitle, ByteBuffer aMessage, ByteBuffer aDialogType, ByteBuffer aIconType, boolean aDefaultButton) {
        return TinyFileDialogs.tinyfd_messageBox(aTitle, aMessage, aDialogType, aIconType, aDefaultButton ? 1 : 0) != 0;
    }

    public static boolean tinyfd_messageBox(CharSequence aTitle, CharSequence aMessage, CharSequence aDialogType, CharSequence aIconType, boolean aDefaultButton) {
        return TinyFileDialogs.tinyfd_messageBox(aTitle, aMessage, aDialogType, aIconType, aDefaultButton ? 1 : 0) != 0;
    }

    public static int tinyfd_messageBox(ByteBuffer aTitle, ByteBuffer aMessage, ByteBuffer aDialogType, ByteBuffer aIconType, int aDefaultButton) {
        return TinyFileDialogs.tinyfd_messageBox(aTitle, aMessage, aDialogType, aIconType, aDefaultButton);
    }

    public static int tinyfd_messageBox(CharSequence aTitle, CharSequence aMessage, CharSequence aDialogType, CharSequence aIconType, int aDefaultButton) {
        return TinyFileDialogs.tinyfd_messageBox(aTitle, aMessage, aDialogType, aIconType, aDefaultButton);
    }

    public static long ntinyfd_inputBox(long aTitle, long aMessage, long aDefaultInput) {
        return TinyFileDialogs.ntinyfd_inputBox(aTitle, aMessage, aDefaultInput);
    }

    public static String tinyfd_inputBox(ByteBuffer aTitle, ByteBuffer aMessage, ByteBuffer aDefaultInput) {
        return TinyFileDialogs.tinyfd_inputBox(aTitle, aMessage, aDefaultInput);
    }

    public static String tinyfd_inputBox(CharSequence aTitle, CharSequence aMessage, CharSequence aDefaultInput) {
        return TinyFileDialogs.tinyfd_inputBox(aTitle, aMessage, aDefaultInput);
    }

    public static long ntinyfd_saveFileDialog(long aTitle, long aDefaultPathAndFile, int aNumOfFilterPatterns, long aFilterPatterns, long aSingleFilterDescription) {
        return TinyFileDialogs.ntinyfd_saveFileDialog(aTitle, aDefaultPathAndFile, aNumOfFilterPatterns, aFilterPatterns, aSingleFilterDescription);
    }

    public static String tinyfd_saveFileDialog(ByteBuffer aTitle, ByteBuffer aDefaultPathAndFile, PointerBuffer aFilterPatterns, ByteBuffer aSingleFilterDescription) {
        return TinyFileDialogs.tinyfd_saveFileDialog(aTitle, aDefaultPathAndFile, aFilterPatterns, aSingleFilterDescription);
    }

    public static String tinyfd_saveFileDialog(CharSequence aTitle, CharSequence aDefaultPathAndFile, PointerBuffer aFilterPatterns, CharSequence aSingleFilterDescription) {
        return TinyFileDialogs.tinyfd_saveFileDialog(aTitle, aDefaultPathAndFile, aFilterPatterns, aSingleFilterDescription);
    }

    public static long ntinyfd_openFileDialog(long aTitle, long aDefaultPathAndFile, int aNumOfFilterPatterns, long aFilterPatterns, long aSingleFilterDescription, int aAllowMultipleSelects) {
        return TinyFileDialogs.ntinyfd_openFileDialog(aTitle, aDefaultPathAndFile, aNumOfFilterPatterns, aFilterPatterns, aSingleFilterDescription, aAllowMultipleSelects);
    }

    public static String tinyfd_openFileDialog(ByteBuffer aTitle, ByteBuffer aDefaultPathAndFile, PointerBuffer aFilterPatterns, ByteBuffer aSingleFilterDescription, boolean aAllowMultipleSelects) {
        return TinyFileDialogs.tinyfd_openFileDialog(aTitle, aDefaultPathAndFile, aFilterPatterns, aSingleFilterDescription, aAllowMultipleSelects);
    }

    public static String tinyfd_openFileDialog(CharSequence aTitle, CharSequence aDefaultPathAndFile, PointerBuffer aFilterPatterns, CharSequence aSingleFilterDescription, boolean aAllowMultipleSelects) {
        return TinyFileDialogs.tinyfd_openFileDialog(aTitle, aDefaultPathAndFile, aFilterPatterns, aSingleFilterDescription, aAllowMultipleSelects);
    }

    public static long ntinyfd_selectFolderDialog(long aTitle, long aDefaultPath) {
        return TinyFileDialogs.ntinyfd_selectFolderDialog(aTitle, aDefaultPath);
    }

    public static String tinyfd_selectFolderDialog(ByteBuffer aTitle, ByteBuffer aDefaultPath) {
        return TinyFileDialogs.tinyfd_selectFolderDialog(aTitle, aDefaultPath);
    }

    public static String tinyfd_selectFolderDialog(CharSequence aTitle, CharSequence aDefaultPath) {
        return TinyFileDialogs.tinyfd_selectFolderDialog(aTitle, aDefaultPath);
    }

    public static long ntinyfd_colorChooser(long aTitle, long aDefaultHexRGB, long aDefaultRGB, long aoResultRGB) {
        return TinyFileDialogs.ntinyfd_colorChooser(aTitle, aDefaultHexRGB, aDefaultRGB, aoResultRGB);
    }

    public static String tinyfd_colorChooser(ByteBuffer aTitle, ByteBuffer aDefaultHexRGB, ByteBuffer aDefaultRGB, ByteBuffer aoResultRGB) {
        return TinyFileDialogs.tinyfd_colorChooser(aTitle, aDefaultHexRGB, aDefaultRGB, aoResultRGB);
    }

    public static String tinyfd_colorChooser(CharSequence aTitle, CharSequence aDefaultHexRGB, ByteBuffer aDefaultRGB, ByteBuffer aoResultRGB) {
        return TinyFileDialogs.tinyfd_colorChooser(aTitle, aDefaultHexRGB, aDefaultRGB, aoResultRGB);
    }
}
