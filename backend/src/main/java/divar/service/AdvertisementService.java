package divar.service;

import divar.entity.Advertisement;
import divar.entity.Category;
import divar.entity.City;
import divar.entity.User;
import divar.enums.AdStatus;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Advertisement save(Advertisement advertisement);

    Optional<Advertisement> findById(Long id);

    List<Advertisement> findAll();

    List<Advertisement> findByOwner(User owner);

    List<Advertisement> findByCategory(Category category);

    List<Advertisement> findByCity(City city);

    List<Advertisement> findByStatus(AdStatus status);

    Advertisement update(Advertisement advertisement);

    void delete(Long id);

}