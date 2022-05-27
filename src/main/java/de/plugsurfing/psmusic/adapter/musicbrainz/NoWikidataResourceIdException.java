package de.plugsurfing.psmusic.adapter.musicbrainz;

import java.util.NoSuchElementException;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
public class NoWikidataResourceIdException extends NoSuchElementException {
    public NoWikidataResourceIdException() {
        super("No wikidate resource id present");
    }
}
