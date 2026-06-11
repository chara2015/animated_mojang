package net.labymod.api.labynet.models;

import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/NameHistory.class */
public class NameHistory {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private boolean accurate;
    private String name;

    @SerializedName("changed_at")
    private String changedAt;
    private long changedAtTimestamp = -1;

    public String getName() {
        return this.name;
    }

    public String getChangedAtString() {
        return this.changedAt;
    }

    public boolean isAccurate() {
        return this.accurate;
    }

    public long getChangedAt() {
        if (this.changedAtTimestamp == -1) {
            try {
                Date date = DATE_FORMAT.parse(this.changedAt);
                this.changedAtTimestamp = date.getTime();
            } catch (NullPointerException | ParseException e) {
                this.changedAtTimestamp = 0L;
            }
        }
        return this.changedAtTimestamp;
    }
}
