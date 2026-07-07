package divar.service.impl;

import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.service.AdminService;
import org.springframework.stereotype.Service;
import divar.repository.AdvertisementRepository;
import divar.repository.UserRepository;
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
                .map(user -> new UserResponse(
                        user.getId(), user.getFullName(), user.getUsername(),
                        user.getPhoneNumber(), user.getEmail(), user.getRole()))
                .toList();
    }

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

    }

    @Override
    public void rejectAdvertisement(Long advertisementId) {

    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {

    }

    @Override
    public void blockUser(Long userId) {

    }

    @Override
    public void unblockUser(Long userId) {

    }
}