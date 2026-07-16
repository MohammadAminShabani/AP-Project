package divar.entity;
import divar.enums.AdStatus;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long price;
    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdvertisementImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Advertisement() {


    }

    public Advertisement(String title, String description, Long price, User owner, Category category, City city) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.owner = owner;
        this.category = category;
        this.city = city;
        this.status = AdStatus.PENDING;
    }


    public void changeStatus(AdStatus newStatus) {
        this.status = newStatus;
    }

    public void addImage(AdvertisementImage image) {
        images.add(image);
        image.setAdvertisement(this);
    }

    public void removeImage(AdvertisementImage image) {
        images.remove(image);
        image.setAdvertisement(null);
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public AdStatus getStatus() { return status; }
    public void setStatus(AdStatus status) { this.status = status; }

    public List<AdvertisementImage> getImages() {
        return images;
    }
    public void setImages(List<AdvertisementImage> images) {
        this.images = images;
    }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
}