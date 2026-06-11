package net.labymod.api.util.io.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/UrlQuery.class */
public class UrlQuery {
    private final Builder builder;
    private final List<Param> params = new ArrayList();

    private UrlQuery(Builder builder) {
        this.builder = builder;
        for (Param param : builder.getParams()) {
            this.params.add(param.copy());
        }
    }

    public static UrlQuery create(Builder builder) {
        return new UrlQuery(builder);
    }

    public Object getParamValue(String key) {
        Param param = getParam(key);
        if (param == null) {
            return null;
        }
        return param.getValue();
    }

    public UrlQuery setParam(String key, Object value) {
        Param param = getParam(key);
        if (param == null) {
            throw new IllegalArgumentException("The param " + key + " has not been set by the builder");
        }
        param.setValue(value);
        return this;
    }

    public List<Param> getChangedParams(String... ignoredKeys) {
        List<String> ignored = new ArrayList<>();
        Collections.addAll(ignored, ignoredKeys);
        List<Param> changedParams = new ArrayList<>();
        for (Param param : this.params) {
            if (!ignored.contains(param.getKey()) && param.isChanged()) {
                changedParams.add(param);
            }
        }
        return changedParams;
    }

    public boolean isChanged(String... ignoredKeys) {
        return !getChangedParams(ignoredKeys).isEmpty();
    }

    public String build() {
        UrlBuilder urlBuilder = new UrlBuilder(this.builder.getUrl());
        for (Param param : this.params) {
            if (param.getValue() != null) {
                urlBuilder.addParameter(param.getKey(), param.getValue().toString());
            }
        }
        return urlBuilder.build();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlQuery query = (UrlQuery) o;
        return query.builder.url.equals(this.builder.url) && query.params.equals(this.params);
    }

    private Param getParam(String key) {
        for (Param param : this.params) {
            if (param.getKey().equals(key)) {
                return param;
            }
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/UrlQuery$Direction.class */
    public enum Direction {
        ASCENDING("asc"),
        DESCENDING("desc");

        private final String id;

        Direction(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return getId();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/UrlQuery$Builder.class */
    public static class Builder {
        private final String url;
        private final Set<Param> params = new HashSet();

        private Builder(String url) {
            this.url = url;
        }

        public static Builder of(String url) {
            return new Builder(url);
        }

        public static Builder of(String url, Object... args) {
            return new Builder(String.format(Locale.ROOT, url, args));
        }

        public String getUrl() {
            return this.url;
        }

        public Set<Param> getParams() {
            return this.params;
        }

        public Builder addParam(String key, Object defaultValue) {
            this.params.add(new Param(key, defaultValue));
            return this;
        }

        public UrlQuery build() {
            return UrlQuery.create(this);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/UrlQuery$Param.class */
    public static class Param {
        private final String key;
        private Object value;
        private boolean changed;

        private Param(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
            this.changed = true;
        }

        public boolean isChanged() {
            return this.changed;
        }

        public boolean equals(Object object) {
            if (object instanceof Param) {
                Param param = (Param) object;
                return this.key.equals(param.getKey()) && this.value.toString().equals(param.getValue().toString());
            }
            return false;
        }

        private Param copy() {
            return new Param(this.key, this.value);
        }
    }
}
