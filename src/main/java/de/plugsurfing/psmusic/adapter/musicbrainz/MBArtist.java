package de.plugsurfing.psmusic.adapter.musicbrainz;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class MBArtist {
    private static final String WIKIDATA_TYPE = "wikidata";

    private String id;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    private List<Relation> relations;
    private Set<ReleaseGroup> releaseGroups;

    public String getWikiResourceId() {
        return this.relations.stream()
                .filter(relation -> WIKIDATA_TYPE.equals(relation.getType()))
                .map(Relation::getUrl)
                .map(Relation.Url::getResource)
                .findAny()
                .flatMap(this::parseResourceId)
                .orElse("");
    }

    private Optional<String> parseResourceId(String url) {
        var pattern = Pattern.compile("^https?://[^/]+/[^/]+/([^/]+)");
        var matcher = pattern.matcher(url);

        return matcher.find()
                ? Optional.of(matcher.group(1))
                : Optional.empty();
    }

    @Getter
    @Builder
    public static final class Relation {
        private String type;
        private Url url;

        @Getter
        @Builder
        public static final class Url {
            private String resource;
        }
    }

    public record ReleaseGroup(String id, String title) {
    }
}
