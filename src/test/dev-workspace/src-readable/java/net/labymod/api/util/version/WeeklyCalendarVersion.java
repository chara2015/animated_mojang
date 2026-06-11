package net.labymod.api.util.version;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/WeeklyCalendarVersion.class */
public class WeeklyCalendarVersion extends SemanticVersion {
    public WeeklyCalendarVersion(int year, int week, char patch) {
        super(year, week, patch - 'a');
    }

    public int getYear() {
        return this.major;
    }

    public int getWeek() {
        return this.minor;
    }

    public char getPatchCharacter() {
        return (char) (this.patch + 97);
    }

    @Override // net.labymod.api.util.version.SemanticVersion
    public String toString() {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(this.major);
        objArr[1] = this.minor < 10 ? "0" + this.minor : Integer.valueOf(this.minor);
        objArr[2] = Character.valueOf(getPatchCharacter());
        return String.format(locale, "%sw%s%s", objArr);
    }

    public static boolean isFormat(@NotNull String version) {
        int length = version.length();
        return length == 6 && Character.isDigit(version.charAt(0)) && version.contains("w") && !version.contains(".") && Character.isLetter(version.charAt(version.length() - 1));
    }
}
