package net.labymod.core.loader.isolated;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/IsolatedLibrary.class */
public class IsolatedLibrary {
    private final String group;
    private final String name;
    private final String version;
    private final String classifier;
    private final String url;
    private final List<String> versions = new ArrayList();

    public IsolatedLibrary(String group, String name, String version, String classifier, String url) {
        this.name = name;
        this.group = group;
        this.version = version;
        this.classifier = classifier;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public String getGroup() {
        return this.group;
    }

    public String getVersion() {
        return this.version;
    }

    public String getClassifier() {
        return this.classifier;
    }

    public String getUrl() {
        return this.url;
    }

    public List<String> getVersions() {
        return this.versions;
    }

    public String getPath() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.group.replace(".", "/"));
        builder.append("/");
        builder.append(this.name);
        builder.append("/");
        builder.append(this.version);
        builder.append("/");
        builder.append(this.name).append("-").append(this.version);
        if (this.classifier != null) {
            builder.append("-").append(this.classifier);
        }
        builder.append(".jar");
        return builder.toString();
    }
}
