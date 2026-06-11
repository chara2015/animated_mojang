package net.labymod.api.util.version.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionParser;
import net.labymod.api.util.version.WeeklyCalendarVersion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/parser/WeeklyCalendarVersionParser.class */
public class WeeklyCalendarVersionParser implements VersionParser {
    private static final Pattern WEEKLY = Pattern.compile("^(\\d{2,4})w(\\d{1,2})([a-z])?$");

    @Override // net.labymod.api.models.version.VersionParser
    public Version tryParse(String version) {
        Matcher matcher = WEEKLY.matcher(version);
        if (!matcher.matches()) {
            return null;
        }
        int year = Integer.parseInt(matcher.group(1));
        int week = Integer.parseInt(matcher.group(2));
        String suffix = matcher.group(3);
        if (week < 1 || week > 53 || suffix.length() > 1) {
            return null;
        }
        char patch = suffix.charAt(0);
        return new WeeklyCalendarVersion(year, week, patch);
    }
}
