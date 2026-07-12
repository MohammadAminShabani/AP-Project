package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.entity.*;
import divar.enums.AdStatus;
import divar.exception.CategoryNotFoundException;
import divar.exception.CityNotFoundException;
import divar.exception.NotFoundException;
import divar.repository.*;
import divar.service.AdvertisementService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public AdvertisementResponse create(CreateAdvertisementRequest request,
                                        User owner) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() ->
                        new CityNotFoundException("City not found"));

        Advertisement advertisement = new Advertisement();

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setPrice(request.getPrice());

        advertisement.setOwner(owner);
        advertisement.setCategory(category);
        advertisement.setCity(city);

        advertisement.setStatus(AdStatus.PENDING);

        Advertisement saved = advertisementRepository.save(advertisement);

        return mapToResponse(saved);
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
    public AdvertisementResponse update(Long id, UpdateAdvertisementRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    private AdvertisementResponse mapToResponse(Advertisement advertisement) {

        List<String> imageUrls = List.of();

        return new AdvertisementResponse(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getCity().getName(),
                advertisement.getCategory().getName(),
                advertisement.getStatus(),
                advertisement.getOwner().getUsername(),
                0.0,
                imageUrls
        );
    }
}