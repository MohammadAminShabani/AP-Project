package divar.entity;

public class AdvertisementImage {

    private Long id;
    private String imageUrl;
    private Advertisement advertisement;

    public AdvertisementImage() {
    }

    public AdvertisementImage(String imageUrl, Advertisement advertisement) {
        this.imageUrl = imageUrl;
        this.advertisement = advertisement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    @Override
    public String toString() {
        return imageUrl;
    }
}