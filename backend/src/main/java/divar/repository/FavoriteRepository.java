package divar.repository;

import divar.entity.Advertisement;
import divar.entity.Favorite;
import divar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    boolean existsByUserAndAdvertisement(User user, Advertisement advertisement);

    Optional<Favorite> findByUserAndAdvertisement(User user, Advertisement advertisement);
    void delete(Favorite favorite);
}