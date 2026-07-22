package divar.service.impl;

import divar.entity.Advertisement;
import divar.entity.Favorite;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.FavoriteRepository;
import divar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private User user;
    private Advertisement advertisement;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        advertisement = new Advertisement();
        advertisement.setId(10L);
    }

    @Test
    void add_ShouldSaveFavoriteSuccessfully() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(favoriteRepository.existsByUserAndAdvertisement(user, advertisement))
                .thenReturn(false);

        favoriteService.add(1L,10L);

        verify(favoriteRepository,times(1))
                .save(any(Favorite.class));
    }

    @Test
    void add_ShouldThrow_WhenAlreadyFavorite(){

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(favoriteRepository.existsByUserAndAdvertisement(user,advertisement))
                .thenReturn(true);

        assertThrows(BadRequestException.class,
                ()->favoriteService.add(1L,10L));

        verify(favoriteRepository,never())
                .save(any());
    }

    @Test
    void add_ShouldThrow_WhenUserNotFound(){

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->favoriteService.add(1L,10L));

        verify(favoriteRepository,never()).save(any());
    }

    @Test
    void remove_ShouldDeleteFavorite(){

        Favorite favorite = new Favorite(user,advertisement);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(favoriteRepository.findByUserAndAdvertisement(user,advertisement))
                .thenReturn(Optional.of(favorite));

        favoriteService.remove(1L,10L);

        verify(favoriteRepository).delete(favorite);
    }

    @Test
    void remove_ShouldThrow_WhenFavoriteNotFound(){

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(favoriteRepository.findByUserAndAdvertisement(user,advertisement))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->favoriteService.remove(1L,10L));

        verify(favoriteRepository,never()).delete(any());
    }

}