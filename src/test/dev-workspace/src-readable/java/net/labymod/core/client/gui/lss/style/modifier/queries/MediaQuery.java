package net.labymod.core.client.gui.lss.style.modifier.queries;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.client.gui.lss.style.modifier.Query;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.DocumentIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.ScreenIdentifier;
import net.labymod.api.util.collection.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/queries/MediaQuery.class */
public class MediaQuery implements Query {
    private static final Set<MediaIdentifier> IDENTIFIERS = new HashSet();
    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z&|_]*) *\\(*([a-zA-Z-]+): *([^)]+)\\)+");

    static {
        IDENTIFIERS.add(new DocumentIdentifier());
        IDENTIFIERS.add(new ScreenIdentifier());
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Query
    public String identifier() {
        return "media";
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Query
    public boolean matches(String key) {
        return key.equals(identifier());
    }

    private List<MediaRule.Requirement> getRequirements(String arguments) throws UnsupportedOperationException {
        List<MediaRule.Requirement> requirements = Lists.newArrayList();
        Matcher matcher = PATTERN.matcher(arguments);
        while (matcher.find()) {
            String typeString = matcher.group(1);
            String featureString = matcher.group(2);
            String value = matcher.group(3);
            MediaRule.RequirementType type = MediaRule.RequirementType.of((typeString == null || typeString.isEmpty()) ? "" : typeString);
            if (type == null) {
                throw new UnsupportedOperationException("Could not find a valid type for \"" + typeString + "\"");
            }
            MediaRule.Feature feature = MediaRule.Feature.of(featureString);
            if (feature == null) {
                throw new UnsupportedOperationException("Could not find a valid feature for \"" + featureString + "\"");
            }
            MediaRule.Requirement requirement = new MediaRule.Requirement(type, feature, value);
            requirements.add(requirement);
        }
        return requirements;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Query
    public MediaRule process(StyleRule rule) {
        MediaIdentifier identifier = getIdentifier(rule.getValue().split(" ")[0]);
        if (identifier == null) {
            throw new UnsupportedOperationException("Could not find a valid identifier for \"" + rule.getValue() + "\"");
        }
        List<MediaRule.Requirement> requirements = getRequirements(rule.getValue());
        return new MediaRule(rule, requirements, identifier);
    }

    private MediaIdentifier getIdentifier(String identifierString) {
        MediaIdentifier mediaIdentifier = null;
        Iterator<MediaIdentifier> it = IDENTIFIERS.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MediaIdentifier identifier = it.next();
            if (identifier.identifier().equalsIgnoreCase(identifierString)) {
                mediaIdentifier = identifier;
                break;
            }
        }
        return mediaIdentifier;
    }
}
