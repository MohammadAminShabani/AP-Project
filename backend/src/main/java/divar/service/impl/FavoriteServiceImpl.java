package divar.service.impl;

import divar.dto.response.AdvertisementResponse;
import divar.entity.AdvertisementImage;
import divar.service.FavoriteService;
import org.springframework.stereotype.Service;
import divar.entity.Advertisement;
import divar.entity.Favorite;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.FavoriteRepository;
import divar.repository.UserRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               UserRepository userRepository,
                               AdvertisementRepository advertisementRepository) {

        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }
    @Override
    public void add(Long userId, Long advertisementId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() ->
                        new ResourceNotFoundException("Advertisement not found"));

        if (favoriteRepository.existsByUserAndAdvertisement(user, advertisement)) {
            throw new BadRequestException("Advertisement already added to favorites");
        }

        Favorite favorite = new Favorite(user, advertisement);
        favoriteRepository.save(favorite);
    }

    @Override
    public void remove(Long userId, Long advertisementId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() ->
                        new ResourceNotFoundException("Advertisement not found"));

        Favorite favorite = favoriteRepository.findByUserAndAdvertisement(user, advertisement).orElseThrow(() ->
                        new ResourceNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementResponse> getUserFavorites(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUser(user)
                .stream()
                .map(favorite -> {

                    Advertisement advertisement = favorite.getAdvertisement();

                    AdvertisementResponse response = new AdvertisementResponse();

                    response.setId(advertisement.getId());
                    response.setTitle(advertisement.getTitle());
                    response.setDescription(advertisement.getDescription());
                    response.setPrice(advertisement.getPrice());

                    response.setStatus(advertisement.getStatus());

                    if (advertisement.getCity() != null)
                        response.setCity(advertisement.getCity().getName());

                    if (advertisement.getCategory() != null)
                        response.setCategory(advertisement.getCategory().getName());

                    if (advertisement.getOwner() != null) {
                        response.setOwnerName(advertisement.getOwner().getFullName());
                        response.setAverageRate(advertisement.getOwner().getAverageRating());}

                    response.setImageUrls(advertisement.getImages()
                                    .stream()
                                    .map(AdvertisementImage::getImageUrl)
                                    .toList());
                    return response;})
                .toList();
    }
}