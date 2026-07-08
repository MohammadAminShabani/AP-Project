package divar.repository;

import divar.entity.Advertisement;
import divar.entity.SellerRating;
import divar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SellerRatingRepository extends JpaRepository<SellerRating, Long> {

    List<SellerRating> findBySeller(User seller);
    Optional<SellerRating> findByBuyerAndAdvertisement(User buyer, Advertisement advertisement);
    List<SellerRating> findByAdvertisement(Advertisement advertisement);
    boolean existsByBuyerAndAdvertisement(User buyer, Advertisement advertisement);
    @Query("""
    select avg(r.score)
    from SellerRating r
    where r.seller.id=:sellerId
    """)
    Double getAverageRating(Long sellerId);

}