package de.plugsurfing.psmusic.artist;

import static java.lang.String.format;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(String mbid, Throwable cause) {
        super(format("Artist with mbid=%s not found", mbid), cause);
    }
}
