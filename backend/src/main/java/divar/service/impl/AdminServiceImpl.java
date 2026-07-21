package divar.service.impl;

import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.entity.Advertisement;
import divar.entity.User;
import divar.enums.AdStatus;
import divar.enums.UserStatus;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.service.AdminService;
import org.springframework.stereotype.Service;
import divar.repository.AdvertisementRepository;
import divar.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse (user.getId(), user.getFullName(), user.getUsername(),
                        user.getPhoneNumber(), user.getEmail(), user.getRole(),
                        user.getAverageRating(), user.getRatingCount(), user.getStatus())).toList();
    }
    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementResponse> getAllAdvertisements() {
        return advertisementRepository.findAll()
                .stream()
                .map(ad -> new AdvertisementResponse(
                        ad.getId(),
                        ad.getTitle(),
                        ad.getDescription(),
                        ad.getPrice(),
                        ad.getCity().getName(),
                        ad.getCategory().getName(),
                        ad.getStatus(),
                        ad.getOwner().getFullName(),
                        ad.getOwner().getId(),
                        ad.getOwner().getAverageRating(),
                        ad.getImages()
                                .stream()
                                .map(image -> image.getImageUrl())
                                .collect(Collectors.toList())
                ))
                .toList();
    }

    @Override
    public void approveAdvertisement(Long advertisementId) {

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found."));
        if (advertisement.getStatus() == AdStatus.ACTIVE) {
            throw new BadRequestException("Advertisement is already approved.");
        }
        advertisement.changeStatus(AdStatus.ACTIVE);

        advertisementRepository.save(advertisement);
    }

    @Override
    public void rejectAdvertisement(Long advertisementId) {

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found."));
        if (advertisement.getStatus() == AdStatus.REJECTED) {
            throw new BadRequestException("Advertisement is already rejected.");
        }
        advertisement.changeStatus(AdStatus.REJECTED);

        advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found."));
        if (advertisement.getStatus() == AdStatus.DELETED) {
            throw new BadRequestException("Advertisement is already deleted.");
        }
        advertisement.changeStatus(AdStatus.DELETED);

        advertisementRepository.save(advertisement);
    }

    @Override
    public void blockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new BadRequestException("User is already blocked.");
        }
        user.setStatus(UserStatus.BLOCKED);

        userRepository.save(user);
    }

    @Override
    public void unblockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new BadRequestException("User is already active.");
        }
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }
}