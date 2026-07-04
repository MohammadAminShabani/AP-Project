package divar.dto.request;

import java.util.List;

public class CreateAdvertisementRequest {

    private String title;

    private String description;

    private Long categoryId;

    private Long cityId;

    private Long price;

    private List<String> images;
}