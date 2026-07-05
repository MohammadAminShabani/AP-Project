package divar.repository;

import divar.entity.Advertisement;
import divar.entity.Category;
import divar.entity.City;
import divar.entity.User;
import divar.enums.AdStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByOwner(User owner);

    List<Advertisement> findByCategory(Category category);

    List<Advertisement> findByCity(City city);

    List<Advertisement> findByStatus(AdStatus status);

    List<Advertisement> findByCategoryAndCity(Category category, City city);
}