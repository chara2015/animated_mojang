package net.labymod.api.util.version.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionParser;
import net.labymod.api.util.version.SemanticVersion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/parser/SemanticVersionParser.class */
public class SemanticVersionParser implements VersionParser {
    private static final Pattern SEMANTIC_VERSION = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.(\\d+))?(?:-([a-zA-Z]+)(-?)(\\d+)?)?$");
    private static final int MAJOR_GROUP = 1;
    private static final int MINOR_GROUP = 2;
    private static final int PATCH_GROUP = 3;
    private static final int EXTENSION_GROUP = 4;
    private static final int SEPARATOR_GROUP = 5;
    private static final int BUILD_GROUP = 6;

    @Override // net.labymod.api.models.version.VersionParser
    public Version tryParse(String version) {
        Matcher matcher = SEMANTIC_VERSION.matcher(version);
        if (!matcher.matches()) {
            return null;
        }
        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        int patch = matcher.group(3) == null ? 0 : Integer.parseInt(matcher.group(3));
        String extension = matcher.group(4);
        String separator = matcher.group(5);
        int build = matcher.group(6) == null ? 0 : Integer.parseInt(matcher.group(6));
        return new SemanticVersion(major, minor, patch, extension, separator, build);
    }
}
