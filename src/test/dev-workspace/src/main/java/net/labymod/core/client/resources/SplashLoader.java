package net.labymod.core.client.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/SplashLoader.class */
public class SplashLoader {
    public static final Logging LOGGER = Logging.getLogger();
    public static final SplashLoader INSTANCE = new SplashLoader();
    private static final Random RANDOM = new Random();
    private static final ResourceLocation SPLASHES = ResourceLocation.create(Namespaces.MINECRAFT, "texts/splashes.txt");
    private final List<String> list = Lists.newArrayList();
    private final Map<SplashDate, String> splashDates = new HashMap();
    private String splashToday = null;

    public void loadSplashDates() {
        registerSplashDate(12, 24, "Merry X-mas!");
        registerSplashDate(1, 1, "Happy new year!");
        registerSplashDate(10, 31, "OOoooOOOoooo! Spooky!");
    }

    public void registerSplashDate(int month, int day, String splashText) {
        synchronized (this.splashDates) {
            this.splashDates.put(new SplashDate(month, day), splashText);
        }
    }

    public String getSplashToday() {
        SplashDate dateToday = SplashDate.today();
        synchronized (this.splashDates) {
            String splashTextToday = this.splashDates.get(dateToday);
            if (splashTextToday != null) {
                return splashTextToday;
            }
            if (this.list.isEmpty()) {
                loadSplashesResource();
            }
            if (this.splashToday == null) {
                String strGenerateRandomSplash = generateRandomSplash();
                this.splashToday = strGenerateRandomSplash;
                return strGenerateRandomSplash;
            }
            return this.splashToday;
        }
    }

    public String generateRandomSplash() {
        String splashText;
        String username;
        boolean isEmpty = this.list.isEmpty();
        boolean isYou = !isEmpty && RANDOM.nextInt(this.list.size()) == 42;
        if (isYou && (username = Laby.labyAPI().minecraft().sessionAccessor().getSession().getUsername()) != null) {
            return username.toUpperCase(Locale.ROOT) + " IS YOU";
        }
        if (!isEmpty) {
            do {
                splashText = this.list.get(RANDOM.nextInt(this.list.size()));
            } while (splashText.hashCode() == 125780783);
            return splashText;
        }
        return "missingno";
    }

    private void loadSplashesResource() {
        BufferedReader bufferedreader;
        this.list.clear();
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(SPLASHES.openStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                String line = bufferedreader.readLine();
                if (line != null) {
                    String line2 = line.trim();
                    if (!line2.isEmpty()) {
                        this.list.add(line2);
                    }
                } else {
                    this.splashToday = null;
                    bufferedreader.close();
                    return;
                }
            } finally {
            }
            return;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/SplashLoader$SplashDate.class */
    public static class SplashDate {
        private final int month;
        private final int day;

        public SplashDate(int month, int day) {
            this.month = month;
            this.day = day;
        }

        public SplashDate(Calendar calendar) {
            this.month = calendar.get(2) + 1;
            this.day = calendar.get(5);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SplashDate that = (SplashDate) o;
            return this.month == that.month && this.day == that.day;
        }

        public int hashCode() {
            int result = this.month;
            return (31 * result) + this.day;
        }

        public static SplashDate today() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return new SplashDate(calendar);
        }
    }
}
