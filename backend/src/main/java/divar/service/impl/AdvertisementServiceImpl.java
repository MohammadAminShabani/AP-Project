package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.SearchAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.entity.Advertisement;
import divar.entity.AdvertisementImage;
import divar.entity.Category;
import divar.entity.City;
import divar.entity.User;
import divar.repository.AdvertisementRepository;
import divar.repository.CategoryRepository;
import divar.repository.CityRepository;
import divar.repository.UserRepository;
import divar.enums.AdStatus;
import divar.service.AdvertisementService;
import divar.specification.AdvertisementSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;

    public AdvertisementServiceImpl(
            AdvertisementRepository advertisementRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            CityRepository cityRepository) {

        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * تبدیل Advertisement به AdvertisementResponse
     */
    private AdvertisementResponse mapToResponse(Advertisement advertisement) {

        AdvertisementResponse response = new AdvertisementResponse();

        response.setId(advertisement.getId());
        response.setTitle(advertisement.getTitle());
        response.setDescription(advertisement.getDescription());
        response.setPrice(advertisement.getPrice());

        if (advertisement.getCity() != null) {
            response.setCity(advertisement.getCity().getName());}

        if (advertisement.getCategory() != null) {
            response.setCategory(advertisement.getCategory().getName());}

        response.setStatus(advertisement.getStatus());

        if (advertisement.getOwner() != null) {

            response.setOwnerName(advertisement.getOwner().getFullName());

            response.setAverageRate(advertisement.getOwner().getAverageRating());
        }

        List<String> imageUrls = advertisement
                .getImages()
                .stream()
                .map(AdvertisementImage::getImageUrl)
                .collect(Collectors.toList());

        response.setImageUrls(imageUrls);
        return response;
    }
    @Override
    public AdvertisementResponse create(Long ownerId, CreateAdvertisementRequest request) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        Advertisement advertisement = new Advertisement(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                owner, category, city);

        advertisement.setStatus(AdStatus.PENDING);

        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);

        return mapToResponse(savedAdvertisement);
    }

    @Override
    public AdvertisementResponse findById(Long id) {

        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        return mapToResponse(advertisement);
    }

    @Override
    public List<AdvertisementResponse> findAll() {

        return advertisementRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public Page<AdvertisementResponse> search(SearchAdvertisementRequest request) {

        Sort sort;

        switch (request.getSort()) {

            case "priceAsc":
                sort = Sort.by("price").ascending();
                break;

            case "priceDesc":
                sort = Sort.by("price").descending();
                break;

            case "oldest":
                sort = Sort.by("id").ascending();
                break;

            case "ratingAsc":
                sort = Sort.by("owner.averageRating").ascending();
                break;

            case "ratingDesc":
                sort = Sort.by("owner.averageRating").descending();
                break;

            case "newest":
            default:
                sort = Sort.by("id").descending();
                break;
        }
        if (request.getPage() < 0) {
            request.setPage(0);
        }

        if (request.getSize() <= 0) {
            request.setSize(10);
        }

        if (request.getSize() > 50) {
            request.setSize(50);
        }
        Pageable pageable = PageRequest.of(
                request.getPage(), request.getSize(), sort);

        if (request.getMinPrice() != null
                && request.getMaxPrice() != null
                && request.getMinPrice() > request.getMaxPrice()) {

            throw new IllegalArgumentException(
                    "Minimum price cannot be greater than maximum price."
            );
        }
        Specification<Advertisement> specification =
                Specification.where(
                                AdvertisementSpecification.hasKeyword(request.getKeyword()))
                        .and(AdvertisementSpecification.hasCity(request.getCityId()))
                        .and(AdvertisementSpecification.hasCategory(request.getCategoryId()))
                        .and(AdvertisementSpecification.hasStatus(
                                request.getStatus() == null ? AdStatus.ACTIVE : request.getStatus()))                        .and(AdvertisementSpecification.hasMinPrice(request.getMinPrice()))
                        .and(AdvertisementSpecification.hasMaxPrice(request.getMaxPrice()));

        Page<Advertisement> advertisements =
                advertisementRepository.findAll(specification, pageable);

        return advertisements.map(this::mapToResponse);
    }
    @Override
    public List<AdvertisementResponse> findByStatus(AdStatus status) {

        return advertisementRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return advertisementRepository
                .findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByCity(Long cityId) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));

        return advertisementRepository
                .findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByOwner(Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return advertisementRepository
                .findByOwner(owner)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public AdvertisementResponse update(Long id, UpdateAdvertisementRequest request) {

        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setPrice(request.getPrice());

        Advertisement updatedAdvertisement =
                advertisementRepository.save(advertisement);

        return mapToResponse(updatedAdvertisement);
    }
    @Override
    public void delete(Long id) {

        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisementRepository.delete(advertisement);
    }
}