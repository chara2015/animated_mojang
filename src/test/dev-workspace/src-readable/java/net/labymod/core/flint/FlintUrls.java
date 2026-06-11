package net.labymod.core.flint;

import net.labymod.core.loader.DefaultLabyModLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/FlintUrls.class */
public class FlintUrls {
    public static final String QUERY_SEARCH_PARAM = "search";
    public static final String QUERY_TAGS_PARAM = "tags";
    public static final String QUERY_PAGE_PARAM = "page";
    public static final String QUERY_SORT_PARAM = "sort";
    public static final String QUERY_DIRECTION_PARAM = "direction";
    private static final String BASE_URL = "https://flintmc.net";
    public static final String BRAND_ORGANIZATION_URL = "https://flintmc.net/brand/organization/%s.png";
    public static final String BRAND_MODIFICATION_URL = "https://flintmc.net/brand/modification/%s.png";
    private static final String API_URL = "https://flintmc.net/api/client-store";
    public static final String QUERY_URL = "https://flintmc.net/api/client-store/query/%s";
    public static final String GET_ORGANIZATION_URL = "https://flintmc.net/api/client-store/get-organization/%d";
    public static final String GET_TAGS_URL = "https://flintmc.net/api/client-store/get-tags";
    public static final String GET_PERMISSIONS = "https://flintmc.net/api/client-store/get-permissions";
    public static final String FETCH_JAR_URL = "https://flintmc.net/api/client-store/fetch-jar/%s/%s";
    public static final String FETCH_JAR_BY_HASH_URL = "https://flintmc.net/api/client-store/fetch-jar-by-hash/%s";
    public static final String ADD_DOWNLOAD_COUNT = "https://flintmc.net/api/client-store/add-download-count/%s";
    public static final String PROOF_MODIFICATION_HASHES = "https://flintmc.net/api/client-store/proof-modification-hashes/%s/%d";
    public static final String GET_MODIFICATION = "https://flintmc.net/api/client-store/get-modification/%s/%s";
    public static final String GET_MODIFICATION_DESCRIPTION = "https://flintmc.net/api/client-store/get-modification-description/%s";
    public static final String GET_MODIFICATION_RATINGS = "https://flintmc.net/api/client-store/get-modification-ratings/%s";
    public static final String GET_MODIFICATION_CHANGELOGS = "https://flintmc.net/api/client-store/get-modification-changelogs/%s/%s";
    public static final String GET_INDEX = "https://flintmc.net/api/client-store/get-index/%s";

    private FlintUrls() {
    }

    public static String getCurrentReleaseChannel() {
        return DefaultLabyModLoader.getInstance().getEffectiveReleaseChannel();
    }
}
