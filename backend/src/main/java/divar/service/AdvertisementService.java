package divar.service;

import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.SearchAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.entity.User;
import divar.enums.AdStatus;
import divar.repository.AdvertisementRepository;
import divar.repository.CategoryRepository;
import divar.repository.CityRepository;
import divar.repository.UserRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdvertisementService {



    AdvertisementResponse create( CreateAdvertisementRequest request , User owner);

    AdvertisementResponse findById(Long id);

    List<AdvertisementResponse> findAll();

    List<AdvertisementResponse> findByStatus(AdStatus status);

    List<AdvertisementResponse> findByCategory(Long categoryId);

    List<AdvertisementResponse> findByCity(Long cityId);

    List<AdvertisementResponse> findByOwner(Long ownerId);

    Page<AdvertisementResponse> search(SearchAdvertisementRequest request);

    AdvertisementResponse update(Long id, UpdateAdvertisementRequest request);

    void delete(Long id);
}