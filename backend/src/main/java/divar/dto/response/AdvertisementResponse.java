package divar.dto.response;

import divar.enums.AdStatus;

import java.util.List;

public class AdvertisementResponse {

    private Long id;

    private String title;

    private String description;

    private Long price;

    private String city;

    private String category;

    private AdStatus status;

    private String ownerName;

    private Double averageRate;

    private List<String> imageUrls;
}