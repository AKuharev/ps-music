package de.plugsurfing.psmusic.adapter.wikidata;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Getter
@Builder
public class WikidataEntityData {
    private static final String EN_SITE = "enwiki";

    private Map<String, Entity> entities;

    public String getEnTitle(String entityId) {
        return this.entities.get(entityId)
                .getSitelinks()
                .get(EN_SITE)
                .getTitle();
    }

    @Getter
    @Builder
    public static final class Entity {
        private Map<String, Sitelink> sitelinks;

        @Getter
        @Builder
        public static final class Sitelink {
            private String title;
        }
    }
}
