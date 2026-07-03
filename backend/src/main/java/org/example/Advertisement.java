package org.example;
import java.util.List;
import java.util.ArrayList;

public class Advertisement {
    private Long id;
    private String title;
    private String description;
    private double price;
    private AdStatus status;
    private List<String> images;

    private User owner;
    private Category category;
    private City city;

    public Advertisement() {
        this.images = new ArrayList<>();
    }

    // کانستراکتور با پارامترهای اصلی هنگام ثبت آگهی جدید
    public Advertisement(String title, String description, double price, User owner, Category category, City city) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.owner = owner;
        this.category = category;
        this.city = city;
        this.status = AdStatus.PENDING; // آگهی پس از ثبت در وضعیت در انتظار بررسی قرار می‌گیرد
        this.images = new ArrayList<>();
    }

    // --- متدهای رفتاری (Behavioral Methods) ---

    // تغییر وضعیت آگهی (مثلاً توسط مدیر یا در زمان فروش)
    public void changeStatus(AdStatus newStatus) {
        this.status = newStatus;
    }

    // افزودن یک تصویر جدید به لیست تصاویر آگهی
    public void addImage(String imageUrl) {
        this.images.add(imageUrl);
    }

    // --- Getters and Setters (کپسوله‌سازی) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public AdStatus getStatus() { return status; }
    public void setStatus(AdStatus status) { this.status = status; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
}