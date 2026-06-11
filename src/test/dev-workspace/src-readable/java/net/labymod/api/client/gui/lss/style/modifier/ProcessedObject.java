package net.labymod.api.client.gui.lss.style.modifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/ProcessedObject.class */
public class ProcessedObject {
    private final PostProcessor postProcessor;
    private final String rawValue;
    private final Object value;

    public ProcessedObject(PostProcessor postProcessor, String rawValue, Object value) {
        this.postProcessor = postProcessor;
        this.rawValue = rawValue;
        this.value = value;
    }

    public static ProcessedObject makeObject(PostProcessor postProcessor, String rawValue, Object value) {
        if (value instanceof ProcessedObject) {
            return makeObject(postProcessor, ((ProcessedObject) value).rawValue(), ((ProcessedObject) value).value());
        }
        if (value instanceof ProcessedObject[]) {
            ProcessedObject[] values = (ProcessedObject[]) value;
            return makeObject(postProcessor, values.length != 0 ? values[0].rawValue() : rawValue, values.length != 0 ? values[0].value() : null);
        }
        return new ProcessedObject(postProcessor, rawValue, value);
    }

    public PostProcessor postProcessor() {
        return this.postProcessor;
    }

    public String rawValue() {
        return this.rawValue;
    }

    public Object value() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
