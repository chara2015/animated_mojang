package net.labymod.api.client.resources;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/IllegalResourceLocationException.class */
public class IllegalResourceLocationException extends RuntimeException {
    public IllegalResourceLocationException(String message) {
        super(escapeJavaString(message));
    }

    public IllegalResourceLocationException(String message, Throwable cause) {
        super(escapeJavaString(message), cause);
    }

    public IllegalResourceLocationException(Throwable cause) {
        super(cause);
    }

    private static String escapeJavaString(String value) {
        return "\"" + value + "\"";
    }
}
