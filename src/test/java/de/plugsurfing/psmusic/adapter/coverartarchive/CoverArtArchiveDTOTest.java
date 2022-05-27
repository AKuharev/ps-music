package de.plugsurfing.psmusic.adapter.coverartarchive;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
class CoverArtArchiveDTOTest {
    private static final String IMAGE_URL = "http://coverartarchive.org/release-group/image.jpg";

    @Test
    void getFrontImageUrl() {
        var coverArtArchiveDTO = CoverArtArchiveDTO.builder()
                .images(List.of(CoverArtArchiveDTO.ImageInfo.builder()
                        .front(true)
                        .image(IMAGE_URL)
                        .build()))
                .build();

        var actual = coverArtArchiveDTO.getFrontImageUrl();

        assertEquals(IMAGE_URL, actual);
    }
}