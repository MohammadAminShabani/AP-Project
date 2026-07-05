package divar.service;

import divar.dto.response.AdvertisementResponse;

import java.util.List;

public interface FavoriteService {

    void add(Long userId, Long advertisementId);

    void remove(Long userId, Long advertisementId);

    List<AdvertisementResponse> getUserFavorites(Long userId);

}