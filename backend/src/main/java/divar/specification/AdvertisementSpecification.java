package divar.specification;

import divar.entity.Advertisement;
import divar.enums.AdStatus;
import org.springframework.data.jpa.domain.Specification;

public final class AdvertisementSpecification {

    private AdvertisementSpecification() {
    }

    public static Specification<Advertisement> hasKeyword(String keyword) {

        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();}

            String search = "%" + keyword.trim().toLowerCase() + "%";

            return cb.or(cb.like(cb.lower(root.get("title")), search),
                    cb.like(cb.lower(root.get("description")), search));};
    }

    public static Specification<Advertisement> hasCity(Long cityId) {

        return (root, query, cb) -> {
            if (cityId == null) {
                return cb.conjunction();}

            return cb.equal(root.get("city").get("id"), cityId);};
    }

    public static Specification<Advertisement> hasCategory(Long categoryId) {

        return (root, query, cb) -> {

            if (categoryId == null) {
                return cb.conjunction();}

            return cb.equal(root.get("category").get("id"), categoryId);};
    }

    public static Specification<Advertisement> hasStatus(AdStatus status) {

        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();}

            return cb.equal(root.get("status"), status);};
    }

    public static Specification<Advertisement> hasMinPrice(Double minPrice) {

        return (root, query, cb) -> {
            if (minPrice == null) {
                return cb.conjunction();}

            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);};
    }

    public static Specification<Advertisement> hasMaxPrice(Double maxPrice) {

        return (root, query, cb) -> {
            if (maxPrice == null) {
                return cb.conjunction();}

            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);};
    }
}