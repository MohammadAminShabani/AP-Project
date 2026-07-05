package divar.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "advertisement_images")
public class AdvertisementImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "advertisement_id")
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