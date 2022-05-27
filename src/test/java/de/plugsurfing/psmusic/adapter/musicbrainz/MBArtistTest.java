package de.plugsurfing.psmusic.adapter.musicbrainz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
class MBArtistTest {
    private static final String RESOURCE_ID = "Q45188";
    private static final String RELATION_TYPE_WIKIDATA = "wikidata";
    private static final String RELATION_TYPE_WIKIPEDIA = "wikipedia";
    private static final String URL = "https://www.wikidata.org/wiki/" + RESOURCE_ID;
    private static final String INCORRECT_URL = "https://localhost:8080/" + RESOURCE_ID;

    @Test
    void getWikiResourceId() {
        var artist = MBArtist.builder()
                .relations(List.of(MBArtist.Relation.builder()
                        .type(RELATION_TYPE_WIKIDATA)
                        .url(MBArtist.Relation.Url.builder()
                                .resource(URL)
                                .build())
                        .build()))
                .build();

        var actual = artist.getWikiResourceId();

        assertEquals(RESOURCE_ID, actual);
    }

    @Test
    void getNoWikiResourceIdException_whenIncorrectUrl() {
        var artist = MBArtist.builder()
                .relations(List.of(MBArtist.Relation.builder()
                        .type(RELATION_TYPE_WIKIDATA)
                                .url(MBArtist.Relation.Url.builder()
                                        .resource(INCORRECT_URL)
                                        .build())
                        .build()))
                .build();

        assertThrows(NoWikidataResourceIdException.class, artist::getWikiResourceId);
    }

    @Test
    void getNoWikiResourceIdException_whenNoWikidataType() {
        var artist = MBArtist.builder()
                .relations(List.of(MBArtist.Relation.builder()
                        .type(RELATION_TYPE_WIKIPEDIA)
                        .build()))
                .build();

        assertThrows(NoWikidataResourceIdException.class, artist::getWikiResourceId);
    }

    @Test
    void getNullPointerException() {
        var artist = MBArtist.builder()
                .relations(List.of(MBArtist.Relation.builder()
                        .type(RELATION_TYPE_WIKIDATA)
                        .build()))
                .build();

        assertThrows(NullPointerException.class, artist::getWikiResourceId);
    }
}