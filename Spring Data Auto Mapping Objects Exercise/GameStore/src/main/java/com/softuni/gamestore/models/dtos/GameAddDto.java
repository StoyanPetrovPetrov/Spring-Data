package com.softuni.gamestore.models.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class GameAddDto {
    private String title;

    private String trailerUrl;

    private String imageThumbnailUrl;

    private BigDecimal size;

    private BigDecimal price;

    private String description;

    private String releaseDate;

    public GameAddDto(String title, BigDecimal price, BigDecimal size, String trailerUrl, String imageThumbnailUrl,
                      String description, String releaseDate) {
        this.title = title;
        this.trailerUrl = trailerUrl;
        this.imageThumbnailUrl = imageThumbnailUrl;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public GameAddDto() {
    }

    @Pattern(regexp = "^[A-Z]+(.){2,99}$", message = "Invalid title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Size(min = 11, max = 11, message = "Invalid trailer key.")
    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)+(.)*$", message = "Invalid Thumbnail URL")
    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    @DecimalMin(value = "0.0", message = "Invalid size. Must be greater than 0.")
    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @DecimalMin(value = "0.0", message = "Invalid price. Must be greater than 0.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Size(min = 20, message = "Invalid description length.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
