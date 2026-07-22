package divar.service.impl;

import divar.entity.Advertisement;
import divar.entity.User;
import divar.enums.AdStatus;
import divar.enums.UserStatus;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private User user;
    private Advertisement advertisement;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setStatus(UserStatus.ACTIVE);

        advertisement = new Advertisement();
        advertisement.setId(1L);
        advertisement.changeStatus(AdStatus.PENDING);
    }

    @Test
    void approveAdvertisement_ShouldApproveSuccessfully() {

        when(advertisementRepository.findById(1L))
                .thenReturn(Optional.of(advertisement));

        adminService.approveAdvertisement(1L);

        verify(advertisementRepository).save(advertisement);
    }

    @Test
    void approveAdvertisement_ShouldThrow_WhenAlreadyApproved() {

        advertisement.changeStatus(AdStatus.ACTIVE);

        when(advertisementRepository.findById(1L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                () -> adminService.approveAdvertisement(1L));

        verify(advertisementRepository, never()).save(any());
    }

    @Test
    void approveAdvertisement_ShouldThrow_WhenAdvertisementNotFound() {

        when(advertisementRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> adminService.approveAdvertisement(1L));
    }

    @Test
    void rejectAdvertisement_ShouldRejectSuccessfully() {

        when(advertisementRepository.findById(1L))
                .thenReturn(Optional.of(advertisement));

        adminService.rejectAdvertisement(1L);

        verify(advertisementRepository).save(advertisement);
    }

    @Test
    void deleteAdvertisement_ShouldDeleteSuccessfully() {

        when(advertisementRepository.findById(1L))
                .thenReturn(Optional.of(advertisement));

        adminService.deleteAdvertisement(1L);

        verify(advertisementRepository).save(advertisement);
    }

    @Test
    void blockUser_ShouldBlockSuccessfully() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.blockUser(1L);

        verify(userRepository).save(user);
    }

    @Test
    void blockUser_ShouldThrow_WhenAlreadyBlocked() {

        user.setStatus(UserStatus.BLOCKED);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class,
                () -> adminService.blockUser(1L));

        verify(userRepository, never()).save(any());
    }

    @Test
    void unblockUser_ShouldUnblockSuccessfully() {

        user.setStatus(UserStatus.BLOCKED);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.unblockUser(1L);

        verify(userRepository).save(user);
    }

    @Test
    void unblockUser_ShouldThrow_WhenAlreadyActive() {

        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class,
                () -> adminService.unblockUser(1L));

        verify(userRepository, never()).save(any());
    }

    @Test
    void blockUser_ShouldThrow_WhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> adminService.blockUser(1L));
    }
}