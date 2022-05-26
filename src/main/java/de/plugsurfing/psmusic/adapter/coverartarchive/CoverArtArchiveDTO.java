package de.plugsurfing.psmusic.adapter.coverartarchive;

import lombok.Getter;

import java.util.List;

/**
 * @author Andrei Kukharau
 * @since 0.0.1
 */
@Getter
public class CoverArtArchiveDTO {
    private List<ImageInfo> images;

    public String getFrontImageUrl() {
        return this.images.stream()
                .filter(ImageInfo::getFront)
                .map(ImageInfo::getImage)
                .findAny()
                .orElse("");
    }

    @Getter
    private static final class ImageInfo {
        private String image;
        private Boolean front;
    }
}
