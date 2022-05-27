package de.plugsurfing.psmusic.adapter.coverartarchive;

import java.util.NoSuchElementException;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
public class NoFrontAlbumImageException extends NoSuchElementException {
    public NoFrontAlbumImageException() {
        super("No front album image present");
    }
}
