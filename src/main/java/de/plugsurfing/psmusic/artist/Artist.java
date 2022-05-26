package de.plugsurfing.psmusic.artist;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Builder
@Getter
public class Artist {
    private String mbid;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    private String description;
    private Set<Album> albums;

    @Builder
    @Getter
    public static final class Album {
        private String id;
        private String title;
        private String imageUrl;
    }
}
