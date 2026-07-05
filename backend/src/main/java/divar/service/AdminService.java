package divar.service;

import divar.entity.Advertisement;
import divar.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    List<Advertisement> getAllAdvertisements();

    void approveAdvertisement(Long advertisementId);

    void rejectAdvertisement(Long advertisementId);

    void deleteAdvertisement(Long advertisementId);

    void blockUser(Long userId);

    void unblockUser(Long userId);

}