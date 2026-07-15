package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.SearchAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;

import divar.entity.*;
import divar.enums.AdStatus;

import divar.exception.BadRequestException;
import divar.exception.NotFoundException;
import divar.exception.ResourceNotFoundException;

import divar.repository.AdvertisementRepository;
import divar.repository.CategoryRepository;
import divar.repository.CityRepository;
import divar.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        return advertisementRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return advertisementRepository
                .findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByCity(Long cityId) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found"));

        return advertisementRepository
                .findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<AdvertisementResponse> findByOwner(Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Owner not found"));

        return advertisementRepository
                .findByOwner(owner)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    @Transactional
    public AdvertisementResponse update(Long id, UpdateAdvertisementRequest request) {

        Advertisement advertisement = getAdvertisement(id);

        checkOwner(advertisement);

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

        Advertisement updated = advertisementRepository.save(advertisement);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Advertisement advertisement = getAdvertisement(id);

        checkOwner(advertisement);

        advertisementRepository.delete(advertisement);
    }

    private Advertisement getAdvertisement(Long id) {

        return advertisementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Advertisement not found"));
    }

    private void checkOwner(Advertisement advertisement) {

        User currentUser =
                (User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        if (!advertisement.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to modify this advertisement");
        }
    }
}