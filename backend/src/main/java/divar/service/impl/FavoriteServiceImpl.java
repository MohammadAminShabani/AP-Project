package divar.service.impl;

import divar.dto.response.AdvertisementResponse;
import divar.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Override
    public void add(Long userId, Long advertisementId) {

    }

    @Override
    public void remove(Long userId, Long advertisementId) {

    }

    @Override
    public List<AdvertisementResponse> getUserFavorites(Long userId) {
        return List.of();
    }
}