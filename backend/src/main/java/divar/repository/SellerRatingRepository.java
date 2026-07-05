package divar.repository;

import divar.entity.SellerRating;
import divar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRatingRepository extends JpaRepository<SellerRating, Long> {

    List<SellerRating> findBySeller(User seller);

}