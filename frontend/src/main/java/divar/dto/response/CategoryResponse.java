package divar.dto.response;

public class CategoryResponse {

    private Long id;
    private String name;
    private Long parentCategoryId;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id,
                            String name,
                            Long parentCategoryId) {

        this.id = id;
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public String toString() {
        return name;
    }
}