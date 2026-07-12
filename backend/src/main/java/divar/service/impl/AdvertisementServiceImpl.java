package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.SearchAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.exception.*;
import divar.entity.*;
import divar.enums.AdStatus;
import divar.repository.*;
import divar.service.AdvertisementService;
import divar.specification.AdvertisementSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public AdvertisementResponse create(CreateAdvertisementRequest request, User owner) {


        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

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
                .orElseThrow(() -> new NotFoundException("Advertisement not found"));

        return mapToResponse(advertisement);
    }

    @Override
    public List<AdvertisementResponse> findAll() {

        return advertisementRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public Page<AdvertisementResponse> search(SearchAdvertisementRequest request) {

        Sort sort;
        String sortType = request.getSort();

        if (sortType == null || sortType.isBlank()) {
            sortType = "newest";
        }
        switch (sortType) {

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
        Integer page = request.getPage();
        Integer size = request.getSize();

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size <= 0) {
            size = 10;
        }

        if (size > 50) {
            size = 50;
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        if (request.getMinPrice() != null
                && request.getMaxPrice() != null
                && request.getMinPrice() > request.getMaxPrice()) {

            throw new BadRequestException(
                    "Minimum price cannot be greater than maximum price.");
        }
        Specification<Advertisement> specification =
                Specification.where(
                                AdvertisementSpecification.hasKeyword(request.getKeyword()))
                        .and(AdvertisementSpecification.hasCity(request.getCityId()))
                        .and(AdvertisementSpecification.hasCategory(request.getCategoryId()))
                        .and(AdvertisementSpecification.hasStatus(
                                request.getStatus() == null ? AdStatus.ACTIVE : request.getStatus()))
                        .and(AdvertisementSpecification.hasMinPrice(request.getMinPrice()))
                        .and(AdvertisementSpecification.hasMaxPrice(request.getMaxPrice()));

        Page<Advertisement> advertisements =
                advertisementRepository.findAll(specification, pageable);

        return advertisements.map(this::mapToResponse);
    }
    @Override
    public List<AdvertisementResponse> findByStatus(AdStatus status) {
        return List.of();
    }

    @Override
    public List<AdvertisementResponse> findByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<AdvertisementResponse> findByCity(Long cityId) {
        return List.of();
    }

    @Override
    public List<AdvertisementResponse> findByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    @Transactional
    public AdvertisementResponse update(Long id, UpdateAdvertisementRequest request) {

        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Advertisement not found"));

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            advertisement.setTitle(request.getTitle());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            advertisement.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            advertisement.setPrice(request.getPrice());
        }

        if (request.getCategoryId() != null) {

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category not found"));

            advertisement.setCategory(category);
        }

        if (request.getCityId() != null) {

            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("City not found"));

            advertisement.setCity(city);
        }

        advertisementRepository.save(advertisement);

        return mapToResponse(advertisement);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Advertisement not found"));

        advertisementRepository.delete(advertisement);
    }
}