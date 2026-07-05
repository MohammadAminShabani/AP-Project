package divar.repository;

import divar.entity.Advertisement;
import divar.entity.AdvertisementImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementImageRepository extends JpaRepository<AdvertisementImage, Long> {

    List<AdvertisementImage> findByAdvertisement(Advertisement advertisement);

}