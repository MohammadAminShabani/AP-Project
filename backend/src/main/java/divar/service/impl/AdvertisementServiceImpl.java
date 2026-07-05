package divar.service.impl;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.enums.AdStatus;
import divar.service.AdvertisementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Override
    public AdvertisementResponse create(Long ownerId, CreateAdvertisementRequest request) {
        return null;
    }

    @Override
    public AdvertisementResponse findById(Long id) {
        return null;
    }

    @Override
    public List<AdvertisementResponse> findAll() {
        return List.of();
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
}