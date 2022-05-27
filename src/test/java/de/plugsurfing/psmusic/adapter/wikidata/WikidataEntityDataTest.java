package de.plugsurfing.psmusic.adapter.wikidata;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
class WikidataEntityDataTest {
    private static final String ENTITY_ID = "Q45188";
    private static final String EN_SITE = "enwiki";
    private static final String TITLE = "Coldplay";

    @Test
    void getEnTitle() {
        var wikidataEntityData = WikidataEntityData.builder()
                .entities(Map.of(ENTITY_ID, WikidataEntityData.Entity.builder()
                        .sitelinks(Map.of(EN_SITE, WikidataEntityData.Entity.Sitelink.builder()
                                .title(TITLE)
                                .build()))
                        .build()))
                .build();

        var actual = wikidataEntityData.getEnTitle(ENTITY_ID);

        assertEquals(TITLE, actual);
    }
}