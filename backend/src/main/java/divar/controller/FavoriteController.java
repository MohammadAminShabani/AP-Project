package divar.controller;

import divar.dto.response.AdvertisementResponse;
import divar.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public void add(@RequestParam Long userId,
                    @RequestParam Long advertisementId) {
        favoriteService.add(userId, advertisementId);
    }

    @DeleteMapping
    public void remove(@RequestParam Long userId,
                       @RequestParam Long advertisementId) {
        favoriteService.remove(userId, advertisementId);
    }

    @GetMapping("/{userId}")
    public List<AdvertisementResponse> getFavorites(@PathVariable Long userId) {
        return favoriteService.getUserFavorites(userId);
    }
}