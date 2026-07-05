package divar.service;

import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();

    List<AdvertisementResponse> getAllAdvertisements();

    void approveAdvertisement(Long advertisementId);

    void rejectAdvertisement(Long advertisementId);

    void deleteAdvertisement(Long advertisementId);

    void blockUser(Long userId);

    void unblockUser(Long userId);

}