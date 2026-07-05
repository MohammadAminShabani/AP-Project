package divar.service;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.enums.AdStatus;

import java.util.List;

public interface AdvertisementService {

    AdvertisementResponse create(Long ownerId, CreateAdvertisementRequest request);

    AdvertisementResponse findById(Long id);

    List<AdvertisementResponse> findAll();

    List<AdvertisementResponse> findByStatus(AdStatus status);

    List<AdvertisementResponse> findByCategory(Long categoryId);

    List<AdvertisementResponse> findByCity(Long cityId);

    List<AdvertisementResponse> findByOwner(Long ownerId);

    AdvertisementResponse update(Long id, UpdateAdvertisementRequest request);

    void delete(Long id);
}