package net.labymod.api.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/uri/URIParser.class */
public class URIParser {
    public static boolean isClickableScheme(String scheme) {
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    public static boolean isHttpUrl(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            return isClickableScheme(new URI(value).getScheme());
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static List<URI> parse(String text) {
        List<URI> foundURIs = new ArrayList<>();
        for (String word : text.split(" ")) {
            URI uri = parseSegment(word);
            if (uri != null) {
                foundURIs.add(uri);
            }
        }
        return foundURIs;
    }

    public static URI parseSegment(String text) {
        String protocol = null;
        String host = null;
        String path = null;
        int index = 0;
        int protocolIndex = text.indexOf("://");
        if (protocolIndex != -1) {
            protocol = text.substring(0, protocolIndex);
            index = protocolIndex + 3;
        }
        int hostIndex = text.indexOf(47, index);
        if (hostIndex != -1) {
            host = text.substring(index, hostIndex);
            path = text.substring(hostIndex);
        } else if (protocol != null) {
            host = text.substring(index);
        }
        if (protocol == null && path == null) {
            int dotIndex = text.indexOf(46, index);
            if (dotIndex == -1 || dotIndex == text.length() - 1) {
                return null;
            }
            host = text.substring(index);
        }
        if (path != null && path.endsWith(".")) {
            path = path.substring(0, path.length() - 1);
        }
        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }
        StringBuilder urlBuilder = new StringBuilder();
        if (protocol != null) {
            urlBuilder.append(protocol).append("://");
        }
        urlBuilder.append(host);
        if (path != null) {
            urlBuilder.append(path);
        }
        try {
            return new URI(urlBuilder.toString());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
