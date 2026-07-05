package divar.service;

import divar.entity.Advertisement;
import divar.entity.Favorite;
import divar.entity.User;

import java.util.List;

public interface FavoriteService {

    Favorite addToFavorites(Favorite favorite);

    void removeFromFavorites(Long id);

    List<Favorite> getUserFavorites(User user);

    boolean exists(User user, Advertisement advertisement);

}