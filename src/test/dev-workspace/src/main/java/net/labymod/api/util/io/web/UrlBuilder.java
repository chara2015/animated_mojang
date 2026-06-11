package net.labymod.api.util.io.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/UrlBuilder.class */
public class UrlBuilder {
    private final String endpoint;
    private final List<KeyValue<String>> parameters = new ArrayList();

    public UrlBuilder(String endpoint) {
        this.endpoint = endpoint;
    }

    public UrlBuilder addParameter(String key, Object value) {
        return addParameter(key, value.toString());
    }

    public UrlBuilder addParameter(String key, String value) {
        KeyValue<String> pair = new KeyValue<>(key, value);
        this.parameters.remove(pair);
        this.parameters.add(pair);
        return this;
    }

    public List<KeyValue<String>> getParameters() {
        return this.parameters;
    }

    public String getParameterValue(String key) {
        for (KeyValue<String> parameter : this.parameters) {
            if (parameter.getKey().equals(key)) {
                return parameter.getValue();
            }
        }
        return null;
    }

    public String build() {
        boolean hasParameters = this.endpoint.contains("?");
        StringBuilder url = new StringBuilder(this.endpoint);
        for (KeyValue<String> parameter : this.parameters) {
            Locale locale = Locale.ROOT;
            Object[] objArr = new Object[3];
            objArr[0] = hasParameters ? "&" : "?";
            objArr[1] = parameter.getKey();
            objArr[2] = parameter.getValue();
            url.append(String.format(locale, "%s%s=%s", objArr));
            hasParameters = true;
        }
        return url.toString();
    }
}
