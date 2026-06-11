package net.labymod.api.models.addon.info.dependency;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/info/dependency/MavenDependency.class */
public class MavenDependency {
    private static final String SEPARATOR = "/";
    private final String repo;
    private String group;
    private String name;
    private String version;
    private String classifier;

    public MavenDependency(String repo, String name) {
        this.repo = repo;
        String[] split = name.split(":");
        if (split.length >= 3 && split.length <= 4) {
            this.group = split[0];
            this.name = split[1];
            this.version = split[2];
            if (split.length == 4) {
                this.classifier = split[3];
            }
        }
    }

    public MavenDependency(String repo, String group, String name, String version, String classifier) {
        this.repo = repo;
        this.group = group;
        this.name = name;
        this.version = version;
        this.classifier = classifier;
    }

    public String buildURL() {
        if (this.repo == null || this.repo.isEmpty() || this.group == null || this.name == null || this.version == null) {
            return null;
        }
        StringBuilder url = new StringBuilder(this.repo);
        if (!this.repo.endsWith(SEPARATOR)) {
            url.append('/');
        }
        appendFilePath(url);
        return url.toString();
    }

    private void appendFilePath(StringBuilder builder) {
        appendFileDictionary(builder);
        appendFileName(builder);
    }

    private void appendFileName(StringBuilder builder) {
        builder.append(this.name).append('-').append(this.version);
        if (this.classifier != null && !this.classifier.isEmpty()) {
            builder.append('-').append(this.classifier);
        }
        builder.append(".jar");
    }

    private void appendFileDictionary(StringBuilder builder) {
        builder.append(this.group.replace(".", SEPARATOR)).append(SEPARATOR);
        builder.append(this.name).append(SEPARATOR);
        builder.append(this.version).append(SEPARATOR);
    }

    public String buildFilePath() {
        StringBuilder builder = new StringBuilder();
        appendFilePath(builder);
        return builder.toString();
    }

    public String buildFileDictionary() {
        StringBuilder builder = new StringBuilder();
        appendFileDictionary(builder);
        return builder.toString();
    }

    public String buildFileName() {
        StringBuilder builder = new StringBuilder();
        appendFileName(builder);
        return builder.toString();
    }

    public String getRepo() {
        return this.repo;
    }

    public String getGroup() {
        return this.group;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getClassifier() {
        return this.classifier;
    }
}
