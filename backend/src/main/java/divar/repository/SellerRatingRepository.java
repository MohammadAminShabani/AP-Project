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

    List<SellerRating> findByAdvertisement(Advertisement advertisement);

    Optional<SellerRating> findByBuyerAndAdvertisement(User buyer,
                                                       Advertisement advertisement);

    boolean existsByBuyerAndAdvertisement(User buyer,
                                          Advertisement advertisement);

    @Query("""
            select avg(r.score)
            from SellerRating r
            where r.seller.id = :sellerId
            """)
    Double getAverageRating(Long sellerId);

    @Query("""
            select count(r)
            from SellerRating r
            where r.seller.id = :sellerId
            """)
    Long getRatingCount(Long sellerId);
}